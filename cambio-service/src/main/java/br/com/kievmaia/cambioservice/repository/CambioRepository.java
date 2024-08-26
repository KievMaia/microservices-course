package br.com.kievmaia.cambioservice.repository;

import br.com.kievmaia.cambioservice.model.Cambio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CambioRepository extends JpaRepository<Cambio, Long> {
    Optional<Cambio> findCambioByFromAndTo(String from, String to);
}
