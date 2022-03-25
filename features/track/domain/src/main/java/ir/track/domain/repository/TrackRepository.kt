package ir.track.domain.repository

import ir.sass.basedomain.model.Domain
import ir.track.domain.model.TrackModel
import kotlinx.coroutines.flow.Flow

interface TrackRepository {
    fun getTrackData(id : Int) : Flow<Domain<TrackModel>>
}