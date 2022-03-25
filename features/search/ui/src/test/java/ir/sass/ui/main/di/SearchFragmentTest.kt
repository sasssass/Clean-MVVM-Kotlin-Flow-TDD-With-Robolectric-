package ir.sass.ui.main.di

import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import ir.sass.basedomain.model.Domain
import ir.search.domain.model.Album
import ir.search.domain.model.Artist
import ir.search.domain.model.SearchModel
import ir.search.domain.model.SearchModelData
import ir.search.domain.repository.SearchingRepository
import ir.search.domain.usecase.SearchAmongSongUseCase
import ir.search.ui.di.SearchUseCaseModule
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [SearchUseCaseModule::class]
)
class TestSearchUseCaseModule {
    @Provides
    fun provideSearchAmongSongUseCase() =
        SearchAmongSongUseCase(fakeRepoSuccess())
}

fun fakeRepoSuccess() = object : SearchingRepository{
    override fun getData(q: String): Flow<Domain<SearchModel>> = flow {
        emit(Domain.Result<SearchModel>(
            Result.success(SearchModel(
            listOf(
                SearchModelData(Album("fake cover",1,"fake title"), Artist(1,"fake name","fake pic"),
                    1,"fake pre","fake title")
            ),0
        ))))
    }
}

