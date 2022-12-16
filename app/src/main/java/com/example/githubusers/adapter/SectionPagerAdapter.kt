package com.example.githubusers.adapter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.githubusers.fragment.FollowerFragment
import com.example.githubusers.fragment.FollowingFragment

class SectionsPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {
    var usernameUrl: String = ""

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null

        when (position) {
            0 -> fragment = FollowerFragment()
            1 -> fragment = FollowingFragment()
        }

        if (fragment != null) {
            fragment.arguments = Bundle().apply {
                putInt(FollowerFragment.ARG_SECTION_NUMBER, position)
                putString(FollowerFragment.ARG_USERNAME_URL, usernameUrl)
            }
        }

        return fragment as Fragment
    }

    override fun getItemCount(): Int {
        return 2
    }
}