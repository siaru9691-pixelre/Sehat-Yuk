package com.example.ui.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.SDGBadge
import com.example.ui.theme.*

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun OnboardingScreen(
    onFinishOnboarding: () -> Unit
) {
    var currentPage by remember { mutableIntStateOf(0) }

    val pages = listOf(
        OnboardingData(
            badge = "✨ Langkah Kecil, Hasil Nyata",
            title = "Tracking Ringan & Terarah",
            description = "Target 8 gelas air (2.000 ml) dan 6.000 langkah harian tanpa rasa terbebani di tengah padatnya jadwal kuliah.",
            illustrationType = 0
        ),
        OnboardingData(
            badge = "🏆 Tantangan Interaktif",
            title = "Bangun Kebiasaan Sehat",
            description = "Tetapkan tujuan dan lakukan tantangan kecil setiap hari bersama rekan kampus untuk hidup bugar berkelanjutan.",
            illustrationType = 1
        ),
        OnboardingData(
            badge = "🌿 Harmoni Fisik & Jiwa",
            title = "Jaga Tubuh & Pikiran",
            description = "Perhatikan kesehatan fisik dan mental melalui check-in mood harian dan micro-habits untuk hidup seimbang.",
            illustrationType = 2
        )
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppBackground)
            .statusBarsPadding()
            .navigationBarsPadding()
            .padding(horizontal = 24.dp, vertical = 16.dp)
            .testTag("onboarding_screen"),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // Top Bar: Badge & Skip
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            SDGBadge(text = "SDG 3 WELL-BEING")
            TextButton(
                onClick = onFinishOnboarding,
                modifier = Modifier.testTag("skip_onboarding_button")
            ) {
                Text(
                    text = "Lewati",
                    style = MaterialTheme.typography.labelLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = TealSecondary
                    )
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Card & Content with Animation
        AnimatedContent(
            targetState = currentPage,
            transitionSpec = { fadeIn() togetherWith fadeOut() },
            label = "page_transition"
        ) { pageIndex ->
            val page = pages[pageIndex]
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Hero Graphic Card
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp),
                    shape = RoundedCornerShape(28.dp),
                    colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
                    elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, CardBorder)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                Brush.verticalGradient(
                                    listOf(
                                        when (pageIndex) {
                                            0 -> Color(0xFFDDF5EA)
                                            1 -> Color(0xFFE3F3FF)
                                            else -> Color(0xFFFFF2D6)
                                        },
                                        SurfaceWhite
                                    )
                                )
                            )
                            .padding(16.dp),
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        // Top Illustration elements
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End
                        ) {
                            Surface(
                                shape = RoundedCornerShape(50),
                                color = when (pageIndex) {
                                    0 -> EmeraldPrimary
                                    1 -> WaterBlue
                                    else -> WarmYellow
                                }
                            ) {
                                Row(
                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        imageVector = when (pageIndex) {
                                            0 -> Icons.Default.DirectionsWalk
                                            1 -> Icons.Default.Star
                                            else -> Icons.Default.SelfImprovement
                                        },
                                        contentDescription = null,
                                        tint = Color.White,
                                        modifier = Modifier.size(14.dp)
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        text = when (pageIndex) {
                                            0 -> "6.000 Langkah"
                                            1 -> "Level 4 Pejuang"
                                            else -> "Target 3.4 Jiwa"
                                        },
                                        color = Color.White,
                                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold)
                                    )
                                }
                            }
                        }

                        // Center artistic composition
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f),
                            contentAlignment = Alignment.Center
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(130.dp)
                                    .clip(CircleShape)
                                    .background(SurfaceWhite.copy(alpha = 0.8f))
                                    .border(2.dp, CardBorder, CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = when (pageIndex) {
                                        0 -> Icons.Default.DirectionsRun
                                        1 -> Icons.Default.EmojiEvents
                                        else -> Icons.Default.Spa
                                    },
                                    contentDescription = null,
                                    tint = when (pageIndex) {
                                        0 -> EmeraldPrimary
                                        1 -> WaterBlue
                                        else -> WarmYellow
                                    },
                                    modifier = Modifier.size(64.dp)
                                )
                            }

                            // Pill Tag
                            Surface(
                                modifier = Modifier
                                    .align(Alignment.BottomStart)
                                    .padding(start = 8.dp, bottom = 8.dp),
                                shape = RoundedCornerShape(50),
                                color = SurfaceWhite,
                                shadowElevation = 2.dp
                            ) {
                                Row(
                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.WaterDrop,
                                        contentDescription = null,
                                        tint = EmeraldPrimary,
                                        modifier = Modifier.size(14.dp)
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        text = when (pageIndex) {
                                            0 -> "2.000 ml tercapai"
                                            1 -> "+50 XP per hari"
                                            else -> "Pernapasan 4-4-4"
                                        },
                                        style = MaterialTheme.typography.labelSmall.copy(
                                            fontWeight = FontWeight.SemiBold,
                                            color = TextCharcoal
                                        )
                                    )
                                }
                            }
                        }

                        // Bottom Target Summary Bar
                        Surface(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(16.dp),
                            color = SurfaceWhite,
                            border = androidx.compose.foundation.BorderStroke(1.dp, CardBorder)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Box(
                                        modifier = Modifier
                                            .size(36.dp)
                                            .clip(CircleShape)
                                            .background(WaterBlueLight),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.LocalDrink,
                                            contentDescription = null,
                                            tint = WaterBlue,
                                            modifier = Modifier.size(20.dp)
                                        )
                                    }
                                    Spacer(modifier = Modifier.width(10.dp))
                                    Column {
                                        Text(
                                            text = when (pageIndex) {
                                                0 -> "Target Hidrasi Harian"
                                                1 -> "Tantangan Kampus Sehat"
                                                else -> "Refleksi Singkat Harian"
                                            },
                                            style = MaterialTheme.typography.titleSmall.copy(
                                                fontWeight = FontWeight.Bold,
                                                color = TextCharcoal
                                            )
                                        )
                                        Text(
                                            text = when (pageIndex) {
                                                0 -> "8 Gelas seimbang untuk fokus kuliah"
                                                1 -> "Raih badge eksklusif mahasiswa"
                                                else -> "Ambil napas dan jeda sejenak"
                                            },
                                            style = MaterialTheme.typography.bodySmall.copy(
                                                color = TextSecondary,
                                                fontSize = 11.sp
                                            )
                                        )
                                    }
                                }

                                Box(
                                    modifier = Modifier
                                        .size(26.dp)
                                        .clip(CircleShape)
                                        .background(EmeraldPrimary),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Check,
                                        contentDescription = "Tuntas",
                                        tint = Color.White,
                                        modifier = Modifier.size(16.dp)
                                    )
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(28.dp))

                // Badge Pill
                Surface(
                    shape = RoundedCornerShape(50),
                    color = MintSoft
                ) {
                    Text(
                        text = page.badge,
                        style = MaterialTheme.typography.labelMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = EmeraldDark
                        ),
                        modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp)
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Title
                Text(
                    text = page.title,
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.ExtraBold,
                        color = TextCharcoal
                    ),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Description
                Text(
                    text = page.description,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = TextSecondary,
                        lineHeight = 22.sp
                    ),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Pagination Dots & Buttons
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Dots
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                pages.indices.forEach { index ->
                    val isSelected = index == currentPage
                    Box(
                        modifier = Modifier
                            .height(8.dp)
                            .width(if (isSelected) 24.dp else 8.dp)
                            .clip(CircleShape)
                            .background(if (isSelected) EmeraldPrimary else CardBorder)
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Primary Button
            Button(
                onClick = {
                    if (currentPage < pages.size - 1) {
                        currentPage++
                    } else {
                        onFinishOnboarding()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .shadow(8.dp, RoundedCornerShape(28.dp), spotColor = EmeraldPrimary.copy(alpha = 0.5f))
                    .testTag("onboarding_action_button"),
                shape = RoundedCornerShape(28.dp),
                colors = ButtonDefaults.buttonColors(containerColor = EmeraldPrimary)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = if (currentPage == pages.size - 1) "Mulai Sekarang" else "Lanjut",
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

            Spacer(modifier = Modifier.height(8.dp))

            TextButton(
                onClick = onFinishOnboarding,
                modifier = Modifier.testTag("bottom_skip_button")
            ) {
                Text(
                    text = "Lewati Pengantar",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = TextSecondary,
                        fontWeight = FontWeight.Medium
                    )
                )
            }
        }
    }
}

private data class OnboardingData(
    val badge: String,
    val title: String,
    val description: String,
    val illustrationType: Int
)
