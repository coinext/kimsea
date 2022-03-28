package io.tommy.kimsea.web.entity.repository

import io.tommy.kimsea.web.entity.domain.Event
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface EventRepository : CrudRepository<Event, Long> {
    fun findAll(pageable: Pageable) : List<Event>
}