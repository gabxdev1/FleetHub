package br.com.gabxdev.fuel_management.domain;

import br.com.gabxdev.fleet_management.domain.Vehicle;
import br.com.gabxdev.fleet_management.domain.enums.FuelType;
import br.com.gabxdev.fuel_management.domain.enums.Fuel;
import br.com.gabxdev.project_management.domain.Project;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "tb_vehicle")
@Getter
@Setter
@EqualsAndHashCode(of = "id", callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FuelSupply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    private Vehicle vehicle;

    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    private Project project;

    @Column(nullable = false)
    private Fuel fuel;

    @Column(nullable = false)
    private Double quantity;

    @Column(nullable = false)
    private Double pricePerUnit;

    @Column(nullable = false)
    private Double meterValue;

    @Column(nullable = false)
    private Long responsible;

    @Column(nullable = false)
    private Instant date;

    private String notes;
}
