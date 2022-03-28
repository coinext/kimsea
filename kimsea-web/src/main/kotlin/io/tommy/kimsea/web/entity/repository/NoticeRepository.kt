package io.tommy.kimsea.web.entity.repository

import io.tommy.kimsea.web.entity.domain.Notice
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface NoticeRepository : CrudRepository<Notice, Long> {
    fun findFirstByDelDttmIsNullOrderByRegDttmDesc() : Notice?
    fun findAllByDelDttmIsNullOrderByRegDttmDesc(pageable: Pageable):List<Notice>
}