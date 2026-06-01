package br.com.sias.api.repository;

import br.com.sias.api.model.Presenca;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface PresencaRepository extends JpaRepository<Presenca, Long> {

    List<Presenca> findByPacienteId(Long pacienteId);
    List<Presenca> findByDataPresenca(LocalDate data);
    boolean existsByPacienteIdAndDataPresenca(Long pacienteId, LocalDate data);
    List<Presenca> findByPacienteIdOrderByDataPresencaDesc(Long pacienteId);
    Optional<Presenca> findByPacienteIdAndDataPresenca(Long pacienteId, LocalDate data);


    @Query(value = """
    SELECT
      DATE_FORMAT(DATE_SUB(p.data_presenca,
        INTERVAL (WEEKDAY(p.data_presenca)) DAY), '%Y-%m-%d') AS semanaInicio,
      p.status AS status,
      COUNT(*) AS quantidade
    FROM presencas p
    WHERE p.data_presenca >= :dataInicio
    GROUP BY semanaInicio, p.status
    ORDER BY semanaInicio
    """, nativeQuery = true)
    List<Object[]> findPresencaAgrupadaPorSemana(@Param("dataInicio") LocalDate dataInicio);


    @Query(value = """
    SELECT p.paciente_id FROM presencas p
    WHERE p.status = 'FALTA' AND p.data_presenca >= :dataInicio
    GROUP BY p.paciente_id HAVING COUNT(*) >= 3
    """, nativeQuery = true)
    List<Long> findPacientesComFaltasConsecutivas(@Param("dataInicio") LocalDate dataInicio);
}