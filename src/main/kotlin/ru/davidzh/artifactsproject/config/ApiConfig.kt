package ru.davidzh.artifactsproject.config

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.openapitools.client.apis.CharactersApi
import org.openapitools.client.apis.MapsApi
import org.openapitools.client.apis.MonstersApi
import org.openapitools.client.apis.MyCharactersApi
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Configuration
class ApiConfig(
    @Value("\${api.key}") private val apiKey: String,
) {

    companion object {
        const val BASE_URL = "https://api.artifactsmmo.com/"
    }

    @Bean
    fun getMyCharactersApi(): MyCharactersApi {
        return buildApi(MyCharactersApi::class.java)
    }

    @Bean
    fun getCharactersApi(): CharactersApi {
        return buildApi(CharactersApi::class.java)
    }

    @Bean
    fun getMapsApi(): MapsApi {
        return buildApi(MapsApi::class.java)
    }

    @Bean
    fun getMonstersApi(): MonstersApi {
        return buildApi(MonstersApi::class.java)
    }

    private fun <T> buildApi(clazz: Class<T>): T {

        val interceptor : HttpLoggingInterceptor = HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BASIC)
            .apply {
                level = HttpLoggingInterceptor.Level.BODY
            }

        val httpClient = OkHttpClient.Builder()
            .addInterceptor { chain ->
                val original = chain.request()
                val builder = original.newBuilder()
                    .header("Authorization", "Bearer $apiKey")
                val request = builder.build()
                chain.proceed(request)
            }
            .addInterceptor(interceptor)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient)
            .build()
        return retrofit.create(clazz)
    }

}