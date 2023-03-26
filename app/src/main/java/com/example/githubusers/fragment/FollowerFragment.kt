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
import com.example.githubusers.adapter.ListUserAdapter
import com.example.githubusers.databinding.FragmentFollowerBinding
import com.example.githubusers.data.remote.models.UserResponseItem
import com.example.githubusers.viewmodels.GithubUserViewModel
import com.example.githubusers.viewmodels.ViewModelFactory
import com.example.githubusers.data.Result

class FollowerFragment : Fragment() {
    private lateinit var rvUser: RecyclerView

    private lateinit var binding: FragmentFollowerBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFollowerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val factory: ViewModelFactory = ViewModelFactory.getInstance(requireActivity())
        val githubUserViewModel: GithubUserViewModel by viewModels { factory }

        rvUser = binding.rvUserFollower

        val index = arguments?.getInt(ARG_SECTION_NUMBER)
        val username = arguments?.getString(ARG_USERNAME_URL)

        if (username != null) {
            if (index == 0) {
                githubUserViewModel.findAllUserFollower(username)
                    .observe(viewLifecycleOwner) { result ->
                        if (result != null) {
                            when (result) {
                                is Result.Loading -> {
                                    binding.progressBar.visibility = View.VISIBLE
                                }
                                is Result.Success -> {
                                    binding.progressBar.visibility = View.GONE
                                    val data = result.data

                                    setListUserData(data)

                                    if (data.isNotEmpty()) {
                                        if (data.size > 3) {
                                            binding.rvUserFollower.minimumHeight = 1000
                                        }
                                        showNotFoundMessage(false)
                                    } else {
                                        showNotFoundMessage(true)
                                    }
                                }
                                is Result.Error -> {
                                    binding.progressBar.visibility = View.GONE
                                    Toast.makeText(
                                        activity,
                                        "Terjadi kesalahan " + result.error,
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        }
                    }
            } else {
                githubUserViewModel.findAllUserFollowing(username)
                    .observe(viewLifecycleOwner) { result ->
                        if (result != null) {
                            when (result) {
                                is Result.Loading -> {
                                    binding.progressBar.visibility = View.VISIBLE
                                }
                                is Result.Success -> {
                                    binding.progressBar.visibility = View.GONE
                                    val data = result.data

                                    setListUserData(data)

                                    if (data.isNotEmpty()) {
                                        if (data.size > 3) {
                                            binding.rvUserFollower.minimumHeight = 1000
                                        }
                                        showNotFoundMessage(false)
                                    } else {
                                        showNotFoundMessage(true)
                                    }
                                }
                                is Result.Error -> {
                                    binding.progressBar.visibility = View.GONE
                                    Toast.makeText(
                                        activity,
                                        "Terjadi kesalahan " + result.error,
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        }
                    }
            }
        }

        githubUserViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }
    }

    private fun setListUserData(listUser: List<UserResponseItem>) {
        rvUser.layoutManager = LinearLayoutManager(requireContext())
        rvUser.setHasFixedSize(true)
        val listUserAdapter = ListUserAdapter()
        listUserAdapter.setListUser(listUser)
        rvUser.adapter = listUserAdapter

        listUserAdapter.setOnItemClickCallback(object :
            ListUserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: UserResponseItem, index: Int) {
                showSelectedUser(data)
            }
        })
    }

    private fun showSelectedUser(user: UserResponseItem) {
        Toast.makeText(activity, "Username: ${user.login}", Toast.LENGTH_SHORT)
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