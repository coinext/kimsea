package io.tommy.kimsea.web.controllers.rest

import io.tommy.kimsea.web.dto.Response
import io.tommy.kimsea.web.entity.domain.Notice
import io.tommy.kimsea.web.services.NoticeService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/notices")
class NoticesRestController(
    private val noticeService: NoticeService
) {
    
    @GetMapping("/all")
    fun getNotices(@RequestParam pageNo:Int, @RequestParam pageSize:Int): Response<List<Notice>> {
        return Response(noticeService.getNotices(pageNo, pageSize))
    }

    @GetMapping("/recently")
    fun getRecentlyNotice() : Response<Notice?> {
        return Response(noticeService.getRecentlyNotice())
    }
}