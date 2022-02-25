package com.example.sfoide.ext

import com.example.sfoide.data.repository.remote.RemoteUserListDataSource
import com.example.sfoide.data.repository.remote.RemoteUserListDataSourceImpl
import com.example.sfoide.data.repository.remote.UserApi
import com.example.sfoide.domain.usecases.GetUserListUseCase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class SFoideModule {
    @Binds
    @Singleton
    abstract fun bindUserListDataSource(remoteUserListDataSource: RemoteUserListDataSourceImpl): RemoteUserListDataSource

    companion object {

        @Singleton
        @Provides
        fun provideRetrofit(): Retrofit =
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        @Singleton
        @Provides
        fun provideUserApi(retrofit: Retrofit): UserApi =
            retrofit.create(UserApi::class.java)

        @Singleton
        @Provides
        fun provideRemoteUseCase(remoteUserListDataSource: RemoteUserListDataSource) = GetUserListUseCase(remoteUserListDataSource)

        private const val BASE_URL = "https://randomuser.me/"
    }
}
