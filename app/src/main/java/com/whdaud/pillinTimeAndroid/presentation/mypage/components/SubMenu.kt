package com.whdaud.pillinTimeAndroid.presentation.mypage.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.whdaud.pillinTimeAndroid.R
import com.whdaud.pillinTimeAndroid.domain.entity.User
import com.whdaud.pillinTimeAndroid.ui.theme.Gray50
import com.whdaud.pillinTimeAndroid.ui.theme.Gray90
import com.whdaud.pillinTimeAndroid.ui.theme.PillinTimeTheme

@Composable
fun SubMenu(
    userDetails: User?,
    onItemClick: (destination: String) -> Unit
) {
    val manage = if (userDetails?.isManager == true) "피보호자 관리" else "보호관계 관리"
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        SubMenuItem(R.drawable.ic_manage, manage) {
            onItemClick("relationManageScreen/${manage}")
        }
        SubMenuItem(R.drawable.ic_calendar, "복약 일정 관리") {
            onItemClick("editScheduleScreen")
        }
    }
}

@Composable
fun SubMenuItem(
    @DrawableRes icon: Int,
    title: String,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(36.dp)
                .clip(shape = CircleShape)
                .border(1.dp, Gray50, CircleShape)
                .background(Color.Transparent),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                modifier = Modifier
                    .clickable(
                        onClick = onClick,
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }
                    ),
                painter = painterResource(id = icon),
                contentDescription = null,
                tint = Color.Unspecified
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = title,
            color = Gray90,
            style = PillinTimeTheme.typography.body2Regular
        )
    }
}