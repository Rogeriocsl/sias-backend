package br.com.sias.api.model;
import br.com.sias.api.model.enums.Genero;
import br.com.sias.api.model.enums.TipoSanguineo;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "pacientes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Paciente {
    String name ;
}
