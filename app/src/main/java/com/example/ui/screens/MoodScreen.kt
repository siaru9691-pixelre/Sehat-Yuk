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
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Call
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.MoodType
import com.example.data.repository.SehatYukRepository
import com.example.ui.components.BreathingExerciseDialog
import com.example.ui.components.SDGBadge
import com.example.ui.theme.*

@Composable
fun MoodScreen(
    onNavigateBack: () -> Unit = {}
) {
    val currentMood by SehatYukRepository.currentMood.collectAsState()
    val feedbackMessage by SehatYukRepository.moodFeedback.collectAsState()
    val journalNotes by SehatYukRepository.journalNotes.collectAsState()
    val selectedTriggers by SehatYukRepository.selectedTriggers.collectAsState()
    val savedMessage by SehatYukRepository.lastReflectionSavedMessage.collectAsState()

    var notesInput by remember { mutableStateOf(journalNotes) }
    var showBreathingDialog by remember { mutableStateOf(false) }
    var showCounselingDialog by remember { mutableStateOf(false) }

    val allTriggers = listOf(
        "Kuliah / Tugas", "Praktikum Lab", "Kurang Tidur",
        "Finansial Kost", "Pertemanan", "Cuaca Terik"
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(AppBackground)
            .padding(horizontal = 20.dp)
            .testTag("mood_screen"),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(10.dp))
            SDGBadge(text = "SDG 3.4 KESEHATAN MENTAL & KESEJAHTERAAN")
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "Bagaimana Perasaanmu Hari Ini?",
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.ExtraBold,
                    color = TextCharcoal
                )
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Luangkan 1 menit untuk check-in emosimu. Catatan ini membantumu mengenali pola stres kuliah.",
                style = MaterialTheme.typography.bodyMedium.copy(color = TextSecondary)
            )
        }

        // 5 Mood Cards Row
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                MoodType.values().forEach { mood ->
                    val isSelected = currentMood == mood
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .weight(1f)
                            .clickable { SehatYukRepository.setMood(mood) }
                            .padding(2.dp)
                            .testTag("mood_btn_${mood.name}")
                    ) {
                        Box(
                            modifier = Modifier
                                .size(54.dp)
                                .clip(RoundedCornerShape(16.dp))
                                .background(if (isSelected) MintSoft else SurfaceWhite)
                                .border(
                                    if (isSelected) 2.dp else 1.dp,
                                    if (isSelected) EmeraldPrimary else CardBorder,
                                    RoundedCornerShape(16.dp)
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = mood.emoji, fontSize = 26.sp)
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = mood.labelIndo,
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                color = if (isSelected) EmeraldDark else TextSecondary,
                                fontSize = 11.sp
                            )
                        )
                    }
                }
            }
        }

        // Dynamic Feedback Banner
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = MintLight),
                border = androidx.compose.foundation.BorderStroke(1.dp, EmeraldBorder)
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = currentMood.emoji, fontSize = 28.sp)
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            text = currentMood.labelIndo,
                            style = MaterialTheme.typography.titleSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = EmeraldDark
                            )
                        )
                        Text(
                            text = feedbackMessage,
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = TextCharcoal,
                                lineHeight = 18.sp
                            )
                        )
                    }
                }
            }
        }

        // Trigger Tags
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
                border = androidx.compose.foundation.BorderStroke(1.dp, CardBorder)
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Text(
                        text = "Faktor yang Memengaruhi Emosi:",
                        style = MaterialTheme.typography.titleSmall.copy(
                            fontWeight = FontWeight.Bold,
                            color = TextCharcoal
                        )
                    )
                    Spacer(modifier = Modifier.height(10.dp))

                    // Flow of chips
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        allTriggers.take(3).forEach { trigger ->
                            val isSelected = selectedTriggers.contains(trigger)
                            FilterChip(
                                selected = isSelected,
                                onClick = { SehatYukRepository.toggleTrigger(trigger) },
                                label = { Text(trigger, fontSize = 12.sp) },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = MintSoft,
                                    selectedLabelColor = EmeraldDark
                                )
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        allTriggers.drop(3).forEach { trigger ->
                            val isSelected = selectedTriggers.contains(trigger)
                            FilterChip(
                                selected = isSelected,
                                onClick = { SehatYukRepository.toggleTrigger(trigger) },
                                label = { Text(trigger, fontSize = 12.sp) },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = MintSoft,
                                    selectedLabelColor = EmeraldDark
                                )
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Journal Notes
                    Text(
                        text = "Catatan Refleksi Singkat:",
                        style = MaterialTheme.typography.titleSmall.copy(
                            fontWeight = FontWeight.Bold,
                            color = TextCharcoal
                        )
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    OutlinedTextField(
                        value = notesInput,
                        onValueChange = { notesInput = it },
                        placeholder = { Text("Tulis hal yang kamu rasakan atau syukuri hari ini...") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(90.dp)
                            .testTag("journal_input"),
                        shape = RoundedCornerShape(14.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = AppBackground,
                            unfocusedContainerColor = AppBackground,
                            focusedBorderColor = EmeraldPrimary,
                            unfocusedBorderColor = CardBorder
                        )
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Button(
                        onClick = { SehatYukRepository.saveReflection(notesInput) },
                        modifier = Modifier.fillMaxWidth().testTag("save_reflection_btn"),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = EmeraldPrimary)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Outlined.Check, contentDescription = null, tint = Color.White)
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Simpan Refleksi (+15 Poin Sehat)", fontWeight = FontWeight.Bold)
                        }
                    }

                    AnimatedVisibility(visible = savedMessage != null) {
                        Column {
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = savedMessage.orEmpty(),
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

        // 3 Micro-Habits Recommendation Cards
        item {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Text(
                    text = "Aksi Cepat Redakan Beban Pikiran:",
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontWeight = FontWeight.Bold,
                        color = TextCharcoal
                    )
                )

                MicroHabitCard(
                    icon = "🌬️",
                    title = "Latihan Pernapasan 4–4–4",
                    sub = "3 menit de-eskalasi detak jantung & stres",
                    cta = "Mulai Sekarang",
                    onClick = { showBreathingDialog = true }
                )

                MicroHabitCard(
                    icon = "🚶",
                    title = "Jalan Santai 10 Menit",
                    sub = "Tinggalkan meja belajar & hirup udara luar",
                    cta = "Buka Sensor",
                    onClick = {}
                )

                MicroHabitCard(
                    icon = "☕",
                    title = "Jeda Minum Teh & Tanpa Layar",
                    sub = "5 menit istirahatkan mata dari laptop",
                    cta = "Pasang Timer",
                    onClick = {}
                )
            }
        }

        // Campus Counseling Support Card
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFEFF6FF)),
                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFBFDBFE))
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Layanan Konseling Kampus",
                            style = MaterialTheme.typography.titleSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = TextCharcoal
                            )
                        )
                        Text(
                            text = "Konseling sebaya & psikolog profesional mahasiswa. Gratis & rahasia.",
                            style = MaterialTheme.typography.bodySmall.copy(color = TextSecondary)
                        )
                    }

                    Button(
                        onClick = { showCounselingDialog = true },
                        colors = ButtonDefaults.buttonColors(containerColor = WaterBlue),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.testTag("counseling_btn")
                    ) {
                        Icon(Icons.Outlined.Call, contentDescription = null, tint = Color.White, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Hubungi", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                    }
                }
            }
        }

        // Disclaimer
        item {
            Text(
                text = "SehatYuk bukan pengganti diagnosis medis formal. Jika mengalami krisis psikologis mendesak, segera hubungi layanan darurat kesehatan 119.",
                style = MaterialTheme.typography.labelSmall.copy(
                    color = TextMuted,
                    textAlign = TextAlign.Center,
                    fontSize = 10.sp
                ),
                modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp)
            )
            Spacer(modifier = Modifier.height(24.dp))
        }
    }

    if (showBreathingDialog) {
        BreathingExerciseDialog(onDismiss = { showBreathingDialog = false })
    }

    if (showCounselingDialog) {
        AlertDialog(
            onDismissRequest = { showCounselingDialog = false },
            title = { Text("Pusat Konseling Mahasiswa") },
            text = {
                Text("Hotline Konseling Kampus Sehat:\n• Telepon: (021) 500-123\n• WhatsApp Halo Mahasiswa: +62 812-3456-7890\n• Jam Operasional: Senin - Jumat 08.00 - 17.00 WIB\n\nLayanan ini sepenuhnya rahasia dan bebas biaya bagi mahasiswa aktif.")
            },
            confirmButton = {
                Button(
                    onClick = { showCounselingDialog = false },
                    colors = ButtonDefaults.buttonColors(containerColor = EmeraldPrimary)
                ) {
                    Text("Tutup")
                }
            }
        )
    }
}

@Composable
fun MicroHabitCard(
    icon: String,
    title: String,
    sub: String,
    cta: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
        border = androidx.compose.foundation.BorderStroke(1.dp, CardBorder)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
            ) {
                Text(text = icon, fontSize = 24.sp)
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
                            color = TextSecondary,
                            fontSize = 11.sp
                        )
                    )
                }
            }

            Surface(
                shape = RoundedCornerShape(50),
                color = MintLight
            ) {
                Text(
                    text = cta,
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
