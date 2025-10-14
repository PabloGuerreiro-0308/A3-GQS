package com.projetoA3.academia.fichaTreino.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity

public class FichaTreino {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

   // @ManyToOne(fetch = FetchType.LAZY)
    //@JoinColumn(name = "aluno_id", nullable = false)
    private String aluno;

    //@ManyToOne(fetch = FetchType.LAZY)
    //@JoinColumn(name = "instrutor_id", nullable = false)
    private String instrutorResponsavel;

    @Column
    private LocalDateTime dataUltimoTreino;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusFicha status;

    @Column
    private String objetivo;

    @Column(nullable = false)
    private String sequencia;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime dataCriacao;

    @UpdateTimestamp
    private LocalDateTime dataUltimaModificacao;

    @Column(nullable = false)
    private int progressoAtual = 0;

    @Column(nullable = false)
    private int progressoTotal;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoTreino proximoTreino;

    @OneToMany(mappedBy = "fichaDeTreino", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<ItemTreino> itensTreino = new ArrayList<>();

}