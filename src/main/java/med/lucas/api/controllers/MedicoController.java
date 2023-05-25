package med.lucas.api.controllers;

import jakarta.validation.Valid;
import med.lucas.api.DTO.CadastroMedico;
import med.lucas.api.medico.Medico;
import med.lucas.api.repository.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
