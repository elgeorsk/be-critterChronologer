package com.udacity.jdnd.course3.critter.controller;

import com.udacity.jdnd.course3.critter.dto.EmployeeDTO;
import com.udacity.jdnd.course3.critter.dto.ScheduleDTO;
import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.entity.Schedule;
import com.udacity.jdnd.course3.critter.service.PetService;
import com.udacity.jdnd.course3.critter.service.ScheduleService;
import com.udacity.jdnd.course3.critter.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Schedules.
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    private UserService userService;
    private PetService petService;
    private ScheduleService scheduleService;

    @Autowired
    public ScheduleController(UserService userService, PetService petService, ScheduleService scheduleService){
        this.userService = userService;
        this.petService = petService;
        this.scheduleService = scheduleService;
    }

    @PostMapping
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        List<Long> employeeIds = scheduleDTO.getEmployeeIds();
        List<Long> petIds = scheduleDTO.getPetIds();

        List<Employee> employees = new ArrayList<>();
        List<Pet> pets = new ArrayList<>();

        if (employeeIds != null) {
            for (Long employeeId: employeeIds) {
                employees.add(userService.getEmployeeById(employeeId));
            }
        }

        if (petIds != null) {
            for (Long petId: petIds) {
                pets.add(petService.getPet(petId));
            }
        }

        Schedule schedule = convertDTO2Schedule(scheduleDTO);
        schedule.setPets(pets);
        schedule.setEmployees(employees);
        Schedule savedSchedule = scheduleService.saveSchedule(schedule);
        return convertSchedule2DTO(savedSchedule);
    }

    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {
        List<ScheduleDTO> schedulesDTO = new ArrayList<>();
        List<Schedule> schedules = scheduleService.getAllSchedules();

        for(Schedule schedule : schedules){
            schedulesDTO.add(convertSchedule2DTO(schedule));
        }

        return schedulesDTO;
    }

    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {
        List<ScheduleDTO> schedulesDTO = new ArrayList<>();
        List<Schedule> schedules = scheduleService.getAllSchedulesByPetId(petId);

        for(Schedule schedule : schedules){
            schedulesDTO.add(convertSchedule2DTO(schedule));
        }

        return schedulesDTO;
    }

    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {
        List<ScheduleDTO> schedulesDTO = new ArrayList<>();
        List<Schedule> schedules = scheduleService.getAllSchedulesByEmployeeId(employeeId);

        for(Schedule schedule : schedules){
            schedulesDTO.add(convertSchedule2DTO(schedule));
        }

        return schedulesDTO;
    }

    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {
        List<ScheduleDTO> schedulesDTO = new ArrayList<>();
        List<Schedule> schedules = scheduleService.getScheduleByCustomerId(customerId);

        for(Schedule schedule : schedules){
            schedulesDTO.add(convertSchedule2DTO(schedule));
        }

        return schedulesDTO;
    }

    private ScheduleDTO convertSchedule2DTO(Schedule schedule){
        ScheduleDTO scheduleDTO = new ScheduleDTO();
        BeanUtils.copyProperties(schedule, scheduleDTO);

        if (schedule.getPets() != null) {
            scheduleDTO.setPetIds(schedule.getPets().stream().map(pet -> pet.getId()).collect(Collectors.toList()));
        }
        if (schedule.getEmployees() != null) {
            scheduleDTO.setEmployeeIds(schedule.getEmployees().stream().map(employee -> employee.getId()).collect(Collectors.toList()));
        }
        return scheduleDTO;
    }

    private Schedule convertDTO2Schedule(ScheduleDTO scheduleDTO){
        Schedule schedule = new Schedule();
        BeanUtils.copyProperties(scheduleDTO, schedule);
        return schedule;
    }
}
