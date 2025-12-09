package com.projetoA3.academia.equipamento.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.projetoA3.academia.equipamento.entity.Equipamento;
import com.projetoA3.academia.equipamento.GrupoMuscular;
import com.projetoA3.academia.equipamento.repository.EquipamentoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class EquipamentoIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EquipamentoRepository repository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        repository.deleteAll(); // Limpa o banco antes de cada teste
    }

    @Test
    void deveCriarEquipamentoViaApi() throws Exception {
        Equipamento equipamento = Equipamento.builder()
                .nome("Leg Press")
                .marca("Matrix")
                .grupoMuscular(GrupoMuscular.INFERIORES)
                .quantidade(3)
                .build();

        mockMvc.perform(post("/api/equipamentos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(equipamento)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.nome", is("Leg Press")));
    }

    @Test
    void deveRetornarErroAoCriarComDadosInvalidos() throws Exception {
        // Envia JSON sem nome (violando @NotBlank)
        Equipamento invalido = Equipamento.builder()
                .marca("Matrix").quantidade(1).build();

        mockMvc.perform(post("/api/equipamentos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalido)))
                .andExpect(status().isBadRequest()); // O Spring valida o @Valid e retorna 400
    }

    @Test
    void deveListarTodosEquipamentos() throws Exception {
        Equipamento e1 = Equipamento.builder().nome("A").marca("M").quantidade(1).build();
        Equipamento e2 = Equipamento.builder().nome("B").marca("M").quantidade(1).build();
        repository.save(e1);
        repository.save(e2);

        mockMvc.perform(get("/api/equipamentos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    void deveRemoverEquipamentoCorretamente() throws Exception {
        Equipamento salvo = repository.save(Equipamento.builder().nome("Bike").marca("K").quantidade(1).build());

        mockMvc.perform(delete("/api/equipamentos/" + salvo.getId()))
                .andExpect(status().isOk())
                .andExpect(content().string("Equipamento removido"));
    }
}