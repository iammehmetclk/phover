package com.phover.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.phover.R
import com.phover.model.RoverPhoto

class DetailsFragment : Fragment(R.layout.fragment_details) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val photo = arguments?.getSerializable("photo") as RoverPhoto
        val imageView: ImageView = view.findViewById(R.id.image_view)
        val roverName: TextView = view.findViewById(R.id.rover_name)
        val cameraText: TextView = view.findViewById(R.id.camera_text)
        val statusText: TextView = view.findViewById(R.id.status_text)
        val launchText: TextView = view.findViewById(R.id.launch_text)

        Glide.with(this).load(photo.imageUrl).into(imageView)
        val text1 = "Rover Name: ${photo.rover.name}"
        roverName.text = text1
        val text2 = "Camera: ${photo.camera.fullName}"
        cameraText.text = text2
        val text3 = "Status: ${photo.rover.status}"
        statusText.text = text3
        val text4 = "Launch Date: ${photo.rover.launchDate}"
        launchText.text = text4

        setHasOptionsMenu(true)
        (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onDestroyView() {
        (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        super.onDestroyView()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            requireActivity().supportFragmentManager.beginTransaction().remove(this).commit()
        }
        return super.onOptionsItemSelected(item)
    }
}