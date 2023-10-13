package med.lucas.api.controllers;

import jakarta.validation.Valid;
import med.lucas.api.dto.DadosAutenticacao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AutenticacaoController {

    @Autowired
    private AuthenticationManager manager;
    @PostMapping
    public ResponseEntity efetuaLogin(@RequestBody @Valid DadosAutenticacao dados) {
        //Após receber o login e senha, devemos consultar o processo de autenticaç  ão!
        //Que está na classe "AutenticaçãoService"
        //Dessa forma, devemos chamar o método loaduserByUsername
        //Porém, não podemos chamar a classe Service diiretamente. Há outra classe que ao chamarmos, irá chamar nosso
        //service por baixo dos panos. Que é o "AuthenticationManager".

        var token = new UsernamePasswordAuthenticationToken(dados.login(), dados.senha());

        var authentication = manager.authenticate(token);

        return ResponseEntity.ok().build();
    }
}
