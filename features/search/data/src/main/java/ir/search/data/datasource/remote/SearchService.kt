package ir.search.data.datasource.remote

import ir.search.data.model.SearchModelDto
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchService {
    @GET("/search")
    suspend fun getSearchModel(@Query("q") input : String) : SearchModelDto
}