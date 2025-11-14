package com.projetoA3.academia.equipamento.entity;

import jakarta.persistence.*;
import com.projetoA3.academia.equipamento.GrupoMuscular;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Table(name = "equipamento")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Equipamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String nome;

    @NotBlank
    @Column(nullable = false)
    private String marca;

    @Enumerated(EnumType.STRING)
    @Column(name = "grupo_muscular")
    private GrupoMuscular grupoMuscular;

    @Min(0)
    @Column(nullable = false)
    private int quantidade;
}
