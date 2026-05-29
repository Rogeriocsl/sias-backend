package br.com.sias.api.repository;

import br.com.sias.api.model.Turmas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TurmasRepository extends JpaRepository<Turmas, Long> {
    public boolean existsByNome(String nome);
}