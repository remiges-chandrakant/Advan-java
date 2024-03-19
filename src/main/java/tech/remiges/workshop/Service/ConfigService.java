package tech.remiges.workshop.Service;

import java.util.List;

import org.hibernate.sql.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import tech.remiges.workshop.Entity.Config;
import tech.remiges.workshop.Repository.ConfigRepository;

@Service
public class ConfigService {

    @Autowired
    ConfigRepository configRepo;

    public Config SaveConfig(Config newConfig) {
        return configRepo.save(newConfig);
    }

    public List<Config> getConfig(String configName) {
        return configRepo.findByConfigName(configName);
    }

    public Config UpdateConfig(Config changeConfig) {
        // List<Config> configs =
        // configRepo.findByConfigName(changeConfig.getConfigName());
        return configRepo.save(changeConfig);
    }
}
