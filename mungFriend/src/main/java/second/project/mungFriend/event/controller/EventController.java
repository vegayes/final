package second.project.mungFriend.event.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import second.project.mungFriend.event.model.service.EventService;

@Controller
public class EventController {

	@Autowired
	private EventService service;
	
	
	
}
