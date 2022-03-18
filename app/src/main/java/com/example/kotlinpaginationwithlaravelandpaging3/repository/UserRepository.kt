package com.example.kotlinpaginationwithlaravelandpaging3.repository

import androidx.paging.PagingData
import com.example.kotlinpaginationwithlaravelandpaging3.data.model.UserData
import com.example.kotlinpaginationwithlaravelandpaging3.data.model.UserResponse
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    suspend fun getUsers(user_name : String?) : Flow<PagingData<UserData>>

}