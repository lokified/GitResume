package com.loki.gitresume.ui.repository

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.loki.gitresume.ui.components.RepositoryItem

@Composable
fun RepositoryScreen(
    viewModel: RepositoryViewModel
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    val openBrowser = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { _ -> }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                MaterialTheme.colorScheme.background
            )
    ) {

        if (uiState.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        }

        if (uiState.message.isNotBlank()) {
            Toast.makeText(
                context,
                uiState.message,
                Toast.LENGTH_LONG
            ).show()
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .background(
                    color = Color.White.copy(.2f),
                    shape = RoundedCornerShape(16.dp)
                )
        ) {

            LazyColumn {

                item {
                    Box(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            text = "My Repositories",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .padding(12.dp)
                                .align(Alignment.Center),
                            color = Color.White.copy(.8f)
                        )
                    }
                }

                items(uiState.repos) { repository ->

                    RepositoryItem(
                        modifier = Modifier.padding(vertical = 16.dp, horizontal = 8.dp),
                        repository = repository,
                        onItemClick = {
                            val intent = Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse(repository.url)
                            )
                            openBrowser.launch(intent)
                        }
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
//fun RepPreview() {
//    GitResumeTheme {
//        val viewModel = hiltViewModel<RepositoryViewModel>()
//        RepositoryScreen(viewModel = viewModel)
//    }
//}