package com.udacity.jdnd.course3.critter.schedule;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    @Query("SELECT s FROM Schedule s " +
            "JOIN s.pets pet " +
            "WHERE pet.id = :petId")
    Optional<List<Schedule>> findScheduleByPetId(Long petId);

    @Query("SELECT s FROM Schedule s " +
            "JOIN s.employees employee " +
            "WHERE employee.id = :employeeId")
    Optional<List<Schedule>> findScheduleByEmployeeId(Long employeeId);
}
