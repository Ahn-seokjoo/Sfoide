package com.example.sfoide.ext

import com.example.sfoide.data.source.RemoteUserListDataSource
import com.example.sfoide.data.source.remote.RemoteDataSourceImpl
import com.example.sfoide.data.source.remote.UserApi
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
object SFoideModule {
    private const val BASE_URL = "https://randomuser.me/"

    @Singleton
    @Provides
    fun provideRemoteUserListDataSource(userApi: UserApi): RemoteUserListDataSource =
        RemoteDataSourceImpl(userApi)

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
}
