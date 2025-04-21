package logo

import Logo
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.PathFillType.Companion.NonZero
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeCap.Companion.Butt
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.StrokeJoin.Companion.Miter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlin.Unit

public val Logo.Logo: ImageVector
    get() {
        if (_logo != null) {
            return _logo!!
        }
        _logo = Builder(name = "Logo", defaultWidth = 1149.0.dp, defaultHeight = 1015.0.dp,
                viewportWidth = 1149.0f, viewportHeight = 1015.0f).apply {
            path(fill = SolidColor(Color(0xFF9C27B0)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(648.8f, 337.8f)
                curveTo(648.4f, 335.7f, 645.7f, 331.3f, 644.2f, 329.3f)
                curveTo(621.7f, 312.1f, 600.0f, 281.6f, 578.3f, 265.3f)
                curveTo(560.0f, 251.6f, 552.2f, 271.0f, 541.0f, 281.6f)
                curveTo(483.8f, 335.5f, 429.2f, 395.2f, 371.3f, 447.7f)
                curveTo(358.6f, 459.2f, 348.8f, 474.3f, 331.7f, 460.8f)
                curveTo(300.8f, 436.6f, 272.4f, 396.2f, 241.5f, 370.5f)
                curveTo(233.5f, 361.5f, 235.8f, 353.0f, 243.1f, 344.8f)
                curveTo(258.0f, 328.3f, 279.9f, 311.5f, 296.2f, 295.3f)
                curveTo(379.7f, 212.1f, 462.5f, 128.1f, 547.4f, 46.1f)
                curveTo(553.4f, 39.8f, 563.1f, 40.3f, 570.5f, 43.8f)
                curveTo(580.5f, 48.4f, 602.6f, 75.5f, 612.3f, 84.9f)
                curveTo(696.1f, 165.8f, 778.3f, 248.6f, 861.5f, 330.4f)
                curveTo(868.4f, 339.1f, 872.2f, 344.8f, 865.2f, 354.9f)
                curveTo(855.5f, 368.7f, 828.3f, 389.8f, 815.2f, 402.8f)
                curveTo(761.9f, 455.5f, 708.9f, 508.4f, 655.3f, 560.9f)
                lineTo(648.7f, 562.6f)
                lineTo(648.8f, 337.8f)
                close()
            }
            path(fill = SolidColor(Color(0xFFCE93D8)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(917.6f, 626.8f)
                lineTo(579.7f, 965.2f)
                curveTo(573.9f, 970.1f, 568.6f, 975.6f, 560.4f, 971.3f)
                curveTo(457.7f, 870.5f, 356.0f, 767.8f, 254.5f, 665.8f)
                curveTo(247.1f, 658.3f, 227.4f, 646.6f, 231.9f, 633.9f)
                curveTo(232.6f, 631.9f, 235.9f, 628.2f, 237.5f, 626.3f)
                curveTo(270.6f, 585.4f, 323.6f, 538.5f, 361.7f, 500.3f)
                curveTo(394.4f, 467.5f, 441.5f, 412.8f, 475.7f, 386.6f)
                curveTo(477.6f, 385.2f, 478.1f, 383.0f, 481.4f, 383.8f)
                lineTo(481.2f, 557.8f)
                curveTo(472.0f, 572.6f, 430.6f, 599.9f, 428.6f, 616.3f)
                curveTo(426.8f, 630.7f, 445.1f, 640.4f, 453.9f, 649.1f)
                curveTo(481.1f, 675.9f, 520.3f, 722.9f, 548.8f, 744.2f)
                curveTo(552.5f, 747.0f, 559.0f, 751.3f, 563.9f, 749.9f)
                lineTo(914.0f, 402.0f)
                lineTo(917.8f, 401.1f)
                curveTo(917.9f, 406.1f, 917.7f, 411.1f, 917.8f, 416.1f)
                curveTo(917.8f, 417.4f, 917.7f, 418.6f, 917.8f, 419.9f)
                curveTo(917.8f, 425.5f, 917.7f, 431.2f, 917.8f, 436.8f)
                curveTo(917.8f, 441.8f, 917.7f, 446.9f, 917.7f, 451.9f)
                curveTo(917.7f, 455.6f, 917.7f, 459.4f, 917.7f, 463.2f)
                curveTo(917.7f, 466.9f, 917.7f, 470.7f, 917.7f, 474.5f)
                curveTo(917.8f, 520.8f, 917.7f, 567.3f, 917.6f, 613.7f)
                lineTo(917.6f, 626.8f)
                lineTo(917.6f, 626.8f)
                close()
            }
        }
        .build()
        return _logo!!
    }

private var _logo: ImageVector? = null

@Preview
@Composable
private fun Preview(): Unit {
    Box(modifier = Modifier.padding(12.dp)) {
        Image(imageVector = Logo.Logo, contentDescription = "")
    }
}
