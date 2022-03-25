package ir.track.data.datasource.remote

import ir.track.data.model.TrackModelDto
import retrofit2.http.GET
import retrofit2.http.Path

interface TrackService {
    @GET("/track/{id}")
    suspend fun getTrack(@Path("id") input : Int) : TrackModelDto
}