package com.marky.strivefit.ui.components.icon

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.PathParser
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.marky.strivefit.ui.theme.StriveFitTheme

@Composable
fun GoogleIcon(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.onBackground
) {
    val icon = ImageVector.Builder(
        name = "GoogleIcon",
        defaultWidth = 256.dp,
        defaultHeight = 256.dp,
        viewportWidth = 256f,
        viewportHeight = 256f
    ).apply {
        addPath(
            pathData = PathParser().parsePathString(
                "M224,128a96,96,0,1,1-21.95-61.09,8,8,0,1,1-12.33,10.18A80,80,0,1,0,207.6,136H128a8,8,0,0,1,0-16h88A8,8,0,0,1,224,128Z"
            ).toNodes(),
            fill = SolidColor(color),
            pathFillType = PathFillType.NonZero
        )
    }.build()

    Image(
        painter = rememberVectorPainter(image = icon),
        contentDescription = "Google Icon",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GoogleIconPreview() {
    StriveFitTheme {
        GoogleIcon(modifier = Modifier.size(48.dp))
    }
}