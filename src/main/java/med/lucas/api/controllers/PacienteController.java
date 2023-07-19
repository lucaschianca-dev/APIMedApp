package med.lucas.api.controllers;

import jakarta.validation.Valid;
import med.lucas.api.dto.CadastroPaciente;
import med.lucas.api.dto.DadosAtualizaPaciente;
import med.lucas.api.dto.DadosDetalhamentoPaciente;
import med.lucas.api.dto.DadosListagemPaciente;
import med.lucas.api.paciente.Paciente;
import med.lucas.api.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
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

    @PutMapping(value = "/att")
    @Transactional
    public ResponseEntity atualizaPaciente(@RequestBody @Valid DadosAtualizaPaciente dadosAtualizaPaciente) {
        var result = pacienteRepository.getReferenceById(dadosAtualizaPaciente.id());
        result.atualizaPaciente(dadosAtualizaPaciente);
        return ResponseEntity.ok(new DadosDetalhamentoPaciente(result));
    }
}
