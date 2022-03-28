package io.tommy.kimsea.web.enums

enum class CategoryEnum(
    var desc:String,
    var mime:String,
    var fileType:String,
    val title:String,
) {
    ALL("전체", "*", "","전체 NFTs"),
    SPORTS("스포츠", "image/*", "image","스포츠관련 NFTs"),
    MUSIC("음악", "audio/*", "audio","음악관련 NFTs"),
    KPOP("K-POP", "audio/*", "audio","K-POP관련 NFTs"),
    TICKET("티케팅", "image/*", "image","티케팅관련 NFTs"),
    GAME("게임", "image/*", "image","게임관련 NFTs"),
    VIDEO("비디오", "video/*", "video","비디오관련 NFTs"),
    PHOTO("사진", "image/*", "image","사진관련 NFTs"),
    ART("디지털아트", "image/*", "image","디지털아트관련 NFTs"),
    PICTURE("그림", "image/*", "image","그림관련 NFTs"),
    VR("VR", "image/*", "image","VR아트관련 NFTs");

    data class CategoryDTO(
        val name:String,
        val desc:String,
        val mime:String,
        val title:String,
    )

    companion object {
        fun toList() = values().map { c-> CategoryDTO(c.name, c.desc, c.mime, c.title) }.toList()
        fun toMap() = values().map { it.name to CategoryDTO(it.name, it.desc, it.mime, it.title) }.toMap()
    }
}