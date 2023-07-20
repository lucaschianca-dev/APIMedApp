package med.lucas.api.dto;

import med.lucas.api.domain.endereco.Endereco;
import med.lucas.api.domain.medico.Especialidade;
import med.lucas.api.domain.medico.Medico;

public record DadosDetalhamentoMedico(
        Long id,
        String nome,
        String email,
        String telefone,
        String crm,
        Especialidade especialidade,
        Endereco endereco
                                    ) {

    public DadosDetalhamentoMedico(Medico medico) {
        this(
                medico.getId(),
                medico.getNome(),
                medico.getEmail(),
                medico.getTelefone(),
                medico.getCrm(),
                medico.getEspecialidade(),
                medico.getEndereco()
            );
    }
}