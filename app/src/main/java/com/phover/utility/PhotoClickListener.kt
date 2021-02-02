package com.phover.utility

import com.phover.model.RoverPhoto

interface PhotoClickListener {
    fun onClick(photo: RoverPhoto)
}