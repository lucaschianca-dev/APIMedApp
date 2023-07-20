package med.lucas.api.controllers;

import jakarta.validation.Valid;
import med.lucas.api.dto.*;
import med.lucas.api.domain.paciente.Paciente;
import med.lucas.api.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping(value = "/pacientes")
public class PacienteController {

    @Autowired
    private PacienteRepository pacienteRepository;

    @PostMapping(value = "/cadastro")
    @Transactional
    public ResponseEntity cadastraPaciente(@RequestBody @Valid CadastroPaciente cadastroPaciente, UriComponentsBuilder uriComponentsBuilder) {
        var resultPaciente = new Paciente(cadastroPaciente);
        pacienteRepository.save(resultPaciente);

        var uri = uriComponentsBuilder.path("/pacientes/{id}").buildAndExpand(resultPaciente.getId()).toUri();

        return ResponseEntity.created(uri).body(new DadosDetalhamentoPaciente(resultPaciente));
    }

    @GetMapping(value = "/all")
    public ResponseEntity<Page<DadosListagemPaciente>> listaPaciente(@PageableDefault(size = 5, page = 0, sort = "nome") Pageable paginacao) {
        var resultPagePacientes = pacienteRepository.findAllByAtivoTrue(paginacao).map(DadosListagemPaciente::new);
        return ResponseEntity.ok(resultPagePacientes);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity buscaPaciente(@PathVariable Long id) {
        var resultPaciente = pacienteRepository.getReferenceById(id);
        return ResponseEntity.ok(new DadosDetalhamentoPaciente(resultPaciente));
    }

    @PutMapping(value = "/att")
    @Transactional
    public ResponseEntity atualizaPaciente(@RequestBody @Valid DadosAtualizaPaciente dadosAtualizaPaciente) {
        var result = pacienteRepository.getReferenceById(dadosAtualizaPaciente.id());
        result.atualizaPaciente(dadosAtualizaPaciente);
        return ResponseEntity.ok(new DadosDetalhamentoPaciente(result));
    }

    @DeleteMapping(value = "/inactive/{id}")
    @Transactional
    public ResponseEntity inativaPaciente(@PathVariable Long id) {
        var resultPaciente = pacienteRepository.getReferenceById(id);
        resultPaciente.inativo();
        return ResponseEntity.ok().build();
    }

    @PutMapping(value = "active/{id}")
    @Transactional
    public ResponseEntity ativaPaciente(@PathVariable Long id) {
        var resultPaciente = pacienteRepository.getReferenceById(id);
        resultPaciente.ativa();
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value = "/delete/{id}")
    @Transactional
    public ResponseEntity excluiPaciente(@PathVariable Long id) {
        pacienteRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
