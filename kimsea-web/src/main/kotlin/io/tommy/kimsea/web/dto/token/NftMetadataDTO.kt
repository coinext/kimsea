package io.tommy.kimsea.web.dto.token

data class NftMetadataDTO(
    val description:String,
    val external_url:String,
    val image:String,
    val name:String,
    val attributes:List<Attribute> = arrayListOf()
) {
    class Attribute {
        var trait_type: String? = null
        var value: String? = null
    }
}