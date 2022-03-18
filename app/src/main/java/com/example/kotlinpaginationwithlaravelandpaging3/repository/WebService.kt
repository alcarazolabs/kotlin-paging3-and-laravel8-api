package com.example.kotlinpaginationwithlaravelandpaging3.repository

import com.example.kotlinpaginationwithlaravelandpaging3.application.AppConstants
import com.example.kotlinpaginationwithlaravelandpaging3.data.model.UserResponse
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface WebService {
    @Headers("Accept: application/json")
    @GET("users")
    suspend fun getUsers(@Query("page") query: Int,
                        @Query ("name") user_name : String? = null) : List<UserResponse>


}


object RetrofitClient {
    val webservice by lazy { //by lazy solo se inicializara la variable al momento de llamar a webservice. Es en el momento

        Retrofit.Builder()
            .baseUrl(AppConstants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create())) //convertir el json del servidor al objeto java/kitlin con gsonbuilder
            .build().create(WebService::class.java)
    }

}

