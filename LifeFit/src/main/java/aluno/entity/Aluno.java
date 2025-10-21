package com.lifefit.academia.model;

import com.lifefit.academia.model.enums.StatusAluno;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "aluno")
public class Aluno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "numero_matricula", nullable = false, unique = true)
    private String numeroMatricula;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String telefone;

    @Column(nullable = false)
    private int idade;

    @Column(nullable = false, unique = true)
    private String cpf;

    private LocalDate dataVencimento;

    private String endereco;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusAluno status = StatusAluno.ATIVO;
}
