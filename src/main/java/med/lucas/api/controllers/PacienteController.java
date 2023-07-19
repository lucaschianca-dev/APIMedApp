package med.lucas.api.controllers;

import jakarta.validation.Valid;
import med.lucas.api.dto.CadastroPaciente;
import med.lucas.api.dto.DadosDetalhamentoPaciente;
import med.lucas.api.paciente.Paciente;
import med.lucas.api.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
}
