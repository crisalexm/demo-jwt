package cmartinez.demojwt.entity;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TelefonoEntity {
    @NotBlank(message = "El número es requerido")
    private String number;
    @NotBlank(message = "El código de ciudad es requerido")
    private String citycode;
    @NotBlank(message = "El código de país es requerido")
    private String contrycode;
}
