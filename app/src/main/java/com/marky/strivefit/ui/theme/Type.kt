package com.marky.strivefit.ui.theme

import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.text.googlefonts.GoogleFont.Provider
import com.marky.strivefit.R

private val googleFontProvider = Provider(
    providerAuthority = "com.google.android.gms.fonts",
    providerPackage = "com.google.android.gms",
    certificates = R.array.com_google_android_gms_fonts_certs
)

private val plusJakartaSansFont = GoogleFont("Plus Jakarta Sans")
private val dmSansFont = GoogleFont("DM Sans")

val PlusJakartaSansFontFamily = FontFamily(
    Font(googleFont = plusJakartaSansFont, fontProvider = googleFontProvider, weight = FontWeight.Normal),
    Font(googleFont = plusJakartaSansFont, fontProvider = googleFontProvider, weight = FontWeight.Bold)
)

val DMSansFontFamily = FontFamily(
    Font(googleFont = dmSansFont, fontProvider = googleFontProvider, weight = FontWeight.Normal),
    Font(googleFont = dmSansFont, fontProvider = googleFontProvider, weight = FontWeight.Bold)
)