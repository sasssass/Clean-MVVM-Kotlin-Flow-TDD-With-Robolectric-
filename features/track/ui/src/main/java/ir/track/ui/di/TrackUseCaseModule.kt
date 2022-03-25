package ir.track.ui.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import ir.track.domain.repository.TrackRepository
import ir.track.domain.usecase.GetTrackUseCase

@Module
@InstallIn(FragmentComponent::class)
class TrackUseCaseModule {
    @Provides
    fun provideSearchAmongSongUseCase(repository: TrackRepository) =
        GetTrackUseCase(repository)
}