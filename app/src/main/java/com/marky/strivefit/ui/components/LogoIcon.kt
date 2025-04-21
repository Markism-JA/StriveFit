package com.marky.strivefit.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
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
import com.marky.strivefit.ui.theme.DefaultDark


@Composable
fun LogoIcon(
    modifier: Modifier = Modifier, color1: Color = MaterialTheme.colorScheme.primary,
    color2: Color = MaterialTheme.colorScheme.secondary
) {

    val (c1, c2) = if (!isSystemInDarkTheme()) {
        color2 to color1
    } else {
        color1 to color2
    }


    val icon = ImageVector.Builder(
        name = "LogoIcon",
        defaultWidth = 1149.dp,
        defaultHeight = 1015.dp,
        viewportWidth = 1149f,
        viewportHeight = 1015f
    ).apply {
        // Path 1
        addPath(
            pathData = PathParser().parsePathString("M648.823 337.828C648.438 335.651 645.672 331.308 644.21 329.276C621.698 312.102 600.029 281.592 578.338 265.349C560.03 251.648 552.229 270.976 540.989 281.584C483.793 335.515 429.169 395.192 371.322 447.721C358.63 459.246 348.849 474.268 331.714 460.846C300.779 436.616 272.42 396.174 241.486 370.47C233.488 361.541 235.768 352.982 243.15 344.765C257.974 328.284 279.932 311.47 296.182 295.28C379.661 212.132 462.548 128.113 547.417 46.1146C553.419 39.8196 563.14 40.2985 570.549 43.7742C580.532 48.4489 602.615 75.4891 612.305 84.8549C696.108 165.819 778.325 248.645 861.462 330.407C868.432 339.13 872.237 344.779 865.156 354.918C855.545 368.659 828.312 389.846 815.232 402.773C761.883 455.475 708.932 508.418 655.329 560.853L648.654 562.637L648.823 337.828Z").toNodes(),
            fill = SolidColor(c1),
            pathFillType = PathFillType.NonZero
        )

        // Path 2
        addPath(
            pathData = PathParser().parsePathString("M917.62 626.802L579.722 965.219C573.866 970.051 568.566 975.572 560.395 971.262C457.661 870.526 356.016 767.796 254.527 665.756C247.133 658.314 227.433 646.559 231.905 633.939C232.619 631.933 235.935 628.248 237.521 626.29C270.56 585.387 323.578 538.549 361.73 500.285C394.389 467.531 441.501 412.758 475.69 386.606C477.589 385.157 478.062 382.981 481.351 383.805L481.22 557.82C472.031 572.649 430.551 599.883 428.555 616.277C426.791 630.725 445.078 640.387 453.861 649.051C481.143 675.938 520.319 722.905 548.753 744.17C552.536 746.99 559.013 751.251 563.899 749.925L913.992 401.977L917.777 401.085C917.883 406.079 917.661 411.133 917.766 416.127C917.789 417.384 917.74 418.641 917.763 419.887C917.844 425.521 917.706 431.18 917.751 436.814C917.795 441.82 917.719 446.85 917.739 451.868C917.748 455.628 917.734 459.388 917.731 463.161C917.728 466.921 917.725 470.681 917.722 474.454C917.784 520.835 917.652 567.289 917.618 613.671L917.608 626.838L917.62 626.802Z").toNodes(),
            fill = SolidColor(c2),
            pathFillType = PathFillType.NonZero
        )
    }.build()

    Image(
        painter = rememberVectorPainter(image = icon),
        contentDescription = "Logo Icon",
        modifier = modifier
    )
}
