package com.example.githubusers

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CompoundButton
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import com.example.githubusers.databinding.ActivityDarkModeBinding
import com.example.githubusers.viewmodels.DarkModeViewModel
import com.example.githubusers.viewmodels.ViewModelFactory

class DarkModeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDarkModeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDarkModeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val switchTheme = binding.switchTheme

        val factory: ViewModelFactory = ViewModelFactory.getInstance(this)
        val darkModeViewModel: DarkModeViewModel by viewModels { factory }

        darkModeViewModel.getThemeSettings().observe(
            this
        ) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                switchTheme.isChecked = true
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                switchTheme.isChecked = false
            }
        }

        switchTheme.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
            darkModeViewModel.saveThemeSetting(isChecked)
        }
    }
}