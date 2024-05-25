package com.example.pillinTimeAndroid.presentation.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.pillinTimeAndroid.R

@Composable
fun LottieView(
    modifier: Modifier,
    block: @Composable () -> Unit
) {
    val preloaderLottieCompositionV1 by rememberLottieComposition(
        LottieCompositionSpec.RawRes(R.raw.signup)
    )
    val preloaderProgressV1 by animateLottieCompositionAsState(
        preloaderLottieCompositionV1,
        speed = .5f,
        iterations = 1,
        isPlaying = true
    )
    val preloaderLottieCompositionV2 by rememberLottieComposition(
        LottieCompositionSpec.RawRes(R.raw.background)
    )
    val preloaderProgressV2 by animateLottieCompositionAsState(
        preloaderLottieCompositionV2,
        speed = 1f,
        iterations = 1,
        isPlaying = true
    )
    Box(modifier = modifier.fillMaxSize()) {
        LottieAnimation(
            composition = preloaderLottieCompositionV2,
            progress = { preloaderProgressV2 },
            modifier = modifier.fillMaxSize(),
            contentScale = ContentScale.FillHeight
        )
        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LottieAnimation(
                modifier = Modifier.size(250.dp),
                composition = preloaderLottieCompositionV1,
                progress = { preloaderProgressV1 },
            )
            block()
        }
    }
}