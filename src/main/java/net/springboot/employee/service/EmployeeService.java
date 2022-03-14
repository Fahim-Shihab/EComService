package net.springboot.employee.service;

import net.springboot.employee.model.Employee;
import net.springboot.employee.payload.SaveEmployeeRequest;
import net.springboot.employee.payload.SaveEmployeeResponse;
import net.springboot.lookup.repository.BaseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class EmployeeService {

    private final BaseRepository repository;

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeService.class);

    public EmployeeService(BaseRepository baseRepository) {
        this.repository = baseRepository;
    }

    public SaveEmployeeResponse SaveEmployee(SaveEmployeeRequest request) {
        try {

            boolean isUpdate = false;
            Employee employee = new Employee();

            if (request.getId() > 0) {
                String sql = "SELECT * FROM employees WHERE id=:employeeId ";
                Map<String, Object> params = new HashMap<>();
                params.clear();
                params.put("employeeId", request.getId());
                employee = repository.findSingleResultByNativeQuery(sql, Employee.class, params);

                if (employee != null) {
                    isUpdate = true;
                    employee.setId(request.getId());
                } else {
                    employee = new Employee();
                }
            }

            employee.setEmailId(request.getEmailId().trim());
            employee.setFirstName(request.getFirstName());
            employee.setLastName(request.getLastName());

            if (!isUpdate) {
                repository.persist(employee);
            } else {
                repository.merge(employee);
            }
        } catch (Throwable t) {
            LOGGER.error("USER CREATION ERROR:", t);
            return new SaveEmployeeResponse(false, "Internal server error.Please contact with admin");
        }
        return new SaveEmployeeResponse(true, "User has been registered successfully");
    }

}
