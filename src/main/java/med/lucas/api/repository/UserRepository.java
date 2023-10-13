package med.lucas.api.repository;

import med.lucas.api.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserRepository extends JpaRepository<User, Long> {
    UserDetails findByLogin(String login); //Método que vai fazer a consulta de usuários no banco de dados
}
