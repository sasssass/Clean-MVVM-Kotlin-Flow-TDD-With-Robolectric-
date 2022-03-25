package ir.track.data.repository

import ir.sass.basedata.model.safeApi
import ir.sass.basedomain.model.Domain
import ir.track.data.datasource.remote.TrackService
import ir.track.domain.model.TrackModel
import ir.track.domain.repository.TrackRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class TrackRepositoryImp@Inject constructor(
    private val apiService: TrackService
) : TrackRepository {
    override fun getTrackData(id: Int): Flow<Domain<TrackModel>> = flow {
        emit(Domain.Progress())
        val response = safeApi { apiService.getTrack(id) }
        response.collect {
            emit(Domain.Result<TrackModel>(it))
        }
    }.flowOn(Dispatchers.IO)
}