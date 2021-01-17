package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.Schedule;
import com.udacity.jdnd.course3.critter.repository.ScheduleRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ScheduleService {

    private ScheduleRepository scheduleRepository;

    public ScheduleService(ScheduleRepository scheduleRepository){
        this.scheduleRepository = scheduleRepository;
    }

    public List<Schedule> getAllSchedules(){
        return scheduleRepository.findAll();
    }
}
