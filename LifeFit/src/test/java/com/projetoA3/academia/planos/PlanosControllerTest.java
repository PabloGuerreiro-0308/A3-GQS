package com.projetoA3.academia.planos.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.projetoA3.academia.planos.entity.Planos;
import com.projetoA3.academia.planos.service.PlanosService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PlanosController.class)
class PlanosControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PlanosService planosService;

    @Autowired
    private ObjectMapper objectMapper;

    private Planos criarPlano() {
        Planos plano = new Planos();
        plano.setId(1L);
        plano.setNome("Plano Gold");
        plano.setDescricao("Acesso total");
        plano.setValor(199.99);
        plano.setDuracaoMeses(12);
        return plano;
    }

    @Test
    void testGetAllPlanos() throws Exception {
        when(planosService.findAll()).thenReturn(Arrays.asList(criarPlano()));

        mockMvc.perform(get("/api/planos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nome").value("Plano Gold"));
    }

    @Test
    void testGetPlanoById() throws Exception {
        when(planosService.findById(1L)).thenReturn(Optional.of(criarPlano()));

        mockMvc.perform(get("/api/planos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Plano Gold"));
    }

    @Test
    void testCreatePlano() throws Exception {
        Planos plano = criarPlano();
        when(planosService.save(any(Planos.class))).thenReturn(plano);

        mockMvc.perform(post("/api/planos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(plano)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Plano Gold"));
    }

    @Test
    void testUpdatePlano() throws Exception {
        Planos plano = criarPlano();
        when(planosService.findById(1L)).thenReturn(Optional.of(plano));
        when(planosService.save(any(Planos.class))).thenReturn(plano);

        mockMvc.perform(put("/api/planos/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(plano)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Plano Gold"));
    }

    @Test
    void testDeletePlano() throws Exception {
        Mockito.doNothing().when(planosService).deleteById(1L);

        mockMvc.perform(delete("/api/planos/1"))
                .andExpect(status().isNoContent());
    }
}