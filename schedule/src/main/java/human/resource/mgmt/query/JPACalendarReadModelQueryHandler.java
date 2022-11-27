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
@ProcessingGroup("calendarReadModel")
public class JPACalendarReadModelQueryHandler {

    @Autowired
    private CalendarReadModelRepository calendarReadModelRepository;

    @QueryHandler
    public List<CalendarReadModel> handle(CalendarReadModelQuery query) {
        return calendarReadModelRepository.findAll();
    }

    @QueryHandler
    public Optional<CalendarReadModel> handle(CalendarReadModelSingleQuery query) {
        return calendarReadModelRepository.findById(query.getUserId());
    }

    @EventHandler
    public void whenEmployeeJoined_then_CREATE_1 (EmployeeJoinedEvent employeeJoined) throws Exception{
            // view 객체 생성
            CalendarReadModel calendarReadModel = new CalendarReadModel();
            // view 객체에 이벤트의 Value 를 set 함
            calendarReadModel.setUserId(employeeJoined.getUserId());
            // view 레파지 토리에 save
            calendarReadModelRepository.save(calendarReadModel);
    }


    @EventHandler
    public void whenVacationRegistered_then_UPDATE_1( VacationRegisteredEvent vacationRegistered) throws Exception{
        // view 객체 조회
        Optional<CalendarReadModel> calendarReadModelOptional = calendarReadModelRepository.findByUserId(vacationRegistered.getUserId());

        if( calendarReadModelOptional.isPresent()) {
                CalendarReadModel calendarReadModel = calendarReadModelOptional.get();
        // view 객체에 이벤트의 eventDirectValue 를 set 함
            calendarReadModel.setScheduleData(calendarReadModel.getScheduleData() + vacationRegistered.getReason());
            // view 레파지 토리에 save
                calendarReadModelRepository.save(calendarReadModel);
            }

    }
    @EventHandler
    public void whenVacationCancelled_then_UPDATE_2( VacationCancelledEvent vacationCancelled) throws Exception{
        // view 객체 조회
        Optional<CalendarReadModel> calendarReadModelOptional = calendarReadModelRepository.findByUserId(vacationCancelled.getUserId());

        if( calendarReadModelOptional.isPresent()) {
                CalendarReadModel calendarReadModel = calendarReadModelOptional.get();
        // view 객체에 이벤트의 eventDirectValue 를 set 함
            calendarReadModel.setScheduleData(calendarReadModel.getScheduleData() - vacationCancelled.getReason());
            // view 레파지 토리에 save
                calendarReadModelRepository.save(calendarReadModel);
            }

    }


}

