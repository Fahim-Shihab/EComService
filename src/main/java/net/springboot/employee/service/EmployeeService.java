package net.springboot.employee.service;

import net.springboot.common.util.Utils;
import net.springboot.employee.model.Employee;
import net.springboot.employee.payload.SaveEmployeeRequest;
import net.springboot.employee.payload.SaveEmployeeResponse;
import net.springboot.lookup.repository.BaseRepository;
import net.springboot.security.model.LoggedInUser;
import net.springboot.security.model.UserInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
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

            if (!(request.getUserId() > 0)) {
                return new SaveEmployeeResponse(false, "User Id is required");
            }

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

            employee.setDateOfBirth(request.getDob());
            employee.setStatus(request.getStatus().getCode());

            if (request.getUserId() > 0) {
                String sql = "SELECT * FROM user_info WHERE id=:userId ";
                Map<String, Object> params = new HashMap<>();
                params.clear();
                params.put("userId", request.getUserId());
                UserInfo userInfo = repository.findSingleResultByNativeQuery(sql, UserInfo.class, params);

                if (userInfo == null) {
                    return new SaveEmployeeResponse(false, "Wrong user id");
                } else {
                    employee.setUserInfo(userInfo);
                }
            }

            Timestamp timestamp = Utils.getCurrentTimeStamp();

            LoggedInUser user = (LoggedInUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            if (!isUpdate) {
                employee.setEntryById(user.getId());
                employee.setEntryDate(timestamp);

                repository.persist(employee);
            } else {
                employee.setUpdById(user.getId());
                employee.setUpdDate(timestamp);

                repository.merge(employee);
            }
        } catch (Throwable t) {
            LOGGER.error("EMPLOYEE CREATION ERROR:", t);
            return new SaveEmployeeResponse(false, "Internal server error.Please contact with admin");
        }
        return new SaveEmployeeResponse(true, "Employee has been saved successfully");
    }

}
