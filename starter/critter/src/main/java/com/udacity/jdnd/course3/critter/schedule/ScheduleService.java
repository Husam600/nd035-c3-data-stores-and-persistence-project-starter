package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.employee.Employee;
import com.udacity.jdnd.course3.critter.employee.EmployeeRepository;
import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.pet.PetRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ScheduleService {

    @Autowired
    ScheduleRepository scheduleRepository;

    @Autowired
    PetRepository petRepository;

    @Autowired
    EmployeeRepository employeeRepository;


    public ScheduleDTO saveSchedule(ScheduleDTO scheduleDTO) {
        Schedule schedule = scheduleRepository.save(convertScheduleDTOToSchedule(scheduleDTO));
        return convertScheduleToScheduleDTO(schedule);
    }

    public List<ScheduleDTO> getAllSchedules() {
        return scheduleRepository
                .findAll()
                .stream()
                .map(this::convertScheduleToScheduleDTO)
                .collect(Collectors.toList());
    }

    public List<ScheduleDTO> getScheduleForPet(Long petId) {
        return createScheduleDTOList(scheduleRepository.findScheduleByPetId(petId));
    }

    public List<ScheduleDTO> getScheduleForEmployee(Long employeeId) {
        return createScheduleDTOList(scheduleRepository.findScheduleByEmployeeId(employeeId));
    }

    public List<ScheduleDTO> getScheduleForCustomer(Long customerId) {
        List<Pet> petLisOfCustomer = petRepository.getPetByCustomerId(customerId);
        List<Long> scheduleIdList = new ArrayList<>();
        List<ScheduleDTO> scheduleDTOList = new ArrayList<>();
        for (Pet pet : petLisOfCustomer) {
            for (ScheduleDTO scheduleDTO : getScheduleForPet(pet.getId())){
               if (!scheduleIdList.contains(scheduleDTO.getId())) {
                   scheduleIdList.add(scheduleDTO.getId());
                   scheduleDTOList.add(scheduleDTO);
               }
            }
        }
        return scheduleDTOList;
    }

    private List<ScheduleDTO> createScheduleDTOList(Optional<List<Schedule>> scheduleList){
        return scheduleList.map(schedules -> schedules.stream().map(this::convertScheduleToScheduleDTO).collect(Collectors.toList())).orElseGet(List::of);
    }


    private Schedule convertScheduleDTOToSchedule(ScheduleDTO scheduleDTO){
        Schedule schedule = new Schedule();
        BeanUtils.copyProperties(scheduleDTO,schedule);

        List<Employee> employeeList = new ArrayList<>();
        for (Long employeeId : scheduleDTO.getEmployeeIds()) {
            Optional<Employee> employee = employeeRepository.findById(employeeId);
            if (employee.isPresent()){
                employeeList.add(employee.get());
            } else {
                throw new EntityNotFoundException();
            }
        }

        List<Pet> petList = new ArrayList<>();
        for (Long petId : scheduleDTO.getPetIds()) {
                Optional<Pet> pet = petRepository.findById(petId);
                if (pet.isPresent()) {
                    petList.add(pet.get());
                } else {
                    throw new EntityNotFoundException();
                }
        }

        schedule.setEmployees(employeeList);
        schedule.setPets(petList);
        return schedule;
    }

    private ScheduleDTO convertScheduleToScheduleDTO(Schedule schedule) {
        ScheduleDTO scheduleDTO = new ScheduleDTO();
        BeanUtils.copyProperties(schedule, scheduleDTO);
        List<Long> employeeIdList = schedule.getEmployees().stream().map(Employee::getId).collect(Collectors.toList());
        scheduleDTO.setEmployeeIds(employeeIdList);
        List<Long> petIdList = schedule.getPets().stream().map(Pet::getId).collect(Collectors.toList());
        scheduleDTO.setPetIds(petIdList);
        return scheduleDTO;
    }
}
