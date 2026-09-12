package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
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
fun ProfileScreen(
    onNavigateToGoals: () -> Unit,
    onNavigateToReminders: () -> Unit,
    onNavigateToAchievements: () -> Unit,
    onNavigateToArticles: () -> Unit,
    onNavigateToSDG: () -> Unit,
    onLogout: () -> Unit
) {
    val user by SehatYukRepository.userProfile.collectAsState()
    val goals by SehatYukRepository.healthGoals.collectAsState()
    val notificationsEnabled by SehatYukRepository.notificationsEnabled.collectAsState()

    var showPrivacyDialog by remember { mutableStateOf(false) }
    var showAboutDialog by remember { mutableStateOf(false) }
    var showLogoutDialog by remember { mutableStateOf(false) }
    var showResetSnackbar by remember { mutableStateOf(false) }

    val activeGoals = goals.filter { it.isSelected }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(AppBackground)
            .padding(horizontal = 20.dp)
            .testTag("profile_screen"),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(10.dp))
            SDGBadge(text = "PROFIL MAHASISWA AKTIF")
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "Profil & Akun",
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.ExtraBold,
                    color = TextCharcoal
                )
            )
        }

        // Hero Profile Card
        item {
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
                        .padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .size(74.dp)
                            .clip(CircleShape)
                            .background(
                                Brush.linearGradient(listOf(EmeraldPrimary, TealSecondary))
                            )
                            .border(3.dp, Color.White, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.Person, contentDescription = null, tint = Color.White, modifier = Modifier.size(46.dp))
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = user.name,
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.ExtraBold,
                            color = TextCharcoal
                        )
                    )

                    Text(
                        text = user.major,
                        style = MaterialTheme.typography.bodySmall.copy(color = TextSecondary)
                    )

                    Text(
                        text = user.email,
                        style = MaterialTheme.typography.labelSmall.copy(color = EmeraldDark, fontWeight = FontWeight.SemiBold)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // 3 Badges stats
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        ProfileMiniStat(title = "Level", value = "${user.level}", sub = user.levelTitle.substringBefore(" "))
                        ProfileMiniStat(title = "Poin Sehat", value = "${user.healthPoints}", sub = "Aktif")
                        ProfileMiniStat(title = "Streak", value = "${user.currentStreak} Hari", sub = "🔥 Membara")
                    }
                }
            }
        }

        // Active Focus Goals Card
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
                            text = "Fokus Sehat Aktif (${activeGoals.size})",
                            style = MaterialTheme.typography.titleSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = TextCharcoal
                            )
                        )
                        Text(
                            text = "Ubah Target",
                            style = MaterialTheme.typography.labelMedium.copy(
                                color = EmeraldDark,
                                fontWeight = FontWeight.Bold
                            ),
                            modifier = Modifier.clickable(onClick = onNavigateToGoals).testTag("edit_goals_btn")
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        activeGoals.take(3).forEach { goal ->
                            Surface(
                                shape = RoundedCornerShape(50),
                                color = MintLight,
                                border = androidx.compose.foundation.BorderStroke(1.dp, EmeraldBorder)
                            ) {
                                Text(
                                    text = goal.title,
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        color = EmeraldDark,
                                        fontWeight = FontWeight.Bold
                                    ),
                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                                )
                            }
                        }
                    }
                }
            }
        }

        // Settings Menu
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
                border = androidx.compose.foundation.BorderStroke(1.dp, CardBorder)
            ) {
                Column(modifier = Modifier.padding(vertical = 6.dp)) {
                    ProfileMenuItem(
                        icon = Icons.Default.Alarm,
                        iconTint = WaterBlue,
                        title = "Pengingat Ritme Harian",
                        onClick = onNavigateToReminders,
                        testTag = "menu_reminders"
                    )
                    ProfileMenuItem(
                        icon = Icons.Default.EmojiEvents,
                        iconTint = WarmYellow,
                        title = "Pencapaian & Lencana",
                        onClick = onNavigateToAchievements,
                        testTag = "menu_achievements"
                    )
                    ProfileMenuItem(
                        icon = Icons.Default.Bookmark,
                        iconTint = EmeraldPrimary,
                        title = "Artikel & Edukasi Tersimpan",
                        onClick = onNavigateToArticles,
                        testTag = "menu_articles"
                    )
                    ProfileMenuItem(
                        icon = Icons.Default.Public,
                        iconTint = TealSecondary,
                        title = "Misi Global PBB: SDG 3",
                        onClick = onNavigateToSDG,
                        testTag = "menu_sdg"
                    )

                    Divider(color = CardBorder, modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp))

                    // Notifications Toggle
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 10.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Outlined.Notifications, contentDescription = null, tint = TextSecondary, modifier = Modifier.size(22.dp))
                            Spacer(modifier = Modifier.width(14.dp))
                            Text("Notifikasi Kebiasaan Sehat", style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium, color = TextCharcoal))
                        }
                        Switch(
                            checked = notificationsEnabled,
                            onCheckedChange = { SehatYukRepository.toggleNotifications() },
                            colors = SwitchDefaults.colors(checkedThumbColor = Color.White, checkedTrackColor = EmeraldPrimary)
                        )
                    }

                    ProfileMenuItem(
                        icon = Icons.Outlined.Security,
                        iconTint = TextSecondary,
                        title = "Privasi Data & Non-Medis",
                        onClick = { showPrivacyDialog = true },
                        testTag = "menu_privacy"
                    )

                    ProfileMenuItem(
                        icon = Icons.Outlined.Info,
                        iconTint = TextSecondary,
                        title = "Tentang SehatYuk v1.0",
                        onClick = { showAboutDialog = true },
                        testTag = "menu_about"
                    )

                    ProfileMenuItem(
                        icon = Icons.Default.Refresh,
                        iconTint = TextSecondary,
                        title = "Reset Data Demo",
                        onClick = {
                            SehatYukRepository.resetDemoData()
                            showResetSnackbar = true
                        },
                        testTag = "menu_reset"
                    )

                    ProfileMenuItem(
                        icon = Icons.AutoMirrored.Filled.Logout,
                        iconTint = SoftCoral,
                        title = "Keluar Akun",
                        onClick = { showLogoutDialog = true },
                        testTag = "menu_logout",
                        titleColor = SoftCoral
                    )
                }
            }
        }

        if (showResetSnackbar) {
            item {
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = MintLight,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Data kebiasaan harian berhasil di-reset ke nilai awal demo!",
                        style = MaterialTheme.typography.bodySmall.copy(color = EmeraldDark, fontWeight = FontWeight.Bold),
                        modifier = Modifier.padding(12.dp)
                    )
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(24.dp))
        }
    }

    if (showPrivacyDialog) {
        AlertDialog(
            onDismissRequest = { showPrivacyDialog = false },
            title = { Text("Privasi Data & Komitmen Non-Medis") },
            text = {
                Text(
                    "1. SehatYuk menjaga privasi seluruh catatan kebiasaan, hidrasi, dan jurnal mood secara lokal di perangkat ini.\n\n2. SehatYuk adalah aplikasi gaya hidup & well-being, BUKAN perangkat diagnosis medis atau pengganti konsultasi dokter.\n\n3. Sesuai prinsip SDG 3, kami mendukung pencegahan mandiri secara etis dan aman bagi mahasiswa."
                )
            },
            confirmButton = {
                Button(onClick = { showPrivacyDialog = false }, colors = ButtonDefaults.buttonColors(containerColor = EmeraldPrimary)) {
                    Text("Paham & Setuju")
                }
            }
        )
    }

    if (showAboutDialog) {
        AlertDialog(
            onDismissRequest = { showAboutDialog = false },
            title = { Text("SehatYuk • SDG 3 Mobile") },
            text = {
                Text(
                    "Versi 1.0.0 (Build Kebugaran Kampus)\n\nAplikasi pendamping kebiasaan hidup sehat dan kesejahteraan mental mahasiswa berbasis Sustainable Development Goals (SDG 3).\n\nDirancang dengan antarmuka modern, ceria, dan bebas stigma rumah sakit."
                )
            },
            confirmButton = {
                Button(onClick = { showAboutDialog = false }, colors = ButtonDefaults.buttonColors(containerColor = EmeraldPrimary)) {
                    Text("Tutup")
                }
            }
        )
    }

    if (showLogoutDialog) {
        AlertDialog(
            onDismissRequest = { showLogoutDialog = false },
            title = { Text("Keluar dari Akun?") },
            text = { Text("Kamu dapat masuk kembali kapan saja untuk melanjutkan streak dan poin sehatmu.") },
            confirmButton = {
                Button(
                    onClick = {
                        showLogoutDialog = false
                        onLogout()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = SoftCoral)
                ) {
                    Text("Keluar")
                }
            },
            dismissButton = {
                TextButton(onClick = { showLogoutDialog = false }) {
                    Text("Batal")
                }
            }
        )
    }
}

@Composable
fun ProfileMiniStat(title: String, value: String, sub: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(title, style = MaterialTheme.typography.labelSmall.copy(color = TextSecondary))
        Spacer(modifier = Modifier.height(2.dp))
        Text(value, style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, color = TextCharcoal))
        Spacer(modifier = Modifier.height(2.dp))
        Text(sub, style = MaterialTheme.typography.labelSmall.copy(color = EmeraldDark, fontSize = 10.sp))
    }
}

@Composable
fun ProfileMenuItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    iconTint: Color,
    title: String,
    onClick: () -> Unit,
    testTag: String,
    titleColor: Color = TextCharcoal
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 13.dp)
            .testTag(testTag),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(imageVector = icon, contentDescription = null, tint = iconTint, modifier = Modifier.size(22.dp))
            Spacer(modifier = Modifier.width(14.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.Medium,
                    color = titleColor
                )
            )
        }
        Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, tint = TextMuted, modifier = Modifier.size(16.dp))
    }
}
