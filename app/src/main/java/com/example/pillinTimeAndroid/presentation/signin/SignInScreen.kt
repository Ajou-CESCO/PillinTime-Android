package com.example.pillinTimeAndroid.presentation.signin

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.pillinTimeAndroid.presentation.common.ButtonColor
import com.example.pillinTimeAndroid.presentation.common.ButtonSize
import com.example.pillinTimeAndroid.presentation.common.CustomButton
import com.example.pillinTimeAndroid.presentation.common.CustomTopBar
import com.example.pillinTimeAndroid.presentation.common.GeneralScreen
import com.example.pillinTimeAndroid.presentation.signin.components.SignInPage
import com.example.pillinTimeAndroid.presentation.signin.components.signInPages
import com.example.pillinTimeAndroid.ui.theme.PillinTimeAndroidTheme

@Composable
fun SignInScreen(
    viewModel: SignInViewModel = hiltViewModel(),
    navController: NavController,
) {
    val currentPage = viewModel.getCurrentPage()
    val currentPageTitle = viewModel.getCurrentPageTitle()
    val inputType = viewModel.getInputType()

    GeneralScreen(
        topBar = {
            CustomTopBar(
                showBackButton = currentPage != signInPages[0],
                onBackClicked = { viewModel.previousPage() }
            )
        },
        title = currentPageTitle,
        subtitle = currentPage.subtitle,
        content = {
            SignInPage(
                state = viewModel.isValidateInput(),
                pageList = currentPage,
                input = viewModel.getCurrentInput(),
                onInputChanged = viewModel::updateInput,
                visualTransformation = viewModel.getVisualTransformations(),
                inputType = inputType
            )
        }
    ) {
        CustomButton(
            modifier = Modifier.fillMaxWidth(),
            enabled = viewModel.isValidateInput() && viewModel.getCurrentInput().isNotEmpty(),
            filled = ButtonColor.FILLED,
            size = ButtonSize.MEDIUM,
            text = if (currentPage == signInPages[3]) "확인" else "다음",
            onClick = {
                if (currentPage == signInPages[3]) {
                    viewModel.signIn(navController)
                } else {
                    viewModel.nextPage()
                }
            }
        )
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun SingInScreenPreview() {
    PillinTimeAndroidTheme {
//        SignInScreen(SignInViewModel(), rememberNavController())
    }
}