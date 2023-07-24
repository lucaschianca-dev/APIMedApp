package med.lucas.api.domain.user;

import jakarta.persistence.*;
import lombok.*;

@Entity(name = "users")
@Table(name = "User")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Getter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String login;
    private String senha;
}
