package com.phover.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class RoverPhoto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("sol")
    val sol: Int,
    @SerializedName("camera")
    val camera: Camera,
    @SerializedName("img_src")
    val imageUrl: String,
    @SerializedName("earth_date")
    val date: String,
    @SerializedName("rover")
    val rover: Rover
) : Serializable {

    data class Camera (
        @SerializedName("id")
        val id: Int,
        @SerializedName("name")
        val name: String,
        @SerializedName("rover_id")
        val roverId: Int,
        @SerializedName("full_name")
        val fullName: String
    ) : Serializable

    data class Rover(
        @SerializedName("id")
        val id: Int,
        @SerializedName("name")
        val name: String,
        @SerializedName("landing_date")
        val landingDate: String,
        @SerializedName("launch_date")
        val launchDate: String,
        @SerializedName("status")
        val status: String
    ) : Serializable
}