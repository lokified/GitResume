package com.loki.gitresume.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.loki.gitresume.R
import com.loki.gitresume.domain.models.Repository

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun RepositoryItem(
    modifier: Modifier,
    repository: Repository,
    onItemClick: () -> Unit
) {

    Column(
        modifier = modifier.fillMaxWidth()
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(
                    MaterialTheme.colorScheme.primary.copy(.05f)
                )
        ) {

            Image(
                painter = painterResource(id = R.drawable.github),
                contentDescription = repository.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .padding(8.dp)
                    .clip(RoundedCornerShape(8.dp)),
                colorFilter = ColorFilter.tint(
                    color = MaterialTheme.colorScheme.primary.copy(.07f)
                )
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = repository.name, fontSize = 16.sp, color = Color.White.copy(.6f))
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = repository.description, fontSize = 13.sp, color = Color.White.copy(.5f))
        Spacer(modifier = Modifier.height(8.dp))

        FlowRow (
            modifier = Modifier.padding(horizontal = 8.dp)
        ) {
            repository.topics.forEach {
                Tag(title = it, modifier = Modifier.padding(4.dp))
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button( onClick = onItemClick,
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary.copy(.5f),
                contentColor = MaterialTheme.colorScheme.primary
            )
        ) {
            Text(text = "View Project")
        }
    }

}

@Composable
fun Tag(
    title: String,
    modifier: Modifier = Modifier
) {

    Box(
        modifier = modifier.background(
            color = MaterialTheme.colorScheme.primary.copy(.5f),
            shape = RoundedCornerShape(4.dp),
        ),
        contentAlignment = Alignment.Center
    ) {
        Text(text = title, modifier.padding(2.dp), color = MaterialTheme.colorScheme.background)
    }
}