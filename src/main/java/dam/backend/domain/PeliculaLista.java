package dam.backend.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity // declaramos la clase como entidad
@Table(name="PELICULAUSUARIO")
@IdClass(PeliculaListaId.class)
public class PeliculaLista {

	
	@Id
	@Column(name="PELICULAID")
	private int peliculaId;
	@Id
	@Column(name="LISTAID")
	private int listaId;
}
