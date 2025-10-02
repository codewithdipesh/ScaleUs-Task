package com.codewithdipesh.scaleustask.presentation.authScreen.elements

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.ui.text.TextStyle
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AnimatedOtpField(
    modifier: Modifier = Modifier,
    isError: Boolean = false,
    onOtpEntered: (String) -> Unit
) {
    var otpValue by rememberSaveable { mutableStateOf("") }
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    //recahed 6
    LaunchedEffect(otpValue) {
        if (otpValue.length == 6) {
            focusManager.clearFocus()
        }
    }

    BasicTextField(
        value = otpValue,
        onValueChange = { newValue ->
            if (newValue.length <= 6 && newValue.all { it.isDigit() }) {
                otpValue = newValue
                onOtpEntered(newValue)
            }
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
        modifier = Modifier.focusRequester(focusRequester).fillMaxWidth(),
        decorationBox = {
            Row(
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                repeat(6) { index ->
                    OTPDigitBox(
                        digit = otpValue.getOrNull(index)?.toString(),
                        isFocused = otpValue.length == index,
                        isError = isError
                    )
                }
            }
        }
    )

}

//each box
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun OTPDigitBox(
    digit: String?,
    isFocused: Boolean,
    isError: Boolean
) {
    val scale by animateFloatAsState(
        targetValue = if (digit != null) 1f else 0.8f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        )
    )

    Box(
        modifier = Modifier
            .size(46.dp)
            .clip(RoundedCornerShape(8.dp))
            .border(
                width = 2.dp,
                color = if (isFocused) MaterialTheme.colorScheme.primary else if (isError) Color.Red.copy(0.5f) else Color.Black.copy(alpha = 0.4f),
                shape = RoundedCornerShape(8.dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        AnimatedContent(
            targetState = digit,
            transitionSpec = {
                //entry
                if (targetState != null && initialState == null) {
                    slideInVertically(//entry anim
                        initialOffsetY = { it },
                        animationSpec = tween(200, easing = FastOutSlowInEasing)
                    ) + fadeIn(animationSpec = tween(200)) togetherWith
                            slideOutVertically(
                                targetOffsetY = { -it },
                                animationSpec = tween(200)
                            ) + fadeOut(animationSpec = tween(200)) + scaleOut(
                        targetScale = 0.8f,
                        animationSpec = tween(200)
                    )
                }
                //exit
                else if (targetState == null && initialState != null) {
                    // Exit animation: shrink and fade
                    fadeIn(animationSpec = tween(200)) togetherWith
                            fadeOut(animationSpec = tween(200)) + scaleOut(
                        targetScale = 0.5f,
                        animationSpec = tween(200, easing = FastOutSlowInEasing)
                    )
                }
                //retype
                else {
                    // Replace animation
                    slideInVertically(
                        initialOffsetY = { it },
                        animationSpec = tween(200)
                    ) + fadeIn() togetherWith
                            slideOutVertically(
                                targetOffsetY = { -it },
                                animationSpec = tween(200)
                            ) + fadeOut()
                }
            }
        ) { targetDigit ->
            if (targetDigit != null) {
                Text(
                    text = targetDigit,
                    style = TextStyle(
                        fontSize = 20.sp,
                        color = MaterialTheme.colorScheme.onBackground,
                        textAlign = TextAlign.Center
                    ),
                    modifier = Modifier.graphicsLayer {
                        scaleX = scale
                        scaleY = scale
                    }
                )
            }
        }
    }
}