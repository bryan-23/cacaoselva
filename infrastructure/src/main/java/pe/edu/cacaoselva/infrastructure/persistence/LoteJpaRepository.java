package pe.edu.cacaoselva.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface LoteJpaRepository extends JpaRepository<LoteEntity, Long> {
}