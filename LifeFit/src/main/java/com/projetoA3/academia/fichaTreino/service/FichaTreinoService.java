package com.projetoA3.academia.fichaTreino.service;

import com.projetoA3.academia.equipamento.entity.Equipamento;
import com.projetoA3.academia.equipamento.repository.EquipamentoRepository;
import com.projetoA3.academia.fichaTreino.entity.FichaTreino;
import com.projetoA3.academia.fichaTreino.entity.ItemTreino;
import com.projetoA3.academia.fichaTreino.repository.FichaTreinoRepository;
import com.projetoA3.academia.fichaTreino.repository.ItemTreinoRepository;
import com.projetoA3.academia.fichaTreino.status.StatusFicha;
import com.projetoA3.academia.fichaTreino.tipoTreino.TipoTreino;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Service
public class FichaTreinoService {

    private final FichaTreinoRepository fichaTreinoRepository;
    private final ItemTreinoRepository itemTreinoRepository;
    private final EquipamentoRepository equipamentoRepository;

    public FichaTreinoService(FichaTreinoRepository fichaTreinoRepository, ItemTreinoRepository itemTreinoRepository, EquipamentoRepository equipamentoRepository) {
        this.fichaTreinoRepository = fichaTreinoRepository;
        this.itemTreinoRepository = itemTreinoRepository;
        this.equipamentoRepository = equipamentoRepository;
    }

    @Transactional
    public FichaTreino criarFicha(FichaTreino fichaTreino) {
        if (fichaTreino.getItensTreino() != null) {
            for (ItemTreino item : fichaTreino.getItensTreino()) {
                item.setFichaTreino(fichaTreino);

                if (item.getEquipamento() != null && item.getEquipamento().getId() != null) {
                    Equipamento equipamentoReal = equipamentoRepository.findById(item.getEquipamento().getId())
                            .orElseThrow(() -> new EntityNotFoundException("Equipamento não encontrado com ID: " + item.getEquipamento().getId()));

                    item.setEquipamento(equipamentoReal);
                } else {
                    item.setEquipamento(null);
                }
            }
        }
        return fichaTreinoRepository.save(fichaTreino);
    }

    public FichaTreino buscarFichaPorId(Long fichaId, String tipoTreinoFiltro) {
        FichaTreino ficha = fichaTreinoRepository.findById(fichaId)
                .orElseThrow(() -> new EntityNotFoundException("Ficha de treino não encontrada com o ID: " + fichaId));

        if (tipoTreinoFiltro != null && !tipoTreinoFiltro.isBlank()) {
            TipoTreino tipo = TipoTreino.valueOf(tipoTreinoFiltro.toUpperCase());
            List<ItemTreino> itensFiltrados = itemTreinoRepository.findByFichaTreino_IdAndTipoTreinoOrderByOrdemAsc(fichaId, tipo);
            ficha.setItensTreino(itensFiltrados);
        }
        return ficha;
    }

    @Transactional
    public FichaTreino obterEProgredirTreino(Long fichaId) {
        FichaTreino ficha = buscarFichaPorId(fichaId, null);
        if (ficha.getStatus() != StatusFicha.ATIVA) {
            throw new IllegalStateException("Esta ficha de treino não está mais ativa.");
        }
        ficha.setDataUltimoTreino(LocalDateTime.now());
        ficha.setProgressoAtual(ficha.getProgressoAtual() + 1);
        if (ficha.getProgressoAtual() >= ficha.getProgressoTotal()) {
            ficha.setStatus(StatusFicha.CONCLUIDA);
        } else {
            TipoTreino proximo = calcularProximoTreinoPelaSequencia(ficha.getProximoTreino(), ficha.getSequencia());
            ficha.setProximoTreino(proximo);
        }
        return fichaTreinoRepository.save(ficha);
    }

    private TipoTreino calcularProximoTreinoPelaSequencia(TipoTreino treinoAtual, String sequencia) {
        if (sequencia == null || sequencia.isBlank()) {
            return TipoTreino.A;
        }
        List<String> treinosDaSequencia = Arrays.asList(sequencia.trim().split(","));
        int indiceAtual = treinosDaSequencia.indexOf(treinoAtual.name());
        if (indiceAtual == -1) {
            return TipoTreino.valueOf(treinosDaSequencia.get(0));
        }
        int proximoIndice = (indiceAtual + 1) % treinosDaSequencia.size();
        return TipoTreino.valueOf(treinosDaSequencia.get(proximoIndice));
    }

    public List<ItemTreino> buscarProximoTreino(Long id) {
        FichaTreino ficha = fichaTreinoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Ficha de treino não encontrada com o ID: " + id));
        TipoTreino treinoDoDia = ficha.getProximoTreino();
        return itemTreinoRepository.findByFichaTreino_IdAndTipoTreinoOrderByOrdemAsc(id, treinoDoDia);
    }
}