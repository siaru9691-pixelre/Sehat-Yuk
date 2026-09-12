package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.example.ui.components.SehatYukBottomBar
import com.example.ui.components.SehatYukTopBar
import com.example.ui.screens.*
import com.example.ui.theme.AppBackground
import com.example.ui.theme.EmeraldPrimary
import com.example.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                SehatYukApp()
            }
        }
    }
}

sealed class Screen(val route: String) {
    data object Splash : Screen("splash")
    data object Onboarding : Screen("onboarding")
    data object Auth : Screen("auth")
    data object HealthGoals : Screen("health_goals")
    data object Main : Screen("main")
    data object Mood : Screen("mood")
    data object Reminders : Screen("reminders")
    data object Articles : Screen("articles")
    data object Achievements : Screen("achievements")
    data object SDGImpact : Screen("sdg_impact")
}

@Composable
fun SehatYukApp() {
    var currentScreen by remember { mutableStateOf<Screen>(Screen.Splash) }
    var currentTab by remember { mutableStateOf("home") }
    var previousScreen by remember { mutableStateOf<Screen>(Screen.Main) }
    var showNotificationDialog by remember { mutableStateOf(false) }

    fun navigateTo(screen: Screen) {
        previousScreen = currentScreen
        currentScreen = screen
    }

    fun navigateBack() {
        currentScreen = when (currentScreen) {
            Screen.Mood, Screen.Reminders, Screen.Articles, Screen.Achievements, Screen.SDGImpact -> Screen.Main
            Screen.HealthGoals -> if (previousScreen == Screen.Auth) Screen.Main else Screen.Main
            Screen.Auth -> Screen.Onboarding
            Screen.Onboarding -> Screen.Splash
            else -> Screen.Main
        }
    }

    BackHandler(enabled = currentScreen != Screen.Splash && currentScreen != Screen.Main) {
        navigateBack()
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = AppBackground
    ) {
        when (currentScreen) {
            Screen.Splash -> {
                SplashScreen(
                    onStartOnboarding = { navigateTo(Screen.Onboarding) }
                )
            }
            Screen.Onboarding -> {
                OnboardingScreen(
                    onFinishOnboarding = { navigateTo(Screen.Auth) }
                )
            }
            Screen.Auth -> {
                AuthScreen(
                    onLoginSuccess = { navigateTo(Screen.HealthGoals) }
                )
            }
            Screen.HealthGoals -> {
                HealthGoalsScreen(
                    onGoalsSaved = { navigateTo(Screen.Main) }
                )
            }
            else -> {
                // Main app container with unified top bar & bottom bar
                val isSubScreen = currentScreen != Screen.Main
                val title = when (currentScreen) {
                    Screen.Mood -> "Kesehatan Mental"
                    Screen.Reminders -> "Pengingat Sehat"
                    Screen.Articles -> "Edukasi & Tips"
                    Screen.Achievements -> "Pencapaian"
                    Screen.SDGImpact -> "SDG 3 PBB"
                    else -> "SehatYuk"
                }
                val subtitle = when (currentScreen) {
                    Screen.Mood -> "Check-in emosi & relaksasi"
                    Screen.Reminders -> "Ritme sehat terjadwal"
                    Screen.Articles -> "Riset medis mahasiswa"
                    Screen.Achievements -> "Lencana & rekor streak"
                    Screen.SDGImpact -> "Good Health & Well-being"
                    else -> when (currentTab) {
                        "home" -> "Dashboard Kebugaran"
                        "track" -> "Aktivitas & Hidrasi"
                        "challenges" -> "Misi & Gamifikasi"
                        "analytics" -> "Statistik Kebugaran"
                        else -> "Profil & Preferensi"
                    }
                }

                Scaffold(
                    topBar = {
                        SehatYukTopBar(
                            title = title,
                            subtitle = subtitle,
                            showBackButton = isSubScreen,
                            onBackClick = { navigateBack() },
                            onNotificationClick = { showNotificationDialog = true },
                            onProfileClick = {
                                if (currentScreen != Screen.Main) {
                                    currentScreen = Screen.Main
                                }
                                currentTab = "profile"
                            }
                        )
                    },
                    bottomBar = {
                        if (!isSubScreen) {
                            SehatYukBottomBar(
                                currentRoute = currentTab,
                                onNavigate = { tab ->
                                    currentTab = tab
                                }
                            )
                        }
                    },
                    containerColor = AppBackground
                ) { innerPadding ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    ) {
                        AnimatedContent(
                            targetState = if (isSubScreen) currentScreen else currentTab,
                            transitionSpec = { fadeIn() togetherWith fadeOut() },
                            label = "screen_content_switch"
                        ) { destination ->
                            when (destination) {
                                is Screen.Mood -> {
                                    MoodScreen(onNavigateBack = { navigateBack() })
                                }
                                is Screen.Reminders -> {
                                    RemindersScreen()
                                }
                                is Screen.Articles -> {
                                    ArticlesScreen()
                                }
                                is Screen.Achievements -> {
                                    AchievementsScreen(onNavigateBack = { navigateBack() })
                                }
                                is Screen.SDGImpact -> {
                                    SDGImpactScreen(onNavigateBack = { navigateBack() })
                                }
                                "home" -> {
                                    HomeScreen(
                                        onNavigateToTrack = { currentTab = "track" },
                                        onNavigateToMood = { navigateTo(Screen.Mood) },
                                        onNavigateToChallenges = { currentTab = "challenges" },
                                        onNavigateToArticles = { navigateTo(Screen.Articles) },
                                        onNavigateToStreak = { navigateTo(Screen.Achievements) },
                                        onNavigateToProfile = { currentTab = "profile" }
                                    )
                                }
                                "track" -> {
                                    TrackingScreen()
                                }
                                "challenges" -> {
                                    ChallengesScreen(
                                        onOpenHydration = { currentTab = "track" },
                                        onOpenSensor = { currentTab = "track" },
                                        onOpenJournal = { navigateTo(Screen.Mood) }
                                    )
                                }
                                "analytics" -> {
                                    AnalyticsScreen()
                                }
                                "profile" -> {
                                    ProfileScreen(
                                        onNavigateToGoals = { navigateTo(Screen.HealthGoals) },
                                        onNavigateToReminders = { navigateTo(Screen.Reminders) },
                                        onNavigateToAchievements = { navigateTo(Screen.Achievements) },
                                        onNavigateToArticles = { navigateTo(Screen.Articles) },
                                        onNavigateToSDG = { navigateTo(Screen.SDGImpact) },
                                        onLogout = { navigateTo(Screen.Auth) }
                                    )
                                }
                                else -> {
                                    HomeScreen(
                                        onNavigateToTrack = { currentTab = "track" },
                                        onNavigateToMood = { navigateTo(Screen.Mood) },
                                        onNavigateToChallenges = { currentTab = "challenges" },
                                        onNavigateToArticles = { navigateTo(Screen.Articles) },
                                        onNavigateToStreak = { navigateTo(Screen.Achievements) },
                                        onNavigateToProfile = { currentTab = "profile" }
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    if (showNotificationDialog) {
        AlertDialog(
            onDismissRequest = { showNotificationDialog = false },
            title = { Text("Notifikasi Sehat Harian") },
            text = {
                Text(
                    "🔔 Waktunya minum segelas air (250 ml) untuk menjaga fokus kuliah!\n\n🚶 Kamu sudah mencapai 5.240 langkah hari ini, tinggal 760 langkah lagi menuju target harian!"
                )
            },
            confirmButton = {
                Button(
                    onClick = { showNotificationDialog = false },
                    colors = ButtonDefaults.buttonColors(containerColor = EmeraldPrimary)
                ) {
                    Text("Tutup")
                }
            }
        )
    }
}
