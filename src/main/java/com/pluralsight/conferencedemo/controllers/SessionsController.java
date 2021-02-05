package com.pluralsight.conferencedemo.controllers;

import com.pluralsight.conferencedemo.models.Session;
import com.pluralsight.conferencedemo.repositories.SessionRepository;
import com.pluralsight.conferencedemo.repositories.SpeakerRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/sessions")
public class SessionsController {

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private SpeakerRepository speakerRepository;

    @GetMapping
    public List<Session> list(){
        return sessionRepository.findAll();
    }

   @GetMapping
   @RequestMapping("{id}")
   Session get(@PathVariable Long id){
       return sessionRepository.getOne(id);
   }

   @PostMapping
   public Session create(@RequestBody final Session session){
       return sessionRepository.saveAndFlush(session);
   }

//   @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
//    public void delete(@PathVariable Long id){
//        //deal with cascades
//       sessionRepository.deletebyId(id);
//   }

   @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public Session update(@PathVariable Long id, @RequestBody Session session){
        //because this is a put we expect all attributes to be passed in
       //TODO add validation that all attributes are passed in othewrwise return a bad payload 400
       Session existingSession = sessionRepository.getOne(id);
       BeanUtils.copyProperties(session, existingSession, "session_id");
       return sessionRepository.saveAndFlush(existingSession);
   }
}
