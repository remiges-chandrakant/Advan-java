package tech.remiges.workshop.Entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "Department")
@XmlAccessorType(XmlAccessType.FIELD)
public class Department {

    @XmlElement
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "deptid")
    private Long id;

    @Column(name = "deptname", length = 100)
    @XmlElement
    private String departmentName;
}
