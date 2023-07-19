package med.lucas.api.dto;

import jakarta.validation.constraints.NotNull;
import med.lucas.api.endereco.Endereco;
import med.lucas.api.paciente.Plano;

public record DadosAtualizaPaciente(
        @NotNull
        Long id,
        String email,
        String telefone,
        Plano plano,
        Endereco endereco
                                    ) {
}
