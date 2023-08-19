package com.loki.gitresume.ui.login

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.loki.gitresume.R
import com.loki.gitresume.ui.components.BasicInput
import com.loki.gitresume.ui.navigation.Screen
import com.loki.gitresume.ui.theme.GitResumeTheme

@Composable
fun LoginScreen(
    viewModel: LoginViewModel,
    openSignUpScreen: (String) -> Unit,
    openHomeScreen: (String, String) -> Unit
) {

    val uiState by viewModel.state
    val context = LocalContext.current


    Box(modifier = Modifier.fillMaxSize()) {

        Image(
            painter = painterResource(id = R.drawable.sun),
            contentDescription = stringResource(R.string.bg_image),
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        if (viewModel.isLoading.value) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        }

        if (viewModel.message.value.isNotBlank()) {
            Toast.makeText(
                context,
                viewModel.message.value,
                Toast.LENGTH_LONG
            ).show()
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    bottom = 50.dp,
                    start = 24.dp,
                    end = 24.dp
                )
                .background(
                    color = Color.Black.copy(.5f),
                    shape = RoundedCornerShape(24.dp)
                )
                .align(Alignment.BottomCenter)
        ) {

            Column (
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                BasicInput(
                    value = uiState.email,
                    label = stringResource(R.string.email),
                    onValueChange = viewModel::onEmailChange,
                    errorMessage = uiState.emailError,
                    isError = uiState.isEmailError
                )

                BasicInput(
                    value = uiState.password,
                    label = stringResource(R.string.password),
                    onValueChange = viewModel::onPasswordChange,
                    errorMessage = uiState.passwordError,
                    isError = uiState.isPasswordError,
                    keyboardType = KeyboardType.Password
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        viewModel.login { openHomeScreen(Screen.HomeScreen.route, Screen.LoginScreen.route) }
                              },
                    modifier = Modifier
                        .fillMaxWidth(.7f)
                        .height(48.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White,
                        contentColor = Color.Black.copy(.5f)
                    )
                ) {
                    Text(text = stringResource(R.string.sign_in))
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = stringResource(R.string.forgot_password_question), color = Color.White)
                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {

                    Text(text = stringResource(R.string.don_t_have_an_account), color = Color.White)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "SignUp",
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.clickable { openSignUpScreen(Screen.RegisterScreen.route) },
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

//@Preview(
//    showBackground = true
//)
//@Composable
//fun LoginPreview() {
//    GitResumeTheme {
//        LoginScreen(viewModel = LoginViewModel(), openHomeScreen = { _, _ -> }, openSignUpScreen = {})
//    }
//}