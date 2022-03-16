package com.example.kotlinpaginationwithlaravelandpaging3.repository

import com.example.kotlinpaginationwithlaravelandpaging3.application.AppConstants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetroInstance {

    companion object {
        val baseURL =  AppConstants.BASE_URL

        fun getRetroInstance(): Retrofit {
            return Retrofit.Builder()
                .baseUrl(baseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        }
    }
}