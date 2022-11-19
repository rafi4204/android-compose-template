package com.monstarlab.features.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.DisposableEffect
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.monstarlab.core.ui.AndroidTemplateApp
import com.monstarlab.core.ui.AppBackground
import com.monstarlab.core.ui.AppTheme
import com.monstarlab.core.ui.rememberAppState
import dagger.hilt.android.AndroidEntryPoint

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        splashScreen.setKeepOnScreenCondition {
            false
            //TODO
            //return true when fetching data from network
            // when (uiState) {
            //                Loading -> true
            //                is Success -> false
            //            }

        }
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setupNStack()
        setContent {
            val systemUiController = rememberSystemUiController()
            val darkTheme = isSystemInDarkTheme()

            // Update the dark content of the system bars to match the theme
            DisposableEffect(systemUiController, darkTheme) {
                systemUiController.systemBarsDarkContentEnabled = !darkTheme
                onDispose {}
            }
            AppTheme {
                AppBackground {
                    AndroidTemplateApp(
                        appState = rememberAppState(
                            windowSizeClass = calculateWindowSizeClass(
                                this
                            )
                        )
                    )
                }
            }
        }
    }
}