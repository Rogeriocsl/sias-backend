package br.com.sias.api.repository;

import br.com.sias.api.model.Agendamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;

@Repository
public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {

    boolean existsByInstrutorIdAndDataHora(Long instrutorId, LocalDateTime dataHora);
}