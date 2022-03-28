package io.tommy.kimsea.web.dto

class StatisticsDTO {
    data class TopAssetsDTO (
        val totalAssetsCnt:Long,
        val totalMovieAssetsCnt:Long,
        val totalMusicAssetsCnt:Long,
        val totalSellingAssetsCnt:Long,
        val totalTradedAmount:Long,
    )
}