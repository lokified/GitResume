package com.loki.gitresume.ui.register

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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.loki.gitresume.R
import com.loki.gitresume.ui.components.BasicInput
import com.loki.gitresume.ui.login.LoginScreen
import com.loki.gitresume.ui.login.LoginViewModel
import com.loki.gitresume.ui.navigation.Screen
import com.loki.gitresume.ui.theme.GitResumeTheme
import com.loki.gitresume.ui.theme.motoClub

@Composable
fun RegisterScreen(
    viewModel: RegisterViewModel,
    openLoginScreen: () -> Unit
) {

    val uiState by viewModel.state
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val lifecycle = lifecycleOwner.lifecycle

    DisposableEffect(key1 = lifecycle) {
        val lifecycleObserver = LifecycleEventObserver { _, event ->
            when(event) {
                Lifecycle.Event.ON_PAUSE -> {
                    viewModel.message.value = ""
                }
                else -> {}
            }
        }

        lifecycle.addObserver(lifecycleObserver)

        onDispose {
            lifecycle.removeObserver(lifecycleObserver)
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.sun),
            contentDescription = stringResource(id = R.string.bg_image),
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

        Column (
            modifier = Modifier.align(Alignment.TopCenter)
                .padding(top = 30.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Wanna See More?", fontSize = 32.sp, color = Color.Black.copy(.7f), fontFamily = motoClub)
            Text(text = "SignUp account", color = Color.Black.copy(.7f))
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
                    label = stringResource(id = R.string.email),
                    onValueChange = viewModel::onEmailChange,
                    errorMessage = uiState.emailError,
                    isError = uiState.isEmailError
                )

                BasicInput(
                    value = uiState.password,
                    label = stringResource(id = R.string.password),
                    onValueChange = viewModel::onPasswordChange,
                    errorMessage = uiState.passwordError,
                    isError = uiState.isPasswordError,
                    keyboardType = KeyboardType.Password
                )

                BasicInput(
                    value = uiState.conPassword,
                    label = stringResource(R.string.confirm_password),
                    onValueChange = viewModel::onConPasswordChange,
                    errorMessage = uiState.conPasswordError,
                    isError = uiState.isConPasswordError,
                    keyboardType = KeyboardType.Password
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {

                    Icon(
                        imageVector = Icons.Filled.Info,
                        contentDescription = stringResource(R.string.info_icon),
                        tint = Color.White.copy(.5f),
                        modifier = Modifier.size(12.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = stringResource(R.string.password_info),
                        color = Color.White.copy(.5f),
                        fontSize = 12.sp,
                        lineHeight = 10.sp
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        viewModel.register { openLoginScreen() }
                    },
                    modifier = Modifier
                        .fillMaxWidth(.7f)
                        .height(48.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White,
                        contentColor = Color.Black.copy(.5f)
                    )
                ) {
                    Text(text = stringResource(R.string.sign_up)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {

                    Text(text = stringResource(R.string.already_have_an_account), color = Color.White)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = stringResource(R.string.signin),
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.clickable { openLoginScreen() },
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
//fun RegisterPreview() {
//    GitResumeTheme {
//        RegisterScreen(viewModel = RegisterViewModel(), openLoginScreen = {})
//    }
//}