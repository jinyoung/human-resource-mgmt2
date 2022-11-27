package human.resource.mgmt.query;


import human.resource.mgmt.event.*;

import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import java.io.IOException;

@Service
@ProcessingGroup("schedule")
public class JPAScheduleQueryHandler {

    @Autowired
    private ScheduleRepository scheduleRepository;

    @QueryHandler
    public List<Schedule> handle(ScheduleQuery query) {
        return scheduleRepository.findAll();
    }

    @QueryHandler
    public Optional<Schedule> handle(ScheduleSingleQuery query) {
        return scheduleRepository.findById(query.getUserId());
    }

    @EventHandler
    public void whenScheduleAdded_then_CREATE_1 (ScheduleAddedEvent scheduleAdded) throws Exception{
            // view 객체 생성
            Schedule schedule = new Schedule();
            // view 객체에 이벤트의 Value 를 set 함
            schedule.setUserId(scheduleAdded.getUserId());
            schedule.setDateId(String.valueOf(scheduleAdded.getDate()));
            // view 레파지 토리에 save
            scheduleRepository.save(schedule);
    }



    @EventHandler
    public void whenScheduleCanceled_then_DELETE_1(ScheduleCanceledEvent scheduleCanceled) {
        // view 레파지 토리에 삭제 쿼리
        scheduleRepository.deleteByDateId(String.valueOf(scheduleCanceled.getDate()));
    }

}

