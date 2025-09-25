package com.pds.ImobiGest.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "PROFISSIONAL_CARGO")
@Table(name = "PROFISSIONAL_CARGO")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProfissionalCargoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PROFISSIONAL_CARGO")
    @SequenceGenerator(name = "SEQ_PROFISSIONAL_CARGO", sequenceName = "SEQ_PROFISSIONAL_CARGO", allocationSize = 1)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_profissional", referencedColumnName = "id")
    private ProfissionalEntity profissional;

    @ManyToOne
    @JoinColumn(name = "id_cargo", referencedColumnName = "id")
    private CargoEntity cargo;
}
