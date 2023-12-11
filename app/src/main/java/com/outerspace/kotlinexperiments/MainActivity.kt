package com.outerspace.kotlinexperiments

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2

const val FIRST_ENTRY = "first-entry"

class MainActivity : AppCompatActivity() {
    private lateinit var viewPager: ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewPager = findViewById(R.id.pager)

        viewPager.adapter = object: FragmentStateAdapter(this) {
            val first: FirstFragment by lazy { FirstFragment(0) }
            val second: FirstFragment by lazy { FirstFragment(1) }

            val fragmentList: MutableList<Fragment> = mutableListOf(
                second, first
            )

            override fun getItemCount(): Int = fragmentList.size

            override fun createFragment(position: Int): Fragment {
                return fragmentList[position]
            }
        }

    }
}