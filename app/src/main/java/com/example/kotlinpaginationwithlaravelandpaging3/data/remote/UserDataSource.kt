package com.example.kotlinpaginationwithlaravelandpaging3.data.remote

import android.net.Uri
import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.kotlinpaginationwithlaravelandpaging3.data.model.UserData
import com.example.kotlinpaginationwithlaravelandpaging3.repository.WebService
import retrofit2.HttpException
import java.io.IOException

class UserDataSource (private val web_service: WebService) : PagingSource<Int, UserData>() {


    override fun getRefreshKey(state: PagingState<Int, UserData>): Int? {

        return state.anchorPosition

    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UserData> {

        return try {
            //obtener numero de pagina
            // Fuente: https://www.youtube.com/watch?v=OeYlsoPQO2c
            val nextPage: Int = params.key ?: FIRST_PAGE_INDEX
            val response = web_service.getUsers(nextPage)
            Log.d("total", response[0].total.toString());

            var nextPageNumber: Int? = null
            if(response[0].next_page_url != null) { //siempre se accede a la posicion 0 ya que en cada peticion se retorna una lista
                val uri = Uri.parse(response[0].next_page_url)
                val nextPageQuery = uri.getQueryParameter("page")
                nextPageNumber = nextPageQuery?.toInt()
            }
            var prevPageNumber: Int? = null
            if(response[0].prev_page_url != null) {
                val uri = Uri.parse(response[0].prev_page_url)
                val prevPageQuery = uri.getQueryParameter("page")

                prevPageNumber = prevPageQuery?.toInt()
            }
            //Requiere de retornar una lista []
            LoadResult.Page(data = response[0].data,
                prevKey = prevPageNumber,
                nextKey = nextPageNumber)

        }
        catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }

    }

    /*
    * Si el api no retorna en el json la url de la pagina actual y de la sgte como laravel no usar el codigo de arriba
    * y solo retorna en el json algo como "page" : 1 y solo requiere de cambiar el valor del parametro page en la peticion GET
    * usar el sgt codigo:
    * https://gist.github.com/alcarazolabs/55217198cc77b12b7a1bdbc83dbd63d3
    * El codigo de eso repo obedece a:
    *   @GET("movie/top_rated")
    suspend fun getTopRatedMovies(
        @Query("language") language: String,
        @Query("page") page: Int
    ): ResponseItems<MovieResponse>
    *
    * Fuente: https://medium.com/nerd-for-tech/pagination-in-android-with-paging-3-retrofit-and-kotlin-flow-2c2454ff776e
    * */


    /**
     * Tambien se puede usar el sgt codigo para el metodo getRefreshKey funciona igual solo que es mas grande.
     * Fuente: https://medium.com/nerd-for-tech/pagination-in-android-with-paging-3-retrofit-and-kotlin-flow-2c2454ff776e
     * The refresh key is used for subsequent calls to PagingSource.Load after the initial load.
     */
    /*
    override fun getRefreshKey(state: PagingState<Int, UserData>): Int? {
        // We need to get the previous key (or next key if previous is null) of the page
        // that was closest to the most recently accessed index.
        // Anchor position is the most recently accessed index.
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
    */

    companion object {
        //Definir constante con número de página inicial
        private const val FIRST_PAGE_INDEX = 1
    }
}

