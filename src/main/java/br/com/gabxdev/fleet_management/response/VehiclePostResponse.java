package br.com.gabxdev.fleet_management.response;

import br.com.gabxdev.fleet_management.domain.enums.FuelType;
import br.com.gabxdev.fleet_management.domain.enums.MeasurementUnit;
import br.com.gabxdev.fleet_management.domain.enums.VehicleStatus;

public record VehiclePostResponse(
        Long id,

        String plate,

        String name,

        String model,

        VehicleStatus status,

        MeasurementUnit measurementUnit,

        FuelType fuelType,

        Double currentMeasurement
) {
}
