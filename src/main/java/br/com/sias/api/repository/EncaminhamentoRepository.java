package br.com.sias.api.repository;

import br.com.sias.api.model.Encaminhamento;
import br.com.sias.api.model.enums.EncaminhamentoStatus;

import java.util.List;

public interface EncaminhamentoRepository {
    List<Encaminhamento> findAll();
    List<Encaminhamento> findByPacienteId(Long pacienteId);
    List<Encaminhamento> findByStatus(EncaminhamentoStatus status);

}
