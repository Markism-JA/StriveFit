package com.marky.strivefit.ui.theme

import android.graphics.Color as AndroidColor
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.graphics.ColorUtils

fun String.toColor(): Color {
    return Color(this.toColorInt())
}

fun String.toColorInt(): Int {
    return try {
        val colorStr = if (startsWith("#")) this else "#$this"
        AndroidColor.parseColor(colorStr)
    } catch (e: Exception) {
        AndroidColor.BLACK
    }
}

fun hexColorWithAlpha(hex: String, alpha: Float): Color {
    return hex.toColor().copy(alpha = alpha)
}

fun transformPrimaryColor(
    newPrimaryHex: String,
    originalPrimaryHex: String = "9C27B0",
    targetSecondaryHex: String = "2E1C3A"
): String {
    val source = newPrimaryHex.toColorInt()
    val from = originalPrimaryHex.toColorInt()
    val to = targetSecondaryHex.toColorInt()

    val sourceHSL = FloatArray(3)
    val fromHSL = FloatArray(3)
    val toHSL = FloatArray(3)

    ColorUtils.colorToHSL(source, sourceHSL)
    ColorUtils.colorToHSL(from, fromHSL)
    ColorUtils.colorToHSL(to, toHSL)

    val deltaHue = toHSL[0] - fromHSL[0]
    val deltaSat = toHSL[1] - fromHSL[1]
    val deltaLight = toHSL[2] - fromHSL[2]

    val resultHSL = FloatArray(3)
    resultHSL[0] = (sourceHSL[0] + deltaHue).coerceIn(0f, 360f)
    resultHSL[1] = (sourceHSL[1] + deltaSat).coerceIn(0f, 1f)
    resultHSL[2] = (sourceHSL[2] + deltaLight).coerceIn(0f, 1f)

    val resultColor = ColorUtils.HSLToColor(resultHSL)
    return "#%06X".format(0xFFFFFF and resultColor)
}



const val baseLightness = 0.42f
const val targetLightness = 0.17f

object CommonColors {
    val sucess = "#4CAF50".toColor()
    val warning = "#FFC107".toColor()
    val error = "#F44336".toColor()
    val onPrimary = "#FFFFFF".toColor()
    val borderDark = "#555555".toColor()
    val borderLight = "#E0E0E0".toColor()
    val bottomNavDark = "#262626".toColor()
    val bottomNavLight = "#FAFAFA".toColor()
    val secondaryContainer = "#BF5AF2".toColor()
    val onSecondaryContainer = hexColorWithAlpha("#9C27B0", 0.2f)
}

object DefaultDark {
    val primary = "#9C27B0".toColor()
    val primaryContainer = transformPrimaryColor("9C27B0").toColor()
    val secondary = "#CE93D8".toColor()
    val background = "#1A1A1A".toColor()
    val surface = "#2D2D2D".toColor()
    val surfaceElevated = "#333333".toColor()
}

object DefaultLight {
    val primary = "#9C27B0".toColor()
    val primaryContainer = transformPrimaryColor("9C27B0").toColor()
    val secondary = "#CE93D8".toColor()
    val background = "#F5F5F5".toColor()
    val surface = "#FFFFFF".toColor()
    val surfaceElevated = "#EEEEEE".toColor()
}

object EnergeticDark {
    val primary = "#FF4655".toColor()
    val primaryContainer = hexColorWithAlpha("#FF4655", 0.2f)
    val secondary = "#30ECCD".toColor()
    val background = "#0F1923".toColor()
    val surface = "#1A2634".toColor()
    val surfaceElevated = "#24313F".toColor()
}

object EnergeticLight {
    val primary = "#FF4655".toColor()
    val primaryContainer = hexColorWithAlpha("#FF4655", 0.2f)
    val secondary = "#00C2A0".toColor()
    val background = "#F9F9F9".toColor()
    val surface = "#FFFFF".toColor()
    val surfaceElevated = "#F0F0F0".toColor()
}

object CommonDark {
    val onBackground = "#E0E0E0".toColor()
    val onBgMedium= "#AAAAAA".toColor()
    val onBgDisabled= "#999999".toColor()
}

object CommonLight {
    val onBackground = "#212121".toColor()
    val onBgMedium= "#666666".toColor()
    val onBgDisabled= "#9E9E9E".toColor()
}
