package br.com.sias.api.repository;

import br.com.sias.api.model.AvaliacaoFisica;
import br.com.sias.api.model.Encaminhamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AvaliacaoFisicaRepository extends JpaRepository<AvaliacaoFisica, Long> {

    List<AvaliacaoFisica> findByPacienteId(Long pacienteId);
    List<AvaliacaoFisica> findByPacienteIdOrderByDataAvaliacaoDesc(Long pacienteId);
}