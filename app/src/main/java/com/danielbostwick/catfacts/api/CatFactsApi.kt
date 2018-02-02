package com.danielbostwick.catfacts.api

import com.danielbostwick.catfacts.api.data.CatFact
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.jackson.JacksonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import rx.Single

interface CatFactsApi {

    companion object {
        const val API_ROOT = "http://catfacts-api-2.herokuapp.com/"

        fun create(apiRoot: String): CatFactsApi = Retrofit.Builder()
                .addConverterFactory(JacksonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(apiRoot)
                .client(createOkHttpClient())
                .build()
                .create(CatFactsApi::class.java)

        private fun createOkHttpClient() = OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                })
                .addInterceptor(TrafficStatInterceptor(100))
                .build()
    }

    @GET("catfacts")
    fun getAllCatfacts(): Single<List<CatFact>>

    @GET("catfacts/{id}")
    fun getCatfact(@Path("id") catfactId: Int): Single<CatFact>
}
