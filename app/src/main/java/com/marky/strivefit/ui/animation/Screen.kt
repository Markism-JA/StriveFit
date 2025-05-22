import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally

fun transitionAnimation(direction: AnimatedContentTransitionScope.SlideDirection, durationMillis: Int) =
    slideInHorizontally(
        initialOffsetX = { if (direction == AnimatedContentTransitionScope.SlideDirection.Left) 1000 else -1000 },
        animationSpec = tween(durationMillis)
    ) + fadeIn(animationSpec = tween(durationMillis))

fun popTransitionAnimation(direction: AnimatedContentTransitionScope.SlideDirection, durationMillis: Int) =
    slideOutHorizontally(
        targetOffsetX = { if (direction == AnimatedContentTransitionScope.SlideDirection.Left) -1000 else 1000 },
        animationSpec = tween(durationMillis)
    ) + fadeOut(animationSpec = tween(durationMillis))
