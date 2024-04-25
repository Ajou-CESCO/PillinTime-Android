package com.example.pillinTimeAndroid.presentation.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pillinTimeAndroid.R
import com.example.pillinTimeAndroid.presentation.Dimens.BasicPadding
import com.example.pillinTimeAndroid.ui.theme.Gray90
import com.example.pillinTimeAndroid.ui.theme.PillinTimeAndroidTheme
import com.example.pillinTimeAndroid.ui.theme.PillinTimeTheme
import com.example.pillinTimeAndroid.ui.theme.White

@Composable
fun ClientListBar() {
    val profiles = listOf("User1", "User2", "User3", "User4", "User5", "User6", "User7", "User8")
    var selectedProfile by remember { mutableStateOf<String?>(profiles[0]) }

    LazyRow(
        modifier = Modifier
            .background(White)
            .padding(start = BasicPadding)
    ) {
        items(profiles.size) { index ->
            val profile = profiles[index]
            val selectedType =
                if (profile == selectedProfile) PillinTimeTheme.typography.caption2Bold else PillinTimeTheme.typography.caption2Regular
            val selectedIcon =
                if (profile == selectedProfile) R.drawable.ic_client_filled else R.drawable.ic_client_unfilled
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .clickable(
                        onClick = { selectedProfile = profile },
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }
                    )
                    .background(
                        color = Color.Transparent,
                    )
                    .padding(
                        top = 8.dp,
                        bottom = 8.dp,
                        end = 20.dp
                    )
            ) {
                Image(
                    painter = painterResource(id = selectedIcon),
                    contentDescription = "Profile Picture",
                )
                Text(
                    text = profile,
                    style = selectedType,
                    color = Gray90
                )
            }
        }
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun ClientListBarPreview() {
    PillinTimeAndroidTheme {
        ClientListBar()
    }
}