package com.projetoA3.academia.fichaTreino.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.projetoA3.academia.fichaTreino.tipoTreino.TipoTreino;
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

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "fichaTreino",nullable = false)
    private FichaTreino fichaTreino;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoTreino tipoTreino;

    @Column(nullable = false)
    private int ordem;

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
