package med.lucas.api.paciente;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.lucas.api.dto.CadastroPaciente;
import med.lucas.api.dto.DadosAtualizaPaciente;
import med.lucas.api.endereco.Endereco;

@Table(name = "Pacientes")
@Entity(name = "Paciente")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Paciente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String email;
    private String telefone;
    private String cpf;
    @Enumerated(EnumType.STRING)
    private Plano plano;
    @Embedded
    private Endereco endereco;
    private boolean ativo;

    public Paciente(CadastroPaciente cadastroPaciente) {
        this.nome = cadastroPaciente.nome();
        this.email = cadastroPaciente.email();
        this.telefone = cadastroPaciente.telefone();
        this.cpf = cadastroPaciente.cpf();
        this.plano = cadastroPaciente.plano();
        this.endereco = new Endereco(cadastroPaciente.endereco());
        this.ativo = true;
    }

    public void atualizaPaciente(DadosAtualizaPaciente dadosAtualizaPaciente) {
        if (dadosAtualizaPaciente.id() == null) {
        }else {
            if (dadosAtualizaPaciente.email() != null)
                this.email = dadosAtualizaPaciente.email();
            if (dadosAtualizaPaciente.telefone() != null)
                this.telefone = dadosAtualizaPaciente.telefone();
            if (dadosAtualizaPaciente.plano() != null)
                this.plano = dadosAtualizaPaciente.plano();
            if (dadosAtualizaPaciente.endereco() != null)
                this.endereco = dadosAtualizaPaciente.endereco();
        }
    }
}
