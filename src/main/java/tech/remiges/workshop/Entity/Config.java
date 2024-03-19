package tech.remiges.workshop.Entity;

import jakarta.annotation.Nonnull;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "config", uniqueConstraints = @UniqueConstraint(columnNames = { "configName" }))
@NoArgsConstructor

public class Config {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long configId;

    @Column
    @Nonnull
    private String configName;

    @Column
    private String configValue;

}
