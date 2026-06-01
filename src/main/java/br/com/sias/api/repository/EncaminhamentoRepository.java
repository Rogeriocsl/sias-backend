package br.com.sias.api.repository;

import br.com.sias.api.dto.EstatisticaOcupacaoTurmaDTO;
import br.com.sias.api.model.Encaminhamento;
import br.com.sias.api.model.enums.EncaminhamentoStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface EncaminhamentoRepository extends JpaRepository<Encaminhamento, Long> {

    List<Encaminhamento> findByPacienteId(Long pacienteId);
    List<Encaminhamento> findByPacienteIdOrderByDataEncaminhamentoDesc(Long pacienteId);
    List<Encaminhamento> findByStatus(EncaminhamentoStatus status);
    long countByStatus(EncaminhamentoStatus status);

    @Query("""
        SELECT e FROM Encaminhamento e
        LEFT JOIN FETCH e.paciente
        LEFT JOIN FETCH e.turma
        ORDER BY e.dataEncaminhamento DESC
        """)
    List<Encaminhamento> findAllComRelacoes();


    @Query("""
        SELECT COUNT(e)
        FROM Encaminhamento e
        WHERE e.status = br.com.sias.api.model.enums.EncaminhamentoStatus.PENDENTE
          AND e.turma IS NULL
          AND e.dataEncaminhamento <= :limite
        """)
    long countPendentesAtrasados(@Param("limite") LocalDate limite);

    @Query("""
        SELECT new br.com.sias.api.dto.EstatisticaOcupacaoTurmaDTO(
            t.id,
            t.nome,
            COUNT(e.id)
        )
        FROM Turmas t
        LEFT JOIN Encaminhamento e
          ON  e.turma.id = t.id
          AND e.status = br.com.sias.api.model.enums.EncaminhamentoStatus.EM_ACOMPANHAMENTO
        GROUP BY t.id, t.nome
        ORDER BY t.nome
        """)
    List<EstatisticaOcupacaoTurmaDTO> findOcupacaoPorTurma();
}