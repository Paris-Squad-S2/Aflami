package com.paris_2.aflami.di

import com.datasource.remote.lists.ListsRemoteDataSourceImp
import com.datasource.remote.lists.service.RetrofitListApiService
import com.paris.domain.lists.repository.ListsRepository
import com.paris.domain.lists.useCase.GetListUseCase
import com.repository.lists.ListsRepositoryImp
import com.repository.lists.dataSource.remote.ListsRemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ListsApiModule {
    @Provides
    fun provideRetrofitListApiService(retrofit: Retrofit): RetrofitListApiService {
        return retrofit.create(RetrofitListApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideListRemoteDataSource(
        listApiService: RetrofitListApiService
    ): ListsRemoteDataSource {
        return ListsRemoteDataSourceImp(listApiService)
    }


    @Provides
    @Singleton
    fun provideListsTvShowRepository(
        listRemoteDataSource: ListsRemoteDataSource
    ): ListsRepository = ListsRepositoryImp(listRemoteDataSource)

    @Provides fun provideGetListUseCase(listRepository: ListsRepository) = GetListUseCase(listRepository)

}

