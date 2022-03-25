package ir.sass.ui.main

import app.cash.turbine.test
import ir.sass.basedomain.model.Domain
import ir.sass.ui.base.BaseTest
import ir.search.domain.model.Album
import ir.search.domain.model.Artist
import ir.search.domain.model.SearchModel
import ir.search.domain.model.SearchModelData
import ir.search.domain.repository.SearchingRepository
import ir.search.domain.usecase.SearchAmongSongUseCase
import ir.search.ui.fragment.main.SearchFragmentViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.test.runTest
import org.junit.Test

class SearchFragmentViewModelTest : BaseTest() {

    @ExperimentalCoroutinesApi
    @Test
    fun `check if calling search() method would get from usecase`() = testScope.runTest{
        val useCase = SearchAmongSongUseCase(fakeRepoSuccess())
        val viewModel = SearchFragmentViewModel(useCase)
        viewModel.search()
        viewModel.data.test {
            delay(1)
            assert(expectMostRecentItem()!!.data_[0].title.equals("fake title"))
        }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `check if calling search() then we must inform View to hide the keyboard`() = testScope.runTest{
        val useCase = SearchAmongSongUseCase(fakeRepoSuccess())
        val viewModel = SearchFragmentViewModel(useCase)
        viewModel.search()
        viewModel.hideKeyBoard.test {
            delay(1)
            assert(expectMostRecentItem())
        }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `check if calling search() method and the result was failed then error is collected`() = testScope.runTest{
        val useCase = SearchAmongSongUseCase(fakeRepoFailed())
        val viewModel = SearchFragmentViewModel(useCase)
        viewModel.search()
        viewModel.error.test {
            delay(1)
            assert(expectMostRecentItem() == "Test Error")
        }
    }
}

fun fakeRepoSuccess() = object : SearchingRepository {
    override fun getData(q: String): Flow<Domain<SearchModel>> = flow {
        emit(
            Domain.Result<SearchModel>(
            Result.success(
                SearchModel(
                listOf(
                    SearchModelData(
                        Album("fake cover",1,"fake title"), Artist(1,"fake name","fake pic"),
                        1,"fake pre","fake title")
                ),0
            )
            )))
    }
}

fun fakeRepoFailed() = object : SearchingRepository{
    override fun getData(q: String): Flow<Domain<SearchModel>> = flow {
        emit(Domain.Result<SearchModel>(
            Result.failure(Throwable("Test Error"))))
    }
}