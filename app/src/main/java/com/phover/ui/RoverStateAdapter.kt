package com.phover.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class RoverStateAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
        FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> {
                val fragment = RoverFragment()
                val bundle = Bundle()
                bundle.putString("rover", "curiosity")
                fragment.arguments = bundle
                fragment
            } 1 -> {
                val fragment = RoverFragment()
                val bundle = Bundle()
                bundle.putString("rover", "opportunity")
                fragment.arguments = bundle
                fragment
            }
            else -> {
                val fragment = RoverFragment()
                val bundle = Bundle()
                bundle.putString("rover", "spirit")
                fragment.arguments = bundle
                fragment
            }
        }
    }
}