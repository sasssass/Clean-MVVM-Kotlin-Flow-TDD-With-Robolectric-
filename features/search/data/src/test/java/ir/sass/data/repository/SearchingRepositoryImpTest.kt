package ir.sass.data.repository

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import ir.sass.basedomain.model.Domain
import ir.sass.data.base.BaseDataTest
import ir.search.data.datasource.remote.SearchService
import ir.search.data.model.SearchModelDto
import ir.search.data.repository.SearchingRepositoryImp
import ir.search.domain.model.SearchModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.test.runTest
import org.junit.Test

class SearchingRepositoryImpTest : BaseDataTest(){
    val apiService: SearchService = mock()
    val mockSearchModelDto : SearchModelDto = mock()
    val repository = SearchingRepositoryImp(apiService)

    @ExperimentalCoroutinesApi
    @Test
    fun `api service should call getSearchModel when we call getData from Repository`() = testScope.runTest{
        whenever(apiService.getSearchModel("Rammstein")).thenReturn(mockSearchModelDto)
        repository.getData("Rammstein").collect {
            verify(apiService, times(1)).getSearchModel("Rammstein")
        }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `repository must emit a Progress from Domain before emitting the actual data then it must emit Result`() = testScope.runTest{
        var flagHelper = "None"
        repository.getData("Rammstein").collect {
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