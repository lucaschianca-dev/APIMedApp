package med.lucas.api.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import med.lucas.api.endereco.Endereco;
import med.lucas.api.paciente.Paciente;
import med.lucas.api.paciente.Plano;

public record CadastroPaciente(
        @NotBlank
        String nome,
        @NotBlank
        @Email
        String email,
        @NotBlank
        String telefone,
        @NotBlank
        @Pattern(regexp = "\\d{4,6}")
        String cpf,
        @NotNull
        Plano plano,
        @NotNull
        @Valid
        DadosEndereco endereco
                                ) {
}
