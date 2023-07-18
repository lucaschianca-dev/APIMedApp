package med.lucas.api.controllers;

import jakarta.validation.Valid;
import med.lucas.api.DTO.CadastroMedico;
import med.lucas.api.DTO.DadosAtualizaMedico;
import med.lucas.api.DTO.DadosListagemMedico;
import med.lucas.api.medico.Medico;
import med.lucas.api.repository.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/medicos")
public class MedicoController {
    @Autowired
    private MedicoRepository repository;

    @PostMapping(value = "/cadastro")
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

    //Eu só quero listar os médicos que estão ativos. Logo, quero listar apenas os médicos que o atributo Ativo seja TRUE
    //O SPRING DATA tem um padrão de nomeclatura que se criarmos o método com esse padrão, ele consegue montar a consulta a query
    //e gerar um comando SQL da maneira que desejarmos
    @GetMapping(value = "/all")
    public Page<DadosListagemMedico> listarMedico(@PageableDefault(size = 5, page = 0, sort = "nome") Pageable paginacao) {
        return repository.findAllByAtivoTrue(paginacao).map(DadosListagemMedico::new);
    }

    @PutMapping(value = "/att")
    @Transactional
    public void atualizaMedico(@RequestBody @Valid DadosAtualizaMedico dados) {
        var medico = repository.getReferenceById(dados.id());
        medico.atualizaMedico(dados);
    }

    @DeleteMapping("/delete/{id}") //A anotação @PathVariable indica a que o ID passado como parâmetro no método excluirMedico será o caractere dinâmido do @DeleteMapping
    @Transactional
    public void excluirMedico(@PathVariable Long id) {
        repository.deleteById(id);
    }

    @DeleteMapping("/inactive/{id}")
    @Transactional
    public ResponseEntity tornaInativo(@PathVariable Long id) {
        var medico = repository.getReferenceById(id);
        medico.inativo();
        return ResponseEntity.ok().build();
    }

    @PutMapping("/active/{id}")
    @Transactional
    public ResponseEntity tornaAtivo(@PathVariable Long id) {
        var medico = repository.getReferenceById(id);
        medico.ativo();
        return ResponseEntity.ok().build();
    }
}
