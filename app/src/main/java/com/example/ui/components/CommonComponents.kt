package com.example.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.HealthArticle
import com.example.ui.theme.*
import kotlinx.coroutines.delay

@Composable
fun SDGBadge(
    text: String = "Target PBB SDG 3: Kehidupan Sehat & Sejahtera",
    modifier: Modifier = Modifier,
    containerColor: Color = MintLight,
    contentColor: Color = EmeraldDark
) {
    Surface(
        modifier = modifier.testTag("sdg_badge"),
        shape = RoundedCornerShape(50),
        color = containerColor,
        border = androidx.compose.foundation.BorderStroke(1.dp, EmeraldBorder.copy(alpha = 0.5f))
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = Icons.Default.Spa,
                contentDescription = "SDG 3 Icon",
                tint = contentColor,
                modifier = Modifier.size(14.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = text,
                style = MaterialTheme.typography.labelMedium.copy(
                    fontWeight = FontWeight.SemiBold,
                    color = contentColor
                )
            )
        }
    }
}

@Composable
fun SehatYukTopBar(
    title: String = "SehatYuk",
    subtitle: String = "Dashboard",
    showBackButton: Boolean = false,
    onBackClick: () -> Unit = {},
    onNotificationClick: () -> Unit = {},
    onProfileClick: () -> Unit = {}
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("top_bar"),
        color = SurfaceWhite,
        shadowElevation = 1.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .padding(horizontal = 16.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                if (showBackButton) {
                    IconButton(
                        onClick = onBackClick,
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(AppBackground)
                            .testTag("back_button")
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Kembali",
                            tint = TextCharcoal
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                } else {
                    // SehatYuk Logo Squircle
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(
                                Brush.linearGradient(
                                    listOf(EmeraldPrimary, EmeraldDark)
                                )
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Favorite,
                            contentDescription = "Logo",
                            tint = Color.White,
                            modifier = Modifier.size(22.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                }

                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = title,
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.Bold,
                                color = TextCharcoal
                            )
                        )
                        if (title == "SehatYuk") {
                            Box(
                                modifier = Modifier
                                    .padding(start = 4.dp, top = 2.dp)
                                    .size(6.dp)
                                    .clip(CircleShape)
                                    .background(EmeraldPrimary)
                            )
                        }
                    }
                    Text(
                        text = subtitle,
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = TextMuted
                        )
                    )
                }
            }

            // Actions: Notifications & Avatar
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                IconButton(
                    onClick = onNotificationClick,
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(AppBackground)
                        .testTag("notification_button")
                ) {
                    Box(contentAlignment = Alignment.TopEnd) {
                        Icon(
                            imageVector = Icons.Outlined.Notifications,
                            contentDescription = "Notifikasi",
                            tint = TextSecondary,
                            modifier = Modifier.size(22.dp)
                        )
                        // Notification alert dot
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .clip(CircleShape)
                                .background(EmeraldPrimary)
                        )
                    }
                }

                // Profile Avatar Button
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(
                            Brush.linearGradient(
                                listOf(EmeraldDark, TealSecondary)
                            )
                        )
                        .clickable(onClick = onProfileClick)
                        .testTag("profile_avatar_top"),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Profil",
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun SehatYukBottomBar(
    currentRoute: String,
    onNavigate: (String) -> Unit
) {
    val items = listOf(
        NavigationItem("home", "Beranda", Icons.Default.Home, Icons.Outlined.Home),
        NavigationItem("track", "Tracking", Icons.Default.WaterDrop, Icons.Outlined.WaterDrop),
        NavigationItem("challenges", "Tantangan", Icons.Default.EmojiEvents, Icons.Outlined.EmojiEvents),
        NavigationItem("analytics", "Statistik", Icons.Default.QueryStats, Icons.Outlined.QueryStats),
        NavigationItem("profile", "Profil", Icons.Default.Person, Icons.Outlined.Person)
    )

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("bottom_nav"),
        color = SurfaceWhite,
        shadowElevation = 8.dp,
        border = androidx.compose.foundation.BorderStroke(0.5.dp, CardBorder)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding()
                .padding(vertical = 6.dp, horizontal = 8.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            items.forEach { item ->
                val isSelected = currentRoute == item.route
                Column(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .clickable { onNavigate(item.route) }
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                        .testTag("nav_item_${item.route}"),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = if (isSelected) {
                            Modifier
                                .clip(RoundedCornerShape(16.dp))
                                .background(EmeraldLight)
                                .padding(horizontal = 14.dp, vertical = 4.dp)
                        } else {
                            Modifier.padding(horizontal = 14.dp, vertical = 4.dp)
                        },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = if (isSelected) item.selectedIcon else item.unselectedIcon,
                            contentDescription = item.label,
                            tint = if (isSelected) EmeraldPrimary else TextMuted,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                    Text(
                        text = item.label,
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                            color = if (isSelected) EmeraldPrimary else TextMuted,
                            fontSize = 11.sp
                        )
                    )
                }
            }
        }
    }
}

private data class NavigationItem(
    val route: String,
    val label: String,
    val selectedIcon: androidx.compose.ui.graphics.vector.ImageVector,
    val unselectedIcon: androidx.compose.ui.graphics.vector.ImageVector
)

@Composable
fun CircularHealthScoreCard(
    score: Int = 78,
    maxScore: Int = 100,
    scoreDiffText: String = "↑ +4 poin vs kemarin",
    categoryLabel: String = "Sangat Baik",
    waterText: String = "1.500 ml",
    waterSub: String = "75% (2.0L)",
    stepsText: String = "4.820",
    stepsSub: String = "80% (6.0k)",
    sleepText: String = "7.2 jam",
    sleepSub: String = "Optimal 🌙",
    onScoreClick: () -> Unit = {}
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onScoreClick)
            .testTag("health_score_card"),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
        border = androidx.compose.foundation.BorderStroke(1.dp, CardBorder),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Header Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(10.dp)
                            .clip(CircleShape)
                            .background(EmeraldPrimary)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Skor Kebugaran Hari Ini",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = TextCharcoal
                        )
                    )
                }

                Surface(
                    shape = RoundedCornerShape(50),
                    color = MintSoft
                ) {
                    Text(
                        text = categoryLabel,
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = FontWeight.Bold,
                            color = EmeraldDark
                        ),
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Animated Circular Progress Ring
            val animatedScore by animateFloatAsState(
                targetValue = score / maxScore.toFloat(),
                animationSpec = tween(durationMillis = 1000, easing = FastOutSlowInEasing),
                label = "score_anim"
            )

            Box(
                modifier = Modifier.size(160.dp),
                contentAlignment = Alignment.Center
            ) {
                androidx.compose.foundation.Canvas(modifier = Modifier.fillMaxSize()) {
                    val strokeWidth = 14.dp.toPx()
                    // Track circle
                    drawCircle(
                        color = Color(0xFFF1F5F9),
                        radius = (size.minDimension - strokeWidth) / 2,
                        style = Stroke(width = strokeWidth)
                    )
                    // Progress arc
                    drawArc(
                        brush = Brush.sweepGradient(
                            listOf(EmeraldPrimary, TealSecondary, EmeraldPrimary)
                        ),
                        startAngle = -90f,
                        sweepAngle = animatedScore * 360f,
                        useCenter = false,
                        style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
                    )
                }

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Row(verticalAlignment = Alignment.Bottom) {
                        Text(
                            text = "$score",
                            style = MaterialTheme.typography.headlineLarge.copy(
                                fontWeight = FontWeight.ExtraBold,
                                fontSize = 38.sp,
                                color = TextCharcoal
                            )
                        )
                        Text(
                            text = "/$maxScore",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = TextMuted
                            ),
                            modifier = Modifier.padding(bottom = 6.dp)
                        )
                    }
                    Text(
                        text = scoreDiffText,
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = FontWeight.SemiBold,
                            color = EmeraldDark
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // 3 Sub-Metrics Cards
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                SubMetricTile(
                    modifier = Modifier.weight(1f),
                    icon = Icons.Default.WaterDrop,
                    iconTint = WaterBlue,
                    bgColor = WaterBlueBackground,
                    title = "Hidrasi",
                    value = waterText,
                    subValue = waterSub
                )
                SubMetricTile(
                    modifier = Modifier.weight(1f),
                    icon = Icons.Default.DirectionsWalk,
                    iconTint = EmeraldPrimary,
                    bgColor = MintLight,
                    title = "Langkah",
                    value = stepsText,
                    subValue = stepsSub
                )
                SubMetricTile(
                    modifier = Modifier.weight(1f),
                    icon = Icons.Default.Bedtime,
                    iconTint = PurpleCalm,
                    bgColor = PurpleCalmLight,
                    title = "Tidur",
                    value = sleepText,
                    subValue = sleepSub
                )
            }
        }
    }
}

@Composable
fun SubMetricTile(
    modifier: Modifier = Modifier,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    iconTint: Color,
    bgColor: Color,
    title: String,
    value: String,
    subValue: String
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        color = bgColor,
        border = androidx.compose.foundation.BorderStroke(1.dp, CardBorder)
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = iconTint,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.labelSmall.copy(
                    color = TextSecondary,
                    fontWeight = FontWeight.Medium
                )
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = value,
                style = MaterialTheme.typography.labelLarge.copy(
                    fontWeight = FontWeight.Bold,
                    color = TextCharcoal
                )
            )
            Text(
                text = subValue,
                style = MaterialTheme.typography.labelSmall.copy(
                    color = TextSecondary,
                    fontSize = 10.sp
                )
            )
        }
    }
}

@Composable
fun BreathingExerciseDialog(
    onDismiss: () -> Unit
) {
    var stepPhase by remember { mutableStateOf("Tarik Napas") }
    var secondsLeftInPhase by remember { mutableIntStateOf(4) }
    var totalSecondsRemaining by remember { mutableIntStateOf(180) } // 3 mins
    var isRunning by remember { mutableStateOf(true) }

    LaunchedEffect(isRunning) {
        val phases = listOf("Tarik Napas", "Tahan Napas", "Hembuskan Perlahan", "Rileks & Tahan")
        var currentPhaseIndex = 0
        while (isRunning && totalSecondsRemaining > 0) {
            delay(1000)
            totalSecondsRemaining--
            secondsLeftInPhase--
            if (secondsLeftInPhase <= 0) {
                currentPhaseIndex = (currentPhaseIndex + 1) % phases.size
                stepPhase = phases[currentPhaseIndex]
                secondsLeftInPhase = 4
            }
        }
    }

    val infiniteTransition = rememberInfiniteTransition(label = "breathe")
    val circleScale by infiniteTransition.animateFloat(
        initialValue = 0.8f,
        targetValue = 1.25f,
        animationSpec = infiniteRepeatable(
            animation = tween(4000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "scale"
    )

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
                SDGBadge(text = "Target SDG 3.4: Relaksasi Mental")
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "Latihan Pernapasan 4–4–4",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = TextCharcoal
                    )
                )
                Text(
                    text = "De-eskalasi stres kuliah & tenangkan sistem saraf",
                    style = MaterialTheme.typography.bodySmall.copy(color = TextSecondary),
                    textAlign = TextAlign.Center
                )
            }
        },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Interactive visual breathing ball
                Box(
                    modifier = Modifier
                        .size(170.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Box(
                        modifier = Modifier
                            .size((140 * circleScale).dp)
                            .clip(CircleShape)
                            .background(
                                Brush.radialGradient(
                                    listOf(MintSoft, TealLight, EmeraldLight.copy(alpha = 0.2f))
                                )
                            )
                            .border(2.dp, EmeraldPrimary.copy(alpha = 0.6f), CircleShape)
                    )
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = stepPhase,
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = EmeraldDark
                            )
                        )
                        Text(
                            text = "$secondsLeftInPhase detik",
                            style = MaterialTheme.typography.headlineMedium.copy(
                                fontWeight = FontWeight.ExtraBold,
                                color = TextCharcoal
                            )
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
                val minutes = totalSecondsRemaining / 60
                val seconds = totalSecondsRemaining % 60
                Text(
                    text = "Waktu tersisa: %02d:%02d".format(minutes, seconds),
                    style = MaterialTheme.typography.labelMedium.copy(
                        color = TextSecondary,
                        fontWeight = FontWeight.SemiBold
                    )
                )
            }
        },
        confirmButton = {
            Button(
                onClick = onDismiss,
                colors = ButtonDefaults.buttonColors(containerColor = EmeraldPrimary),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.testTag("close_breathing_btn")
            ) {
                Text("Selesai & Tenang", fontWeight = FontWeight.Bold)
            }
        }
    )
}

@Composable
fun ArticleDetailDialog(
    article: HealthArticle,
    onDismiss: () -> Unit,
    onBookmarkToggle: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        modifier = Modifier.fillMaxWidth(0.95f),
        title = {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    SDGBadge(text = article.sdgTag)
                    IconButton(onClick = onBookmarkToggle) {
                        Icon(
                            imageVector = if (article.isBookmarked) Icons.Default.Bookmark else Icons.Outlined.BookmarkBorder,
                            contentDescription = "Simpan",
                            tint = if (article.isBookmarked) EmeraldPrimary else TextMuted
                        )
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = article.title,
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = TextCharcoal
                    )
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "${article.author} • ${article.readTimeMinutes} menit baca",
                    style = MaterialTheme.typography.labelSmall.copy(color = TextSecondary)
                )
            }
        },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(max = 380.dp)
            ) {
                Divider(color = CardBorder, modifier = Modifier.padding(vertical = 8.dp))
                androidx.compose.foundation.rememberScrollState().let { scrollState ->
                    Column(modifier = Modifier.verticalScroll(scrollState)) {
                        Text(
                            text = article.content,
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = TextDark,
                                lineHeight = 22.sp
                            )
                        )
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = onDismiss,
                colors = ButtonDefaults.buttonColors(containerColor = EmeraldPrimary),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Tutup", fontWeight = FontWeight.Bold)
            }
        }
    )
}
