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
import com.example.data.model.ChallengeItem
import com.example.data.repository.SehatYukRepository
import com.example.ui.components.SDGBadge
import com.example.ui.theme.*

@Composable
fun ChallengesScreen(
    onOpenHydration: () -> Unit = {},
    onOpenSensor: () -> Unit = {},
    onOpenJournal: () -> Unit = {}
) {
    val user by SehatYukRepository.userProfile.collectAsState()
    val challenges by SehatYukRepository.challenges.collectAsState()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(AppBackground)
            .padding(horizontal = 20.dp)
            .testTag("challenges_screen"),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Gamification Header Banner
        item {
            Spacer(modifier = Modifier.height(10.dp))
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
                border = androidx.compose.foundation.BorderStroke(1.dp, CardBorder),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            Brush.verticalGradient(
                                listOf(Color(0xFFE6F8F0), SurfaceWhite)
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
                                    .size(46.dp)
                                    .clip(CircleShape)
                                    .background(WarmYellow),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.EmojiEvents,
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier.size(26.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text(
                                    text = "Level ${user.level} • ${user.levelTitle}",
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        fontWeight = FontWeight.ExtraBold,
                                        color = TextCharcoal
                                    )
                                )
                                Text(
                                    text = "${user.healthPoints} Poin Sehat Terkumpul",
                                    style = MaterialTheme.typography.bodySmall.copy(color = EmeraldDark, fontWeight = FontWeight.SemiBold)
                                )
                            }
                        }

                        Surface(
                            shape = RoundedCornerShape(50),
                            color = WarmYellowLight
                        ) {
                            Text(
                                text = "🔥 2x XP Aktif",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = FlameOrange,
                                    fontWeight = FontWeight.Bold
                                ),
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // XP Progress
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Progres ke Level ${user.level + 1}",
                            style = MaterialTheme.typography.labelSmall.copy(color = TextSecondary)
                        )
                        Text(
                            text = "${user.currentXp} / ${user.maxXp} XP",
                            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, color = TextCharcoal)
                        )
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    val xpProgress = (user.currentXp.toFloat() / user.maxXp).coerceIn(0f, 1f)
                    LinearProgressIndicator(
                        progress = { xpProgress },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(8.dp)
                            .clip(CircleShape),
                        color = EmeraldPrimary,
                        trackColor = MintLight
                    )
                }
            }
        }

        // Daily Challenges Section
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Misi Sehat Harian",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = TextCharcoal
                    )
                )
                Text(
                    text = "${challenges.count { it.isCompleted }}/${challenges.size} Selesai",
                    style = MaterialTheme.typography.labelMedium.copy(color = EmeraldDark, fontWeight = FontWeight.Bold)
                )
            }
        }

        items(challenges) { challenge ->
            ChallengeCard(
                challenge = challenge,
                onAction = {
                    when (challenge.iconType) {
                        "water" -> {
                            SehatYukRepository.addWater(500)
                            SehatYukRepository.completeChallenge(challenge.id)
                        }
                        "walk" -> {
                            SehatYukRepository.addSteps(800)
                            SehatYukRepository.completeChallenge(challenge.id)
                        }
                        "journal" -> onOpenJournal()
                        else -> SehatYukRepository.completeChallenge(challenge.id)
                    }
                }
            )
        }

        // Campus Collective Challenge
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(22.dp),
                colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
                border = androidx.compose.foundation.BorderStroke(1.dp, EmeraldBorder)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            Brush.verticalGradient(
                                listOf(Color(0xFFF0FDF4), SurfaceWhite)
                            )
                        )
                        .padding(18.dp)
                ) {
                    SDGBadge(text = "TANTANGAN KOLEKTIF KAMPUS")
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "50.000 Langkah Bersama Minggu Ini",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = TextCharcoal
                        )
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "42.300 / 50.000 langkah terkumpul oleh 128 mahasiswa aktif. Raih lencana SDG 3 kampus!",
                        style = MaterialTheme.typography.bodySmall.copy(color = TextSecondary, lineHeight = 18.sp)
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    LinearProgressIndicator(
                        progress = { 0.84f },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(8.dp)
                            .clip(CircleShape),
                        color = EmeraldPrimary,
                        trackColor = MintSoft
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(horizontalArrangement = Arrangement.spacedBy((-6).dp)) {
                            (1..4).forEach { i ->
                                Box(
                                    modifier = Modifier
                                        .size(28.dp)
                                        .clip(CircleShape)
                                        .background(
                                            when (i) {
                                                1 -> EmeraldPrimary
                                                2 -> WaterBlue
                                                3 -> PurpleCalm
                                                else -> WarmYellow
                                            }
                                        )
                                        .border(2.dp, SurfaceWhite, CircleShape),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(Icons.Default.Person, contentDescription = null, tint = Color.White, modifier = Modifier.size(16.dp))
                                }
                            }
                        }

                        Text(
                            text = "+124 mahasiswa lain",
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = FontWeight.SemiBold,
                                color = EmeraldDark
                            )
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
fun ChallengeCard(
    challenge: ChallengeItem,
    onAction: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("challenge_card_${challenge.id}"),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
        border = androidx.compose.foundation.BorderStroke(1.dp, CardBorder),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(1f)
                ) {
                    Box(
                        modifier = Modifier
                            .size(42.dp)
                            .clip(RoundedCornerShape(14.dp))
                            .background(
                                when (challenge.iconType) {
                                    "water" -> WaterBlueLight
                                    "walk" -> MintLight
                                    "eye" -> Color(0xFFF1F5F9)
                                    else -> WarmYellowLight
                                }
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = when (challenge.iconType) {
                                "water" -> Icons.Default.WaterDrop
                                "walk" -> Icons.Default.DirectionsWalk
                                "eye" -> Icons.Default.RemoveRedEye
                                else -> Icons.Default.EditNote
                            },
                            contentDescription = null,
                            tint = when (challenge.iconType) {
                                "water" -> WaterBlue
                                "walk" -> EmeraldPrimary
                                "eye" -> TextSecondary
                                else -> WarmYellow
                            },
                            modifier = Modifier.size(22.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Column {
                        Text(
                            text = challenge.title,
                            style = MaterialTheme.typography.titleSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = TextCharcoal
                            )
                        )
                        Text(
                            text = challenge.description,
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = TextSecondary,
                                fontSize = 11.sp
                            )
                        )
                    }
                }

                Surface(
                    shape = RoundedCornerShape(50),
                    color = WarmYellowLight
                ) {
                    Text(
                        text = "+${challenge.xpReward} XP",
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = FontWeight.ExtraBold,
                            color = FlameOrange
                        ),
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = challenge.progressLabel,
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = if (challenge.isCompleted) EmeraldDark else TextSecondary,
                        fontWeight = if (challenge.isCompleted) FontWeight.Bold else FontWeight.Normal
                    )
                )

                if (challenge.isCompleted) {
                    Surface(
                        shape = RoundedCornerShape(50),
                        color = MintLight
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(Icons.Default.Check, contentDescription = null, tint = EmeraldDark, modifier = Modifier.size(14.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Selesai", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, color = EmeraldDark))
                        }
                    }
                } else {
                    Button(
                        onClick = onAction,
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = EmeraldPrimary),
                        modifier = Modifier.height(34.dp)
                    ) {
                        Text(challenge.actionText, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}
