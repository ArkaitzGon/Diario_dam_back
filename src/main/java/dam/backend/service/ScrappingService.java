package dam.backend.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;
import dam.backend.domain.Cartelera;
import dam.backend.domain.Cine;
import dam.backend.domain.Pelicula;
import dam.backend.domain.Votos;
import dam.backend.dto.PeliculaCartelera;
import dam.backend.dto.ValoracionDTO;
import dam.backend.repository.CarteleraRepository;
import dam.backend.repository.CineRepository;
import dam.backend.repository.PeliculaRepository;
import dam.backend.repository.VotosRepository;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.*;

@Service
public class ScrappingService {
    @Autowired
    private CineRepository repository;

    @Autowired
    private PeliculaRepository peRepository;

    @Autowired
    private CarteleraRepository caRepository;

    @Autowired
    private VotosRepository voRepository;

    /**
     * Busca las peliculas haciendo webscraping
     * @return
     */
    public boolean carteleraScrapping(){
        List<Cine> cines = repository.findAll();
        if(cines == null || cines.isEmpty())
            return false;

        RemoteWebDriver driver = new FirefoxDriver(new FirefoxOptions());
        cines.forEach(cine ->{
            List<PeliculaCartelera> peliculas = new ArrayList<>();
            driver.get(cine.getUrl());
            Wait<WebDriver> wait = new WebDriverWait(driver, Duration.ofSeconds(2));
            Iterator<WebElement> iterator = driver
                .findElements(By.className("movie"))
                .iterator();
            //Itera sobre todas las peliculas del cine
            while (iterator.hasNext()) {
                WebElement element = iterator.next();
                wait.until(d -> element.isDisplayed());
                //Busca los horarios de la pelicula
                ArrayList<String> horario = buscarCine(
                        element.findElements(By.className("movie-showtimes-n"))
                );
                if(horario.size() > 0){
                    WebElement value =  element.findElement(By.className("mc-title"));
                    String titulo = element.findElement(By.className("mc-title")).getText();
                    //Busca la pelicula en la bd o en la web
                    Pelicula pelicula = buscarPelicula(
                        value.findElement(By.tagName("a")).getAttribute("href"),
                        titulo
                    );

                    if(pelicula != null){
                        Cartelera c = caRepository.save(new Cartelera(
                            null,
                            cine.getId(),
                            pelicula.getId(),
                            StringUtils.join(horario, ","),
                            fecha(true)//Cambiar a false para fecha de hoy, true mañana
                        ));
                    }
                }
            }
         });
        driver.quit();
        return true;
    }

    /**
     * Dado un elemento Web obtiene los horarios de pelicula
     * @param sessions
     * @return
     */
    private ArrayList<String> buscarCine(List<WebElement> sessions) {
        String tomorrow = fecha(true);

        Iterator<WebElement> iterator = sessions.iterator();
        ArrayList<String> horarios = new ArrayList();
        //Busca en todas las salas del cines
        while (iterator.hasNext()){
            WebElement session = iterator.next();
            //Comprueba que los horarios sean en español
            if(!session.findElement(By.className("mv-title"))
                    .getText().contains("(VOS"))
            {
                WebElement element = null;
                Iterator<WebElement> it = session.findElements(By.className("g-0")).iterator();
                while (it.hasNext()){
                    element = it.next();
                    String date = element.getAttribute("data-sess-date");
                    if(date != null && date.equals(tomorrow))
                        break;
                }
                //Obtiene los horarios de una sala
                if(element !=null ) {
                    String [] h = session.findElement(By.className("g-0"))
                            .findElement(By.className("sess-times"))
                            .getText().split("\n");
                    if(h.length > 0 && !h[0].isEmpty())
                        horarios.addAll(Arrays.asList(h));
                }
            }
        }
        //Ordena los horarios por si emiten la pelicula en mas de una sala
        horarios.sort( Comparator.naturalOrder());
        return horarios ;
    }

    /**
     * Busca la pelicula en la Base de datos y sino existe la busca en filmAffinity
     * @param url
     * @param titulo
     * @return
     */
    private Pelicula buscarPelicula(String url, String titulo) {
        int year = Calendar.getInstance().get(Calendar.YEAR) - 10;

        Pelicula pelicula;

        Optional<List<Pelicula>> optional = peRepository.findByTituloOrderByFechaEstrenoDesc(titulo);
        if(optional.isPresent() && optional.get().size() > 0){
            //if(String.valueOf(optional.get().get(0).getFechaEstreno()).equals(String.valueOf(year))
            if(optional.get().get(0).getFechaEstreno() >= year )
            return  optional.get().get(0);
        }
        else {
            HtmlPage page = scrape(url);
            pelicula = scrapePeliculaInfo(page);
            Votos votos = scrapeValoracion(page);
            if(pelicula != null){
                pelicula.setTitulo(titulo);

                pelicula.setResumen(pelicula.getResumen().substring(0, 100));
                pelicula = peRepository.save(pelicula);
                if(votos != null){
                    votos.setPeliculaId(pelicula.getId());
                    
                    //voRepository.save(votos);
                }
                return pelicula;
            }
        }
        return null;
    }

    /**
     * Dado un html obtiene la informacion de la pelicula
     * @param page
     * @return
     */
    public  Pelicula scrapePeliculaInfo(HtmlPage page) {
        try {
            Pelicula film = new Pelicula();

            //Obtiene la imagen
            DomElement imgContainer = page.getElementById("movie-main-image-container");
            if(imgContainer != null){
                if(!imgContainer.getElementsByTagName("img").isEmpty()){
                    HtmlElement img = imgContainer.getElementsByTagName("img").get(0);
                    //System.out.println("IMG::"+img.getAttribute("src"));
                    //System.out.println("IMGH::"+Integer.parseInt(img.getAttribute("height")));
                    //System.out.println("IMGW::"+Integer.parseInt(img.getAttribute("width")));
                    film.setImagen(img.getAttribute("src"));
                    film.setAltoImagen(Integer.parseInt(
                            img.getAttribute("height")
                    ));
                    film.setAnchoImagen(Integer.parseInt(
                            img.getAttribute("width")
                    ));
                }

            }

            //Año
            List<HtmlElement> filmInfo = page.getByXPath("//dl[@class='movie-info']");
            if(!filmInfo.isEmpty()){
                HtmlElement info = filmInfo.get(0);
                //Año
                String año = ((HtmlElement)info
                        .getFirstByXPath(".//dd[@itemprop='datePublished']"))
                        .getTextContent();
                //System.out.println("AÑO::"+año);
                film.setFechaEstreno(Integer.parseInt(año));
                //Reparto
                HtmlElement element =  info
                        .getFirstByXPath(".//dd[@class='card-cast-debug']");
                if(element != null){
                            List<HtmlElement>elements = element.getByXPath(".//div[@class='name']");
                    ArrayList<String> reparto = new ArrayList<>();
                    if(elements != null && !elements.isEmpty()){
                        for (HtmlElement e : elements) {
                            //System.out.println("REPARTO::"+e.getTextContent());
                            reparto.add(e.getTextContent());
                        }

                        film.setReparto(String.join(", ", reparto));
                    }
                }



                //Genero
                element =  info
                        .getFirstByXPath(".//dd[@class='card-genres']");
                if(element != null){
                    List<HtmlElement>elements = element.getByXPath(".//a");
                    ArrayList<String> reparto = new ArrayList<>();
                    if(elements != null && !elements.isEmpty()){
                        for (HtmlElement e : elements) {
                            //System.out.println("genero::"+e.getTextContent());
                            reparto.add(e.getTextContent());
                        }
                    }
                    film.setGenero(String.join(", ", reparto));
                    }

                //Resumen
                element = info
                    .getFirstByXPath(".//dd[@itemprop='description']");
                if(element != null){
                    //System.out.println("RESUMEN::"+element.getTextContent());
                	film.setResumen(element.getTextContent());
                }
            }
            return film;
        }catch (Exception e) {
            System.out.println("ERROR::");
            return null;
        }
    }

    /**
     * Obtiene la valoracion de la pelicula
     * @param page
     * @return  Devuelve un Voto
     */
    public Votos scrapeValoracion(HtmlPage page){
        Votos votos = new Votos();
        try {
            DomElement puntuacionContainer = page.getElementById("movie-rat-avg");
            if(puntuacionContainer != null){
                String puntuacion = puntuacionContainer
                    .getTextContent()
                    .replace(",",".")
                    .trim();
                votos.setPuntuacion(
                    Math.round(Float.parseFloat(puntuacion) / 2)
                );
            }
            return votos;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Realiza la peticion Http
     * @param url
     * @return
     */
    public HtmlPage scrape(String url ) {

        WebClient client = new WebClient();
        client.getOptions().setCssEnabled(false);
        client.getOptions().setJavaScriptEnabled(false);
        try {
            return client.getPage(url);
        } catch (Exception e) {
            return null;
        }
    }

    public String fecha(boolean mañana){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        if(mañana)
            calendar.add(Calendar.DATE, 1);

        return new SimpleDateFormat("yyyy-MM-dd")
                .format(calendar.getTime());
    }
}
