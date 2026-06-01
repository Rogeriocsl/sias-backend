package br.com.sias.api.repository;

import br.com.sias.api.model.Encaminhamento;
import br.com.sias.api.model.enums.EncaminhamentoStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EncaminhamentoRepository extends JpaRepository<Encaminhamento, Long> {

    List<Encaminhamento> findByPacienteId(Long pacienteId);

    List<Encaminhamento> findByPacienteIdOrderByDataEncaminhamentoDesc(Long pacienteId);

    List<Encaminhamento> findByStatus(EncaminhamentoStatus status);

    @Query("""
        SELECT e FROM Encaminhamento e
        LEFT JOIN FETCH e.paciente
        LEFT JOIN FETCH e.turma
        ORDER BY e.dataEncaminhamento DESC
    """)
    List<Encaminhamento> findAllComRelacoes();
}