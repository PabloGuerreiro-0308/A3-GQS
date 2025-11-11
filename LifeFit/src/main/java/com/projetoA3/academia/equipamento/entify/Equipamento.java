package com.projetoA3.academia.equipamento.entify;

import jakarta.persistence.*;
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
    private String grupoMuscular;

    @Min(0)
    @Column(nullable = false)
    private int quantidade;
}
