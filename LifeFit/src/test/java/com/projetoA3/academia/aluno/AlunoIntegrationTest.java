package com.projetoA3.aluno;

import com.projetoA3.aluno.model.Aluno;
import com.projetoA3.aluno.repository.AlunoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class AlunoIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private Aluno aluno;

    @BeforeEach
    void setUp() {
        alunoRepository.deleteAll();

        aluno = new Aluno(
                null,
                "João Silva",
                "joao@email.com",
                "(11) 98765-4321",
                25,
                "123.456.789-00",
                "01/12/2025",
                "Rua A, 123",
                "MAT001"
        );
    }

    @Test
    void testCriarAlunoIntegracao() throws Exception {
        // Act & Assert
        mockMvc.perform(post("/api/alunos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(aluno)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nome", is("João Silva")))
                .andExpect(jsonPath("$.email", is("joao@email.com")))
                .andExpect(jsonPath("$.id", notNullValue()));

        assertEquals(1, alunoRepository.count());
    }

    @Test
    void testObterAlunoPorIdIntegracao() throws Exception {
        // Arrange
        Aluno alunoSalvo = alunoRepository.save(aluno);

        // Act & Assert
        mockMvc.perform(get("/api/alunos/" + alunoSalvo.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(alunoSalvo.getId().intValue())))
                .andExpect(jsonPath("$.nome", is("João Silva")));
    }

    @Test
    void testObterAlunoPorMatriculaIntegracao() throws Exception {
        // Arrange
        alunoRepository.save(aluno);

        // Act & Assert
        mockMvc.perform(get("/api/alunos/matricula/MAT001"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.numeroMatricula", is("MAT001")))
                .andExpect(jsonPath("$.nome", is("João Silva")));
    }

    @Test
    void testObterTodosAlunosIntegracao() throws Exception {
        // Arrange
        alunoRepository.save(aluno);

        Aluno aluno2 = new Aluno(null, "Maria", "maria@email.com", "(11) 99999-9999",
                23, "987.654.321-00", "01/12/2025", "Rua B, 456", "MAT002");
        alunoRepository.save(aluno2);

        // Act & Assert
        mockMvc.perform(get("/api/alunos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].nome", is("João Silva")))
                .andExpect(jsonPath("$[1].nome", is("Maria")));
    }

    @Test
    void testBuscarAlunoPorNomeIntegracao() throws Exception {
        // Arrange
        alunoRepository.save(aluno);

        // Act & Assert
        mockMvc.perform(get("/api/alunos/buscar?nome=João"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].nome", is("João Silva")));
    }

    @Test
    void testAtualizarAlunoIntegracao() throws Exception {
        // Arrange
        Aluno alunoSalvo = alunoRepository.save(aluno);

        Aluno alunoAtualizado = new Aluno(
                alunoSalvo.getId(),
                "João Silva Junior",
                "joao.junior@email.com",
                "(11) 98765-4321",
                26,
                "123.456.789-00",
                "01/12/2026",
                "Rua C, 789",
                "MAT001"
        );

        // Act & Assert
        mockMvc.perform(put("/api/alunos/" + alunoSalvo.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(alunoAtualizado)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome", is("João Silva Junior")))
                .andExpect(jsonPath("$.email", is("joao.junior@email.com")));
    }

    @Test
    void testDeletarAlunoIntegracao() throws Exception {
        // Arrange
        Aluno alunoSalvo = alunoRepository.save(aluno);

        // Act & Assert
        mockMvc.perform(delete("/api/alunos/" + alunoSalvo.getId()))
                .andExpect(status().isNoContent());

        assertEquals(0, alunoRepository.count());
    }

    @Test
    void testCriarAlunoComMatriculaDuplicadaIntegracao() throws Exception {
        // Arrange
        alunoRepository.save(aluno);

        // Act & Assert
        mockMvc.perform(post("/api/alunos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(aluno)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testCriarAlunoComValidacaoIntegracao() throws Exception {
        // Arrange
        Aluno alunoInvalido = new Aluno(null, "", "email-invalido", "(11) 1234",
                10, "123", "01/12/2025", "Rua A", "");

        // Act & Assert
        mockMvc.perform(post("/api/alunos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(alunoInvalido)))
                .andExpect(status().isBadRequest());
    }
}
