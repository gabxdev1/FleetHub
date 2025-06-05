package br.com.gabxdev.project_management.repository;

import br.com.gabxdev.project_management.domain.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {
}
