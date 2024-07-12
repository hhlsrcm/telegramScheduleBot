package com.telegramBot.scheduleBot.repository;

import com.telegramBot.scheduleBot.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
}
