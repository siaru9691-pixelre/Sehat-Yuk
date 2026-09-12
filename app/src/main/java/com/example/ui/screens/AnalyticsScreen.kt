package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import com.example.ui.components.SDGBadge
import com.example.ui.theme.*

@Composable
fun AnalyticsScreen() {
    var isWeekly by remember { mutableStateOf(true) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(AppBackground)
            .padding(horizontal = 20.dp)
            .testTag("analytics_screen"),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    SDGBadge(text = "SDG 3 INDIKATOR SEHAT")
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Analisis Progres",
                        style = MaterialTheme.typography.headlineMedium.copy(
                            fontWeight = FontWeight.ExtraBold,
                            color = TextCharcoal
                        )
                    )
                }

                // Toggle Week / Month
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = Color(0xFFEDF2F7)
                ) {
                    Row(modifier = Modifier.padding(2.dp)) {
                        Surface(
                            modifier = Modifier
                                .clickable { isWeekly = true }
                                .testTag("analytics_week_toggle"),
                            shape = RoundedCornerShape(10.dp),
                            color = if (isWeekly) SurfaceWhite else Color.Transparent
                        ) {
                            Text(
                                text = "Mingguan",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = if (isWeekly) FontWeight.Bold else FontWeight.Medium,
                                    color = if (isWeekly) EmeraldDark else TextSecondary
                                ),
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                            )
                        }
                        Surface(
                            modifier = Modifier
                                .clickable { isWeekly = false }
                                .testTag("analytics_month_toggle"),
                            shape = RoundedCornerShape(10.dp),
                            color = if (!isWeekly) SurfaceWhite else Color.Transparent
                        ) {
                            Text(
                                text = "Bulanan",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = if (!isWeekly) FontWeight.Bold else FontWeight.Medium,
                                    color = if (!isWeekly) EmeraldDark else TextSecondary
                                ),
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                            )
                        }
                    }
                }
            }
        }

        // Positive Trend Banner
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
                border = androidx.compose.foundation.BorderStroke(1.dp, EmeraldBorder)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            Brush.horizontalGradient(
                                listOf(Color(0xFFE8F8F1), SurfaceWhite)
                            )
                        )
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(44.dp)
                            .clip(CircleShape)
                            .background(EmeraldPrimary),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.TrendingUp,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(26.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(14.dp))
                    Column {
                        Text(
                            text = "Tren Kebugaran Naik +12% 📈",
                            style = MaterialTheme.typography.titleSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = TextCharcoal
                            )
                        )
                        Text(
                            text = "Konsistensi hidrasi dan tidurmu di atas rata-rata mahasiswa se-fakultas.",
                            style = MaterialTheme.typography.bodySmall.copy(color = EmeraldDark)
                        )
                    }
                }
            }
        }

        // 7-Day Hydration Bar Chart Card
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(22.dp),
                colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
                border = androidx.compose.foundation.BorderStroke(1.dp, CardBorder),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Grafik Hidrasi (ml / hari)",
                            style = MaterialTheme.typography.titleSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = TextCharcoal
                            )
                        )
                        Text(
                            text = "Target: 2.000 ml",
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = WaterBlue,
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    val weekDays = listOf("Sen", "Sel", "Rab", "Kam", "Jum", "Sab", "Min")
                    val amounts = listOf(1800, 2000, 1600, 2100, 1900, 2000, 1500)

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(130.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Bottom
                    ) {
                        amounts.forEachIndexed { i, ml ->
                            val heightFraction = (ml / 2500f).coerceIn(0.1f, 1f)
                            val reachedTarget = ml >= 2000

                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Bottom,
                                modifier = Modifier.fillMaxHeight()
                            ) {
                                Text(
                                    text = if (ml >= 2000) "2.0k" else "1.${ml % 1000 / 100}k",
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        fontSize = 9.sp,
                                        color = if (reachedTarget) WaterBlue else TextMuted
                                    )
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Box(
                                    modifier = Modifier
                                        .width(26.dp)
                                        .fillMaxHeight(heightFraction)
                                        .clip(RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp))
                                        .background(
                                            if (reachedTarget) WaterBlue else WaterBlueLight
                                        )
                                )
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(
                                    text = weekDays[i],
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        color = TextSecondary,
                                        fontWeight = if (i == 6) FontWeight.Bold else FontWeight.Normal,
                                        fontSize = 11.sp
                                    )
                                )
                            }
                        }
                    }
                }
            }
        }

        // 4 KPI Stat Cards
        item {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    KpiStatCard(
                        modifier = Modifier.weight(1f),
                        icon = Icons.Default.CheckCircle,
                        iconTint = EmeraldPrimary,
                        title = "Konsistensi Target",
                        value = "85%",
                        sub = "6 dari 7 hari tercapai"
                    )
                    KpiStatCard(
                        modifier = Modifier.weight(1f),
                        icon = Icons.Default.Bedtime,
                        iconTint = PurpleCalm,
                        title = "Rata-rata Tidur",
                        value = "7.2 Jam",
                        sub = "Optimal daya ingat"
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    KpiStatCard(
                        modifier = Modifier.weight(1f),
                        icon = Icons.Default.SelfImprovement,
                        iconTint = WarmYellow,
                        title = "Tingkat Stres",
                        value = "Terkontrol",
                        sub = "Berdasarkan 7 check-in"
                    )
                    KpiStatCard(
                        modifier = Modifier.weight(1f),
                        icon = Icons.Default.LocalFireDepartment,
                        iconTint = FlameOrange,
                        title = "Kalori Terbakar",
                        value = "1.715 kkal",
                        sub = "Total aktivitas fisik"
                    )
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
fun KpiStatCard(
    modifier: Modifier = Modifier,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    iconTint: Color,
    title: String,
    value: String,
    sub: String
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
        border = androidx.compose.foundation.BorderStroke(1.dp, CardBorder),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Icon(imageVector = icon, contentDescription = null, tint = iconTint, modifier = Modifier.size(22.dp))
            Spacer(modifier = Modifier.height(8.dp))
            Text(title, style = MaterialTheme.typography.labelSmall.copy(color = TextSecondary))
            Spacer(modifier = Modifier.height(2.dp))
            Text(value, style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, color = TextCharcoal))
            Spacer(modifier = Modifier.height(2.dp))
            Text(sub, style = MaterialTheme.typography.bodySmall.copy(color = TextMuted, fontSize = 10.sp))
        }
    }
}
