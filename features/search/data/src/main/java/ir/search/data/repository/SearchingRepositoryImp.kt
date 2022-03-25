package ir.search.data.repository

import ir.sass.basedata.model.safeApi
import ir.sass.basedomain.model.Domain
import ir.search.data.datasource.remote.SearchService
import ir.search.domain.model.SearchModel
import ir.search.domain.repository.SearchingRepository
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class SearchingRepositoryImp @Inject constructor(
    private val apiService: SearchService
) : SearchingRepository {
    override fun getData(q: String): Flow<Domain<SearchModel>> = flow {
        emit(Domain.Progress<SearchModel>())
        val response = safeApi { apiService.getSearchModel(q) }
        response.collect {
            emit(Domain.Result<SearchModel>(it))
        }
    }.flowOn(IO)
}