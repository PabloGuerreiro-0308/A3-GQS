package com.projetoA3.academia.equipamento.entity;

import com.projetoA3.academia.equipamento.GrupoMuscular;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EquipamentoValidationTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void deveValidarEquipamentoCorreto() {
        Equipamento equipamento = Equipamento.builder()
                .nome("Halter")
                .marca("Iron")
                .grupoMuscular(GrupoMuscular.SUPERIORES)
                .quantidade(10)
                .build();

        Set<ConstraintViolation<Equipamento>> violacoes = validator.validate(equipamento);
        assertTrue(violacoes.isEmpty(), "Não deve haver erros de validação");
    }

    @Test
    void deveFalharQuandoNomeEstiverEmBranco() {
        Equipamento equipamento = Equipamento.builder()
                .nome("") // Inválido
                .marca("Iron")
                .quantidade(10)
                .build();

        Set<ConstraintViolation<Equipamento>> violacoes = validator.validate(equipamento);
        assertFalse(violacoes.isEmpty());
        assertTrue(violacoes.stream().anyMatch(v -> v.getPropertyPath().toString().equals("nome")));
    }

    @Test
    void deveFalharQuandoQuantidadeForMenorQueZero() {
        // Testando a anotação @Min(0) da entidade (diferente da regra do Service)
        Equipamento equipamento = Equipamento.builder()
                .nome("Halter")
                .marca("Iron")
                .quantidade(-5) // Inválido
                .build();

        Set<ConstraintViolation<Equipamento>> violacoes = validator.validate(equipamento);
        assertFalse(violacoes.isEmpty());
        assertTrue(violacoes.stream().anyMatch(v -> v.getPropertyPath().toString().equals("quantidade")));
    }
}