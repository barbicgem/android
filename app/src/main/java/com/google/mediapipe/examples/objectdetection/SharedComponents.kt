@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.enroute.views

import android.speech.tts.TextToSpeech
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.enroute.R

@Composable
fun NavigationRow(
    currentView: MutableState<String>,
    tts: TextToSpeech?,
    textToSpeechState: MutableState<Boolean>,
    modifier: Modifier = Modifier // Allow customization of the row's modifier
) {
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically, // Ensures icons are centered vertically
        modifier = modifier
            .fillMaxWidth()
            .background(Color.White.copy(alpha = 0.8f)) // Sliver of white background
            .padding(16.dp) // Padding for aesthetics
    ) {
        // Start Detect Icon
        Icon(
            painter = painterResource(id = R.drawable.ic_start),
            contentDescription = "Start Detect",
            tint = MaterialTheme.colorScheme.primary, // Ensures consistent theming
            modifier = Modifier.clickable {
                currentView.value = "startDetect"
                if (textToSpeechState.value) {
                    tts?.speak("Navigate to Start Detect", TextToSpeech.QUEUE_FLUSH, null, null)
                }
            }
        )

        // Idle Icon
        Icon(
            painter = painterResource(id = R.drawable.ic_idle),
            contentDescription = "Idle",
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.clickable {
                currentView.value = "idle"
                if (textToSpeechState.value) {
                    tts?.speak("Navigate to Idle", TextToSpeech.QUEUE_FLUSH, null, null)
                }
            }
        )

        // Settings Icon
        Icon(
            painter = painterResource(id = R.drawable.ic_settings),
            contentDescription = "Settings",
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.clickable {
                currentView.value = "settings"
                if (textToSpeechState.value) {
                    tts?.speak("Navigate to Settings", TextToSpeech.QUEUE_FLUSH, null, null)
                }
            }
        )
    }
}
