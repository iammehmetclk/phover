package com.phover.data

import androidx.paging.*
import com.phover.api.ApiService
import com.phover.model.RoverPhoto
import kotlinx.coroutines.flow.Flow

object MainRepository {

    private val apiService: ApiService by lazy { ApiService.create() }

    fun getCuriosity(camera: String?): Flow<PagingData<RoverPhoto>> {
        return Pager(
            config = PagingConfig(
                pageSize = 25,
                initialLoadSize = 25,
                enablePlaceholders = false
            ), pagingSourceFactory = { PhotoPagingSource(apiService, "curiosity", camera) }
        ).flow
    }

    fun getOpportunity(camera: String?): Flow<PagingData<RoverPhoto>> {
        return Pager(
            config = PagingConfig(
                pageSize = 25,
                initialLoadSize = 25,
                enablePlaceholders = false
            ), pagingSourceFactory = { PhotoPagingSource(apiService, "opportunity", camera) }
        ).flow
    }

    fun getSpirit(camera: String?): Flow<PagingData<RoverPhoto>> {
        return Pager(
            config = PagingConfig(
                pageSize = 25,
                initialLoadSize = 25,
                enablePlaceholders = false
            ), pagingSourceFactory = { PhotoPagingSource(apiService, "spirit", camera) }
        ).flow
    }
}

