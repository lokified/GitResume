package com.loki.gitresume.di

import com.loki.gitresume.BuildConfig
import com.loki.gitresume.data.remote.api.GithubApi
import com.loki.gitresume.data.remote.repository.RepoRepositoryImpl
import com.loki.gitresume.domain.repository.RepoRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

private const val BASE_URL = "https://api.github.com/users/"

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor {
            val request = it.request().newBuilder()
                .addHeader("Authorization", "token ${BuildConfig.ACCESS_TOKEN}")
                .build()
            it.proceed(request)
        }.build()

    @Provides
    @Singleton
    fun providesRetrofit(): GithubApi {

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(GithubApi::class.java)
    }

    @Provides
    @Singleton
    fun provideApi(api: GithubApi): RepoRepository {
        return RepoRepositoryImpl(api)
    }
}