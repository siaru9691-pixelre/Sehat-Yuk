package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.*
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.DirectionsWalk
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.SelfImprovement
import androidx.compose.material.icons.filled.WaterDrop
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.SDGBadge
import com.example.ui.theme.*

@Composable
fun SplashScreen(
    onStartOnboarding: () -> Unit
) {
    var isVisible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        isVisible = true
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(
                        Color(0xFFE8F8F2),
                        AppBackground,
                        Color(0xFFEAF5FF)
                    )
                )
            )
            .statusBarsPadding()
            .navigationBarsPadding()
            .padding(24.dp)
            .testTag("splash_screen"),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Top SDG Badge
            SDGBadge(text = "Target PBB SDG 3: Kehidupan Sehat & Sejahtera")

            Spacer(modifier = Modifier.weight(0.6f))

            AnimatedVisibility(
                visible = isVisible,
                enter = fadeIn(tween(800)) + slideInVertically(initialOffsetY = { 40 })
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    // Logo Icon Squircle
                    Box(
                        modifier = Modifier
                            .size(110.dp)
                            .shadow(16.dp, RoundedCornerShape(32.dp), spotColor = EmeraldPrimary.copy(alpha = 0.4f))
                            .clip(RoundedCornerShape(32.dp))
                            .background(
                                Brush.linearGradient(
                                    listOf(Color(0xFF00D284), EmeraldPrimary, EmeraldDark)
                                )
                            )
                            .border(3.dp, Color.White.copy(alpha = 0.6f), RoundedCornerShape(32.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        // Soft glow circle
                        Box(
                            modifier = Modifier
                                .size(75.dp)
                                .clip(CircleShape)
                                .background(Color.White.copy(alpha = 0.2f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Favorite,
                                contentDescription = "Logo SehatYuk",
                                tint = Color.White,
                                modifier = Modifier.size(46.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    // App Name with Brand Dot
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "SehatYuk",
                            style = MaterialTheme.typography.headlineLarge.copy(
                                fontWeight = FontWeight.ExtraBold,
                                fontSize = 34.sp,
                                color = TextCharcoal
                            )
                        )
                        Box(
                            modifier = Modifier
                                .padding(start = 6.dp, top = 6.dp)
                                .size(9.dp)
                                .clip(CircleShape)
                                .background(EmeraldPrimary)
                        )
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "Jaga Sehatmu, Mulai Hari Ini",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = EmeraldDark
                        )
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "Sahabat Hidup Sehat Mahasiswa & Generasi Muda",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = TextSecondary,
                            textAlign = TextAlign.Center
                        )
                    )

                    Spacer(modifier = Modifier.height(28.dp))

                    // 3 Highlight Feature Cards
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        FeatureHighlightCard(
                            icon = Icons.Default.WaterDrop,
                            iconBg = WaterBlueLight,
                            iconTint = WaterBlue,
                            title = "Hidrasi Terpantau",
                            sub = "Target 2.000 ml per hari",
                            tag = "Optimal",
                            tagBg = WaterBlueLight,
                            tagColor = WaterBlue
                        )
                        FeatureHighlightCard(
                            icon = Icons.Default.DirectionsWalk,
                            iconBg = MintLight,
                            iconTint = EmeraldPrimary,
                            title = "6.000 Langkah Harian",
                            sub = "Aktivitas ringan di kampus",
                            tag = "🔥 Aktif",
                            tagBg = MintLight,
                            tagColor = EmeraldDark
                        )
                        FeatureHighlightCard(
                            icon = Icons.Default.SelfImprovement,
                            iconBg = WarmYellowLight,
                            iconTint = WarmYellow,
                            title = "Kesehatan Mental & Mood",
                            sub = "Check-in refleksi & relaksasi",
                            tag = "Seimbang",
                            tagBg = WarmYellowLight,
                            tagColor = FlameOrange
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.weight(0.8f))

            // CTA Button
            Button(
                onClick = onStartOnboarding,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .shadow(8.dp, RoundedCornerShape(28.dp), spotColor = EmeraldPrimary.copy(alpha = 0.5f))
                    .testTag("start_onboarding_button"),
                shape = RoundedCornerShape(28.dp),
                colors = ButtonDefaults.buttonColors(containerColor = EmeraldPrimary)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Mulai Onboarding",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = "Lanjut",
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Indicator Dots
            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                Box(modifier = Modifier.size(8.dp, 8.dp).clip(CircleShape).background(EmeraldPrimary))
                Box(modifier = Modifier.size(8.dp, 8.dp).clip(CircleShape).background(CardBorder))
                Box(modifier = Modifier.size(8.dp, 8.dp).clip(CircleShape).background(CardBorder))
            }
        }
    }
}

@Composable
fun FeatureHighlightCard(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    iconBg: Color,
    iconTint: Color,
    title: String,
    sub: String,
    tag: String,
    tagBg: Color,
    tagColor: Color
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
        border = androidx.compose.foundation.BorderStroke(1.dp, CardBorder),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(42.dp)
                        .clip(CircleShape)
                        .background(iconBg),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = title,
                        tint = iconTint,
                        modifier = Modifier.size(22.dp)
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleSmall.copy(
                            fontWeight = FontWeight.Bold,
                            color = TextCharcoal
                        )
                    )
                    Text(
                        text = sub,
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = TextSecondary
                        )
                    )
                }
            }

            Surface(
                shape = RoundedCornerShape(50),
                color = tagBg
            ) {
                Text(
                    text = tag,
                    style = MaterialTheme.typography.labelSmall.copy(
                        fontWeight = FontWeight.Bold,
                        color = tagColor
                    ),
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                )
            }
        }
    }
}
