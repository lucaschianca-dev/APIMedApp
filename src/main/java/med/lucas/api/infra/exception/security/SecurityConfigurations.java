package med.lucas.api.infra.exception.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity  //Informa ao Spring que vamos personalizar as configurações de segurança
public class SecurityConfigurations {
    //Queremos informar que o processo de autenticação não é mais STATEFUL  e sim STATELESS

    @Bean //Devolve o objeto para o Spring
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.csrf().disable() //O JWT já protege de ataques CSRF, então seria redundande
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().build();

    }
}
