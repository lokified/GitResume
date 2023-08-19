package com.loki.gitresume.ui.home

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BusinessCenter
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.OpenInNew
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.loki.gitresume.R
import com.loki.gitresume.ui.components.ContentBubble
import com.loki.gitresume.ui.components.HomeBottomBar
import com.loki.gitresume.ui.components.TimelineNode
import com.loki.gitresume.ui.components.TimelineNodePosition
import com.loki.gitresume.util.CircleParametersDefaults
import com.loki.gitresume.util.LineParametersDefaults

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {

    Scaffold(
        bottomBar = {
            HomeBottomBar(
                onSeeProjectClick = {},
                onDownloadClick = {}
            )
        }
    ) { padding ->
        var currentTab by rememberSaveable { mutableStateOf(TabPage.EXPERIENCE) }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        MaterialTheme.colorScheme.primary
                    )
            ) {

                val textColor = Color.White

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.TopCenter)
                        .padding(top = 50.dp, bottom = 150.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Text(text = stringResource(R.string.sheldon_okware), fontSize = 24.sp, fontWeight = FontWeight.Bold, color = textColor)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = stringResource(R.string.mobile_developer), fontSize = 18.sp, color = textColor)
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {

                        Icon(
                            imageVector = Icons.Filled.LocationOn,
                            contentDescription = stringResource(R.string.location_icon),
                            modifier = Modifier.size(15.dp),
                            tint = textColor
                        )

                        Spacer(modifier = Modifier.width(4.dp))

                        Text(text = stringResource(R.string.nairobi_kenya), fontSize = 16.sp, color = textColor)
                    }
                }

                IconButton(
                    onClick = { /*TODO*/ },
                    modifier = Modifier.align(Alignment.BottomStart)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.github),
                        contentDescription = stringResource(R.string.github_image),
                        modifier = Modifier.size(20.dp),
                        colorFilter = ColorFilter.tint(
                            color = textColor
                        )
                    )
                }

                IconButton(
                    onClick = { /*TODO*/ },
                    modifier = Modifier.align(Alignment.BottomEnd)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.linkedin),
                        contentDescription = stringResource(R.string.linkedin_image),
                        modifier = Modifier.size(20.dp),
                        colorFilter = ColorFilter.tint(
                            color = textColor
                        )
                    )
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter)
                ) {

                    Column (
                        modifier = Modifier
                            .align(Alignment.Center)
                    ) {
                        Box(modifier = Modifier
                            .offset(y = 100.dp)
                            .shadow(
                                elevation = 12.dp,
                                shape = CircleShape,
                                spotColor = Color.Black
                            )
                        ) {

                            Image(
                                painter = painterResource(id = R.drawable.pass),
                                contentDescription = stringResource(R.string.profile_img),
                                modifier = Modifier
                                    .clip(CircleShape)
                            )
                        }
                    }
                }
            }

            Column(
                modifier = Modifier
                    .padding(start = 16.dp, top = 120.dp, end = 16.dp)
            ) {

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceAround,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    ColumnItem(number = "100", title = "Repositories", isClickable = true)
                    ColumnItem(number = "23", title = "Following")
                    ColumnItem(number = "26", title = "Followers")
                }

                Spacer(modifier = Modifier.height(16.dp))
                Text(text = "About Sheldon", fontSize = 22.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = stringResource(R.string.profile_summary))

                Spacer(modifier = Modifier.height(16.dp))

                ExperienceEducationSection(
                    currentTab = currentTab,
                    onSelectedTab = {
                        currentTab = it
                    }
                )
            }
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ColumnItem(
    number: String,
    title: String,
    isClickable: Boolean = false,
    onItemClick: () -> Unit = {}
) {

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable { onItemClick() }
    ) {
        if (isClickable) {
            BadgedBox(
                badge = {
                    Icon(
                        imageVector = Icons.Filled.OpenInNew,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(15.dp)
                    )
                }
            ) {
                Text(
                    text = number,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
        else {
            Text(
                text = number,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
        }
        Text(text = title, fontSize = 13.sp)
    }
}


@Composable
fun ExperienceEducationSection(
    currentTab: TabPage,
    onSelectedTab: (TabPage) -> Unit
) {

    SelectionTab(
        onSelectedTab = onSelectedTab
    )

    Crossfade(
        targetState = currentTab,
        animationSpec = tween(
            durationMillis = 500,
            easing = FastOutSlowInEasing
        ),
        label = "tab_layout"
    ) { tab ->
        when(tab) {
            TabPage.EXPERIENCE -> ExperienceTab()
            TabPage.EDUCATION -> EducationTab()
        }
    }
}

@Composable
fun ExperienceTab() {

    Column(modifier = Modifier.fillMaxWidth()) {
        val bgColor = MaterialTheme.colorScheme.primary

        Spacer(modifier = Modifier.height(12.dp))
        TimelineNode(
            position = TimelineNodePosition.FIRST,
            circleParameters = CircleParametersDefaults.circleParameters(
                radius = 8.dp,
                backgroundColor = bgColor
            ),
            lineParameters = LineParametersDefaults.linearGradient(
                startColor = bgColor,
                endColor = bgColor
            )
        ) {

            ContentBubble(
                modifier = it,
                startDate = "February 2023",
                endDate = "Present",
                title = "Android Developer",
                company = "Machini Technologies",
                description = "We did it"
            )
        }

        TimelineNode(
            position = TimelineNodePosition.MIDDLE,
            circleParameters = CircleParametersDefaults.circleParameters(
                radius = 8.dp,
                backgroundColor = bgColor
            ),
            lineParameters = LineParametersDefaults.linearGradient(
                startColor = bgColor,
                endColor = bgColor
            )
        ) {
            ContentBubble(
                modifier = it,
                startDate = "May 2022",
                endDate = "August 2022",
                title = "Software Developer Intern",
                company = "Eclectics International",
                description = "Wololo")
        }

        TimelineNode(
            position = TimelineNodePosition.LAST,
            circleParameters = CircleParametersDefaults.circleParameters(
                radius = 8.dp,
                backgroundColor = bgColor
            )
        ) {
            ContentBubble(
                modifier = it,
                startDate = "February 2021",
                endDate = "May 2022",
                title = "Android Developer",
                company = "Freelance",
                description = "desr"
            )

        }
    }
}

@Composable
fun EducationTab() {
    Column(modifier = Modifier.fillMaxWidth()) {

        val bgColor = MaterialTheme.colorScheme.primary
        Spacer(modifier = Modifier.height(12.dp))

        TimelineNode(
            position = TimelineNodePosition.FIRST,
            circleParameters = CircleParametersDefaults.circleParameters(
                radius = 8.dp,
                backgroundColor = bgColor
            ),
            lineParameters = LineParametersDefaults.linearGradient(
                startColor = bgColor,
                endColor = bgColor
            )
        ) {
            ContentBubble(
                modifier = it,
                startDate = "July 2021",
                endDate = "February 2022",
                title = "Software Engineering",
                company = "Moringa School",
                description = "desr"
            )
        }

        TimelineNode(
            position = TimelineNodePosition.LAST,
            circleParameters = CircleParametersDefaults.circleParameters(
                radius = 8.dp,
                backgroundColor = bgColor
            )
        ) {

            ContentBubble(
                modifier = it,
                startDate = "September 2017",
                endDate = "May 2022",
                title = "Bsc Mathematics and Computer Science",
                company = "Taita Taveta University",
                description = "desr"
            )
        }
    }
}

@Composable
fun SelectionTab(
    onSelectedTab: (TabPage) -> Unit
) {

    val titles = listOf(TabPage.EXPERIENCE.title, TabPage.EDUCATION.title)
    var selectedIndex by rememberSaveable { mutableStateOf(0)  }

    TabRow(
        selectedTabIndex = selectedIndex,
        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
    ) {

        titles.forEachIndexed { index, title ->
            val selected = selectedIndex == index

            val textColor by animateColorAsState(
                if (!selected) MaterialTheme.colorScheme.onBackground
                else MaterialTheme.colorScheme.primary, label = "txt_color"
            )

            val selectedTab = if (title == TabPage.EXPERIENCE.title)
                TabPage.EXPERIENCE else TabPage.EDUCATION

            Tab(
                selected = selected,
                onClick = {
                    selectedIndex = index
                    onSelectedTab(selectedTab)
                },
                text = {
                    Text(text = title)
                },
                icon = {
                       Icon(
                           imageVector = if (selectedTab == TabPage.EXPERIENCE)
                               Icons.Filled.BusinessCenter else
                               Icons.Filled.School,
                           contentDescription = null
                       )

                },
                selectedContentColor = textColor,
                modifier = Modifier.background(Color.Transparent)
            )
        }
    }
}

enum class TabPage(val title: String) {
    EXPERIENCE("Experience"),
    EDUCATION("Education")
}

@Preview(
    showBackground = true,
    uiMode = UI_MODE_NIGHT_YES,
    heightDp = 1000
)
@Composable
fun HomePreview() {
    //GitResumeTheme {
        HomeScreen()
    //}
}