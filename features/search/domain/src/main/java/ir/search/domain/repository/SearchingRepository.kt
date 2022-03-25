package ir.search.domain.repository

import ir.sass.basedomain.model.Domain
import ir.search.domain.model.SearchModel
import kotlinx.coroutines.flow.Flow

interface SearchingRepository {
    fun getData(q : String) : Flow<Domain<SearchModel>>
}