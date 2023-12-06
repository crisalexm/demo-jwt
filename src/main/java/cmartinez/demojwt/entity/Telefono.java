package cmartinez.demojwt.entity;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Embeddable
@Data
public class Telefono {
    private String number;
    private String citycode;
    private String contrycode;
}
