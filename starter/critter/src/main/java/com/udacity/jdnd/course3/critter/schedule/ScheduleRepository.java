package com.udacity.jdnd.course3.critter.schedule;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    //Optional<List<Schedule>> findScheduleByPetId(Long petId);
    //Optional<List<Schedule>> findScheduleByEmployeeId(Long employeeId);
    //Optional<List<Schedule>> findScheduleByCustomerId(Long customerId);
}
