package com.phover.ui

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.phover.data.MainRepository
import com.phover.model.RoverPhoto
import kotlinx.coroutines.flow.Flow

class MainViewModel : ViewModel() {

    var curiosityFilter: Int? = null
    var opportunityFilter: Int? = null
    var spiritFilter: Int? = null

    fun getCuriosity(camera: String?): Flow<PagingData<RoverPhoto>> {
        return MainRepository.getCuriosity(camera).cachedIn(viewModelScope)
    }

    fun getOpportunity(camera: String?): Flow<PagingData<RoverPhoto>> {
        return MainRepository.getOpportunity(camera).cachedIn(viewModelScope)
    }

    fun getSpirit(camera: String?): Flow<PagingData<RoverPhoto>> {
        return MainRepository.getSpirit(camera).cachedIn(viewModelScope)
    }
}