package com.example.weatherapiusingcoroutines.composable

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.em
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
    subHead1 = TextStyle(
        fontSize = 14.sp,
        fontWeight = FontWeight.Normal
    ),
    subHead2 = TextStyle(
        fontSize = 12.sp,
        fontWeight = FontWeight.Normal
    ),
    title1 = TextStyle(
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold
    ),
    title2 = TextStyle(
        fontSize = 14.sp,
        fontWeight = FontWeight.Bold
    ),
    subTitle1 = TextStyle(
        fontSize = 16.sp,
        fontWeight = FontWeight.Normal
    ),
    subTitle2 = TextStyle(
        fontSize = 14.sp,
        fontWeight = FontWeight.Normal
    ),
    subTitle3 = TextStyle(
        fontSize = 12.sp,
        fontWeight = FontWeight.Normal
    ),
    plainText = TextStyle(
        fontSize = 12.sp,
        fontWeight = FontWeight.Medium
    ),
    plainTextSmall = TextStyle(
        fontSize = 10.sp,
        fontWeight = FontWeight.Normal
    ),
    plainTextLegal = TextStyle(
        fontSize = 10.sp,
        fontWeight = FontWeight.Normal,
        fontStyle = FontStyle.Italic
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
    badgeLabel = TextStyle(
        fontSize = 10.sp,
        fontWeight = FontWeight.Bold
    ),
    label1 = TextStyle(
        fontSize = 14.sp,
        fontWeight = FontWeight.Bold,
        letterSpacing = (-0.03).em
    ),
    label2 = TextStyle(
        fontSize = 13.sp,
        fontWeight = FontWeight.Normal,
        letterSpacing = (-0.03).em
    ),
    link1 = TextStyle(
        fontSize = 14.sp,
        fontWeight = FontWeight.Bold
    ),
    buttonText = TextStyle(
        fontSize = 14.sp,
        fontWeight = FontWeight.Bold
    ),
    mktgBrand2 = TextStyle(
        fontSize = 24.sp,
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
    subHead1: TextStyle,
    subHead2: TextStyle,
    title1: TextStyle,
    title2: TextStyle,
    subTitle1: TextStyle,
    subTitle2: TextStyle,
    subTitle3: TextStyle,
    plainTextSmall: TextStyle,
    plainTextLegal: TextStyle,
    plainTextBold: TextStyle,
    errorMessaging: TextStyle,
    plainText: TextStyle,
    buttonText: TextStyle,
    label1: TextStyle,
    label2: TextStyle,
    badgeLabel: TextStyle,
    link1: TextStyle,
    mktgBrand2: TextStyle,
    bodyAction: TextStyle
) {
    var headline1 by mutableStateOf(headline1)
        private set
    var headline2 by mutableStateOf(headline2)
        private set
    var headline3 by mutableStateOf(headline3)
        private set
    var subHead1 by mutableStateOf(subHead1)
        private set
    var subHead2 by mutableStateOf(subHead1)
        private set
    var title1 by mutableStateOf(title1)
        private set
    var title2 by mutableStateOf(title2)
        private set
    var subTitle1 by mutableStateOf(subTitle1)
        private set
    var subTitle2 by mutableStateOf(subTitle2)
        private set
    var subTitle3 by mutableStateOf(subTitle3)
        private set
    var plainTextSmall by mutableStateOf(plainTextSmall)
        private set
    var plainTextBold by mutableStateOf(plainTextBold)
        private set
    var plainTextLegal by mutableStateOf(plainTextLegal)
        private set
    var errorMessaging by mutableStateOf(errorMessaging)
        private set
    var plainText by mutableStateOf(plainText)
        private set
    var label1 by mutableStateOf(label1)
    var label2 by mutableStateOf(label2)
    var link1 by mutableStateOf(link1)
    var badgeLabel by mutableStateOf(badgeLabel)
        private set
    var bodyAction by mutableStateOf(bodyAction)
        private set
    var buttonText by mutableStateOf(buttonText)
        private set
    var mktgBrand2 by mutableStateOf(mktgBrand2)
        private set

    fun update(other: AppFonts) {
        headline1 = other.headline1
        headline2 = other.headline2
        subHead1 = other.subHead1
        subHead2 = other.subHead2
        title1 = other.title1
        title2 = other.title2
        subTitle1 = other.subTitle1
        subTitle2 = other.subTitle2
        subTitle3 = other.subTitle3
        plainTextSmall = other.plainTextSmall
        plainTextLegal = other.plainTextLegal
        plainText = other.plainText
        label1 = other.label1
        label2 = other.label2
        link1 = other.link1
        badgeLabel = other.badgeLabel
        buttonText = other.buttonText
        mktgBrand2 = other.mktgBrand2
        plainTextBold = other.plainTextBold
    }
}

val LocalAppFonts = staticCompositionLocalOf<AppFonts> {
    error("No App Fonts provided")
}
