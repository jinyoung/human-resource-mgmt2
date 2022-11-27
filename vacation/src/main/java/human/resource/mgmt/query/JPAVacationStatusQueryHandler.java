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
@ProcessingGroup("vacationStatus")
public class JPAVacationStatusQueryHandler {

    @Autowired
    private VacationStatusRepository vacationStatusRepository;

    @QueryHandler
    public List<VacationStatus> handle(VacationStatusQuery query) {
        return vacationStatusRepository.findAll();
    }

    @EventHandler
    public void whenVacationRegistered_then_CREATE_1 (VacationRegisteredEvent vacationRegistered) throws Exception{
            // view 객체 생성
            VacationStatus vacationStatus = new VacationStatus();
            // view 객체에 이벤트의 Value 를 set 함

            //TODO: formatting blank method if the class type is not compilant
            vacationStatus.setId(vacationRegistered.getId());
            vacationStatus.setStartDate(vacationRegistered.getStartDate());
            vacationStatus.setEndDate(vacationRegistered.getEndDate());
            vacationStatus.setReason(vacationRegistered.getReason());
            // view 레파지 토리에 save
            vacationStatusRepository.save(vacationStatus);
    }


    @EventHandler
    public void whenVacationApproved_then_UPDATE_1( VacationApprovedEvent vacationApproved) throws Exception{
        // view 객체 조회
        Optional<VacationStatus> vacationStatusOptional = vacationStatusRepository.findById(vacationApproved.getId());

        if( vacationStatusOptional.isPresent()) {
                VacationStatus vacationStatus = vacationStatusOptional.get();
        // view 객체에 이벤트의 eventDirectValue 를 set 함
            vacationStatus.setStatus("APPROVED");    
            // view 레파지 토리에 save
                vacationStatusRepository.save(vacationStatus);
            }

    }
    @EventHandler
    public void whenVacationRejected_then_UPDATE_2( VacationRejectedEvent vacationRejected) throws Exception{
        // view 객체 조회
        Optional<VacationStatus> vacationStatusOptional = vacationStatusRepository.findById(vacationRejected.getId());

        if( vacationStatusOptional.isPresent()) {
                VacationStatus vacationStatus = vacationStatusOptional.get();
        // view 객체에 이벤트의 eventDirectValue 를 set 함
            vacationStatus.setStatus("REJECTED");    
            // view 레파지 토리에 save
                vacationStatusRepository.save(vacationStatus);
            }

    }


}

