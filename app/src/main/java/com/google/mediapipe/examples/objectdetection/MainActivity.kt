/*
 * Copyright 2022 The TensorFlow Authors. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.mediapipe.examples.objectdetection

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.enroute.views.AlertView
import com.example.enroute.views.IdleView
import com.example.enroute.views.IntroView
import com.example.enroute.views.SettingsView
import com.example.enroute.views.StartDetectView
import com.google.mediapipe.examples.objectdetection.databinding.ActivityMainBinding

/**
 * Main entry point into our app. This app follows the single-activity pattern, and all
 * functionality is implemented in the form of fragments.
 */
class MainActivity : AppCompatActivity() {

    private lateinit var activityMainBinding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()
    lateinit var result: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment
        val navController = navHostFragment.navController
        activityMainBinding.navigation.setupWithNavController(navController)
        activityMainBinding.navigation.setOnNavigationItemReselectedListener {
            // ignore the reselection
        }

        enableEdgeToEdge()
        setContent {
            EnrouteTheme {
                // Shared mutable states
                val fontSizeState = remember { mutableStateOf(16f) } // Default font size
                val userNameState = remember { mutableStateOf("") } // Default user name
                val textToSpeechState =
                    remember { mutableStateOf(false) } // Default Text-to-Speech state
                val currentView = remember { mutableStateOf("intro") } // Track current view

                // Navigation logic based on currentView
                when (currentView.value) {
                    "intro" -> IntroView(
                        imagePath = R.drawable.custom_logo, // Replace with your image path
                        placeholderText = "Enter your name",
                        buttonText = "Next",
                        fontSizeState = fontSizeState,
                        userNameState = userNameState,
                        textToSpeechState = textToSpeechState,
                        currentView = currentView,
                        onNavigate = { destination ->
                            currentView.value = destination
                        } // Update currentView
                    )

                    "startDetect" -> StartDetectView(
                        userNameState = userNameState,
                        fontSizeState = fontSizeState,
                        textToSpeechState = textToSpeechState,
                        currentView = currentView,
                        onNavigate = { destination ->
                            currentView.value = destination
                        } // Update currentView
                    )

                    "idle" -> IdleView(
                        userNameState = userNameState,
                        fontSizeState = fontSizeState,
                        textToSpeechState = textToSpeechState,
                        currentView = currentView,
                        onNavigate = { destination ->
                            currentView.value = destination
                        } // Update currentView
                    )

                    "alert" -> AlertView(
                        detectedObject = result, // Example detected object for AlertView
                        currentView = currentView,
                        fontSizeState = fontSizeState,
                        onNavigate = { destination ->
                            currentView.value = destination
                        } // Update currentView
                    )

                    "settings" -> SettingsView(
                        fontSizeState = fontSizeState,
                        textToSpeechState = textToSpeechState,
                        userNameState = userNameState,
                        currentView = currentView,
                        onNavigate = { destination ->
                            currentView.value = destination
                        } // Update currentView
                    )
                }
            }
        }

        override fun onBackPressed() {
            finish()
        }
    }
}
