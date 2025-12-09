package com.projetoA3.academia.instrutor.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.projetoA3.academia.instrutor.entity.Instrutor;
import com.projetoA3.academia.instrutor.service.InstrutorService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(InstrutorController.class)
public class InstrutorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private InstrutorService instrutorService;

    @Autowired
    private ObjectMapper objectMapper;

    private Instrutor createInstrutor(Long id, String nome, String email) {
        return new Instrutor(id, nome, 30, email, "11900000000", "Lutas", Arrays.asList());
    }

    // --- Testes para GET /api/instrutores ---
    @Test
    void shouldListAllInstrutores() throws Exception {
        // Cenário
        Instrutor instrutor1 = createInstrutor(1L, "Ana", "ana@a.com");
        Instrutor instrutor2 = createInstrutor(2L, "Bruno", "bruno@b.com");
        List<Instrutor> instrutores = Arrays.asList(instrutor1, instrutor2);

        when(instrutorService.listarTodos()).thenReturn(instrutores);

        // Ação e Verificação
        mockMvc.perform(get("/api/instrutores"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].nome", is("Ana")))
                .andExpect(jsonPath("$[1].nome", is("Bruno")));

        verify(instrutorService, times(1)).listarTodos();
    }

    // --- Testes para POST /api/instrutores (criar) ---
    @Test
    void shouldCreateInstrutorSuccessfully() throws Exception {
        // Cenário
        Instrutor novoInstrutor = createInstrutor(null, "Carlos", "carlos@c.com");
        Instrutor instrutorSalvo = createInstrutor(3L, "Carlos", "carlos@c.com");

        when(instrutorService.salvar(any(Instrutor.class))).thenReturn(instrutorSalvo);

        // Ação e Verificação
        mockMvc.perform(post("/api/instrutores")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(novoInstrutor)))
                .andExpect(status().isOk()) // Nota: O método retorna 200 OK
                .andExpect(jsonPath("$.id", is(3)))
                .andExpect(jsonPath("$.nome", is("Carlos")));

        verify(instrutorService, times(1)).salvar(any(Instrutor.class));
    }

    @Test
    void shouldReturnBadRequestWhenCreationFails() throws Exception {
        // Cenário
        Instrutor instrutorComEmailDuplicado = createInstrutor(null, "Carlos", "carlos@c.com");

        when(instrutorService.salvar(any(Instrutor.class)))
                .thenThrow(new IllegalArgumentException("Email já cadastrado"));

        // Ação e Verificação
        mockMvc.perform(post("/api/instrutores")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(instrutorComEmailDuplicado)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("Email já cadastrado")));

        verify(instrutorService, times(1)).salvar(any(Instrutor.class));
    }


    // --- Testes para DELETE /api/instrutores/{id} (remover) ---
    @Test
    void shouldRemoveInstrutorSuccessfully() throws Exception {
        // Cenário
        Long id = 1L;
        doNothing().when(instrutorService).remover(id);

        // Ação e Verificação
        mockMvc.perform(delete("/api/instrutores/{id}", id))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Instrutor removido")));

        verify(instrutorService, times(1)).remover(id);
    }

    @Test
    void shouldReturnBadRequestWhenInstrutorNotFoundOnRemove() throws Exception {
        // Cenário
        Long id = 99L;
        doThrow(new IllegalArgumentException("Instrutor não encontrado")).when(instrutorService).remover(id);

        // Ação e Verificação
        mockMvc.perform(delete("/api/instrutores/{id}", id))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("Instrutor não encontrado")));

        verify(instrutorService, times(1)).remover(id);
    }

    // --- Testes para POST /api/instrutores/{id}/turmas (adicionarTurma) ---
    @Test
    void shouldAddTurmaSuccessfully() throws Exception {
        // Cenário
        Long id = 1L;
        String turma = "Musculação B";
        Instrutor instrutorAtualizado = createInstrutor(1L, "Ana", "ana@a.com");
        instrutorAtualizado.getTurmas().add(turma);

        when(instrutorService.adicionarTurma(eq(id), anyString())).thenReturn(instrutorAtualizado);

        // Ação e Verificação
        mockMvc.perform(post("/api/instrutores/{id}/turmas", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(turma)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.turmas", hasItem("Musculação B")));

        verify(instrutorService, times(1)).adicionarTurma(eq(id), anyString());
    }

    @Test
    void shouldReturnBadRequestWhenInstrutorNotFoundOnAdicionarTurma() throws Exception {
        // Cenário
        Long id = 99L;
        String turma = "Musculação B";

        when(instrutorService.adicionarTurma(eq(id), anyString()))
                .thenThrow(new IllegalArgumentException("Instrutor não encontrado"));

        // Ação e Verificação
        mockMvc.perform(post("/api/instrutores/{id}/turmas", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(turma)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("Instrutor não encontrado")));

        verify(instrutorService, times(1)).adicionarTurma(eq(id), anyString());
    }
}