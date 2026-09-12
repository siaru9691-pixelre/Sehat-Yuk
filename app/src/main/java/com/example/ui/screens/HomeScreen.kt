package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.HealthArticle
import com.example.data.model.MoodType
import com.example.data.repository.SehatYukRepository
import com.example.ui.components.ArticleDetailDialog
import com.example.ui.components.CircularHealthScoreCard
import com.example.ui.components.SDGBadge
import com.example.ui.theme.*

@Composable
fun HomeScreen(
    onNavigateToTrack: () -> Unit,
    onNavigateToMood: () -> Unit,
    onNavigateToChallenges: () -> Unit,
    onNavigateToArticles: () -> Unit,
    onNavigateToStreak: () -> Unit,
    onNavigateToProfile: () -> Unit
) {
    val user by SehatYukRepository.userProfile.collectAsState()
    val tracking by SehatYukRepository.trackingState.collectAsState()
    val dailyTasks by SehatYukRepository.dailyTasks.collectAsState()
    val articles by SehatYukRepository.articles.collectAsState()
    val currentMood by SehatYukRepository.currentMood.collectAsState()

    var selectedArticleForDialog by remember { mutableStateOf<HealthArticle?>(null) }
    var quickHydrateMessage by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(quickHydrateMessage) {
        if (quickHydrateMessage != null) {
            kotlinx.coroutines.delay(2500)
            quickHydrateMessage = null
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(AppBackground)
            .padding(horizontal = 20.dp)
            .testTag("home_screen"),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // User Greeting & Stats Bar
        item {
            Spacer(modifier = Modifier.height(14.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.clickable(onClick = onNavigateToProfile)
                ) {
                    Box(
                        modifier = Modifier
                            .size(46.dp)
                            .clip(CircleShape)
                            .background(
                                Brush.linearGradient(listOf(EmeraldPrimary, TealSecondary))
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "Avatar",
                            tint = Color.White,
                            modifier = Modifier.size(28.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "Halo, ${user.name.substringBefore(" ")} 👋",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = TextCharcoal
                                )
                            )
                        }
                        Text(
                            text = "Semangat sehat kuliah hari ini!",
                            style = MaterialTheme.typography.bodySmall.copy(color = TextSecondary)
                        )
                    }
                }

                // Points Chip
                Surface(
                    shape = RoundedCornerShape(50),
                    color = WarmYellowLight,
                    border = androidx.compose.foundation.BorderStroke(1.dp, WarmYellow.copy(alpha = 0.4f)),
                    modifier = Modifier.clickable(onClick = onNavigateToChallenges).testTag("points_chip")
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Bolt,
                            contentDescription = null,
                            tint = FlameOrange,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "${user.healthPoints} Poin",
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = FontWeight.ExtraBold,
                                color = TextCharcoal
                            )
                        )
                    }
                }
            }
        }

        // Hero 7-Day Streak Card
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(onClick = onNavigateToStreak)
                    .testTag("streak_banner_card"),
                shape = RoundedCornerShape(22.dp),
                colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
                border = androidx.compose.foundation.BorderStroke(1.dp, EmeraldBorder),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            Brush.horizontalGradient(
                                listOf(Color(0xFFE6F8F1), Color(0xFFF0FDF8), SurfaceWhite)
                            )
                        )
                        .padding(16.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(38.dp)
                                    .clip(CircleShape)
                                    .background(FlameOrange),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.LocalFireDepartment,
                                    contentDescription = "Streak",
                                    tint = Color.White,
                                    modifier = Modifier.size(22.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Text(
                                    text = "${user.currentStreak} Hari Beruntun!",
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        fontWeight = FontWeight.ExtraBold,
                                        color = TextCharcoal
                                    )
                                )
                                Text(
                                    text = "Target hidrasi & langkah konsisten",
                                    style = MaterialTheme.typography.bodySmall.copy(color = EmeraldDark)
                                )
                            }
                        }

                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                            contentDescription = "Detail",
                            tint = EmeraldDark,
                            modifier = Modifier.size(20.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // 7 Days Pill Row
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        val days = listOf("Sen", "Sel", "Rab", "Kam", "Jum", "Sab", "Min")
                        days.forEachIndexed { index, day ->
                            val isDone = index < 6
                            val isToday = index == 6
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = day,
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        color = if (isToday) EmeraldDark else TextMuted,
                                        fontWeight = if (isToday) FontWeight.Bold else FontWeight.Normal,
                                        fontSize = 10.sp
                                    )
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Box(
                                    modifier = Modifier
                                        .size(28.dp)
                                        .clip(CircleShape)
                                        .background(
                                            when {
                                                isDone -> EmeraldPrimary
                                                isToday -> MintSoft
                                                else -> CardBorder
                                            }
                                        )
                                        .border(
                                            if (isToday) 2.dp else 0.dp,
                                            if (isToday) EmeraldPrimary else Color.Transparent,
                                            CircleShape
                                        ),
                                    contentAlignment = Alignment.Center
                                ) {
                                    if (isDone) {
                                        Icon(
                                            imageVector = Icons.Default.Check,
                                            contentDescription = null,
                                            tint = Color.White,
                                            modifier = Modifier.size(16.dp)
                                        )
                                    } else if (isToday) {
                                        Icon(
                                            imageVector = Icons.Default.Star,
                                            contentDescription = null,
                                            tint = EmeraldDark,
                                            modifier = Modifier.size(14.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        // Circular Health Score Card
        item {
            CircularHealthScoreCard(
                score = user.healthScore,
                maxScore = 100,
                scoreDiffText = "↑ +4 poin vs kemarin",
                categoryLabel = "Sangat Baik",
                waterText = "${tracking.waterCurrentMl} ml",
                waterSub = "${((tracking.waterCurrentMl.toFloat() / tracking.waterTargetMl) * 100).toInt()}% (2.0L)",
                stepsText = "${tracking.stepsCurrent}",
                stepsSub = "${((tracking.stepsCurrent.toFloat() / tracking.stepsTarget) * 100).toInt()}% (6.0k)",
                sleepText = tracking.sleepDuration.substringBefore(" "),
                sleepSub = "Optimal 🌙",
                onScoreClick = onNavigateToTrack
            )
        }

        // Quick Hydration Logger Bar
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = WaterBlueBackground),
                border = androidx.compose.foundation.BorderStroke(1.dp, WaterBlueLight)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.LocalDrink,
                                contentDescription = null,
                                tint = WaterBlue,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Catat Cepat Minum Air",
                                style = MaterialTheme.typography.titleSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = TextCharcoal
                                )
                            )
                        }

                        Text(
                            text = "${tracking.waterCurrentMl} / ${tracking.waterTargetMl} ml",
                            style = MaterialTheme.typography.labelMedium.copy(
                                color = WaterBlue,
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Button(
                            onClick = {
                                SehatYukRepository.addWater(250)
                                quickHydrateMessage = "+250 ml air dicatat! Tetap segar & fokus 💧"
                            },
                            modifier = Modifier.weight(1f).height(44.dp).testTag("quick_water_250"),
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = WaterBlue)
                        ) {
                            Text("+250 ml (Gelas)", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                        }

                        Button(
                            onClick = {
                                SehatYukRepository.addWater(600)
                                quickHydrateMessage = "+600 ml tumbler dicatat! Luar biasa 🌊"
                            },
                            modifier = Modifier.weight(1f).height(44.dp).testTag("quick_water_600"),
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0369A1))
                        ) {
                            Text("+600 ml (Tumbler)", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                        }
                    }

                    AnimatedVisibility(visible = quickHydrateMessage != null) {
                        Column {
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = quickHydrateMessage.orEmpty(),
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = EmeraldDark,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                        }
                    }
                }
            }
        }

        // Daily Checklist Card
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
                border = androidx.compose.foundation.BorderStroke(1.dp, CardBorder)
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Target Harianmu",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = TextCharcoal
                            )
                        )
                        Text(
                            text = "${dailyTasks.count { it.isCompleted }}/${dailyTasks.size} Selesai",
                            style = MaterialTheme.typography.labelMedium.copy(
                                color = EmeraldDark,
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    dailyTasks.forEach { task ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { SehatYukRepository.toggleDailyTask(task.id) }
                                .padding(vertical = 8.dp)
                                .testTag("task_row_${task.id}"),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.weight(1f)
                            ) {
                                Checkbox(
                                    checked = task.isCompleted,
                                    onCheckedChange = { SehatYukRepository.toggleDailyTask(task.id) },
                                    colors = CheckboxDefaults.colors(checkedColor = EmeraldPrimary)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = task.title,
                                    style = MaterialTheme.typography.bodyMedium.copy(
                                        color = if (task.isCompleted) TextMuted else TextCharcoal,
                                        fontWeight = if (task.isCompleted) FontWeight.Normal else FontWeight.Medium
                                    )
                                )
                            }

                            Surface(
                                shape = RoundedCornerShape(50),
                                color = if (task.isCompleted) MintLight else WarmYellowLight
                            ) {
                                Text(
                                    text = "+${task.pointsReward} Pts",
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = if (task.isCompleted) EmeraldDark else FlameOrange
                                    ),
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                                )
                            }
                        }
                    }
                }
            }
        }

        // Quick Mood Check-In Section
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(onClick = onNavigateToMood)
                    .testTag("quick_mood_card"),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
                border = androidx.compose.foundation.BorderStroke(1.dp, CardBorder)
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            SDGBadge(text = "Target 3.4 Jiwa")
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Bagaimana perasaanmu sekarang?",
                                style = MaterialTheme.typography.titleSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = TextCharcoal
                                )
                            )
                        }

                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                            contentDescription = "Buka Mood",
                            tint = EmeraldDark
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        MoodType.values().forEach { mood ->
                            val isCurrent = currentMood == mood
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier
                                    .clip(RoundedCornerShape(12.dp))
                                    .clickable { SehatYukRepository.setMood(mood) }
                                    .padding(4.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(44.dp)
                                        .clip(CircleShape)
                                        .background(if (isCurrent) MintSoft else AppBackground)
                                        .border(
                                            if (isCurrent) 2.dp else 1.dp,
                                            if (isCurrent) EmeraldPrimary else CardBorder,
                                            CircleShape
                                        ),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(text = mood.emoji, fontSize = 22.sp)
                                }
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = mood.labelIndo,
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        fontWeight = if (isCurrent) FontWeight.Bold else FontWeight.Medium,
                                        color = if (isCurrent) EmeraldDark else TextSecondary,
                                        fontSize = 11.sp
                                    )
                                )
                            }
                        }
                    }
                }
            }
        }

        // Editor's Pick Health Article
        item {
            val featuredArticle = articles.firstOrNull { it.isEditorChoice } ?: articles.first()
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Rekomendasi Minggu Ini",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = TextCharcoal
                        )
                    )
                    Text(
                        text = "Lihat Semua",
                        style = MaterialTheme.typography.labelMedium.copy(
                            color = EmeraldDark,
                            fontWeight = FontWeight.Bold
                        ),
                        modifier = Modifier.clickable(onClick = onNavigateToArticles)
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { selectedArticleForDialog = featuredArticle }
                        .testTag("featured_article_card"),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
                    border = androidx.compose.foundation.BorderStroke(1.dp, CardBorder)
                ) {
                    Column(modifier = Modifier.padding(18.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            SDGBadge(text = featuredArticle.sdgTag)
                            Text(
                                text = "${featuredArticle.readTimeMinutes} menit baca",
                                style = MaterialTheme.typography.labelSmall.copy(color = TextMuted)
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = featuredArticle.title,
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = TextCharcoal
                            )
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = featuredArticle.summary,
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = TextSecondary,
                                lineHeight = 18.sp
                            ),
                            maxLines = 2
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = featuredArticle.author,
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = FontWeight.SemiBold,
                                    color = EmeraldDark
                                )
                            )

                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.Star,
                                    contentDescription = null,
                                    tint = WarmYellow,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(2.dp))
                                Text(
                                    text = "${featuredArticle.rating}",
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = TextCharcoal
                                    )
                                )
                            }
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
        }
    }

    selectedArticleForDialog?.let { article ->
        ArticleDetailDialog(
            article = article,
            onDismiss = { selectedArticleForDialog = null },
            onBookmarkToggle = { SehatYukRepository.toggleBookmark(article.id) }
        )
    }
}
