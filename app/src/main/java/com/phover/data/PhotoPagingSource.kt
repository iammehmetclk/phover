package com.phover.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.phover.api.ApiService
import com.phover.model.RoverPhoto
import com.phover.model.Param
import retrofit2.HttpException
import java.io.IOException

class PhotoPagingSource(
    private val apiService: ApiService,
    private val rover: String,
    private val camera: String?
) : PagingSource<Param, RoverPhoto>() {

    override suspend fun load(params: LoadParams<Param>): LoadResult<Param, RoverPhoto> {
        var sol = 1
        var page = 1
        params.key?.let {
            sol = it.sol
            page = it.page
        }
        return try {
            val response = when (rover) {
                "curiosity" -> {
                    apiService.getCuriosity(sol = sol, page = page, camera = camera)
                }
                "opportunity" -> {
                    apiService.getOpportunity(sol = sol, page = page, camera = camera)
                }
                else -> {
                    apiService.getSpirit(sol = sol, page = page, camera = camera)
                }
            }

            val photos = response.photos
            val nextKey = if (photos.isEmpty()) {
                Param(sol + 1, 1)
            } else {
                Param(sol, page + 1)
            }

            LoadResult.Page(
                data = photos,
                prevKey = null,
                nextKey = nextKey,
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Param, RoverPhoto>): Param {
        return Param(1, 1)
    }
}