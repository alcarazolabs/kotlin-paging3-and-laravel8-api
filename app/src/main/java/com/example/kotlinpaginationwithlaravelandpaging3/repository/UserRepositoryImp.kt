package com.example.kotlinpaginationwithlaravelandpaging3.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.kotlinpaginationwithlaravelandpaging3.data.model.UserData
import com.example.kotlinpaginationwithlaravelandpaging3.data.remote.UserPagingDataSource
import kotlinx.coroutines.flow.Flow

//constante para establecer el número de registros por página. Se recomienda que sea igual al numero de registros
// que retorna el api en cada pagina.

const val NETWORK_PAGE_SIZE = 15

class UserRepositoryImp (private val webService: WebService ) : UserRepository {


    override suspend fun getUsers(user_name : String?): Flow<PagingData<UserData>> {
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                UserPagingDataSource(web_service = webService,
                                    user_name = user_name)
            }
        ).flow
    }


}