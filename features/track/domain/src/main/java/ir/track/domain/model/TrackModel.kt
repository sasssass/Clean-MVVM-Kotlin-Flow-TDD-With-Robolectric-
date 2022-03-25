package ir.track.domain.model

data class TrackModel(
    val album: Album,
    val artist: Artist,
    val bpm: Double,
    val id: Int,
    val preview: String,
    val title: String
)

data class Album(
    val cover: String,
    val id: Int,
    val title: String
)

data class Artist(
    val id: Int,
    val name: String,
    val picture: String
)

