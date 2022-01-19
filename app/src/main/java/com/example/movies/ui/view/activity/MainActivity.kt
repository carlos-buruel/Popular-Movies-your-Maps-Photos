package com.example.movies.ui.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.movies.core.Constant
import com.example.movies.databinding.ActivityMainBinding
import com.example.movies.ui.view.SlideAdapter
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity: AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = SlideAdapter(this, 2)
        binding.run {
            viewPager.adapter = adapter

            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                val title = Constant.nameTabs[position]
                tab.text = title
            }.attach()
        }


    }
}