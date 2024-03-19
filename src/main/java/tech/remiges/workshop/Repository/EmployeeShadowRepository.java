package tech.remiges.workshop.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tech.remiges.workshop.Entity.Employee;
import tech.remiges.workshop.Entity.EmployeeShadow;

@Repository
public interface EmployeeShadowRepository extends JpaRepository<EmployeeShadow, Long> {

    Optional<Employee> findByEmpId(String employeeId);
}
