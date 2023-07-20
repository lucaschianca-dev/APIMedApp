package med.lucas.api.dto;

import med.lucas.api.domain.paciente.Paciente;
import med.lucas.api.domain.paciente.Plano;

public record DadosListagemPaciente(
        Long id,
        String nome,
        String email,
        String cpf,
        Plano plano
                                    ) {
    public DadosListagemPaciente(Paciente paciente) {
        this(
                paciente.getId(),
                paciente.getNome(),
                paciente.getEmail(),
                paciente.getCpf(),
                paciente.getPlano()
            );
    }
}
