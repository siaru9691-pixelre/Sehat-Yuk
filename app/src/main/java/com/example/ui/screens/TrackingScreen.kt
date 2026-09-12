package com.example.ui.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.repository.SehatYukRepository
import com.example.ui.components.SDGBadge
import com.example.ui.theme.*

@Composable
fun TrackingScreen(
    onNavigateBack: () -> Unit = {}
) {
    val tracking by SehatYukRepository.trackingState.collectAsState()
    var selectedTab by remember { mutableIntStateOf(0) } // 0: Hidrasi, 1: Aktivitas, 2: Tidur

    val tabs = listOf("💧 Hidrasi", "🚶 Aktivitas", "🌙 Tidur")

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(AppBackground)
            .padding(horizontal = 20.dp)
            .testTag("tracking_screen"),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Date Selector Bar
        item {
            Spacer(modifier = Modifier.height(10.dp))
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
                border = androidx.compose.foundation.BorderStroke(1.dp, CardBorder)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = {}, modifier = Modifier.size(36.dp)) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Kemarin", tint = TextSecondary)
                    }

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.CalendarToday, contentDescription = null, tint = EmeraldPrimary, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = tracking.dateLabel,
                            style = MaterialTheme.typography.titleSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = TextCharcoal
                            )
                        )
                    }

                    IconButton(onClick = {}, modifier = Modifier.size(36.dp)) {
                        Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = "Besok", tint = TextSecondary)
                    }
                }
            }
        }

        // Segmented Tabs
        item {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                color = Color(0xFFEDF2F7)
            ) {
                Row(
                    modifier = Modifier.padding(4.dp),
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    tabs.forEachIndexed { index, label ->
                        val isSelected = selectedTab == index
                        Surface(
                            modifier = Modifier
                                .weight(1f)
                                .clickable { selectedTab = index }
                                .testTag("tracking_tab_$index"),
                            shape = RoundedCornerShape(12.dp),
                            color = if (isSelected) SurfaceWhite else Color.Transparent,
                            shadowElevation = if (isSelected) 2.dp else 0.dp
                        ) {
                            Text(
                                text = label,
                                style = MaterialTheme.typography.labelMedium.copy(
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                    color = if (isSelected) EmeraldDark else TextSecondary
                                ),
                                modifier = Modifier
                                    .padding(vertical = 10.dp)
                                    .wrapContentWidth(Alignment.CenterHorizontally)
                            )
                        }
                    }
                }
            }
        }

        // Tab Content
        item {
            AnimatedContent(
                targetState = selectedTab,
                transitionSpec = { fadeIn() togetherWith fadeOut() },
                label = "tracking_tab_content"
            ) { tab ->
                when (tab) {
                    0 -> HydrationTrackingTab(tracking)
                    1 -> ActivityTrackingTab(tracking)
                    else -> SleepTrackingTab(tracking)
                }
            }
        }

        // SDG Fact Card
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = MintLight),
                border = androidx.compose.foundation.BorderStroke(1.dp, EmeraldBorder)
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    Text(text = "🌿", fontSize = 24.sp)
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        SDGBadge(text = "SDG 3.4 Kebugaran Mahasiswa")
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Keseimbangan Hidrasi, Langkah, & Tidur",
                            style = MaterialTheme.typography.titleSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = TextCharcoal
                            )
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = "Menjaga ketiga pilar ini secara stabil meningkatkan stamina mental hingga 40% dan mencegah burnout akademik berkepanjangan.",
                            style = MaterialTheme.typography.bodySmall.copy(color = TextSecondary, lineHeight = 18.sp)
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
fun HydrationTrackingTab(tracking: com.example.data.model.DailyTrackingState) {
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        // Hero Hydration Progress Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
            border = androidx.compose.foundation.BorderStroke(1.dp, CardBorder),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Progres Minum Air",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = TextCharcoal
                        )
                    )
                    Surface(shape = RoundedCornerShape(50), color = WaterBlueLight) {
                        Text(
                            text = "${tracking.glassesDrank} dari 8 Gelas",
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = WaterBlue
                            ),
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Big Volume Numbers
                Row(verticalAlignment = Alignment.Bottom) {
                    Text(
                        text = "${tracking.waterCurrentMl}",
                        style = MaterialTheme.typography.headlineLarge.copy(
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 36.sp,
                            color = WaterBlue
                        )
                    )
                    Text(
                        text = " / ${tracking.waterTargetMl} ml",
                        style = MaterialTheme.typography.titleMedium.copy(
                            color = TextMuted,
                            fontWeight = FontWeight.Bold
                        ),
                        modifier = Modifier.padding(bottom = 6.dp)
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Linear Progress
                val progress = (tracking.waterCurrentMl.toFloat() / tracking.waterTargetMl).coerceIn(0f, 1f)
                LinearProgressIndicator(
                    progress = { progress },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(10.dp)
                        .clip(CircleShape),
                    color = WaterBlue,
                    trackColor = WaterBlueLight
                )

                Spacer(modifier = Modifier.height(20.dp))

                // 8 Glasses Grid (Interactive toggle)
                Text(
                    text = "Ketuk gelas untuk mencatat atau membatalkan:",
                    style = MaterialTheme.typography.bodySmall.copy(color = TextSecondary)
                )

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    (0 until 8).forEach { index ->
                        val isDrunk = index < tracking.glassesDrank
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .clickable { SehatYukRepository.toggleGlass(index) }
                                .padding(4.dp)
                                .testTag("glass_icon_$index")
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(32.dp)
                                    .clip(CircleShape)
                                    .background(if (isDrunk) WaterBlue else Color(0xFFE2E8F0)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.WaterDrop,
                                    contentDescription = "Gelas ${index + 1}",
                                    tint = if (isDrunk) Color.White else TextMuted,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "${index + 1}",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = if (isDrunk) FontWeight.Bold else FontWeight.Normal,
                                    color = if (isDrunk) WaterBlue else TextMuted,
                                    fontSize = 10.sp
                                )
                            )
                        }
                    }
                }
            }
        }

        // Quick Add Actions
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
            border = androidx.compose.foundation.BorderStroke(1.dp, CardBorder)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Tambah Cepat",
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontWeight = FontWeight.Bold,
                        color = TextCharcoal
                    )
                )
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    OutlinedButton(
                        onClick = { SehatYukRepository.addWater(250) },
                        modifier = Modifier.weight(1f).height(44.dp).testTag("track_water_250"),
                        shape = RoundedCornerShape(12.dp),
                        border = androidx.compose.foundation.BorderStroke(1.dp, WaterBlue)
                    ) {
                        Text("+250 ml", color = WaterBlue, fontWeight = FontWeight.Bold)
                    }
                    OutlinedButton(
                        onClick = { SehatYukRepository.addWater(500) },
                        modifier = Modifier.weight(1f).height(44.dp).testTag("track_water_500"),
                        shape = RoundedCornerShape(12.dp),
                        border = androidx.compose.foundation.BorderStroke(1.dp, WaterBlue)
                    ) {
                        Text("+500 ml", color = WaterBlue, fontWeight = FontWeight.Bold)
                    }
                    Button(
                        onClick = { SehatYukRepository.addWater(600) },
                        modifier = Modifier.weight(1.2f).height(44.dp).testTag("track_water_tumbler"),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = WaterBlue)
                    ) {
                        Text("+600 ml Tumbler", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                    }
                }
            }
        }
    }
}

@Composable
fun ActivityTrackingTab(tracking: com.example.data.model.DailyTrackingState) {
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        // Steps Hero Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
            border = androidx.compose.foundation.BorderStroke(1.dp, CardBorder),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Langkah Harian",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = TextCharcoal
                        )
                    )
                    Surface(shape = RoundedCornerShape(50), color = MintLight) {
                        Text(
                            text = "Sensor Otomatis",
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = EmeraldDark
                            ),
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row(verticalAlignment = Alignment.Bottom) {
                    Text(
                        text = "${tracking.stepsCurrent}",
                        style = MaterialTheme.typography.headlineLarge.copy(
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 38.sp,
                            color = EmeraldDark
                        )
                    )
                    Text(
                        text = " / ${tracking.stepsTarget} langkah",
                        style = MaterialTheme.typography.titleMedium.copy(
                            color = TextMuted,
                            fontWeight = FontWeight.Bold
                        ),
                        modifier = Modifier.padding(bottom = 6.dp)
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                val progress = (tracking.stepsCurrent.toFloat() / tracking.stepsTarget).coerceIn(0f, 1f)
                LinearProgressIndicator(
                    progress = { progress },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(10.dp)
                        .clip(CircleShape),
                    color = EmeraldPrimary,
                    trackColor = MintLight
                )

                Spacer(modifier = Modifier.height(20.dp))

                // Stats Tiles
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    ActivityStatTile(
                        modifier = Modifier.weight(1f),
                        icon = Icons.Default.DirectionsWalk,
                        label = "Jarak",
                        value = "${tracking.distanceKm} km"
                    )
                    ActivityStatTile(
                        modifier = Modifier.weight(1f),
                        icon = Icons.Default.LocalFireDepartment,
                        label = "Kalori",
                        value = "${tracking.caloriesKcal} kkal"
                    )
                    ActivityStatTile(
                        modifier = Modifier.weight(1f),
                        icon = Icons.Default.Timer,
                        label = "Aktif",
                        value = "${tracking.activeMinutes} mnt"
                    )
                }
            }
        }

        // Hourly Steps Chart
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
            border = androidx.compose.foundation.BorderStroke(1.dp, CardBorder)
        ) {
            Column(modifier = Modifier.padding(18.dp)) {
                Text(
                    text = "Aktivitas Per Jam",
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontWeight = FontWeight.Bold,
                        color = TextCharcoal
                    )
                )
                Spacer(modifier = Modifier.height(14.dp))

                val hours = listOf("08:00", "10:00", "12:00", "14:00", "16:00", "18:00")
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Bottom
                ) {
                    tracking.hourlySteps.forEachIndexed { idx, frac ->
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Bottom,
                            modifier = Modifier.fillMaxHeight()
                        ) {
                            Box(
                                modifier = Modifier
                                    .width(24.dp)
                                    .fillMaxHeight(frac)
                                    .clip(RoundedCornerShape(topStart = 6.dp, topEnd = 6.dp))
                                    .background(
                                        Brush.verticalGradient(
                                            listOf(EmeraldPrimary, EmeraldDark)
                                        )
                                    )
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = hours.getOrElse(idx) { "" },
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = TextSecondary,
                                    fontSize = 10.sp
                                )
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                Button(
                    onClick = { SehatYukRepository.addSteps(500) },
                    modifier = Modifier.fillMaxWidth().testTag("add_500_steps_btn"),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = EmeraldPrimary)
                ) {
                    Text("+500 Langkah Keliling Kampus", fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
fun SleepTrackingTab(tracking: com.example.data.model.DailyTrackingState) {
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        // Sleep Hero Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
            border = androidx.compose.foundation.BorderStroke(1.dp, CardBorder),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Durasi Tidur Semalam",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = TextCharcoal
                        )
                    )
                    Surface(shape = RoundedCornerShape(50), color = PurpleCalmLight) {
                        Text(
                            text = tracking.sleepQuality,
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = PurpleCalm
                            ),
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = tracking.sleepDuration,
                    style = MaterialTheme.typography.headlineLarge.copy(
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 32.sp,
                        color = PurpleCalm
                    )
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "Skor Kualitas Tidur: ${tracking.sleepScore}% (Optimal)",
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = EmeraldDark,
                        fontWeight = FontWeight.SemiBold
                    )
                )

                Spacer(modifier = Modifier.height(20.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Surface(
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(16.dp),
                        color = AppBackground,
                        border = androidx.compose.foundation.BorderStroke(1.dp, CardBorder)
                    ) {
                        Column(modifier = Modifier.padding(14.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("Mulai Tidur", style = MaterialTheme.typography.labelSmall.copy(color = TextSecondary))
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(tracking.sleepBedtime, style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, color = TextCharcoal))
                        }
                    }

                    Surface(
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(16.dp),
                        color = AppBackground,
                        border = androidx.compose.foundation.BorderStroke(1.dp, CardBorder)
                    ) {
                        Column(modifier = Modifier.padding(14.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("Waktu Bangun", style = MaterialTheme.typography.labelSmall.copy(color = TextSecondary))
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(tracking.sleepWakeup, style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, color = TextCharcoal))
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ActivityStatTile(
    modifier: Modifier = Modifier,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    value: String
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        color = AppBackground,
        border = androidx.compose.foundation.BorderStroke(1.dp, CardBorder)
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(imageVector = icon, contentDescription = null, tint = EmeraldPrimary, modifier = Modifier.size(18.dp))
            Spacer(modifier = Modifier.height(4.dp))
            Text(label, style = MaterialTheme.typography.labelSmall.copy(color = TextSecondary))
            Spacer(modifier = Modifier.height(2.dp))
            Text(value, style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold, color = TextCharcoal))
        }
    }
}
