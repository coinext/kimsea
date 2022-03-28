package io.tommy.kimsea.web.services

import io.tommy.kimsea.web.entity.domain.Event
import io.tommy.kimsea.web.entity.repository.EventRepository
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service

@Service
class EventService(
    private val eventRepository: EventRepository
) {

    fun getEvents(pageNo:Int,pageSize:Int): List<Event> {
        return eventRepository.findAll(PageRequest.of(pageNo, pageSize))
    }
}