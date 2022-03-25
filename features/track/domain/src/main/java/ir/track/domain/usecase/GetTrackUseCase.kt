package ir.track.domain.usecase

import ir.sass.basedomain.model.Domain
import ir.sass.basedomain.useCase.MotherUseCase
import ir.track.domain.model.TrackModel
import ir.track.domain.repository.TrackRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTrackUseCase @Inject constructor(
    private val repository: TrackRepository
): MotherUseCase<Int, TrackModel>() {
    override fun invoke(input: Int): Flow<Domain<TrackModel>> = repository.getTrackData(input)
}