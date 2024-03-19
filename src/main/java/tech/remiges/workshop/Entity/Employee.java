package tech.remiges.workshop.Entity;

import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import tech.remiges.workshop.Service.IDateLoader;
import tech.remiges.workshop.Utils.CommonUtils;

@Data
@Entity
@Table(name = "employee")
@XmlRootElement(name = "employee")
@XmlAccessorType(XmlAccessType.FIELD)
public class Employee implements IDateLoader {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "empid", nullable = false, unique = true)
    private String empId;

    @XmlElement(name = "first name")
    @Column(name = "fname", nullable = false)
    private String firstName;

    @XmlElement(name = "fullName")
    @Column(name = "fullname")
    private String fullName;

    @Column(name = "dob", nullable = false)
    private Date dateOfBirth;

    @Column(name = "doj", nullable = false)
    private Date dateOfJoining;

    @Column(name = "salary", nullable = false)
    private int salary;

    @Column(name = "reportsto")
    private Long reportsTo;

    @ManyToOne
    @JoinColumn(name = "deptid")
    private Department department;

    @ManyToOne
    @JoinColumn(name = "rankid")
    private Ranks rank;

    @Column(name = "createdat", nullable = false)
    @CreationTimestamp
    private Date createdAt;

    @Column(name = "updatedat")
    @UpdateTimestamp
    private Date updatedAt;

    @Column(name = "client_reqid", nullable = false)
    @UuidGenerator
    private String clientReqId;

    @Override
    public List<String> GetHeaders() {
        return CommonUtils.getPropertyNames(this);
    }

    @Override
    public List<String> GetData() {
        // TODO Auto-generated method stub
        return CommonUtils.getPropertyData(this);
    }
}
