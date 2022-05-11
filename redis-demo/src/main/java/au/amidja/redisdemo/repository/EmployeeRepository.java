package au.amidja.redisdemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import au.amidja.redisdemo.domain.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long>
{
}
