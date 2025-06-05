package br.com.gabxdev.fleet_management.domain;

import br.com.gabxdev.fleet_management.domain.enums.FuelType;
import br.com.gabxdev.fleet_management.domain.enums.MeasurementUnit;
import br.com.gabxdev.fleet_management.domain.enums.VehicleStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "tb_vehicle")
@Getter
@Setter
@EqualsAndHashCode(of = "id", callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String plate;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String model;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private VehicleStatus status;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MeasurementUnit measurementUnit;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FuelType fuelType;

    @Column(nullable = false)
    private Double currentMeasurement;

    @Column(nullable = false, updatable = false)
    private UUID accountId;
}
