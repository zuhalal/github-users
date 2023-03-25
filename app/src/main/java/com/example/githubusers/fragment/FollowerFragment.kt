package com.example.githubusers.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.githubusers.R
import com.example.githubusers.adapter.ListUserFragmentAdapter
import com.example.githubusers.databinding.FragmentFollowerBinding
import com.example.githubusers.data.remote.models.UserResponseItem
import com.example.githubusers.viewmodels.GithubUserViewModel
import com.example.githubusers.viewmodels.ViewModelFactory

class FollowerFragment : Fragment() {
    private lateinit var rvUser: RecyclerView

    private lateinit var binding: FragmentFollowerBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFollowerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val factory: ViewModelFactory = ViewModelFactory.getInstance(requireActivity())
        val githubUserViewModel: GithubUserViewModel by viewModels { factory }

        rvUser = binding.rvUserFollower
        rvUser.setHasFixedSize(true)

        val index = arguments?.getInt(ARG_SECTION_NUMBER, 0)
        val username = arguments?.getString(ARG_USERNAME_URL)


        if (username != null) {
            if (index == 0) {
                if (githubUserViewModel.listFollower.value == null) {
                    githubUserViewModel.findAllUserFollower(username)
                }
            } else {
                if (githubUserViewModel.listFollowing.value == null) {
                    githubUserViewModel.findAllUserFollowing(username)
                }
            }
        }

        githubUserViewModel.listFollower.observe(viewLifecycleOwner) {
            setListUserData(it)
            if (it.isNotEmpty()) {
                if (it.size > 3) {
                    binding.rvUserFollower.minimumHeight = 1000
                }
            } else {
                showNotFoundMessage(true)
            }
        }

        githubUserViewModel.listFollowing.observe(viewLifecycleOwner) {
            setListUserData(it)
            if (it.isNotEmpty()) {
                if (it.size > 3) {
                    binding.rvUserFollower.minimumHeight = 1000
                }
            } else {
                showNotFoundMessage(true)
            }
        }

        githubUserViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }
    }

    private fun setListUserData(listUser: List<UserResponseItem>) {
        rvUser.layoutManager = LinearLayoutManager(requireContext())
        val listUserAdapter = ListUserFragmentAdapter(listUser)
        rvUser.adapter = listUserAdapter

        listUserAdapter.setOnItemClickCallback(object :
            ListUserFragmentAdapter.OnItemClickCallback {
            override fun onItemClicked(data: UserResponseItem, index: Int) {
                showSelectedUser(data)
            }
        })
    }

    private fun showSelectedUser(user: UserResponseItem) {
        Toast.makeText(activity, "Follower with username: ${user.login}", Toast.LENGTH_SHORT)
            .show()
    }


    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showNotFoundMessage(show: Boolean) {
        binding.notFound.visibility = if (show) View.VISIBLE else View.GONE
    }

    companion object {
        const val ARG_SECTION_NUMBER = "section_number"
        const val ARG_USERNAME_URL = "username_url"
    }
}