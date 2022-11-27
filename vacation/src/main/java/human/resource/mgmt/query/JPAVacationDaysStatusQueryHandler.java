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
@ProcessingGroup("vacationDaysStatus")
public class JPAVacationDaysStatusQueryHandler {

    @Autowired
    private VacationDaysStatusRepository vacationDaysStatusRepository;

    @QueryHandler
    public List<VacationDaysStatus> handle(VacationDaysStatusQuery query) {
        return vacationDaysStatusRepository.findAll();
    }



    @EventHandler
    public void whenVacationDaysAdded_then_UPDATE_1( VacationDaysAddedEvent vacationDaysAdded) throws Exception{
        // view 객체 조회
        Optional<VacationDaysStatus> vacationDaysStatusOptional = vacationDaysStatusRepository.findById(vacationDaysAdded.getId());

        if( vacationDaysStatusOptional.isPresent()) {
                VacationDaysStatus vacationDaysStatus = vacationDaysStatusOptional.get();
        // view 객체에 이벤트의 eventDirectValue 를 set 함
            vacationDaysStatus.setDaysLeft(vacationDaysStatus.getDaysLeft() + vacationDaysAdded.getDayCount());
            // view 레파지 토리에 save
                vacationDaysStatusRepository.save(vacationDaysStatus);
            }

    }
    @EventHandler
    public void whenVacationDaysUsed_then_UPDATE_2( VacationDaysUsedEvent vacationDaysUsed) throws Exception{
        // view 객체 조회   //TODO:  findByUserId --> fidById
        Optional<VacationDaysStatus> vacationDaysStatusOptional = vacationDaysStatusRepository.findById(vacationDaysUsed.getId());

        if( vacationDaysStatusOptional.isPresent()) {
                VacationDaysStatus vacationDaysStatus = vacationDaysStatusOptional.get();
        // view 객체에 이벤트의 eventDirectValue 를 set 함
            vacationDaysStatus.setDaysLeft(vacationDaysStatus.getDaysLeft() - vacationDaysUsed.getDayCount());
            // view 레파지 토리에 save
                vacationDaysStatusRepository.save(vacationDaysStatus);
            }

    }


}

