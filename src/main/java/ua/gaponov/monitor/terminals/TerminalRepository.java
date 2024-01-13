package ua.gaponov.monitor.terminals;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TerminalRepository extends JpaRepository<Terminal, Long> {

    List<Terminal> findAllByOrderByArmId();

    Optional<Terminal> findByArmId(Long id);
}
