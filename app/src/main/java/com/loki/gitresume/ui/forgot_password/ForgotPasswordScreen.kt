package com.loki.gitresume.ui.forgot_password

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.loki.gitresume.R
import com.loki.gitresume.ui.components.AppBar
import com.loki.gitresume.ui.theme.GitResumeTheme

@Composable
fun ForgotPasswordScreen(
    viewModel: ForgotPasswordViewModel,
    openLoginScreen: () -> Unit,
    popScreen: () -> Unit
) {

    val uiState by viewModel.state
    var isFieldVisible by remember { mutableStateOf(true) }
    val context = LocalContext.current
    
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        
        AppBar(title = stringResource(R.string.forgot_password)) {
            popScreen()
        }

        if(isFieldVisible) {
            SendResetLinkContent(
                value = uiState.email,
                onValueChange = viewModel::onEmailChange,
                error = uiState.emailError,
                isError = uiState.isEmailError,
                onSendClick = {
                    viewModel.sendResetLink {
                        isFieldVisible = false
                    }
                }
            )
        }
        else{
            SuccessResetLinkContent {
                openLoginScreen()
            }
        }

        Box(modifier = Modifier.fillMaxSize()) {
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
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SendResetLinkContent(
    value: String,
    onValueChange: (String) -> Unit,
    error: String,
    isError: Boolean,
    onSendClick: () -> Unit
) {

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(24.dp)
    ) {

        Text(text = stringResource(R.string.email_will_be_send))

        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            isError = isError,
            label = {
                Text(text = stringResource(R.string.enter_email))
            },
            modifier = Modifier
                .fillMaxWidth(),
            supportingText = {
                if (isError) {
                    Text(text = error, color = MaterialTheme.colorScheme.error, fontSize = 12.sp)
                }
            },
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = onSendClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
        ) {
            Text(text = stringResource(R.string.send_password_reset_link))
        }
    }
}

@Composable
fun SuccessResetLinkContent(
    onLoginClick: () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(24.dp)
    ) {

        Text(text = stringResource(R.string.an_email_has_been_sent))

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = onLoginClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
        ) {
            Text(text = stringResource(R.string.go_back_to_login))
        }
    }
}

//@Preview(
//    showBackground = true
//)
//@Composable
//fun ForgotPreview() {
//    GitResumeTheme {
//        ForgotPasswordScreen(
//            viewModel = ForgotPasswordViewModel(),
//            openLoginScreen = { /*TODO*/ }) {
//
//        }
//    }
//}