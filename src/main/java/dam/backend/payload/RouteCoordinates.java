package dam.backend.payload;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RouteCoordinates {
    @NotBlank
    private String cine;

    @NotBlank
    private String posicion;
}

