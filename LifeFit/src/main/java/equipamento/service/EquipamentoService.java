package equipamento.service;

import equipamento.entity.Equipamento;
import equipamento.GrupoMuscular;

import java.util.List;

public interface EquipamentoService {
    Equipamento salvar(Equipamento equipamento);
    List<Equipamento> listarTodos();
    List<Equipamento> listarPorGrupo(GrupoMuscular grupo);
    void remover(Long id);
}
