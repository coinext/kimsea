package io.tommy.kimsea.web.services

import io.tommy.kimsea.web.entity.domain.Notice
import io.tommy.kimsea.web.entity.repository.NoticeRepository
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service

@Service
class NoticeService(
    private val noticeRepository: NoticeRepository
) {
    fun getRecentlyNotice() : Notice? {
        return noticeRepository.findFirstByDelDttmIsNullOrderByRegDttmDesc()
    }

    fun getNotices(pageNo:Int, pageSize:Int) : List<Notice> {
        return noticeRepository.findAllByDelDttmIsNullOrderByRegDttmDesc(PageRequest.of(pageNo, pageSize))
    }
}