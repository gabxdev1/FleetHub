package br.com.gabxdev.fleet_management.repository;

import br.com.gabxdev.fleet_management.domain.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

    Optional<Vehicle> findByIdAndAccountId(Long id, UUID accountId);
}
