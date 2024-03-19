package tech.remiges.workshop.Entity;

import java.util.Date;

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

@Entity
@Data
@Table(name = "employee_shadow")
public class EmployeeShadow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "shawid")
    private Long shawid;

    @Column(name = "id")
    private Long id;

    @Column(name = "empid", nullable = false)
    private String empId;

    @Column(name = "fname", nullable = false)
    private String firstName;

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

}
