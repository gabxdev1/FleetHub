package br.com.gabxdev.fleet_management.request;

import br.com.gabxdev.fleet_management.domain.enums.FuelType;
import br.com.gabxdev.fleet_management.domain.enums.MeasurementUnit;
import br.com.gabxdev.fleet_management.domain.enums.VehicleStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record VehiclePostRequest(
        @NotBlank
        String plate,

        @NotBlank
        String name,

        @NotBlank
        String model,

        @NotNull
        VehicleStatus status,

        @NotNull
        MeasurementUnit measurementUnit,

        @NotNull
        FuelType fuelType,

        @NotNull
        @PositiveOrZero
        Double currentMeasurement
) {
}
