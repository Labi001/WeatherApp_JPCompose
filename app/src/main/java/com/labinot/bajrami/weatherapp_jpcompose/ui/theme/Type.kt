package com.labinot.bajrami.weatherapp_jpcompose.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.Font
import com.labinot.bajrami.weatherapp_jpcompose.R


val ubuntuFont = FontFamily(listOf(

    Font(R.font.ubunturegular)

))

val russoFont = FontFamily(listOf(

    Font(R.font.russooneregular)

))


val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = ubuntuFont,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )

)