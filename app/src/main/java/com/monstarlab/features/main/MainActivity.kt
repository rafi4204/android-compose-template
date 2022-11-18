package com.monstarlab.features.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.monstarlab.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(R.layout.activity_main) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupNStack()
    }
}
