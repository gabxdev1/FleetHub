package br.com.gabxdev.fleet_management.mapper;

import br.com.gabxdev.fleet_management.domain.Vehicle;
import br.com.gabxdev.fleet_management.request.VehiclePostRequest;
import br.com.gabxdev.fleet_management.response.VehiclePostResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface VehicleMapper {

    Vehicle toEntity(VehiclePostRequest request);

    VehiclePostResponse toVehiclePostResponse(Vehicle vehicle);
}
