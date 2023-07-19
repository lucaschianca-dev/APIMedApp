package med.lucas.api.dto;

import med.lucas.api.endereco.Endereco;
import med.lucas.api.paciente.Paciente;
import med.lucas.api.paciente.Plano;

public record DadosDetalhamentoPaciente(
        Long id,
        String nome,
        String email,
        String telefone,
        String cpf,
        Plano plano,
        Endereco endereco
                                        ) {
    public DadosDetalhamentoPaciente(Paciente paciente) {
        this(
                paciente.getId(),
                paciente.getNome(),
                paciente.getEmail(),
                paciente.getTelefone(),
                paciente.getCpf(),
                paciente.getPlano(),
                paciente.getEndereco()
            );
    }
}
