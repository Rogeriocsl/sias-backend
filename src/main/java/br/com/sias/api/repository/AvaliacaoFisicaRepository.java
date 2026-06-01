package br.com.sias.api.repository;

import br.com.sias.api.model.AvaliacaoFisica;
import br.com.sias.api.model.Encaminhamento;
import br.com.sias.api.repository.projection.EvolucaoAvaliacaoProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AvaliacaoFisicaRepository extends JpaRepository<AvaliacaoFisica, Long> {
    List<AvaliacaoFisica> findByPacienteId(Long pacienteId);
    List<AvaliacaoFisica> findByPacienteIdOrderByDataAvaliacaoDesc(Long pacienteId);

    @Query("""
        SELECT
            a1.peso                    AS pesoInicial,
            a2.peso                    AS pesoFinal,
            a1.imc                     AS imcInicial,
            a2.imc                     AS imcFinal,
            a1.circunferenciaAbdominal AS circAbdominalInicial,
            a2.circunferenciaAbdominal AS circAbdominalFinal
        FROM AvaliacaoFisica a1
        JOIN AvaliacaoFisica a2
          ON  a2.paciente.id = a1.paciente.id
          AND a2.dataAvaliacao = (
                SELECT MAX(ax.dataAvaliacao)
                FROM AvaliacaoFisica ax
                WHERE ax.paciente.id = a1.paciente.id
              )
        WHERE a1.dataAvaliacao = (
              SELECT MIN(ay.dataAvaliacao)
              FROM AvaliacaoFisica ay
              WHERE ay.paciente.id = a1.paciente.id
            )
          AND a1.dataAvaliacao <> a2.dataAvaliacao
        """)
    List<EvolucaoAvaliacaoProjection> findEvolucaoPorPaciente();

}