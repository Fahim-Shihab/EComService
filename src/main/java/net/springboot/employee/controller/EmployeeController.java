package net.springboot.employee.controller;

import net.springboot.employee.model.Employee;
import net.springboot.employee.payload.SaveEmployeeRequest;
import net.springboot.employee.payload.SaveEmployeeResponse;
import net.springboot.employee.repository.EmployeeRepository;
import net.springboot.employee.service.EmployeeService;
import net.springboot.exception.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;

    private final EmployeeService employeeService;
    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeController.class);

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    public List<Employee> getAllEmployees(){
        return employeeRepository.findAll();
    }

    // build create employee REST API
    //@PostMapping("create")
    public Employee createEmployee(@RequestBody Employee employee) {
        return employeeRepository.save(employee);
    }
    
    @PostMapping("/saveEmployee")
    public @ResponseBody
    SaveEmployeeResponse SaveEmployee(@RequestBody SaveEmployeeRequest request){
        return employeeService.SaveEmployee(request);
    } 

    // build get employee by id REST API
    @GetMapping("{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable long id){
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not exist with id:" + id));
        return ResponseEntity.ok(employee);
    }

    // build delete employee REST API
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> deleteEmployee(@PathVariable long id){

        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not exist with id: " + id));

        employeeRepository.delete(employee);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
