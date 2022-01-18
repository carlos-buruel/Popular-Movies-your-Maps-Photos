package com.example.movies.ui.view

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.movies.ui.view.fragment.MoviesFragment

class SlideAdapter(fa: FragmentActivity, var numPages: Int): FragmentStateAdapter(fa) {
    override fun getItemCount(): Int = numPages

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> MoviesFragment()
            else -> MoviesFragment()
        }
    }
}