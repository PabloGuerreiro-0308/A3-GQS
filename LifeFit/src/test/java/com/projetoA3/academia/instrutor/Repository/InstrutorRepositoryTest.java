package com.projetoA3.academia.instrutor.repository;

import com.projetoA3.academia.instrutor.entity.Instrutor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class InstrutorRepositoryTest {

    @Autowired
    private InstrutorRepository instrutorRepository;

    @Test
    void shouldReturnTrueWhenEmailExists() {
        // Cenário
        Instrutor instrutor = new Instrutor();
        instrutor.setNome("João Silva");
        instrutor.setIdade(30);
        instrutor.setEmail("joao.silva@academia.com");
        instrutor.setTelefone("11999999999");
        instrutorRepository.save(instrutor);

        // Ação
        boolean exists = instrutorRepository.existsByEmail("joao.silva@academia.com");

        // Verificação
        assertThat(exists).isTrue();
    }

    @Test
    void shouldReturnFalseWhenEmailDoesNotExist() {
        // Ação
        boolean exists = instrutorRepository.existsByEmail("email.nao.existe@academia.com");

        // Verificação
        assertThat(exists).isFalse();
    }
}