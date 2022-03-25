package ir.track.data.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ir.track.data.datasource.remote.TrackService
import ir.track.data.repository.TrackRepositoryImp
import ir.track.domain.repository.TrackRepository
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataTrackModule {
    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit) : TrackService = retrofit
        .create(TrackService::class.java)


    @Module
    @InstallIn(SingletonComponent::class)
    abstract class BindRepoes{
        @Binds
        abstract fun bindTrackRepository(trackRepositoryImp : TrackRepositoryImp) : TrackRepository
    }

}