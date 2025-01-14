package dam.backend.scheduled;

import dam.backend.service.CarteleraService;
import dam.backend.service.ScrappingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class CarteleraScheduled {
    @Autowired
    CarteleraService carteleraService;
    @Autowired
    ScrappingService scrappingService;


    @Scheduled(cron = "0 0 10 * * *")
    //@Scheduled(cron = "0 */3 * * * *")
    public void carteleraScraping(){
        scrappingService.carteleraScrapping();
       carteleraService.deleteCarterleraAyer();
       System.out.println("Cartelera Ayer deleted");
    }
}
