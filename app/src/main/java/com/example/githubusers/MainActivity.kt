package com.example.githubusers

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.githubusers.databinding.ActivityMainBinding
import com.example.githubusers.models.UserResponseItem
import com.example.githubusers.viewmodels.GithubUserViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var rvUser: RecyclerView

    private lateinit var binding: ActivityMainBinding
    private val githubUserViewModel by viewModels<GithubUserViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        rvUser = findViewById(R.id.rv_user)
        rvUser.setHasFixedSize(true)

        githubUserViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        binding.btnSend.setOnClickListener { view ->
            if (binding.searchInput.text.toString() != "") {
                githubUserViewModel.findUser(binding.searchInput.text.toString())
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(view.windowToken, 0)
            } else {
                githubUserViewModel.findAllUser()
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(view.windowToken, 0)
            }
        }

        githubUserViewModel.listUserResponse.observe(this){
            setListUserData(it)

            if (githubUserViewModel.listUserResponse.value?.isEmpty() == true) {
                showNotFoundMessage(true)
            } else {
                showNotFoundMessage(false)
            }
        }
    }

    private fun setListUserData(listUser: List<UserResponseItem>) {
        rvUser.layoutManager = LinearLayoutManager(this)
        val listUserAdapter = ListUserAdapter(listUser)
        rvUser.adapter = listUserAdapter

        listUserAdapter.setOnItemClickCallback(object: ListUserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: UserResponseItem, index: Int) {
                showSelectedUser(data)
//                val intent = Intent(this@MainActivity, UserDetailActivity::class.java)
//                intent.putExtra(UserDetailActivity.EXTRA_USER, data)
//                startActivity(intent)
            }
        })
    }

    private fun showSelectedUser(user: UserResponseItem) {
        Toast.makeText(this, "Kamu memilih " + user.login, Toast.LENGTH_SHORT).show()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showNotFoundMessage(show: Boolean) {
        binding.notFound.visibility = if (show) View.VISIBLE else View.GONE
    }
}