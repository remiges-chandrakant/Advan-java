package tech.remiges.workshop.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tech.remiges.workshop.Entity.Config;
import java.util.List;

@Repository
public interface ConfigRepository extends JpaRepository<Config, Long> {

    public List<Config> findByConfigName(String configName);
}
