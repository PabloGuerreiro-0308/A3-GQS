package com.projetoA3.academia.fichaTreino.controller;



import com.fasterxml.jackson.databind.ObjectMapper;
import com.projetoA3.academia.aluno.entity.Aluno;
import com.projetoA3.academia.fichaTreino.entity.FichaTreino;
import com.projetoA3.academia.fichaTreino.service.FichaTreinoService;
import com.projetoA3.academia.instrutor.entity.Instrutor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FichaTreinoController.class)
class FichaTreinoValidationTest {


    @Autowired
    private  MockMvc mockMvc;


    @MockitoBean
    private FichaTreinoService fichaTreinoService;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Deve retornar Erro 400 (Bad Request) se tentar criar ficha sem nome")
    void naoDeveCriarFichaInvalida() throws Exception {
        FichaTreino fichaErrada = new FichaTreino();
        fichaErrada.setNome("");
        fichaErrada.setProgressoTotal(10);

        // Configuração de dependências mínimas...
        fichaErrada.setAluno(new Aluno());
        fichaErrada.setInstrutorResponsavel(new Instrutor());

        mockMvc.perform(post("/api/fichas-treino")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(fichaErrada)))
                .andExpect(status().isBadRequest()); // Validação de rejeição
    }
}