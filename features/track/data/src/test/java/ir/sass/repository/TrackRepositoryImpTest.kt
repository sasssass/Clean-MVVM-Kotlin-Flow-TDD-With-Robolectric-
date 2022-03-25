package ir.sass.data.repository

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import ir.sass.basedomain.model.Domain
import ir.sass.data.base.BaseDataTest
import ir.track.data.datasource.remote.TrackService
import ir.track.data.model.TrackModelDto
import ir.track.data.repository.TrackRepositoryImp
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.test.runTest
import org.junit.Test

class TrackRepositoryImpTest : BaseDataTest(){
    val apiService: TrackService = mock()
    val mockTrackModelDto : TrackModelDto = mock()
    val repository = TrackRepositoryImp(apiService)

    @ExperimentalCoroutinesApi
    @Test
    fun `api service should call getTrack when we call getTrackData from Repository`() = testScope.runTest{
        whenever(apiService.getTrack(1)).thenReturn(mockTrackModelDto)
        repository.getTrackData(1).collect {
            verify(apiService, times(1)).getTrack(1)
        }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `repository must emit a Progress from Domain before emitting the actual data then it must emit Result`() = testScope.runTest{
        var flagHelper = "None"
        repository.getTrackData(1).collect {
            when(flagHelper){
                "None"->{
                    assert(it is Domain.Progress)
                    flagHelper = "Progress"
                }
                "Progress"->{
                    assert(it is Domain.Result)
                }
            }
        }
    }
}