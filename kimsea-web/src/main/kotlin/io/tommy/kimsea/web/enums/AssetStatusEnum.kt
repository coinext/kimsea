package io.tommy.kimsea.web.enums

enum class AssetStatusEnum(
    var desc:String,
    val title:String,
) {
    ALL("전체", "나의 전체 NFTs"), PLACED("소유", "나의 소유중인 NTFs"), SELL("판매중", "나의 판매중인 NFTs" ), BUY("구매중", "나의 구매중인 NFTs"), WITHDRAWING("출금중", "나의 출금중인 NFTs"), WITHDRAW("출금", "나의 출금된 NFTs"), DELETE("삭제", "나의 삭제된 NFTs");


    data class AssetStatusDTO(
        val name:String,
        val desc:String,
        val title:String,
    )

    companion object {
        fun toList() = values().map { c-> AssetStatusDTO(c.name, c.desc, c.title) }.toList()
        fun toMap() = values().map { it.name to AssetStatusDTO(it.name,it.desc,it.title)}.toMap()
    }
}