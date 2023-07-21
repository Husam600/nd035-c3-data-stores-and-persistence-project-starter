package com.udacity.jdnd.course3.critter.employee;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class EmployeeService {

    @Autowired
    EmployeeRepository employeeRepository;

    public EmployeeDTO saveEmployee(EmployeeDTO employeeDTO) {
        Employee employee = employeeRepository.save(convertEmployeeDTOToEmployee(employeeDTO));
        return convertEmployeeToEmployeeDTO(employee);
    }

    public List<EmployeeDTO> getAllEmployee(){
        List<Employee> employeeList = employeeRepository.findAll();
        return employeeList.stream().map(this::convertEmployeeToEmployeeDTO).collect(Collectors.toList());
    }

    public EmployeeDTO findEmployeeById(Long employeeId) {
        Optional<Employee> employee = employeeRepository.findById(employeeId);
        if (employee.isEmpty()) {
            throw new EntityNotFoundException();
        } else {
            return convertEmployeeToEmployeeDTO(employee.get());
        }
    }

    public void setEmployeeAvailability(Set<DayOfWeek> daysAvailable, long employeeId) {
        Optional<Employee> employee = employeeRepository.findById(employeeId);
        if (employee.isEmpty()) {
            throw new EntityNotFoundException();
        } else {
            Employee updatedEmployee = employee.get();
            updatedEmployee.setDaysAvailable(daysAvailable);
            employeeRepository.save(updatedEmployee);
        }
    }

    public List<EmployeeDTO> findEmployeesForService(EmployeeRequestDTO employeeRequestDTO) {
        DayOfWeek searchedDay = employeeRequestDTO.getDate().getDayOfWeek();
        List<Employee> employeeListFreeInThatDay = employeeRepository.findEmployeeByDateAvailable(searchedDay);
        List<Employee> employeeListFreeInThatDayAndHavingTheSkills = new ArrayList<>();
        Set<EmployeeSkill> requestEmployeeSkills = employeeRequestDTO.getSkills();
        for (Employee employee : employeeListFreeInThatDay){
            if (employee.getSkills().containsAll(requestEmployeeSkills)) {
                employeeListFreeInThatDayAndHavingTheSkills.add(employee);
            }
        }
        return employeeListFreeInThatDayAndHavingTheSkills.stream().map(this::convertEmployeeToEmployeeDTO).collect(Collectors.toList());
    }

    private EmployeeDTO convertEmployeeToEmployeeDTO(Employee employee) {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        BeanUtils.copyProperties(employee, employeeDTO);
        return employeeDTO;
    }

    private Employee convertEmployeeDTOToEmployee(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeDTO, employee);
        return employee;
    }
}
