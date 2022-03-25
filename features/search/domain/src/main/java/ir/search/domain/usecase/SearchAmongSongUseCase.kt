package ir.search.domain.usecase

import ir.sass.basedomain.model.Domain
import ir.sass.basedomain.useCase.MotherUseCase
import ir.search.domain.model.SearchModel
import ir.search.domain.repository.SearchingRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchAmongSongUseCase @Inject constructor(
    private val repository: SearchingRepository
        ): MotherUseCase<String, SearchModel>() {
    override fun invoke(input: String): Flow<Domain<SearchModel>> = repository.getData(input)
}