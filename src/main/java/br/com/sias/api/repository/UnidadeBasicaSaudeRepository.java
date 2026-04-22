package br.com.sias.api.repository;

import br.com.sias.api.model.UnidadeBasicaSaude;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UnidadeBasicaSaudeRepository extends JpaRepository<UnidadeBasicaSaude, Long> {
    public List<UnidadeBasicaSaude> existsByNomeUnidade(String nomeUnidade);
}