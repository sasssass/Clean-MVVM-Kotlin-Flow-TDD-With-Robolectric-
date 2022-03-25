package ir.sass.ui.main.di

import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import ir.sass.basedomain.model.Domain
import ir.track.domain.model.Album
import ir.track.domain.model.Artist
import ir.track.domain.model.TrackModel
import ir.track.domain.repository.TrackRepository
import ir.track.domain.usecase.GetTrackUseCase
import ir.track.ui.di.TrackUseCaseModule
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [TrackUseCaseModule::class]
)
class TestSearchUseCaseModule {
    @Provides
    fun provideSearchAmongSongUseCase() =
        GetTrackUseCase(fakeRepoSuccess())
}

fun fakeRepoSuccess() = object : TrackRepository {
    override fun getTrackData(id: Int): Flow<Domain<TrackModel>> = flow {
        emit(Domain.Result(
            Result.success(
                TrackModel(
                    Album("fake cover",1,"fake album"), Artist(1,"fake name","fake pic"),1.0,1,"fake url","fake title"
                )
            )
        ))
    }

}

