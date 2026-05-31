package br.com.sias.api.repository;

import br.com.sias.api.model.Presenca;
import org.springframework.data.jpa.repository.JpaRepository;
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

}


