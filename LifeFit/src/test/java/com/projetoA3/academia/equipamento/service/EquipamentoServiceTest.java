package com.projetoA3.academia.equipamento.service;

import com.projetoA3.academia.equipamento.entity.Equipamento;
import com.projetoA3.academia.equipamento.GrupoMuscular;
import com.projetoA3.academia.equipamento.repository.EquipamentoRepository;
import com.projetoA3.academia.service.EquipamentoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EquipamentoServiceTest {

    @Mock
    private EquipamentoRepository repository;

    @InjectMocks
    private EquipamentoService service;

    @Test
    void deveSalvarEquipamentoComSucesso() {
        Equipamento equipamento = Equipamento.builder()
                .nome("Supino").marca("TechGym").grupoMuscular(GrupoMuscular.SUPERIORES).quantidade(5).build();

        when(repository.save(any(Equipamento.class))).thenReturn(equipamento);

        Equipamento salvo = service.salvar(equipamento);

        assertNotNull(salvo);
        assertEquals("Supino", salvo.getNome());
        verify(repository, times(1)).save(equipamento);
    }

    @Test
    void deveLancarErroAoSalvarQuantidadeNegativa() {
        Equipamento equipamento = Equipamento.builder()
                .nome("Esteira").quantidade(-1).build();

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            service.salvar(equipamento);
        });

        assertEquals("Quantidade não pode ser negativa", exception.getMessage());
        verify(repository, never()).save(any());
    }

    @Test
    void deveListarPorGrupoMuscular() {
        when(repository.findByGrupoMuscular(GrupoMuscular.INFERIORES))
                .thenReturn(List.of(new Equipamento()));

        List<Equipamento> lista = service.listarPorGrupo(GrupoMuscular.INFERIORES);

        assertFalse(lista.isEmpty());
        verify(repository).findByGrupoMuscular(GrupoMuscular.INFERIORES);
    }

    @Test
    void deveRemoverEquipamentoExistente() {
        Long id = 1L;
        when(repository.existsById(id)).thenReturn(true);

        service.remover(id);

        verify(repository).deleteById(id);
    }

    @Test
    void deveLancarErroAoRemoverEquipamentoInexistente() {
        Long id = 99L;
        when(repository.existsById(id)).thenReturn(false);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            service.remover(id);
        });

        assertEquals("Equipamento não encontrado", exception.getMessage());
        verify(repository, never()).deleteById(anyLong());
    }
}