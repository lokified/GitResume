package com.loki.gitresume.ui.projects

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
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
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.loki.gitresume.ui.components.ProjectItem
import com.loki.gitresume.ui.theme.GitResumeTheme
import com.loki.gitresume.util.projects
import kotlinx.coroutines.delay

@Composable
fun ProjectsScreen(
    openRepositoryScreen: () -> Unit
) {

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

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .background(
                    color = MaterialTheme.colorScheme.primary.copy(.04f),
                    shape = RoundedCornerShape(16.dp)
                )
        ) {

            LazyColumn {

                item {
                    Box(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            text = "Let's See My Work",
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

                items(projects) { project ->

                    ProjectItem(
                        modifier = Modifier.padding(vertical = 16.dp, horizontal = 8.dp),
                        project = project,
                        onItemClick = {
                            val intent = android.content.Intent(
                                android.content.Intent.ACTION_VIEW,
                                android.net.Uri.parse(project.url)
                            )
                            openBrowser.launch(intent)
                        }
                    )
                }

                item {
                    Box(modifier = Modifier.fillMaxWidth()) {
                        TextButton(
                            onClick = openRepositoryScreen,
                            modifier = Modifier.align(Alignment.Center),
                            colors = ButtonDefaults.textButtonColors(
                                contentColor = Color.White.copy(.8f)
                            )
                        ) {
                            Text(
                                text = "See More",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                                    .padding(12.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(
    showBackground = true
)
@Composable
fun ProjectPreview() {
    GitResumeTheme {
        ProjectsScreen(openRepositoryScreen = {})
    }
}