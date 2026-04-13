package br.com.sias.api.repository;

import br.com.sias.api.model.AvaliacaoFisica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AvaliacaoFisicaRepository extends JpaRepository<AvaliacaoFisica, Long> {
    public List<AvaliacaoFisica> findByPacienteIdOrderByDataAvaliacaoDesc(Long PacienteId);
}