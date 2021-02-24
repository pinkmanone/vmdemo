package com.wc.learn.utils

import androidx.core.view.get
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomnavigation.BottomNavigationView

/**
 * BottomNavigationView联动viewpager
 */
object BottomNavigationViewPagerHelper {

    fun setupWithViewPager(nav: BottomNavigationView, pager: ViewPager) {
        val menu = nav.menu
        nav.setOnNavigationItemSelectedListener {
            for (i in 0 until menu.size()) {
                val item = menu[i]
                if (item.itemId == it.itemId) {
                    pager.currentItem = i
                }
            }
            return@setOnNavigationItemSelectedListener true
        }
        pager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }

            override fun onPageSelected(position: Int) {
                nav.selectedItemId = menu[position].itemId
            }
        })
    }

    fun setupWithViewPager(nav: BottomNavigationView, pager: ViewPager2) {
        val menu = nav.menu
        nav.setOnNavigationItemSelectedListener {
            for (i in 0 until menu.size()) {
                val item = menu[i]
                if (item.itemId == it.itemId) {
                    pager.currentItem = i
                }
            }
            return@setOnNavigationItemSelectedListener true
        }

        pager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                nav.selectedItemId = menu[position].itemId
            }
        })
    }
}