package equipamento.service;

import equipamento.entity.Equipamento;
import equipamento.GrupoMuscular;
import equipamento.repository.EquipamentoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EquipamentoServiceImpl implements EquipamentoService {

    private final EquipamentoRepository repository;

    public EquipamentoServiceImpl(EquipamentoRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    public Equipamento salvar(Equipamento equipamento) {
        if (equipamento.getQuantidade() < 0) {
            throw new IllegalArgumentException("Quantidade não pode ser negativa");
        }
        return repository.save(equipamento);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Equipamento> listarTodos() {
        return repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Equipamento> listarPorGrupo(GrupoMuscular grupo) {
        return repository.findByGrupoMuscular(grupo);
    }

    @Override
    @Transactional
    public void remover(Long id) {
        if (!repository.existsById(id)) {
            throw new IllegalArgumentException("Equipamento não encontrado");
        }
        repository.deleteById(id);
    }
}
