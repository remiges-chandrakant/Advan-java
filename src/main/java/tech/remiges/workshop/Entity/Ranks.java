package tech.remiges.workshop.Entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

import jakarta.annotation.Generated;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "Ranks")
@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class Ranks {

    @Id
    @XmlElement
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rankid")
    private Long rankid;

    @XmlElement
    @Column(name = "Rank")
    private String rank;

}
