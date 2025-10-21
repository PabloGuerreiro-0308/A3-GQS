package instrutor.service;

import instrutor.entity.Instrutor;
import instrutor.repository.InstrutorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface InstrutorService {
    Instrutor salvar(Instrutor instrutor);
    List<Instrutor> listarTodos();
    void remover(Long id);
    Instrutor adicionarTurma(Long id, String turma);
    @Service
public class InstrutorServiceImpl implements InstrutorService {

    private final InstrutorRepository repository;

    public InstrutorServiceImpl(InstrutorRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    public Instrutor salvar(Instrutor instrutor) {
        if (instrutor.getEmail() != null && repository.existsByEmail(instrutor.getEmail())) {
            throw new IllegalArgumentException("Email já cadastrado");
        }
        return repository.save(instrutor);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Instrutor> listarTodos() {
        return repository.findAll();
    }

    @Override
    @Transactional
    public void remover(Long id) {
        if (!repository.existsById(id)) {
            throw new IllegalArgumentException("Instrutor não encontrado");
        }
        repository.deleteById(id);
    }

    @Override
    @Transactional
    public Instrutor adicionarTurma(Long id, String turma) {
        Instrutor instrutor = repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Instrutor não encontrado"));
        instrutor.getTurmas().add(turma);
        return repository.save(instrutor);
    }
}
}
