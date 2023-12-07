package cmartinez.demojwt.entity;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
public class UsuarioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;


    @NotBlank(message = "El nombre es requerido")
    @Column(name = "name")
    private String name;

    @NotBlank(message = "El email es requerido")
    private String email;

    @NotBlank(message = "El password es requerido")
    @Column(name = "password")
    private String password;

    @NotEmpty(message = "La lista de teléfonos no puede estar vacía")
    @Valid
    @ElementCollection
    @CollectionTable(name = "telefono", joinColumns = @JoinColumn(name = "user_id"))
    private List<TelefonoEntity> phones;

    @Column(name = "created")
    private Date created;

    @Column(name = "modified")
    private Date modified;

    @Column(name = "last_login")
    @JsonProperty("last_login")
    private Date lastLogin;

    @Column(name = "token")
    private String token;

    @Column(name = "isactive")
    @JsonProperty("isactive")
    private boolean IsActive;
}

