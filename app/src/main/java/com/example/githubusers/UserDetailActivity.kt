
package com.example.githubusers

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.githubusers.adapter.SectionsPagerAdapter
import com.example.githubusers.databinding.ActivityUserDetailBinding
import com.example.githubusers.models.UserDetail
import com.example.githubusers.viewmodels.GithubUserViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class UserDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUserDetailBinding
    private val githubUserViewModel by viewModels<GithubUserViewModel>()

    companion object {
        const val EXTRA_USER = "extra_user"
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.follower,
            R.string.following
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_detail)

        binding = ActivityUserDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val userUrl = intent.getStringExtra(EXTRA_USER)

        githubUserViewModel.userDetail.observe(this) {
            setUserDetailData(it)
        }

        githubUserViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        if (userUrl != null && userUrl !== "") {
            githubUserViewModel.findUserByUrl(userUrl)
        }

        val btn: Button = binding.btnShare
        btn.setOnClickListener {
            val openURL = Intent(Intent.ACTION_SEND)
            openURL.putExtra(Intent.EXTRA_TEXT, githubUserViewModel.userDetail.value?.htmlUrl)
            openURL.type = "text/plain"

            val shareIntent = Intent.createChooser(openURL, null)
            startActivity(shareIntent)
        }

        val sectionsPagerAdapter = SectionsPagerAdapter(this)

        if (userUrl != null && userUrl !== "") {
            sectionsPagerAdapter.usernameUrl = userUrl
        }

        val viewPager: ViewPager2 = binding.viewPager
        viewPager.adapter = sectionsPagerAdapter

        val tabs: TabLayout = binding.tabs

        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
        supportActionBar?.elevation = 0f
    }

    private fun setUserDetailData(user: UserDetail) {
        val image: ImageView = binding.ivAvatar
        Glide.with(this).load(user.avatarUrl).into(image)

        val name: TextView = binding.tvName
        name.text = user.name

        val username: TextView = binding.tvUsernameDetail
        username.text = user.htmlUrl

        val company: TextView = binding.tvCompanyDetail
        company.text = user.company ?: "Tidak ada perusahaan"

        val follower: TextView = binding.tvFollowerDetail
        follower.text = user.followers.toString()

        val following: TextView = binding.tvFollowingDetail
        following.text = user.following.toString()

        val location: TextView = binding.tvLocationDetail
        location.text = user.location ?: "Tidak ada lokasi"

        val repository: TextView = binding.tvRepositoryDetail
        repository.text = user.htmlUrl
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}