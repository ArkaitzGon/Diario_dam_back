package dam.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ValoracionDTO {

	private boolean votado;
	private int puntuacion;
	private int peliculaId;
	
	
	
}
