package com.example.githubusers

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.githubusers.adapter.ListUserAdapter
import com.example.githubusers.data.Result
import com.example.githubusers.data.local.entity.FavoriteUserEntity
import com.example.githubusers.data.remote.models.UserResponseItem
import com.example.githubusers.databinding.ActivityFavoriteUserBinding
import com.example.githubusers.viewmodels.GithubUserViewModel
import com.example.githubusers.viewmodels.ViewModelFactory

class FavoriteUserActivity : AppCompatActivity() {
    private lateinit var rvUser: RecyclerView
    private lateinit var binding: ActivityFavoriteUserBinding
    private lateinit var data: List<FavoriteUserEntity>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        rvUser = binding.rvUserFavorite
        rvUser.setHasFixedSize(true)

        supportActionBar?.setDisplayHomeAsUpEnabled(true);

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
                        data = result.data
                        setListUserData(data)
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

        binding.btnSend.setOnClickListener { view ->
            if (binding.searchInput.text.toString() != "") {
                data.forEach { Log.d("BBB",
                    (binding.searchInput.text.toString().lowercase() in it.username.lowercase()).toString()
                ) }
                val filtered = data.filter { binding.searchInput.text.toString().lowercase() in it.username.lowercase() }
                setListUserData(filtered)
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(view.windowToken, 0)
            } else {
                setListUserData(data)
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(view.windowToken, 0)
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        onBackPressed()
        return true
    }

    private fun setListUserData(listUser: List<FavoriteUserEntity>) {
        rvUser.layoutManager = LinearLayoutManager(this)
        val items = arrayListOf<UserResponseItem>()
        listUser.map {
            val item = UserResponseItem(login = it.username, avatarUrl = it.avatarUrl ?: "", htmlUrl = it.htmlUrl, url = it.url)
            items.add(item)
        }
        val listUserAdapter = ListUserAdapter(items)
        rvUser.adapter = listUserAdapter

        listUserAdapter.setOnItemClickCallback(object : ListUserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: UserResponseItem, index: Int) {
                showSelectedUser(data)
                val intent = Intent(this@FavoriteUserActivity, UserDetailActivity::class.java)
                intent.putExtra(UserDetailActivity.EXTRA_USER, data)
                startActivity(intent)
            }
        })
    }

    private fun showSelectedUser(user: UserResponseItem) {
        Toast.makeText(this, "You choose " + user.login, Toast.LENGTH_SHORT).show()
    }
}