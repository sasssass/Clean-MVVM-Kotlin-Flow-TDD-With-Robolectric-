package ir.search.data.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ir.search.data.datasource.remote.SearchService
import ir.search.data.repository.SearchingRepositoryImp
import ir.search.domain.repository.SearchingRepository
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataSearchModule {
    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit) : SearchService = retrofit
        .create(SearchService::class.java)


    @Module
    @InstallIn(SingletonComponent::class)
    abstract class BindRepoes{
        @Binds
        abstract fun bindSearchingRepository(searchingRepositoryImp : SearchingRepositoryImp) : SearchingRepository
    }

}