package aluno.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity

@Table(name="aluno")
public class Aluno {
    @Id
    private Long id;

    @Column(name="numero_matricula",nullable=false,unique=true)
    private String numeroMatricula;
    @Column(nullable = false)
    private String nome;
    @Column(nullable = false)
    private String cpf;
    @Column(nullable = false)
    private String telefone;
    @Column(nullable = false)
    private int idade;
    private LocalDate dataVencimento;
    private String endereco;

}
