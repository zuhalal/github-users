package com.example.githubusers.adapter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.githubusers.fragment.FollowerFragment

class SectionsPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {
    var usernameUrl: String = ""

    override fun createFragment(position: Int): Fragment {
        val fragment: Fragment = FollowerFragment()

        fragment.arguments = Bundle().apply {
            putInt(FollowerFragment.ARG_SECTION_NUMBER, position)
            putString(FollowerFragment.ARG_USERNAME_URL, usernameUrl)
        }

        return fragment
    }

    override fun getItemCount(): Int {
        return 2
    }
}