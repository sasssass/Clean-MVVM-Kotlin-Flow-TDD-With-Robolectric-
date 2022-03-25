package ir.track.data.model

import com.google.gson.annotations.SerializedName
import ir.sass.basedomain.model.Mapper
import ir.track.domain.model.Album
import ir.track.domain.model.Artist
import ir.track.domain.model.TrackModel

data class TrackModelDto(
    @SerializedName("album")
    val album: AlbumDto,
    @SerializedName("artist")
    val artist: ArtistDto,
    @SerializedName("bpm")
    val bpm: Double,
    @SerializedName("id")
    val id: Int,
    @SerializedName("preview")
    val preview: String,
    @SerializedName("title")
    val title: String
) : Mapper<TrackModel> {
    override fun cast(): TrackModel = TrackModel(
        album.cast(),artist.cast(),
        bpm,id,preview,title
    )
}

data class AlbumDto(
    @SerializedName("cover")
    val cover: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("title")
    val title: String
) : Mapper<Album>{
    override fun cast(): Album = Album(cover,id,title)
}

data class ArtistDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("picture")
    val picture: String
) : Mapper<Artist>{
    override fun cast(): Artist = Artist(id,name,picture)
}

