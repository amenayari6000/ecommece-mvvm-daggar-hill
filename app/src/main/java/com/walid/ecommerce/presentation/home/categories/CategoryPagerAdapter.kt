package com.walid.ecommerce.presentation.home.categories

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.walid.ecommerce.common.Constants.CATEGORY

class CategoryPagerAdapter(fragment: Fragment, private val categories: List<String>) :
    FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = categories.size

    override fun createFragment(position: Int): Fragment {
        val fragment = CategoryFragment()
        fragment.arguments = Bundle().apply {
            putString(CATEGORY, categories[position])
        }
        return fragment
    }
}