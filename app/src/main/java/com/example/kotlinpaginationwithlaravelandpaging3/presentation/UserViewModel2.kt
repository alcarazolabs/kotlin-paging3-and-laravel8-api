package com.example.kotlinpaginationwithlaravelandpaging3.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.kotlinpaginationwithlaravelandpaging3.data.model.UserData
import com.example.kotlinpaginationwithlaravelandpaging3.data.remote.UserDataSource
import com.example.kotlinpaginationwithlaravelandpaging3.repository.RetroInstance
import com.example.kotlinpaginationwithlaravelandpaging3.repository.UserRepository
import com.example.kotlinpaginationwithlaravelandpaging3.repository.WebService
import kotlinx.coroutines.flow.Flow

class UserViewModel2 () : ViewModel() {

    /*

    -   Este viewModel es un viewModel simple sin viewModelFactory este accede directamente
        al webservice sin usar un repositorio.
    -   Utiliza la clase RetroInstance.
     */

    var retrofitService: WebService

    init {
        retrofitService = RetroInstance.getRetroInstance().create(WebService::class.java)
    }

    fun getUsers(): Flow<PagingData<UserData>> {
        return Pager (config = PagingConfig(pageSize = 15, maxSize = 200),
            pagingSourceFactory = {UserDataSource(retrofitService)}).flow.cachedIn(viewModelScope)
    }

}
