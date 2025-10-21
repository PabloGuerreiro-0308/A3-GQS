package equipamento.repository;

import equipamento.entity.Equipamento;
import equipamento.GrupoMuscular;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EquipamentoRepository extends JpaRepository<Equipamento, Long> {
    List<Equipamento> findByGrupoMuscular(GrupoMuscular grupo);
}
