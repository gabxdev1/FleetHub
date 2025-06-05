package br.com.gabxdev.fleet_management.controller;

import br.com.gabxdev.core.common.HeaderUtils;
import br.com.gabxdev.fleet_management.domain.Vehicle;
import br.com.gabxdev.fleet_management.mapper.VehicleMapper;
import br.com.gabxdev.fleet_management.request.VehiclePostRequest;
import br.com.gabxdev.fleet_management.response.VehiclePostResponse;
import br.com.gabxdev.fleet_management.service.VehicleService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/vehicles")
@RequiredArgsConstructor
@Validated
public class VehicleController {

    private final HeaderUtils headerUtils;

    private final VehicleService vehicleService;

    private final VehicleMapper mapper;

    @PostMapping(headers = HttpHeaders.AUTHORIZATION)
    public ResponseEntity<VehiclePostResponse> saveVehicle(VehiclePostRequest request) {
        var entity = mapper.toEntity(request);

        var vehicle = vehicleService.save(entity);

        var headerLocation = headerUtils.createHeaderLocation(vehicle.getId().toString());

        var response = mapper.toVehiclePostResponse(vehicle);

        return ResponseEntity.created(headerLocation).body(response);
    }

    @GetMapping(path = "/{id}", headers = HttpHeaders.AUTHORIZATION)
    public ResponseEntity<VehiclePostResponse> findVehicleById(@NotNull @PathVariable Long id) {
        var vehicleById = vehicleService.findVehicleById(id);

        return ResponseEntity.ok(mapper.toVehiclePostResponse(vehicleById));
    }


}
