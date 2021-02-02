package com.phover.api

import com.google.gson.annotations.SerializedName
import com.phover.model.RoverPhoto

class ApiResponse(
    @SerializedName("photos")
    val photos: List<RoverPhoto>
)