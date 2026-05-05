package br.com.sias.api.repository;

import br.com.sias.api.model.UnidadeBasicaSaude;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UnidadeBasicaSaudeRepository extends JpaRepository<UnidadeBasicaSaude, Long> {
    public boolean existsByNomeUnidadeIgnoreCase(String nomeUnidade);
}