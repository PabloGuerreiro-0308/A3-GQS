package com.projetoA3.academia.fichaTreino.service;

import com.projetoA3.academia.aluno.entity.Aluno;
import com.projetoA3.academia.equipamento.entity.Equipamento;
import com.projetoA3.academia.equipamento.repository.EquipamentoRepository;
import com.projetoA3.academia.fichaTreino.entity.FichaTreino;
import com.projetoA3.academia.fichaTreino.entity.ItemTreino;
import com.projetoA3.academia.fichaTreino.repository.FichaTreinoRepository;
import com.projetoA3.academia.fichaTreino.repository.ItemTreinoRepository;
import com.projetoA3.academia.fichaTreino.status.StatusFicha;
import com.projetoA3.academia.fichaTreino.tipoTreino.TipoTreino;
import com.projetoA3.academia.instrutor.entity.Instrutor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FichaTreinoServiceTest {

    @Mock
    private FichaTreinoRepository fichaTreinoRepository;

    @Mock
    private ItemTreinoRepository itemTreinoRepository;

    @Mock
    private EquipamentoRepository equipamentoRepository;

    @InjectMocks
    private FichaTreinoService fichaTreinoService;


    @Test
    @DisplayName("Deve criar ficha, vincular itens e buscar equipamento real pelo ID")
    void deveCriarFichaComSucesso() {



        Aluno alunoMock = new Aluno();
        alunoMock.setId(1L);
        alunoMock.setNome("Pablo Souza");

        Instrutor instrutorMock = new Instrutor();
        instrutorMock.setId(1L);
        instrutorMock.setNome("Ana Pereira");

        Equipamento equipamentoBanco = new Equipamento();
        equipamentoBanco.setId(10L);
        equipamentoBanco.setNome("Banco Supino Oficial");

        ItemTreino itemInput = new ItemTreino();
        itemInput.setExercicio("Supino Reto");
        itemInput.setTipoTreino(TipoTreino.A);

        Equipamento equipInput = new Equipamento();
        equipInput.setId(10L);
        itemInput.setEquipamento(equipInput);

        FichaTreino fichaInput = new FichaTreino();
        fichaInput.setNome("Treino Teste Unitário");
        fichaInput.setAluno(alunoMock);
        fichaInput.setInstrutorResponsavel(instrutorMock);

        List<ItemTreino> listaItens = new ArrayList<>();
        listaItens.add(itemInput);
        fichaInput.setItensTreino(listaItens);


        when(equipamentoRepository.findById(10L)).thenReturn(Optional.of(equipamentoBanco));

        when(fichaTreinoRepository.save(any(FichaTreino.class))).thenAnswer(i -> i.getArguments()[0]);

        FichaTreino fichaSalva = fichaTreinoService.criarFicha(fichaInput);

        assertNotNull(fichaSalva);

        assertEquals("Banco Supino Oficial", fichaSalva.getItensTreino().get(0).getEquipamento().getNome());

        assertEquals(fichaInput, fichaSalva.getItensTreino().get(0).getFichaTreino());

        verify(fichaTreinoRepository, times(1)).save(fichaInput);
    }


    @Test
    @DisplayName("Deve criar ficha com exercício livre (sem equipamento) definindo como null")
    void deveCriarFichaComExercicioLivre() {
        ItemTreino itemLivre = new ItemTreino();
        itemLivre.setExercicio("Polichinelo");
        itemLivre.setEquipamento(null);

        FichaTreino ficha = new FichaTreino();
        List<ItemTreino> itens = new ArrayList<>();
        itens.add(itemLivre);
        ficha.setItensTreino(itens);

        when(fichaTreinoRepository.save(any(FichaTreino.class))).thenAnswer(i -> i.getArguments()[0]);


        FichaTreino result = fichaTreinoService.criarFicha(ficha);


        assertNull(result.getItensTreino().get(0).getEquipamento());

        verify(equipamentoRepository, never()).findById(any());
    }


    @Test
    @DisplayName("Deve progredir o treino, atualizar data e rotacionar de A para B")
    void deveProgredirFichaCorretamente() {

        Long idFicha = 1L;
        FichaTreino fichaBanco = new FichaTreino();
        fichaBanco.setId(idFicha);
        fichaBanco.setStatus(StatusFicha.ATIVA);
        fichaBanco.setProgressoAtual(0);
        fichaBanco.setProgressoTotal(10);
        fichaBanco.setSequencia("A,B");
        fichaBanco.setProximoTreino(TipoTreino.A);


        when(fichaTreinoRepository.findById(idFicha)).thenReturn(Optional.of(fichaBanco));

        when(fichaTreinoRepository.save(any(FichaTreino.class))).thenAnswer(i -> i.getArguments()[0]);


        FichaTreino resultado = fichaTreinoService.obterEProgredirTreino(idFicha);


        assertEquals(1, resultado.getProgressoAtual());
        assertEquals(TipoTreino.B, resultado.getProximoTreino());
        assertNotNull(resultado.getDataUltimoTreino());
    }


    @Test
    @DisplayName("Deve finalizar a ficha quando atingir o progresso total")
    void deveConcluirFicha() {

        Long idFicha = 1L;
        FichaTreino fichaBanco = new FichaTreino();
        fichaBanco.setId(idFicha);
        fichaBanco.setStatus(StatusFicha.ATIVA);
        fichaBanco.setProgressoAtual(29);
        fichaBanco.setProgressoTotal(30);
        fichaBanco.setProximoTreino(TipoTreino.C);

        when(fichaTreinoRepository.findById(idFicha)).thenReturn(Optional.of(fichaBanco));
        when(fichaTreinoRepository.save(any(FichaTreino.class))).thenAnswer(i -> i.getArguments()[0]);


        FichaTreino resultado = fichaTreinoService.obterEProgredirTreino(idFicha);


        assertEquals(30, resultado.getProgressoAtual());
        assertEquals(StatusFicha.CONCLUIDA, resultado.getStatus());
    }

    @Test
    @DisplayName("Deve buscar ficha por ID sem filtros")
    void deveBuscarFichaSemFiltro() {
        Long id = 1L;
        FichaTreino fichaMock = new FichaTreino();
        fichaMock.setId(id);
        fichaMock.setItensTreino(new ArrayList<>());


        when(fichaTreinoRepository.findById(id)).thenReturn(Optional.of(fichaMock));


        FichaTreino resultado = fichaTreinoService.buscarFichaPorId(id, null);

        assertNotNull(resultado);
        assertEquals(id, resultado.getId());

        verify(itemTreinoRepository, never()).findByFichaTreino_IdAndTipoTreinoOrderByOrdemAsc(any(), any());
    }


    @Test
    @DisplayName("Deve buscar ficha e filtrar itens quando parametro filtro for enviado")
    void deveBuscarFichaComFiltro() {
        Long id = 1L;
        String filtro = "A";
        FichaTreino fichaMock = new FichaTreino();
        fichaMock.setId(id);

        List<ItemTreino> itensFiltrados = new ArrayList<>();
        itensFiltrados.add(new ItemTreino());

        when(fichaTreinoRepository.findById(id)).thenReturn(Optional.of(fichaMock));
        when(itemTreinoRepository.findByFichaTreino_IdAndTipoTreinoOrderByOrdemAsc(id, TipoTreino.A))
                .thenReturn(itensFiltrados);


        FichaTreino resultado = fichaTreinoService.buscarFichaPorId(id, filtro);

        assertNotNull(resultado);
        assertEquals(1, resultado.getItensTreino().size());
        verify(itemTreinoRepository).findByFichaTreino_IdAndTipoTreinoOrderByOrdemAsc(id, TipoTreino.A);
    }


    @Test
    @DisplayName("Deve buscar apenas os itens do treino atual (Baseado no ProximoTreino da ficha)")
    void deveBuscarItensDoTreinoAtual() {
        Long id = 1L;
        FichaTreino fichaMock = new FichaTreino();
        fichaMock.setId(id);
        fichaMock.setProximoTreino(TipoTreino.B);

        List<ItemTreino> itensDoTreinoB = List.of(new ItemTreino(), new ItemTreino());

        when(fichaTreinoRepository.findById(id)).thenReturn(Optional.of(fichaMock));
        when(itemTreinoRepository.findByFichaTreino_IdAndTipoTreinoOrderByOrdemAsc(id, TipoTreino.B))
                .thenReturn(itensDoTreinoB);

        List<ItemTreino> resultado = fichaTreinoService.buscarProximoTreino(id);

        assertEquals(2, resultado.size());
        verify(itemTreinoRepository).findByFichaTreino_IdAndTipoTreinoOrderByOrdemAsc(id, TipoTreino.B);
    }

    @Test
    @DisplayName("Deve lançar erro EntityNotFoundException ao buscar ID inexistente")
    void deveFalharAoBuscarIdInexistente() {
        Long idInexistente = 99L;

        when(fichaTreinoRepository.findById(idInexistente)).thenReturn(Optional.empty());

        assertThrows(jakarta.persistence.EntityNotFoundException.class, () -> {
            fichaTreinoService.buscarFichaPorId(idInexistente, null);
        });
    }
}