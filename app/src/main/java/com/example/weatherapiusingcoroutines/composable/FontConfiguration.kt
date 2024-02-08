package com.example.weatherapiusingcoroutines.composable

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

val FontConfiguration = AppFonts(
    headline1 = TextStyle(
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold
    ),
    headline2 = TextStyle(
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold
    ),
    headline3 = TextStyle(
        fontSize = 14.sp,
        fontWeight = FontWeight.Bold
    ),
    plainTextBold = TextStyle(
        fontSize = 14.sp,
        fontWeight = FontWeight.Bold
    ),
    errorMessaging = TextStyle(
        fontSize = 12.sp,
        fontWeight = FontWeight.Normal,
        fontStyle = FontStyle.Italic
    ),
    buttonText = TextStyle(
        fontSize = 14.sp,
        fontWeight = FontWeight.Bold
    ),
    bodyAction = TextStyle(
        fontSize = 14.sp,
        fontWeight = FontWeight.Normal
    )
)

class AppFonts(
    headline1: TextStyle,
    headline2: TextStyle,
    headline3: TextStyle,
    plainTextBold: TextStyle,
    errorMessaging: TextStyle,
    buttonText: TextStyle,
    bodyAction: TextStyle
) {
    var headline1 by mutableStateOf(headline1)
        private set
    var headline2 by mutableStateOf(headline2)
        private set
    var headline3 by mutableStateOf(headline3)
        private set
    var plainTextBold by mutableStateOf(plainTextBold)
        private set
    var errorMessaging by mutableStateOf(errorMessaging)
        private set
    var bodyAction by mutableStateOf(bodyAction)
        private set
    var buttonText by mutableStateOf(buttonText)
        private set
}

val LocalAppFonts = staticCompositionLocalOf<AppFonts> {
    error("No App Fonts provided")
}
