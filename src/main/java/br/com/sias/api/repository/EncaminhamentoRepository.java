package br.com.sias.api.repository;

import br.com.sias.api.model.Encaminhamento;
import br.com.sias.api.model.enums.EncaminhamentoStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EncaminhamentoRepository extends JpaRepository<Encaminhamento, Long> {
    List<Encaminhamento> findByEncaminhamentoId(Long EncaminhamentoId);
    List<Encaminhamento> findByStatus(EncaminhamentoStatus status);

}
