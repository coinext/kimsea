package io.tommy.kimsea.web.controllers.rest

import io.tommy.kimsea.web.dto.Response
import io.tommy.kimsea.web.entity.domain.Event
import io.tommy.kimsea.web.services.EventService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/events")
class EventsRestController(
    private val eventService: EventService
) {
    
    @GetMapping("/getEvents")
    fun getEvents(@RequestParam pageNo:Int, @RequestParam pageSize:Int): Response<List<Event>> {
        return Response(eventService.getEvents(pageNo, pageSize))
    }
}