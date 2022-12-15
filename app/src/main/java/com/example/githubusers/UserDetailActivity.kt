
package com.example.githubusers

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide

class UserDetailActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_USER = "extra_user"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_detail)

        val user = intent.getParcelableExtra<User>(EXTRA_USER) as User

        val image: ImageView = findViewById(R.id.iv_avatar)
        Glide.with(this).load(user.avatar).into(image)

        changeText(user);

        val btn: Button = findViewById(R.id.btn_share)
        btn.setOnClickListener {
            val openURL = Intent(Intent.ACTION_SEND)
            openURL.putExtra(Intent.EXTRA_TEXT, user.repository)
            openURL.type = "text/plain"

            val shareIntent = Intent.createChooser(openURL, null)
            startActivity(shareIntent)
        }
    }

    private fun changeText(user: User) {
        val name: TextView = findViewById(R.id.tv_name)
        name.text = user.name

        val username: TextView = findViewById(R.id.tv_username_detail)
        username.text = user.username

        val company: TextView = findViewById(R.id.tv_company_detail)
        company.text = user.company

        val follower: TextView = findViewById(R.id.tv_follower_detail)
        follower.text = user.follower.toString()

        val following: TextView = findViewById(R.id.tv_following_detail)
        following.text = user.following.toString()

        val location: TextView = findViewById(R.id.tv_location_detail)
        location.text = user.location

        val repository: TextView = findViewById(R.id.tv_repository_detail)
        repository.text = user.repository
    }
}