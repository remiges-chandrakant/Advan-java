package tech.remiges.workshop.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import tech.remiges.workshop.Entity.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    List<Employee> findByfullNameContaining(String Name);

    @Query(value = "insert into employee_shadow (id, client_reqid, createdat, dob, doj, empid, fname, fullname, reportsto, salary, updatedat, deptid, rankid) "
            +
            "select id, client_reqid, createdat, dob, doj, empid, fname, fullname, reportsto, salary, updatedat, deptid, rankid FROM employee WHERE id = ?1", nativeQuery = true)
    void createShadowEmpoyee(Long id);
}
