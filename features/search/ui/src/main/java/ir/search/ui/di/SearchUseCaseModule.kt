package ir.search.ui.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import ir.search.domain.repository.SearchingRepository
import ir.search.domain.usecase.SearchAmongSongUseCase

@Module
@InstallIn(FragmentComponent::class)
class SearchUseCaseModule {
    @Provides
    fun provideSearchAmongSongUseCase(repository: SearchingRepository) =
        SearchAmongSongUseCase(repository)
}