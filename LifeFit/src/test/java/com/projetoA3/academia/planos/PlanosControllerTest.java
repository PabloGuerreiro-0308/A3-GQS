package com.projetoA3.academia.planos;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.projetoA3.academia.planos.controller.PlanosController;
import com.projetoA3.academia.planos.entity.Planos;
import com.projetoA3.academia.planos.service.PlanosService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class PlanosControllerTest {

    private MockMvc mockMvc;

    @Mock
    private PlanosService planosService;

    @InjectMocks
    private PlanosController planosController;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(planosController).build();
        objectMapper = new ObjectMapper();
    }

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
        doNothing().when(planosService).deleteById(1L);

        mockMvc.perform(delete("/api/planos/1"))
                .andExpect(status().isNoContent());
    }
}