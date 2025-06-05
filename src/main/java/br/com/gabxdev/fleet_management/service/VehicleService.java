package br.com.gabxdev.fleet_management.service;

import br.com.gabxdev.core.common.AuthUtil;
import br.com.gabxdev.core.exception.NotFoundException;
import br.com.gabxdev.core.properties.FleetHubProperties;
import br.com.gabxdev.core.properties.ServiceRegister;
import br.com.gabxdev.fleet_management.domain.Vehicle;
import br.com.gabxdev.fleet_management.repository.VehicleRepository;
import br.com.gabxdev.iam.authorization.AuthorizationGuard;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VehicleService {

    private final VehicleRepository vehicleRepository;

    private final FleetHubProperties fleetHubProperties;

    private final AuthUtil authUtil;

    private final AuthorizationGuard authorizationGuard;

    public Vehicle save(Vehicle vehicle) {
        authorizationGuard.checkReadPermission(getServiceRegister());

        var currentUser = authUtil.getCurrentUser();

        vehicle.setAccountId(currentUser.getAccountId());

        return vehicleRepository.save(vehicle);
    }

    public Vehicle findVehicleById(Long vehicleId) {
        authorizationGuard.checkWritePermission(getServiceRegister());

        var currentUser = authUtil.getCurrentUser();

        return vehicleRepository.findByIdAndAccountId(vehicleId, currentUser.getAccountId())
                .orElseThrow(() -> new NotFoundException("Vehicle not found"));
    }

    private ServiceRegister getServiceRegister() {
        return fleetHubProperties.getFleetManagement();
    }
}
