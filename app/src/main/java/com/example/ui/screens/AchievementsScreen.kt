package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
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
import com.example.data.model.AchievementBadge
import com.example.data.repository.SehatYukRepository
import com.example.ui.components.SDGBadge
import com.example.ui.theme.*

@Composable
fun AchievementsScreen(
    onNavigateBack: () -> Unit = {}
) {
    val user by SehatYukRepository.userProfile.collectAsState()
    val achievements by SehatYukRepository.achievements.collectAsState()
    var showShareSnackbar by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(AppBackground)
            .padding(horizontal = 20.dp)
            .testTag("achievements_screen"),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(10.dp))
            SDGBadge(text = "SDG 3 PENCAPAIAN & GAMIFIKASI")
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "Pencapaian & Lencana",
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.ExtraBold,
                    color = TextCharcoal
                )
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Koleksi lencana atas konsistensi kebiasaan hidup sehatmu di kampus.",
                style = MaterialTheme.typography.bodyMedium.copy(color = TextSecondary)
            )
        }

        // Hero Streak Gradient Card
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
                border = androidx.compose.foundation.BorderStroke(1.dp, EmeraldBorder)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            Brush.linearGradient(
                                listOf(Color(0xFFE6F8F1), Color(0xFFF0FDF8), SurfaceWhite)
                            )
                        )
                        .padding(20.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(48.dp)
                                    .clip(CircleShape)
                                    .background(FlameOrange),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(Icons.Default.LocalFireDepartment, contentDescription = null, tint = Color.White, modifier = Modifier.size(28.dp))
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text(
                                    text = "${user.currentStreak} Hari Beruntun",
                                    style = MaterialTheme.typography.titleLarge.copy(
                                        fontWeight = FontWeight.ExtraBold,
                                        color = TextCharcoal
                                    )
                                )
                                Text(
                                    text = "Rekor terpanjang: ${user.longestStreak} hari",
                                    style = MaterialTheme.typography.bodySmall.copy(color = EmeraldDark)
                                )
                            }
                        }

                        Surface(shape = RoundedCornerShape(50), color = WarmYellowLight) {
                            Text(
                                text = "Luar Biasa!",
                                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, color = FlameOrange),
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Progress to Next Level
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Level ${user.level}: ${user.levelTitle}", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, color = TextCharcoal))
                        Text("${user.currentXp} / ${user.maxXp} XP", style = MaterialTheme.typography.labelSmall.copy(color = TextSecondary))
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    LinearProgressIndicator(
                        progress = { (user.currentXp.toFloat() / user.maxXp) },
                        modifier = Modifier.fillMaxWidth().height(8.dp).clip(CircleShape),
                        color = EmeraldPrimary,
                        trackColor = MintLight
                    )
                }
            }
        }

        item {
            Text(
                text = "Lencana SehatYuk (${achievements.count { it.isUnlocked }}/${achievements.size} Terbuka)",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = TextCharcoal
                )
            )
        }

        items(achievements) { badge ->
            BadgeCard(badge = badge)
        }

        item {
            Button(
                onClick = { showShareSnackbar = true },
                modifier = Modifier.fillMaxWidth().height(50.dp).testTag("share_achievement_btn"),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(containerColor = EmeraldPrimary)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Share, contentDescription = null, tint = Color.White, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Bagikan Pencapaian ke Teman Kampus", fontWeight = FontWeight.Bold)
                }
            }

            if (showShareSnackbar) {
                Spacer(modifier = Modifier.height(8.dp))
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = MintLight,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Tautan pencapaian disalin ke clipboard! Siap dibagikan ke Story WhatsApp / Instagram kampus 🏆",
                        style = MaterialTheme.typography.bodySmall.copy(color = EmeraldDark, fontWeight = FontWeight.SemiBold),
                        modifier = Modifier.padding(12.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
fun BadgeCard(badge: AchievementBadge) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (badge.isUnlocked) SurfaceWhite else Color(0xFFFAFAFA)
        ),
        border = androidx.compose.foundation.BorderStroke(
            1.dp,
            if (badge.isUnlocked) CardBorder else Color(0xFFE2E8F0)
        )
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                Box(
                    modifier = Modifier
                        .size(46.dp)
                        .clip(RoundedCornerShape(14.dp))
                        .background(
                            if (badge.isUnlocked) {
                                when (badge.iconType) {
                                    "trophy" -> WarmYellowLight
                                    "water" -> WaterBlueLight
                                    "walk" -> MintLight
                                    else -> PurpleCalmLight
                                }
                            } else Color(0xFFEDF2F7)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = when (badge.iconType) {
                            "trophy" -> Icons.Default.EmojiEvents
                            "water" -> Icons.Default.WaterDrop
                            "walk" -> Icons.Default.DirectionsWalk
                            "zen" -> Icons.Default.SelfImprovement
                            "moon" -> Icons.Default.Bedtime
                            else -> Icons.Default.MilitaryTech
                        },
                        contentDescription = null,
                        tint = if (badge.isUnlocked) {
                            when (badge.iconType) {
                                "trophy" -> WarmYellow
                                "water" -> WaterBlue
                                "walk" -> EmeraldPrimary
                                else -> PurpleCalm
                            }
                        } else TextMuted,
                        modifier = Modifier.size(24.dp)
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column {
                    Text(
                        text = badge.title,
                        style = MaterialTheme.typography.titleSmall.copy(
                            fontWeight = FontWeight.Bold,
                            color = if (badge.isUnlocked) TextCharcoal else TextSecondary
                        )
                    )
                    Text(
                        text = badge.description,
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = TextSecondary,
                            fontSize = 11.sp
                        )
                    )
                }
            }

            if (badge.isUnlocked) {
                Surface(
                    shape = RoundedCornerShape(50),
                    color = MintLight
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Default.Check, contentDescription = null, tint = EmeraldDark, modifier = Modifier.size(14.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Terbuka", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, color = EmeraldDark))
                    }
                }
            } else {
                Surface(
                    shape = RoundedCornerShape(50),
                    color = Color(0xFFEDF2F7)
                ) {
                    Text(
                        text = badge.progressText ?: "Terkunci",
                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Medium, color = TextMuted),
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }
        }
    }
}
