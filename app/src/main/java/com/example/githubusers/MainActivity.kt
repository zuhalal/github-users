package com.example.githubusers

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.githubusers.adapter.ListUserAdapter
import com.example.githubusers.databinding.ActivityMainBinding
import com.example.githubusers.data.remote.models.UserResponseItem
import com.example.githubusers.viewmodels.GithubUserViewModel
import com.example.githubusers.viewmodels.ViewModelFactory
import com.example.githubusers.data.Result
import com.example.githubusers.viewmodels.DarkModeViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var rvUser: RecyclerView
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        rvUser = binding.rvUser
        rvUser.setHasFixedSize(true)

        val factory: ViewModelFactory = ViewModelFactory.getInstance(this)
        val githubUserViewModel: GithubUserViewModel by viewModels { factory }

        githubUserViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        val darkModeViewModel: DarkModeViewModel by viewModels { factory }

        darkModeViewModel.getThemeSettings().observe(this
        ) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        binding.btnSend.setOnClickListener { view ->
            if (binding.searchInput.text.toString() != "") {
                githubUserViewModel.findUser(binding.searchInput.text.toString()).observe(this) {
                    when (it) {
                        is Result.Success -> {
                            if (it.data.isEmpty()) {
                                showNotFoundMessage(true)
                            } else {
                                showNotFoundMessage(false)
                            }
                        }
                        else -> {}
                    }
                }
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(view.windowToken, 0)
            } else {
                showNotFoundMessage(false)
                githubUserViewModel.findAllUser()
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(view.windowToken, 0)
            }
        }

        githubUserViewModel.findAllUser().observe(this) { result ->
            if (result != null) {
                when (result) {
                    is Result.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }
                    is Result.Success -> {
                        binding.progressBar.visibility = View.GONE
                        val data = result.data

                        setListUserData(data)
                    }
                    is Result.Error -> {
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(
                            this,
                            "Terjadi kesalahan " + result.error,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }

    override fun onStart() {
        binding.searchInput.text?.clear()
        super.onStart()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.favorite -> {
                val i = Intent(this, FavoriteUserActivity::class.java)
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(i)
                return true
            }
            R.id.setting -> {
                val i = Intent(this, DarkModeActivity::class.java)
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(i)
                return true
            }
        }
        return true
    }


    private fun setListUserData(listUser: List<UserResponseItem>) {
        rvUser.layoutManager = LinearLayoutManager(this)
        rvUser.setHasFixedSize(true)

        val listUserAdapter = ListUserAdapter()
        listUserAdapter.setListUser(listUser)

        rvUser.adapter = listUserAdapter

        listUserAdapter.setOnItemClickCallback(object : ListUserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: UserResponseItem, index: Int) {
                showSelectedUser(data)
                val intent = Intent(this@MainActivity, UserDetailActivity::class.java)
                intent.putExtra(UserDetailActivity.EXTRA_USER, data)
                startActivity(intent)
            }
        })
    }

    private fun showSelectedUser(user: UserResponseItem) {
        Toast.makeText(this, "You choose " + user.login, Toast.LENGTH_SHORT).show()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showNotFoundMessage(show: Boolean) {
        binding.notFound.visibility = if (show) View.VISIBLE else View.GONE
    }
}