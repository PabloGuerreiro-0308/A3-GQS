package com.projetoA3.academia.fichaTreino.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.projetoA3.academia.aluno.entity.Aluno;
import com.projetoA3.academia.equipamento.entity.Equipamento;
import com.projetoA3.academia.fichaTreino.entity.FichaTreino;
import com.projetoA3.academia.fichaTreino.entity.ItemTreino;
import com.projetoA3.academia.fichaTreino.service.FichaTreinoService;
import com.projetoA3.academia.fichaTreino.status.StatusFicha;
import com.projetoA3.academia.fichaTreino.tipoTreino.TipoTreino;
import com.projetoA3.academia.instrutor.entity.Instrutor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FichaTreinoController.class)
class FichaTreinoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private FichaTreinoService fichaTreinoService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("POST /api/fichas-treino -> Deve criar ficha e retornar 201 Created")
    void deveCriarFichaComSucesso() throws Exception {
        FichaTreino fichaRetorno = criarFichaMock(10L, "Treino Teste", 0);

        when(fichaTreinoService.criarFicha(any(FichaTreino.class))).thenReturn(fichaRetorno);

        String jsonRequest = """
            {
                "nome": "Treino Teste",
                "aluno": { "id": 1 },
                "instrutorResponsavel": { "id": 1 },
                "status": "ATIVA",
                "sequencia": "A,B",
                "progressoTotal": 10,
                "proximoTreino": "A"
            }
        """;

        mockMvc.perform(post("/api/fichas-treino")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(10))
                .andExpect(jsonPath("$.aluno.nome").value("Pablo Souza"));
    }

    @Test
    @DisplayName("GET /api/fichas-treino/{id} -> Deve retornar a ficha completa")
    void deveBuscarFichaPorId() throws Exception {
        Long id = 1L;
        FichaTreino fichaBanco = criarFichaMock(id, "Ficha Recuperada", 5);

        when(fichaTreinoService.buscarFichaPorId(eq(id), any())).thenReturn(fichaBanco);

        mockMvc.perform(get("/api/fichas-treino/{id}", id)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nome").value("Ficha Recuperada"))
                .andExpect(jsonPath("$.progressoAtual").value(5));
    }

    @Test
    @DisplayName("POST /api/fichas-treino/{id}/progredir -> Deve avançar o treino")
    void deveProgredirTreino() throws Exception {
        Long id = 1L;
        FichaTreino fichaAtualizada = criarFichaMock(id, "Ficha Progredida", 6);

        when(fichaTreinoService.obterEProgredirTreino(id)).thenReturn(fichaAtualizada);

        mockMvc.perform(post("/api/fichas-treino/{id}/progredir", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.progressoAtual").value(6))
                .andExpect(jsonPath("$.status").value("ATIVA"));
    }

    @Test
    @DisplayName("GET /api/fichas-treino/{id}/treino-atual -> Deve retornar lista de itens")
    void deveBuscarTreinoAtual() throws Exception {
        Long id = 1L;

        ItemTreino item1 = new ItemTreino();
        item1.setExercicio("Supino");
        item1.setTipoTreino(TipoTreino.A);

        Equipamento eq = new Equipamento(); eq.setId(1L); eq.setNome("Banco");
        item1.setEquipamento(eq);

        List<ItemTreino> listaItens = List.of(item1);

        when(fichaTreinoService.buscarProximoTreino(id)).thenReturn(listaItens);

        mockMvc.perform(get("/api/fichas-treino/{id}/treino-atual", id)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].exercicio").value("Supino"))
                .andExpect(jsonPath("$[0].tipoTreino").value("A"));
    }

    private FichaTreino criarFichaMock(Long id, String nome, int progresso) {
        FichaTreino f = new FichaTreino();
        f.setId(id);
        f.setNome(nome);
        f.setStatus(StatusFicha.ATIVA);
        f.setProgressoAtual(progresso);

        Aluno a = new Aluno(); a.setId(1L); a.setNome("Pablo Souza");
        f.setAluno(a);

        Instrutor i = new Instrutor(); i.setId(1L); i.setNome("Ana Pereira");
        f.setInstrutorResponsavel(i);

        return f;
    }
}