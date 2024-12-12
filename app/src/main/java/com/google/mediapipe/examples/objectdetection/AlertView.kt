@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.enroute.views

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

@Composable
fun AlertView(
    detectedObject: String, // Detected object to display
    currentView: MutableState<String>, // For navigation control
    fontSizeState: MutableState<Float>,
    onNavigate: (String) -> Unit
) {
    // Animation for the red alert circle
    var circleOffset by remember { mutableStateOf(0.dp) }
    val animatedOffset by animateDpAsState(
        targetValue = circleOffset,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 500),
            repeatMode = RepeatMode.Reverse
        )
    )

    LaunchedEffect(Unit) {
        circleOffset = 10.dp // Start bobbing animation
        delay(7000L)
        onNavigate("idle")
    }

    Scaffold { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color.White),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Alert Text
                Text(
                    text = "Alert!",
                    style = TextStyle(
                        color = Color.Red,
                        fontSize = fontSizeState.value.sp * 2f,
                        fontWeight = FontWeight.Bold
                    ),
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                )

                Box(
                    modifier = Modifier
                        .offset(y = animatedOffset - 300.dp)
                        .size(300.dp)
                        .background(Color.Red, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = detectedObject,
                        style = TextStyle(
                            fontSize = fontSizeState.value.sp * 1.8f,
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        ),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}
