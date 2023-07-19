package med.lucas.api.controllers;

import jakarta.validation.Valid;
import med.lucas.api.dto.CadastroMedico;
import med.lucas.api.dto.DadosAtualizaMedico;
import med.lucas.api.dto.DadosDetalhamentoMedico;
import med.lucas.api.dto.DadosListagemMedico;
import med.lucas.api.medico.Medico;
import med.lucas.api.repository.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/medicos")
public class MedicoController {

    @Autowired
    private MedicoRepository repository;

    //O método de cadastrar precisa devolver o código 201, a URI e tem que devolver no corpo da resposta uma representação do recurso recém criado.
    @PostMapping(value = "/cadastro")
    @Transactional
    public ResponseEntity cadastraMedico(@RequestBody @Valid CadastroMedico dados, UriComponentsBuilder uriBuilder) {
        var resultMedico = new Medico(dados);
        repository.save(resultMedico);

        var uri = uriBuilder.path("/medicos/{id}").buildAndExpand(resultMedico.getId()).toUri();

        return ResponseEntity.created(uri).body(new DadosDetalhamentoMedico(resultMedico));
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
    public ResponseEntity<Page<DadosListagemMedico>> listaMedico(@PageableDefault(size = 5, page = 0, sort = "nome") Pageable paginacao) {
        var resultPageMedicos = repository.findAllByAtivoTrue(paginacao).map(DadosListagemMedico::new);
        return ResponseEntity.ok(resultPageMedicos);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity buscaMedico(@PathVariable Long id) {
        var resultMedico = repository.getReferenceById(id);
        return ResponseEntity.ok(new DadosDetalhamentoMedico(resultMedico));
    }

    @PutMapping(value = "/att")
    @Transactional
    public ResponseEntity atualizaMedico(@RequestBody @Valid DadosAtualizaMedico dados) {
        var resultMedico = repository.getReferenceById(dados.id());
        resultMedico.atualizaMedico(dados);
        return ResponseEntity.ok(new DadosDetalhamentoMedico(resultMedico));
    }

    @DeleteMapping("/delete/{id}") //A anotação @PathVariable indica a que o ID passado como parâmetro no método excluirMedico será o caractere dinâmido do @DeleteMapping
    @Transactional
    public ResponseEntity excluiMedico(@PathVariable Long id) {
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/inactive/{id}")
    @Transactional
    public ResponseEntity inativaMedico(@PathVariable Long id) {
        var medico = repository.getReferenceById(id);
        medico.inativo();
        return ResponseEntity.ok().build();
    }

    @PutMapping("/active/{id}")
    @Transactional
    public ResponseEntity ativaMedico(@PathVariable Long id) {
        var medico = repository.getReferenceById(id);
        medico.ativo();
        return ResponseEntity.ok().build();
    }
}