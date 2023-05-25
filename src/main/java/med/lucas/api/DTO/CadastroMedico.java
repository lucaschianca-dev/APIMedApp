package med.lucas.api.DTO;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import med.lucas.api.medico.Especialidade;

//Esta é um DTO
public record CadastroMedico(
        @NotBlank //Semelhante ao @NotNull, o @NotBlank verifica se o campo não é Nulo e nem VAZIO. APENAS PARA STRINGS
        String nome,
        @NotBlank
        @Email //Verifica se esta no formado de email.
        String email,
        @NotBlank
        String telefone,
        @NotBlank
        @Pattern(regexp = "\\d{4,6}")   //Verifica se o atributo necessita de um determinado padrão, como neste caso do CRM,
                                        // necessita ter de 4 a 6 números. passando o "\\d" (DÍGITO) e "{4,6}", ou seja, de 4 a 6 dígitos.
        String crm,
        @NotNull //Aqui é NotNull pois especialidade não é uma String, e sim um enum.
        Especialidade especialidade,
        @NotNull //Valida se não está nulo. Não pode ser NULO!
        @Valid   //Valida um DTO que tem outro DTO dentro dele. Como é o caso do DTO CadastroMedico que contém o DTO DadosEndereco dentro dele.
                 //Em @Valid, é necessário definir, também, no Controller.
                 // No nosso caso, no método de cadastrar médicos é necessário passar como argumento, além do @Request Body, o @Valid novamente!
        DadosEndereco endereco) {
}
