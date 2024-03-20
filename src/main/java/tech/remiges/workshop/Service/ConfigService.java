package tech.remiges.workshop.Service;

import java.util.List;
import java.util.Optional;

import org.hibernate.sql.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import tech.remiges.workshop.Entity.Config;
import tech.remiges.workshop.Entity.Employee;
import tech.remiges.workshop.Entity.EmployeeShadow;
import tech.remiges.workshop.Repository.ConfigRepository;

@Service
public class ConfigService {

    private ConfigRepository configRepo;

    private final ObjectMapper objectMapper;

    ConfigService(ConfigRepository configRepo, ObjectMapper objectMapper) {

        this.configRepo = configRepo;
        this.objectMapper = objectMapper;
    }

    public Config SaveConfig(Config newConfig) {
        return configRepo.save(newConfig);
    }

    public List<Config> getConfig(String configName) {
        return configRepo.findByConfigName(configName);
    }

    public Config UpdateConfig(Config changeConfig) {
        // List<Config> configs =
        // configRepo.findByConfigName(changeConfig.getConfigName());
        List<Config> entity = configRepo.findByConfigName(changeConfig.getConfigName());

        if (entity.isEmpty()) {
            return configRepo.save(changeConfig);

        } else {
            Config eConfig = entity.get(0);
            changeConfig.setConfigId(eConfig.getConfigId());
            return configRepo.save(changeConfig);
        }

    }
}
