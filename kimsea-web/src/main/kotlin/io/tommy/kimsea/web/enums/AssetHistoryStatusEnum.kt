package io.tommy.kimsea.web.enums

enum class AssetHistoryStatusEnum(
    val title:String,
    var desc:String,
) {
    CREATE("생성", "NTF 생성"),
    SELL("판매", "NFT 판매" ),
    DELETE("삭제", "NFT 삭제"),
    DEPOSIT("입금", "NFT 입금"),
    WITHDRAW("출금", "NFT 출금"),
    ;


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