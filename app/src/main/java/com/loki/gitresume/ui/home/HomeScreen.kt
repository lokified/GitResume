package com.loki.gitresume.ui.home

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BusinessCenter
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.OpenInNew
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.loki.gitresume.R
import com.loki.gitresume.data.permissions.PermissionAction
import com.loki.gitresume.data.permissions.PermissionDialog
import com.loki.gitresume.ui.components.ContentBubble
import com.loki.gitresume.ui.components.HomeBottomBar
import com.loki.gitresume.ui.components.TimelineNode
import com.loki.gitresume.ui.components.TimelineNodePosition
import com.loki.gitresume.util.CircleParametersDefaults
import com.loki.gitresume.util.LineParametersDefaults
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    openProjectScreen: () -> Unit,
    openRepositoryScreen: () -> Unit,
    openLoginScreen: () -> Unit
) {

    val userProfile = viewModel.userProfile.value
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val openBrowser = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { _ -> }

    var isDownloadClick by remember { mutableStateOf(false) }

    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val lifecycle = lifecycleOwner.lifecycle

    DisposableEffect(key1 = lifecycle) {
        val lifecycleObserver = LifecycleEventObserver { _, event ->
            when(event) {
                Lifecycle.Event.ON_PAUSE -> {
                    uiState.errorMessage = ""
                    uiState.message = ""
                }
                else -> {}
            }
        }

        lifecycle.addObserver(lifecycleObserver)

        onDispose {
            lifecycle.removeObserver(lifecycleObserver)
        }
    }

    if (uiState.message.isNotEmpty()) {
        LaunchedEffect(key1 = uiState.message) {
            Toast.makeText(
                context,
                uiState.message,
                Toast.LENGTH_LONG
            ).show()
        }
    }

    if (uiState.errorMessage.isNotEmpty()) {
        LaunchedEffect(key1 = uiState.errorMessage) {
            Toast.makeText(
                context,
                uiState.errorMessage,
                Toast.LENGTH_LONG
            ).show()
        }
    }

    if (isDownloadClick) {
        PermissionDialog(
            context = context,
            permission = Manifest.permission.WRITE_EXTERNAL_STORAGE,
            permissionRationale = Manifest.permission.WRITE_EXTERNAL_STORAGE,
            snackbarHostState = snackbarHostState
        ) { action ->
            when (action) {

                PermissionAction.PermissionAlreadyGranted -> {
                    viewModel.downloadResume()
                    isDownloadClick = false
                }

                PermissionAction.PermissionGranted -> {
                    Toast.makeText(
                        context,
                        "Permission Granted",
                        Toast.LENGTH_LONG
                    ).show()
                    viewModel.downloadResume()
                    isDownloadClick = false
                }

                PermissionAction.PermissionDenied -> {
                    Toast.makeText(
                        context,
                        "Permission Denied",
                        Toast.LENGTH_LONG
                    ).show()
                    isDownloadClick = false
                }
            }
        }
    }

    Scaffold(
        bottomBar = {
            HomeBottomBar(
                onSeeProjectClick = openProjectScreen,
                onDownloadClick = {
                    isDownloadClick = true
                }
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

                    Text(
                        text = stringResource(R.string.sheldon_okware),
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = textColor
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = stringResource(R.string.mobile_developer),
                        fontSize = 18.sp,
                        color = textColor
                    )
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

                        Text(
                            text = stringResource(R.string.nairobi_kenya),
                            fontSize = 16.sp,
                            color = textColor
                        )
                    }
                }

                IconButton(
                    onClick = {
                        val intent = Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse("https://github.com/lokified")
                        )

                        openBrowser.launch(intent)
                              },
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
                    onClick = {
                        val intent = Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse("https://linkedin.com/in/sheldon-okware")
                        )

                        openBrowser.launch(intent)
                              },
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

                IconButton(
                    onClick = {
                        viewModel.logout(onLogout = openLoginScreen)
                    },
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Logout,
                        contentDescription = stringResource(R.string.logout_icon),
                        tint = Color.White)
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter)
                ) {

                    Column(
                        modifier = Modifier
                            .align(Alignment.Center)
                    ) {
                        Box(
                            modifier = Modifier
                                .offset(y = 100.dp)
                                .shadow(
                                    elevation = 12.dp,
                                    shape = CircleShape,
                                    spotColor = Color.Black
                                )
                        ) {

                            Image(
                                painter = painterResource(id = R.drawable.profile),
                                contentDescription = stringResource(R.string.profile_img),
                                modifier = Modifier
                                    .clip(CircleShape)
                                    .size(200.dp)
                            )
                        }
                    }
                }
            }

            Column(
                modifier = Modifier
                    .padding(start = 16.dp, top = 120.dp, end = 16.dp, bottom = 16.dp)
                    .background(
                        color = MaterialTheme.colorScheme.primary.copy(.03f),
                        shape = RoundedCornerShape(12.dp)
                    )
            ) {

                userProfile?.let { user ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceAround,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 24.dp)
                    ) {
                        ColumnItem(
                            number = user.publicRepos!!.toString(),
                            title = stringResource(R.string.repositories),
                            isClickable = true,
                            onItemClick = openRepositoryScreen
                        )
                        ColumnItem(number = user.following!!.toString(), title = stringResource(R.string.following))
                        ColumnItem(number = user.followers!!.toString(), title = stringResource(R.string.followers))
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = stringResource(R.string.about_sheldon),
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = stringResource(R.string.profile_summary),
                    modifier = Modifier.padding(horizontal = 8.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                ExperienceEducationSection(
                    currentTab = currentTab,
                    onSelectedTab = {
                        currentTab = it
                    }
                )

                Spacer(modifier = Modifier.height(16.dp))

                SkillsSection()
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
                startDate = stringResource(R.string.february_2023),
                endDate = stringResource(R.string.present),
                title = stringResource(R.string.android_developer),
                company = stringResource(R.string.machini_technologies),
                description = stringResource(R.string.machini_description)
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
                startDate = stringResource(R.string.may_2022),
                endDate = stringResource(R.string.august_2022),
                title = stringResource(R.string.software_developer_intern),
                company = stringResource(R.string.eclectics_international),
                description = stringResource(R.string.eclectics_description)
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
                startDate = stringResource(R.string.february_2021),
                endDate = stringResource(R.string.may_2022),
                title = stringResource(R.string.android_developer),
                company = stringResource(R.string.freelance),
                description = stringResource(R.string.freelance_description)
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
                startDate = stringResource(R.string.july_2021),
                endDate = stringResource(R.string.february_2022),
                title = stringResource(R.string.software_engineering),
                company = stringResource(R.string.moringa_school),
                description = stringResource(R.string.moringa_description)
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
                startDate = stringResource(R.string.september_2017),
                endDate = stringResource(R.string.may_2022),
                title = stringResource(R.string.bsc_mathematics_and_computer_science),
                company = stringResource(R.string.taita_taveta_university),
                description = stringResource(R.string.taita_description)
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

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SkillsSection() {

    val skills = listOf(
        "Kotlin", "Java" ,"Jetpack Compose", "XML", "Kotlin-coroutines", "MVVM", "MVI",
        "Retrofit", "Jetpack Components", "Unit Testing", "Firebase", "CI/CD",
        "Android Studio and Gradle", "Git", "Rest Api's"
        )

    Text(
        text = stringResource(R.string.skills),
        fontSize = 22.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(horizontal = 8.dp)
    )
    Spacer(modifier = Modifier.height(8.dp))

    FlowRow (
        modifier = Modifier.padding(horizontal = 8.dp)
    ) {
        skills.forEach {
            SkillItem(title = it, modifier = Modifier.padding(4.dp))
        }
    }

    Spacer(modifier = Modifier.height(16.dp))

}

@Composable
fun SkillItem(
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

enum class TabPage(val title: String) {
    EXPERIENCE("Experience"),
    EDUCATION("Education")
}

//@Preview(
//    showBackground = true,
//    heightDp = 2000
//)
//@Composable
//fun HomePreview() {
//    GitResumeTheme {
//        HomeScreen(openProjectScreen = {}, openRepositoryScreen = {})
//    }
//}