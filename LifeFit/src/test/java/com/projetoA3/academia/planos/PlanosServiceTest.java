package com.projetoA3.academia.planos;

import com.projetoA3.academia.planos.entity.Planos;
import com.projetoA3.academia.planos.repository.PlanosRepository;
import com.projetoA3.academia.planos.service.PlanosService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PlanosServiceTest {

    @Mock
    private PlanosRepository planosRepository;

    @InjectMocks
    private PlanosService planosService;

    private Planos plano;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        plano = new Planos();
        plano.setId(1L);
        plano.setNome("Plano Gold");
        plano.setDescricao("Acesso completo");
        plano.setValor(150.00);
        plano.setDuracaoMeses(12);
    }

    @Test
    void deveRetornarTodosOsPlanos() {
        when(planosRepository.findAll()).thenReturn(Arrays.asList(plano));

        List<Planos> resultado = planosService.findAll();

        assertEquals(1, resultado.size());
        assertEquals("Plano Gold", resultado.get(0).getNome());
        verify(planosRepository, times(1)).findAll();
    }

    @Test
    void deveRetornarPlanoPorIdQuandoExistir() {
        when(planosRepository.findById(1L)).thenReturn(Optional.of(plano));

        Optional<Planos> resultado = planosService.findById(1L);

        assertTrue(resultado.isPresent());
        assertEquals("Plano Gold", resultado.get().getNome());
        verify(planosRepository, times(1)).findById(1L);
    }

    @Test
    void deveRetornarOptionalVazioQuandoPlanoNaoExistir() {
        when(planosRepository.findById(2L)).thenReturn(Optional.empty());

        Optional<Planos> resultado = planosService.findById(2L);

        assertFalse(resultado.isPresent());
        verify(planosRepository, times(1)).findById(2L);
    }

    @Test
    void deveSalvarPlanoComSucesso() {
        when(planosRepository.save(plano)).thenReturn(plano);

        Planos resultado = planosService.save(plano);

        assertNotNull(resultado);
        assertEquals(12, resultado.getDuracaoMeses());
        verify(planosRepository, times(1)).save(plano);
    }

    @Test
    void deveDeletarPlanoPorId() {
        doNothing().when(planosRepository).deleteById(1L);

        planosService.deleteById(1L);

        verify(planosRepository, times(1)).deleteById(1L);
    }
}

