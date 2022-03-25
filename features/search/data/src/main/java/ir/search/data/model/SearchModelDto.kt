package ir.search.data.model

import com.google.gson.annotations.SerializedName
import ir.sass.basedomain.model.Mapper
import ir.search.domain.model.Album
import ir.search.domain.model.Artist
import ir.search.domain.model.SearchModel
import ir.search.domain.model.SearchModelData

data class SearchModelDto(
    @SerializedName("data")
    val data_: List<DataDto>,
    @SerializedName("total")
    val total: Int
) : Mapper<SearchModel> {
    override fun cast(): SearchModel {
        val dataMapped = data_.map {
            it.cast()
        }
        return SearchModel(dataMapped,total)
    }
}

data class DataDto(
    @SerializedName("album")
    val album: AlbumDto,
    @SerializedName("artist")
    val artist: ArtistDto,
    @SerializedName("id")
    val id: Int,
    @SerializedName("preview")
    val preview: String,
    @SerializedName("title")
    val title: String
): Mapper<SearchModelData>{
    override fun cast(): SearchModelData =
        SearchModelData(album.cast(),artist.cast(),id,preview,title)
}


data class AlbumDto(
    @SerializedName("cover")
    val cover: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("title")
    val title: String
): Mapper<Album>{
    override fun cast(): Album =
        Album(cover,id,title)
}

data class ArtistDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("picture")
    val picture: String
) : Mapper<Artist>{

    override fun cast(): Artist =
        Artist(id,name,picture)

}