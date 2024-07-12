package com.telegramBot.scheduleBot.service;

import com.telegramBot.scheduleBot.entity.Schedule;
import com.telegramBot.scheduleBot.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScheduleService {
    @Autowired
    ScheduleRepository scheduleRepository;

    public String getSchedule() {
        List<Schedule> scheduleList = scheduleRepository.findAll();
        return scheduleList.get(0).toString();
    }
}
