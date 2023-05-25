package med.lucas.api.controllers;

import jakarta.validation.Valid;
import med.lucas.api.DTO.CadastroMedico;
import med.lucas.api.DTO.DadosListagemMedico;
import med.lucas.api.medico.Medico;
import med.lucas.api.repository.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/medicos")
public class MedicoController {
    @Autowired
    private MedicoRepository repository;

    @PostMapping
    @Transactional
    public void cadastraMedico(@RequestBody @Valid CadastroMedico dados) {
        repository.save(new Medico(dados));
    }

    //Este método irá armazenar os médicos em uma lista, mostrando alguns de seus atributos.
    //O tipo Pageable (Interface do Spring) irá paginar nosso GET
    //O tipo Page, além de devolver a lista com os dados dos médicos, ele vai devolver os dados da paginação também
    //Para controlar quantos registros queremos trazer, é necessário passar o parâmetro "?size=x" na URL
    //Podemos controlar, também, a página, passando o parâmetro "?size=X&page=Y"

    //Para controlar a ordem dos itens exibidos, passamos como parâmetro "?sort=atributo" e
    // "?sort=atributo,desc" para descrescente

    //Podemos mudar os nomes desses parâmetros no application.properties
    @GetMapping
    public Page<DadosListagemMedico> listarMedicos(@PageableDefault(size = 5, page = 0, sort = "nome") Pageable paginacao) {
        return repository.findAll(paginacao).map(DadosListagemMedico::new);
    }
}
