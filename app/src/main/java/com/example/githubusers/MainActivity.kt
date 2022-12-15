package com.example.githubusers

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.githubusers.databinding.ActivityMainBinding
import com.example.githubusers.models.ListUserResponseItem
import com.example.githubusers.viewmodels.GithubUserViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var rvUser: RecyclerView

    private lateinit var binding: ActivityMainBinding
    private val githubUserViewModel by viewModels<GithubUserViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rvUser = findViewById(R.id.rv_user)
        rvUser.setHasFixedSize(true)

        githubUserViewModel.listUserResponse.observe(this){
            listUserResponse -> setListUserData(listUserResponse)
        }
    }

    private fun setListUserData(listUser: List<ListUserResponseItem>) {
        rvUser.layoutManager = LinearLayoutManager(this)
        val listUserAdapter = ListUserAdapter(listUser)
        rvUser.adapter = listUserAdapter

        listUserAdapter.setOnItemClickCallback(object: ListUserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: ListUserResponseItem, index: Int) {
                showSelectedUser(data)
//                val intent = Intent(this@MainActivity, UserDetailActivity::class.java)
//                intent.putExtra(UserDetailActivity.EXTRA_USER, data)
//                startActivity(intent)
            }
        })
    }

    private fun showSelectedUser(user: ListUserResponseItem) {
        Toast.makeText(this, "Kamu memilih " + user.login, Toast.LENGTH_SHORT).show()
    }
}