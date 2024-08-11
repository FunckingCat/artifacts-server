package ru.davidzh.artifactsproject.config

import okhttp3.OkHttpClient
import org.openapitools.client.apis.MyCharactersApi
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Configuration
class ApiConfig {

    companion object {
        const val API_KEY: String = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VybmFtZSI6Ikt1a296aCIsInBhc3N3b3JkX2NoYW5nZWQiOiIifQ.B1g6XhZwgTO0QZ4Yc491rWp8ay-OG8EfjeF14Q3akd8"
    }

    @Bean
    fun getCharacterService(): MyCharactersApi {
        val httpClient = OkHttpClient.Builder()
                .addInterceptor { chain ->
                    val original = chain.request()
                    val builder = original.newBuilder()
                            .header("Authorization", API_KEY)
                    val request = builder.build()
                    chain.proceed(request)
                }
                .build()

        val retrofit = Retrofit.Builder()
                .baseUrl("https://api.github.com")
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .build()
        return retrofit.create(MyCharactersApi::class.java)
    }

}