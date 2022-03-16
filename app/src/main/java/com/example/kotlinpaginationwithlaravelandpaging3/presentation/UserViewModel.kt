package com.example.kotlinpaginationwithlaravelandpaging3.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.kotlinpaginationwithlaravelandpaging3.data.model.UserData
import com.example.kotlinpaginationwithlaravelandpaging3.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserViewModel (private val repo : UserRepository) : ViewModel() {
        /*

        -   Este viewModel es un viewModel tradicional con viewModelFactory

        -   Tiene como parametro el repositorio y el repo accede al dataSource y este ultimo al webService.
         */

    suspend fun getUsers(): Flow<PagingData<UserData>> {
        return repo.getUsers()
            .map { it.map { it } }
            .cachedIn(viewModelScope)
    }
    // Si se quiere mapear los datos revisar:
    // 1. https://gist.github.com/ChristopherME/e0a621d87035bbdb7fff26086a710ea3#file-moviesviewmodel-kt
    // and: https://github.com/ChristopherME/movies-android/blob/feature/movies-paging3/common/models/src/main/java/com/christopher_elias/common/models/mapper/MovieMapperImpl.kt
}



class UserViewModelFactory(private val repo: UserRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(UserRepository::class.java).newInstance(repo)
    }
}