package com.example.uistatewithsealdclass

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.example.uistatewithsealdclass.databinding.ActivityMainBinding
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val mainViewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        collectFlow()
        binding.apply {
            btnReset.setOnClickListener {
                mainViewModel.reset()
            }
            btnSuccess.setOnClickListener {
                mainViewModel.doSuccess()
            }
            btnFailed.setOnClickListener {
                mainViewModel.doFailed()
            }
        }
    }

    private fun collectFlow() {
        lifecycleScope.launch {
            mainViewModel.uiState.collect { uiState ->
                binding.ivError.isVisible = uiState is State.Failure
                binding.progressBar.isVisible = uiState is State.Loading
                binding.tvResult.isVisible = uiState is State.Success

                if (uiState is State.Success) {
                    binding.tvResult.text = uiState.name
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.unbind()
    }
}