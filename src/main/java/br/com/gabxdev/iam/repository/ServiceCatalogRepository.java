package br.com.gabxdev.iam.repository;

import br.com.gabxdev.iam.domain.ServiceCatalog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceCatalogRepository extends JpaRepository<ServiceCatalog, Long> {
}
