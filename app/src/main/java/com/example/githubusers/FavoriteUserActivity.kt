package com.example.githubusers

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.githubusers.adapter.ListFavoriteUserAdapter
import com.example.githubusers.data.Result
import com.example.githubusers.data.local.entity.FavoriteUserEntity
import com.example.githubusers.databinding.ActivityFavoriteUserBinding
import com.example.githubusers.viewmodels.GithubUserViewModel
import com.example.githubusers.viewmodels.ViewModelFactory

class FavoriteUserActivity : AppCompatActivity() {
    private lateinit var rvUser: RecyclerView
    private lateinit var binding: ActivityFavoriteUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        rvUser = binding.rvUserFavorite
        rvUser.setHasFixedSize(true)

        val factory: ViewModelFactory = ViewModelFactory.getInstance(this)
        val githubUserViewModel: GithubUserViewModel by viewModels { factory }

        githubUserViewModel.findAllFavoriteUser().observe(this) { result ->
            if (result != null) {
                when (result) {
                    is Result.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }
                    is Result.Success -> {
                        binding.progressBar.visibility = View.GONE
                        val newsData = result.data
                        setListUserData(newsData)
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
    }

    private fun setListUserData(listUser: List<FavoriteUserEntity>) {
        rvUser.layoutManager = LinearLayoutManager(this)
        val listUserAdapter = ListFavoriteUserAdapter(listUser)
        rvUser.adapter = listUserAdapter

        listUserAdapter.setOnItemClickCallback(object : ListFavoriteUserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: FavoriteUserEntity, index: Int) {
                showSelectedUser(data)
                val intent = Intent(this@FavoriteUserActivity, UserDetailActivity::class.java)
                intent.putExtra(UserDetailActivity.EXTRA_USER, data)
                startActivity(intent)
            }
        })
    }

    private fun showSelectedUser(user: FavoriteUserEntity) {
        Toast.makeText(this, "You choose " + user.username, Toast.LENGTH_SHORT).show()
    }
}