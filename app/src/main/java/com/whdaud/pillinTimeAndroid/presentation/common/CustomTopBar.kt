package com.whdaud.pillinTimeAndroid.presentation.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.whdaud.pillinTimeAndroid.presentation.Dimens.ProgressBarHeight
import com.whdaud.pillinTimeAndroid.presentation.Dimens.TopBarSize
import com.whdaud.pillinTimeAndroid.ui.theme.Gray10
import com.whdaud.pillinTimeAndroid.ui.theme.Gray70
import com.whdaud.pillinTimeAndroid.ui.theme.PillinTimeAndroidTheme
import com.whdaud.pillinTimeAndroid.ui.theme.PillinTimeTheme
import com.whdaud.pillinTimeAndroid.ui.theme.Primary60

@Composable
fun CustomTopBar(
    showProgressBar: Boolean = false,
    progress: Float = 0f,
    onBackClicked: () -> Unit = { },
    showBackButton: Boolean = false,
    title: String? = null,
    showDeleteButton: Boolean = false,
    onDeleteClicked: () -> Unit = { }
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(TopBarSize),
    ) {
        if (showProgressBar) {
            LinearProgressIndicator(
                progress = { progress },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(ProgressBarHeight),
                color = Primary60,
                trackColor = Gray10
            )
        }
        if (showBackButton) {
            BackButton(
                onClick = onBackClicked
            )
        }
        if (title != null) {
            Text(
                text = title,
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(top = 14.dp),
                color = Gray70,
                style = PillinTimeTheme.typography.body1Medium
            )
        }
        if (showDeleteButton) {
            TextButton(
                onClick = onDeleteClicked
            ) {
                Text(
                    text = "삭제",
                    modifier = Modifier
                )
            }
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun ExampleScreen() {
    PillinTimeAndroidTheme {
        Column {
            CustomTopBar(
                showProgressBar = true,
                progress = 1 / 3f,
                showBackButton = true,
                onBackClicked = { },
                title = "전체 기능 포함",
                showDeleteButton = true
            )
        }
    }
}