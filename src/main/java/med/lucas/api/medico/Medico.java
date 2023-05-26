package med.lucas.api.medico;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.lucas.api.DTO.CadastroMedico;
import med.lucas.api.DTO.DadosAtualizaMedico;
import med.lucas.api.endereco.Endereco;

//Esta é a entidade JPA
@Table(name = "medicos")
@Entity(name = "Medico")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Medico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String email;
    private String telefone;
    private String crm;
    @Enumerated(EnumType.STRING)
    private Especialidade especialidade;
    @Embedded
    private Endereco endereco;
    boolean ativo;

    //Construtor que trabalha com os DTOs
    public Medico(CadastroMedico dados) {
        this.ativo = true;
        this.nome = dados.nome();
        this.email = dados.email();
        this.telefone = dados.telefone();
        this.crm = dados.crm();
        this.especialidade = dados.especialidade();
        this.endereco = new Endereco(dados.endereco());
    }

    public void atualizaMedico(DadosAtualizaMedico dados) {
        if (dados.id() == null) {
        } else {
            if (dados.email() != null)
                this.email = dados.email();
            if (dados.endereco() != null)
                this.endereco = dados.endereco();
            if (dados.telefone() != null)
                this.telefone = dados.telefone();
        }
    }

    public void inativo() {
        this.ativo = false;
    }
}
