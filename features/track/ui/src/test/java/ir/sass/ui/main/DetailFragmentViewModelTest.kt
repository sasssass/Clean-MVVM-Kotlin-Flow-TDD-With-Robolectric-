package ir.sass.ui.main

import android.media.MediaPlayer
import app.cash.turbine.test
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import ir.sass.basedomain.model.Domain
import ir.sass.ui.base.BaseTest
import ir.track.domain.model.Album
import ir.track.domain.model.Artist
import ir.track.domain.model.PlayState
import ir.track.domain.model.TrackModel
import ir.track.domain.repository.TrackRepository
import ir.track.domain.usecase.GetTrackUseCase
import ir.track.ui.fragment.main.DetailFragmentViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.robolectric.shadow.api.Shadow

class DetailFragmentViewModelTest : BaseTest()  {

    @ExperimentalCoroutinesApi
    @Test
    fun `when we call getData the use-case must be invoked and pass proper data`() = testScope.runTest{
        val useCase = GetTrackUseCase(fakeRepoSuccess())
        val viewModel = DetailFragmentViewModel(useCase)
        viewModel.getData(1)
        viewModel.data.test {
            delay(1)
            assert(expectMostRecentItem()!!.title == "fake title")
        }
        viewModel.onCleared()
    }

//    @ExperimentalCoroutinesApi
//    @Test
//    fun `media player must has State such as Play,Pause,NotInit,Done and viewModel must controls it`() = testScope.runTest{
//        val useCase = GetTrackUseCase(fakeRepoSuccess())
//        val viewModel = DetailFragmentViewModel(useCase)
//        val mediaPlayerFake : MediaPlayer = mock()
//
//        assert(viewModel.playState.value == PlayState.NOT_INIT)
//
//        viewModel.setMediaPlayer(mediaPlayerFake)
//        whenever(mediaPlayerFake.getCurrentPosition()).thenReturn(100)
//        whenever(mediaPlayerFake.getDuration()).thenReturn(1000)
//        whenever(mediaPlayerFake.isPlaying()).thenReturn(true)
//
//        viewModel.changeStateToPlaying()
//
//        assert(viewModel.playState.value == PlayState.PLAYING)
//
//        viewModel.pause()
//
//        assert(viewModel.playState.value == PlayState.PAUSE)
//
//        whenever(mediaPlayerFake.isPlaying()).thenReturn(false)
//
//        viewModel.play()
//
//        assert(viewModel.playState.value == PlayState.PLAYING)
//
//        viewModel.onCleared()
//    }
}

fun fakeRepoSuccess() = object : TrackRepository {
    override fun getTrackData(id: Int): Flow<Domain<TrackModel>> = flow {
        emit(
            Domain.Result(
            Result.success(
                TrackModel(
                    Album("fake cover",1,"fake album"), Artist(1,"fake name","fake pic"),1.0,1,"fake url","fake title"
                )
            )
        ))
    }

}

fun fakeRepoFailed() = object : TrackRepository {
    override fun getTrackData(id: Int): Flow<Domain<TrackModel>> = flow {
        emit(Domain.Result(
            Result.failure(
                Throwable("Test Error")
            )
        ))
    }

}
