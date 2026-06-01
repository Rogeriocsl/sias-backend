package br.com.sias.api.repository;

import br.com.sias.api.dto.EstatisticaComorbidadeDTO;
import br.com.sias.api.dto.EstatisticaUbsDTO;
import br.com.sias.api.model.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PacienteRepository extends JpaRepository<Paciente, Long> {

    Optional<Paciente> findByCpf(String cpf);
    List<Paciente> findByTurmasId(Long turmaId);

    @Query("SELECT new br.com.sias.api.dto.EstatisticaUbsDTO(u.nomeUnidade, COUNT(p)) " +
            "FROM Paciente p JOIN p.unidadeOrigem u GROUP BY u.nomeUnidade")
    List<EstatisticaUbsDTO> contarPacientesPorUbs();

    @Query("SELECT new br.com.sias.api.dto.EstatisticaComorbidadeDTO(c, COUNT(p)) " +
            "FROM Paciente p JOIN p.condicoesSaude c GROUP BY c")
    List<EstatisticaComorbidadeDTO> contarPacientesPorComorbidade();


    @Query("""
        SELECT COUNT(DISTINCT p.id)
        FROM Paciente p
        JOIN p.encaminhamentos e
        WHERE e.status = 'EM_ACOMPANHAMENTO'
        """)
    long countPacientesAtivos();
}