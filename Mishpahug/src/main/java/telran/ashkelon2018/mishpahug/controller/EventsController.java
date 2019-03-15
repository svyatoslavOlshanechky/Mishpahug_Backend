package telran.ashkelon2018.mishpahug.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import telran.ashkelon2018.mishpahug.configuration.SessionConfiguration;
import telran.ashkelon2018.mishpahug.domain.Event;
import telran.ashkelon2018.mishpahug.dto.AddEventDto;
import telran.ashkelon2018.mishpahug.dto.CodeResponseDto;
import telran.ashkelon2018.mishpahug.dto.EventListRequestDto;
import telran.ashkelon2018.mishpahug.dto.EventListResponseDto;
import telran.ashkelon2018.mishpahug.service.EventsService;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/event") // all will be start from event

public class EventsController {

	@Autowired
	EventsService eventsService;

	@Autowired
	SessionConfiguration sessionConfiguration;

	@PostMapping("/creation")
	public CodeResponseDto addEvent(@RequestBody AddEventDto newEvent) {
		String sessionLogin = sessionConfiguration.sessionUserName();
		return eventsService.addNewEvent(newEvent, sessionLogin);
	}

	// @GetMapping("/event/{eventId}") // id=login+dateCreated
	// public Event getEvent(@PathVariable String login, @PathVariable LocalDateTime
	// dateCreated) {
	// return siteService.getEvent(login, dateCreated);
	// }

	// i think delete must be automaticaly after getting status "done"+some days
	// @DeleteMapping("/event/{login,dateCreated}") // id=login,dateCreated
	// public Event removeEvent(@PathVariable String login, @PathVariable
	// LocalDateTime dateCreated,
	// @RequestBody String token) {
	// return service.removeEvent(login, dateCreated, token);
	// }

	// @PutMapping("/event/{login,dateCreated}/rate")
	// public boolean addRating(@PathVariable String login, @PathVariable
	// LocalDateTime dateCreated) {
	// return service.addRating(login, dateCreated);
	// }

	// @PostMapping("/events/city") // how get city from address
	// public Iterable<Event> getEventsByCity(@PathVariable String city) {
	// return service.findEventsByCity(city);
	// }

	// @PostMapping("/events/period")
	// public Iterable<Event> getEventsBetweenDates(@RequestBody DatePeriodDto
	// periodDto) {
	// return service.findEventsByDates(periodDto);
	// }

	// @PostMapping("/events/holiday")
	// public Iterable<Event> getEventsByHoliday(@PathVariable String holiday) {
	// return service.findEventsByHoliday(holiday);
	// }

	// @PostMapping("/events/confession")
	// public Iterable<Event> getEventsByConfession(@PathVariable String confession)
	// {
	// return service.findEventsByConfession(confession);
	// }

	// @PostMapping("/events/foodPref")
	// public Iterable<Event> getEventsByFoodPref(@PathVariable String
	// foodPreference) {
	// return service.findEventsByFoodPref(foodPreference);
	// }

	// without authentication
	@PostMapping("/allprogresslist") // ?page={Integer}&size={Integer}
	public EventListResponseDto findAllEventsInProgress(//@RequestParam Integer page, @RequestParam Integer size,
			@RequestBody EventListRequestDto eventListRequestDto) {
		String sessionLogin = sessionConfiguration.sessionUserName();
		return eventsService.findEventsInProgress( eventListRequestDto, sessionLogin);// page, size,
	}
}