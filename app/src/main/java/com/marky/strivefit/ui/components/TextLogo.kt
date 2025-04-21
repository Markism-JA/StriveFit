package com.marky.strivefit.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.PathParser
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import com.marky.strivefit.ui.theme.CommonDark
import com.marky.strivefit.ui.theme.DefaultDark


@Composable
fun TextLogoIcon(modifier: Modifier = Modifier,
                 color1: Color = MaterialTheme.colorScheme.onBackground,
                 color2: Color = MaterialTheme.colorScheme.secondary
) {

    val icon = ImageVector.Builder(
        name = "LogoIcon",
        defaultWidth = 1150.dp,
        defaultHeight = 350.dp,
        viewportWidth = 1149f,
        viewportHeight = 366f
    ).apply {

        // S
        addPath(
            pathData = PathParser().parsePathString("M212.034 246.44C216.874 260.92 243.674 261.15 246.284 245.7C248.574 232.11 227.334 229.09 217.854 224.7C208.954 220.57 199.474 214.45 197.794 203.86C191.634 164.97 243.604 152.53 261.884 182.77C263.924 186.14 266.374 190.64 265.044 194.71C263.034 197.52 248.054 193.28 244.164 194.68C240.054 181.17 212.524 184.76 219.284 200.37C221.554 205.6 243.454 211.34 250.164 215.4C265.624 224.76 271.924 239.49 264.324 256.77C252.474 283.73 205.154 283.61 193.364 257.11C192.194 254.49 189.524 248.32 192.054 246.46H212.034V246.44Z").toNodes(),
            fill = SolidColor(color2),
            pathFillType = PathFillType.NonZero
        )

        // T
        addPath(
            pathData = PathParser().parsePathString("M351.364 166.73V186.66H323.404V273.84L321.904 275.33H302.924L301.424 273.84V186.66H272.464V168.22L273.964 166.73H351.364Z").toNodes(),
            fill = SolidColor(color1),
            pathFillType = PathFillType.NonZero
        )

        // R
        addPath(
            pathData = PathParser().parsePathString("M418.784 166.73C454.504 170.95 461.914 221 423.744 232.47C422.634 232.8 421.964 231.79 422.294 233.97L452.254 275.33H427.784L398.414 236.87L397.324 214.55H416.804C417.064 214.55 420.984 212.8 421.644 212.41C433.574 205.25 424.604 186.65 413.804 186.65H392.334C392.884 187.92 391.334 189.6 391.334 190.14V275.33H369.364V166.72C384.984 168.06 403.554 164.92 418.804 166.72L418.784 166.73ZM418.784 166.73C454.504 170.95 461.914 221 423.744 232.47C422.634 232.8 421.964 231.79 422.294 233.97L452.254 275.33H427.784L398.414 236.87L397.324 214.55H416.804C417.064 214.55 420.984 212.8 421.644 212.41C433.574 205.25 424.604 186.65 413.804 186.65H392.334C392.884 187.92 391.334 189.6 391.334 190.14V275.33H369.364V166.72C384.984 168.06 403.554 164.92 418.804 166.72L418.784 166.73Z").toNodes(),
            fill = SolidColor(color1),
            pathFillType = PathFillType.NonZero
        )

        // I
        addPath(
            pathData = PathParser().parsePathString("M495.194 166.73H473.224V275.34H495.194V166.73Z").toNodes(),
            fill = SolidColor(color1),
            pathFillType = PathFillType.NonZero
        )

        // V
        addPath(
            pathData = PathParser().parsePathString("M536.144 166.73L563.614 244.46L593.074 166.73H617.044L574.994 273.74C574.504 274.84 573.734 275.14 572.604 275.34C570.234 275.78 554.764 275.63 553.124 274.84C551.924 274.27 551.164 272.54 550.624 271.35L512.174 166.73H536.144Z").toNodes(),
            fill = SolidColor(color1),
            pathFillType = PathFillType.NonZero
        )

        // E
        addPath(
            pathData = PathParser().parsePathString("M683.464 166.73C687.694 167.91 685.954 182.71 684.854 186.55L644.444 188.59L645.094 210.51C646.054 209.87 646.904 210.57 647.504 210.57H680.964V229C680.084 230.42 678.974 230.38 677.524 230.55C671.534 231.25 663.764 230.26 657.454 230.46C653.184 230.59 649.574 232.66 645.014 230.5V254.41H683.464C683.644 254.41 685.964 256.72 685.964 256.9V274.34C669.184 277.41 651.304 273.91 634.484 274.3C630.744 274.39 626.084 277.17 623.034 273.84V166.73H683.464Z").toNodes(),
            fill = SolidColor(color2),
            pathFillType = PathFillType.NonZero
        )

        // F
        addPath(
            pathData = PathParser().parsePathString("M809.814 166.73C810.884 167.52 793.634 185.7 792.074 186.39C783.504 190.21 759.034 186.77 748.904 186.65L747.894 275.33H726.924V166.72H809.824L809.814 166.73Z").toNodes(),
            fill = SolidColor(color1),
            pathFillType = PathFillType.NonZero
        )

        // F horizontal bar
        addPath(
            pathData = PathParser().parsePathString("M753.884 232.49V210.57H788.344L789.834 212.06V230.99L788.344 232.49H753.884Z").toNodes(),
            fill = SolidColor(color1),
            pathFillType = PathFillType.NonZero
        )

        // I
        addPath(
            pathData = PathParser().parsePathString("M822.884 166.589L822.049 275.356L844.308 275.527L845.143 166.76L822.884 166.589Z").toNodes(),
            fill = SolidColor(color1),
            pathFillType = PathFillType.NonZero
        )

        // T
        addPath(
            pathData = PathParser().parsePathString("M944.654 166.73C944.774 171.21 944.504 175.73 944.604 180.22C944.664 182.75 946.624 185.05 944.154 186.65H917.684V273.83L916.184 275.32H897.204L895.704 273.83V186.65H867.234L865.734 185.16V169.22C865.734 168.89 867.054 167.71 866.734 166.73H944.644H944.654Z").toNodes(),
            fill = SolidColor(color1),
            pathFillType = PathFillType.NonZero
        )

        // dot for T
        addPath(
            pathData = PathParser().parsePathString("M941.964 274.02C941.614 273.77 939.134 270.17 938.904 269.61C933.094 255.3 951.364 249.03 957.484 259.04C964.054 269.78 950.444 279.97 941.964 274.02Z").toNodes(),
            fill = SolidColor(color2),
            pathFillType = PathFillType.NonZero
        )

        // Trapezoid
        addPath(
            pathData = PathParser().parsePathString("M583.584 162.74C583.324 162.76 581.794 164.84 581.094 163.24L597.534 129.82L646.994 90L615.544 162.74C605.104 163.32 593.914 161.95 583.584 162.74Z").toNodes(),
            fill = SolidColor(color2),
            pathFillType = PathFillType.NonZero
        )

    }.build()

    Image(
        painter = rememberVectorPainter(image = icon),
        contentDescription = "Text Logo",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun TextLogoIconPreview() {
    Surface(color = DefaultDark.background) {
        TextLogoIcon()
    }
}
