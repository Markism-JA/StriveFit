import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection

class CustomCloudShape : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val path = Path().apply {
            val scaleX = size.width / 1296f
            val scaleY = size.height / 1728f

            moveTo(920.017f * scaleX, 778.918f * scaleY)
            cubicTo(
                909.09f * scaleX, 852.84f * scaleY,
                874.662f * scaleX, 988.815f * scaleY,
                817.625f * scaleX, 1060.99f * scaleY
            )
            cubicTo(
                758.252f * scaleX, 1136.12f * scaleY,
                669.112f * scaleX, 1146f * scaleY,
                589.659f * scaleX, 1146f * scaleY
            )
            cubicTo(
                515.05f * scaleX, 1146f * scaleY,
                434.605f * scaleX, 1128.62f * scaleY,
                379.081f * scaleX, 1060.99f * scaleY
            )
            cubicTo(
                318.676f * scaleX, 987.42f * scaleY,
                265.098f * scaleX, 860.09f * scaleY,
                265.098f * scaleX, 778.918f * scaleY
            )
            cubicTo(
                247.711f * scaleX, 678.454f * scaleY,
                141.455f * scaleX, 724.822f * scaleY,
                126f * scaleX, 649.474f * scaleY
            )
            cubicTo(
                187.451f * scaleX, 599.413f * scaleY,
                505.877f * scaleX, 581.854f * scaleY,
                603.183f * scaleX, 581.854f * scaleY
            )
            cubicTo(
                717.754f * scaleX, 581.854f * scaleY,
                985.219f * scaleX, 568.268f * scaleY,
                1034f * scaleX, 649.474f * scaleY
            )
            cubicTo(
                1034f * scaleX, 709.366f * scaleY,
                956.723f * scaleX, 663.53f * scaleY,
                920.017f * scaleX, 778.918f * scaleY
            )
            close()
        }
        return Outline.Generic(path)
    }
}