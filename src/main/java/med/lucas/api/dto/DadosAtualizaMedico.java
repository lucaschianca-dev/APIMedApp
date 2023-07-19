package med.lucas.api.dto;

import jakarta.validation.constraints.NotNull;
import med.lucas.api.endereco.Endereco;

public record DadosAtualizaMedico(
        @NotNull Long id,
        String email,
        String telefone,
        Endereco endereco,
        boolean ativo
                                ) {
}
