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
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.*
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
import com.example.data.model.HealthGoalItem
import com.example.data.repository.SehatYukRepository
import com.example.ui.components.SDGBadge
import com.example.ui.theme.*

@Composable
fun HealthGoalsScreen(
    onGoalsSaved: () -> Unit
) {
    val goals by SehatYukRepository.healthGoals.collectAsState()
    val selectedCount = goals.count { it.isSelected }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .testTag("health_goals_screen"),
        containerColor = AppBackground,
        bottomBar = {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = SurfaceWhite,
                shadowElevation = 8.dp,
                border = androidx.compose.foundation.BorderStroke(1.dp, CardBorder)
            ) {
                Column(
                    modifier = Modifier
                        .navigationBarsPadding()
                        .padding(horizontal = 20.dp, vertical = 14.dp)
                ) {
                    Button(
                        onClick = onGoalsSaved,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(54.dp)
                            .shadow(6.dp, RoundedCornerShape(27.dp), spotColor = EmeraldPrimary.copy(alpha = 0.4f))
                            .testTag("save_goals_button"),
                        shape = RoundedCornerShape(27.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = EmeraldPrimary)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "Simpan Target & Masuk Dasbor",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                }
            }
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(10.dp))
                SDGBadge(text = "LANGKAH 3 DARI 3: PILIH FOKUS SEHATMU")
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "Pilih Fokus Sehatmu",
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.ExtraBold,
                        color = TextCharcoal
                    )
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Sesuaikan target dengan rutinitas kuliahmu. Kamu bisa mengubahnya kapan saja di profil.",
                    style = MaterialTheme.typography.bodyMedium.copy(color = TextSecondary)
                )
                Spacer(modifier = Modifier.height(10.dp))
                // Counter Chip
                Surface(
                    shape = RoundedCornerShape(50),
                    color = MintSoft
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .clip(CircleShape)
                                .background(EmeraldPrimary)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "$selectedCount Target Terpilih (Rekomendasi Mahasiswa)",
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = EmeraldDark
                            )
                        )
                    }
                }
                Spacer(modifier = Modifier.height(6.dp))
            }

            // 6 Goal Cards
            items(goals) { goal ->
                GoalSelectionCard(
                    goal = goal,
                    onToggle = { SehatYukRepository.toggleGoalSelection(goal.id) }
                )
            }

            // Student Tip
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(containerColor = WarmYellowLight),
                    border = androidx.compose.foundation.BorderStroke(1.dp, WarmYellow.copy(alpha = 0.3f))
                ) {
                    Row(
                        modifier = Modifier.padding(14.dp),
                        verticalAlignment = Alignment.Top
                    ) {
                        Text(text = "💡", fontSize = 24.sp)
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = "Tahukah Kamu?",
                                style = MaterialTheme.typography.titleSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = TextCharcoal
                                )
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = "Mahasiswa yang menjaga hidrasi dan tidur minimal 7 jam memiliki retensi memori 35% lebih tinggi saat menghadapi ujian semester.",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    color = TextSecondary,
                                    lineHeight = 18.sp
                                )
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }
}

@Composable
fun GoalSelectionCard(
    goal: HealthGoalItem,
    onToggle: () -> Unit
) {
    val isSelected = goal.isSelected

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onToggle)
            .testTag("goal_card_${goal.id}"),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) SurfaceWhite else Color(0xFFFAFAFA)
        ),
        border = androidx.compose.foundation.BorderStroke(
            if (isSelected) 2.dp else 1.dp,
            if (isSelected) EmeraldPrimary else CardBorder
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = if (isSelected) 2.dp else 0.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
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
                            .size(44.dp)
                            .clip(RoundedCornerShape(14.dp))
                            .background(
                                when (goal.iconType) {
                                    "water" -> WaterBlueLight
                                    "walk" -> MintLight
                                    "sleep" -> PurpleCalmLight
                                    "mental" -> WarmYellowLight
                                    "diet" -> Color(0xFFDCFCE7)
                                    else -> Color(0xFFF1F5F9)
                                }
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = when (goal.iconType) {
                                "water" -> Icons.Default.WaterDrop
                                "walk" -> Icons.Default.DirectionsWalk
                                "sleep" -> Icons.Default.Bedtime
                                "mental" -> Icons.Default.SelfImprovement
                                "diet" -> Icons.Default.Restaurant
                                else -> Icons.Default.RemoveRedEye
                            },
                            contentDescription = goal.title,
                            tint = when (goal.iconType) {
                                "water" -> WaterBlue
                                "walk" -> EmeraldPrimary
                                "sleep" -> PurpleCalm
                                "mental" -> WarmYellow
                                "diet" -> EmeraldDark
                                else -> TextSecondary
                            },
                            modifier = Modifier.size(24.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Column {
                        Text(
                            text = goal.title,
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = TextCharcoal
                            )
                        )
                        Text(
                            text = goal.description,
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = TextSecondary
                            )
                        )
                    }
                }

                // Checkbox circle
                Box(
                    modifier = Modifier
                        .size(26.dp)
                        .clip(CircleShape)
                        .background(if (isSelected) EmeraldPrimary else Color.Transparent)
                        .border(
                            1.5.dp,
                            if (isSelected) EmeraldPrimary else TextMuted,
                            CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    if (isSelected) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = "Selected",
                            tint = Color.White,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            }

            if (goal.statusText != null) {
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = goal.statusText,
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = if (isSelected) EmeraldDark else TextMuted,
                            fontWeight = FontWeight.Medium
                        )
                    )

                    Surface(
                        shape = RoundedCornerShape(50),
                        color = if (isSelected) MintSoft else Color(0xFFEDF2F7)
                    ) {
                        Text(
                            text = goal.categoryBadge,
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = if (isSelected) EmeraldDark else TextSecondary
                            ),
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                        )
                    }
                }
            }
        }
    }
}
