package com.example.testpayday.core.library.viewpager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.testpayday.core.base.presentation.BaseFragment

class BaseFragmentStateAdapter(
    rootActivity: FragmentActivity,
    private val fragments: List<BaseFragment>
) : FragmentStateAdapter(rootActivity) {

    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }
}
