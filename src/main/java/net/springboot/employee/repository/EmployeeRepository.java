package net.javaguides.springboot.employee.repository;

import net.javaguides.springboot.employee.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    // all crud database methods
}


