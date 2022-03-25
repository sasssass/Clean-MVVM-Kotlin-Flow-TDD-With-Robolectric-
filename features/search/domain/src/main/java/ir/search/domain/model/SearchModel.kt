package ir.search.domain.model


data class SearchModel(
    val data_: List<SearchModelData>,
    val total: Int
)

data class SearchModelData(
    val album: Album,
    val artist: Artist,
    val id: Int,
    val preview: String,
    val title: String
)


data class Album(
    val cover: String,
    val id: Int,
    val title: String
){

}

data class Artist(
    val id: Int,
    val name: String,
    val picture: String
)