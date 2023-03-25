package com.example.githubusers

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatDelegate
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.githubusers.adapter.SectionsPagerAdapter
import com.example.githubusers.data.Result
import com.example.githubusers.data.local.entity.FavoriteUserEntity
import com.example.githubusers.databinding.ActivityUserDetailBinding
import com.example.githubusers.data.remote.models.UserDetail
import com.example.githubusers.data.remote.models.UserResponseItem
import com.example.githubusers.viewmodels.DarkModeViewModel
import com.example.githubusers.viewmodels.GithubUserViewModel
import com.example.githubusers.viewmodels.ViewModelFactory
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class UserDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUserDetailBinding
    private lateinit var userDetail: UserDetail

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val factory: ViewModelFactory = ViewModelFactory.getInstance(this)
        val githubUserViewModel: GithubUserViewModel by viewModels { factory }
        val darkModeViewModel: DarkModeViewModel by viewModels { factory }

        darkModeViewModel.getThemeSettings().observe(this
        ) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        val user = intent.getParcelableExtra<UserResponseItem>(EXTRA_USER)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = user?.login ?: "Detail"

        val userUrl = user?.url
        var isFavorited = false

        if (userUrl != null) {
//            if (githubUserViewModel.findUserByUrl(userUrl).value == null) {
//
//            }

            githubUserViewModel.findUserByUrl(userUrl).observe(this) { result ->
                if (result != null) {
                    when (result) {
                        is Result.Loading -> {
                            binding.progressBar.visibility = View.VISIBLE
                        }
                        is Result.Success -> {
                            binding.progressBar.visibility = View.GONE
                            val newsData = result.data
                            userDetail = newsData
                            setUserDetailData(newsData)
                        }
                        is Result.Error -> {
                            binding.progressBar.visibility = View.GONE
                            Toast.makeText(
                                this,
                                "Terjadi kesalahan" + result.error,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
        }

//        githubUserViewModel.userDetail.observe(this) {
//            setUserDetailData(it)
//        }

        val btnFav: Button = binding.btnFavorite

        githubUserViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        githubUserViewModel.findAllFavoriteUser().observe(this) { result ->
            if (result != null) {
                when (result) {
                    is Result.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }
                    is Result.Success -> {
                        binding.progressBar.visibility = View.GONE
                        val newsData = result.data
                        isFavorited = newsData.find{it.username == user.login}?.username !== null

                        if (!isFavorited) {
                            btnFav.text = getString(R.string.favorite_this_user)
                        } else {
                            btnFav.text = getString(R.string.unfavorite_this_user)
                        }
                    }
                    is Result.Error -> {
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(
                            this,
                            "Terjadi kesalahan" + result.error,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }

        val btn: Button = binding.btnShare
        btn.setOnClickListener {
            val openURL = Intent(Intent.ACTION_SEND)
            openURL.putExtra(Intent.EXTRA_TEXT, userDetail.htmlUrl)
            openURL.type = "text/plain"

            val shareIntent = Intent.createChooser(openURL, null)
            startActivity(shareIntent)
        }


        btnFav.setOnClickListener {
            if (!isFavorited) {
                githubUserViewModel.setFavoriteUser(
                    FavoriteUserEntity(
                        user.login,
                        user.url,
                        user.avatarUrl,
                        user.htmlUrl
                    )
                )
            } else {
                githubUserViewModel.deleteFavoriteUser(user.login)
            }
        }

        val sectionsPagerAdapter = SectionsPagerAdapter(this)

        if (userUrl !== "") {
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
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        onBackPressed()
        return true
    }

    private fun setUserDetailData(user: UserDetail) {
        binding.apply {
            Glide.with(applicationContext).load(user.avatarUrl).into(ivAvatar)

            tvName.text = user.name
            tvUsernameDetail.text = user.login
            tvCompanyDetail.text = user.company ?: "-"
            tvFollowerDetail.text = user.followers.toString()
            tvFollowingDetail.text = user.following.toString()
            tvLocationDetail.text = user.location ?: "-"
            tvRepositoryDetail.text = user.htmlUrl
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    companion object {
        const val EXTRA_USER = "extra_user"

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.follower, R.string.following
        )
    }
}