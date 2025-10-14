package com.projetoA3.academia.fichaTreino.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ItemTreino {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ficha_treino_id",nullable = false)
    private FichaTreino fichaTreino;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoTreino TipoTreino;

    @Column(nullable = false)
    private int Ordem;

    @Column
    private String series;

    @Column
    private String repeticoes;

    @Column
    private String carga;

    @Column
    private String tempoDescanso;

    @Column(length = 500)
    private String observacoes;
}
