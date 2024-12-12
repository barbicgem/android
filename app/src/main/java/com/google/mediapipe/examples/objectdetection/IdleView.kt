@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.enroute.views

import android.speech.tts.TextToSpeech
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.foundation.clickable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.enroute.ui.theme.EnrouteTheme
import kotlinx.coroutines.delay
import java.util.*

@Composable
fun IdleView(
    userNameState: MutableState<String>,
    fontSizeState: MutableState<Float>,
    textToSpeechState: MutableState<Boolean>,
    currentView: MutableState<String>,
    onNavigate: (String) -> Unit
) {
    val context = LocalContext.current
    var tts: TextToSpeech? by remember { mutableStateOf(null) }

    // Initialize TextToSpeech
    LaunchedEffect(Unit) {
        tts = TextToSpeech(context) { status ->
            if (status == TextToSpeech.SUCCESS) {
                tts?.language = Locale.getDefault()
            }
        }
        delay(3000L)
        currentView.value = "alert"
    }

    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color.White),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Greeting Text at the top
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 20.dp),
                contentAlignment = Alignment.TopStart
            ) {
                Text(
                    text = "Hello, ${userNameState.value}!",
                    style = TextStyle(
                        fontSize = fontSizeState.value.sp * 1.8f, // Bigger font size
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    ),
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        .clickable {
                            if (textToSpeechState.value) {
                                tts?.speak("Hello, ${userNameState.value}", TextToSpeech.QUEUE_FLUSH, null, null)
                            }
                        }
                )
            }

            Box(
                modifier = Modifier
                    .size(300.dp)
                    .background(Color(0xFFC9EEFD), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "All Clear",
                    style = TextStyle(
                        fontSize = fontSizeState.value.sp * 2f,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    ),
                    textAlign = TextAlign.Center
                )
            }

            NavigationRow(
                currentView = currentView,
                tts = tts,
                textToSpeechState = textToSpeechState,
                modifier = Modifier
                    .background(Color.White.copy(alpha = 0.9f))
                    .padding(8.dp)
            )
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            tts?.stop()
            tts?.shutdown()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun IdleViewPreview() {
    val fontSizeState = remember { mutableStateOf(16f) }
    val textToSpeechState = remember { mutableStateOf(false) }
    val userNameState = remember { mutableStateOf("Test User") }
    val currentView = remember { mutableStateOf("idle") }

    EnrouteTheme {
        IdleView(
            userNameState = userNameState,
            fontSizeState = fontSizeState,
            textToSpeechState = textToSpeechState,
            currentView = currentView,
            onNavigate = { currentView.value = it }
        )
    }
}
