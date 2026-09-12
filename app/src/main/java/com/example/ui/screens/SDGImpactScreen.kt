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
import androidx.compose.material.icons.outlined.OpenInNew
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
fun SDGImpactScreen(
    onNavigateBack: () -> Unit = {}
) {
    var showDetailDialog by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(AppBackground)
            .padding(horizontal = 20.dp)
            .testTag("sdg_impact_screen"),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(10.dp))
            SDGBadge(text = "UNITED NATIONS SUSTAINABLE DEVELOPMENT GOALS")
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "Misi Global SDG 3",
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.ExtraBold,
                    color = TextCharcoal
                )
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Good Health and Well-Being — Kehidupan Sehat dan Sejahtera untuk Semua.",
                style = MaterialTheme.typography.bodyMedium.copy(color = TextSecondary)
            )
        }

        // Hero UN SDG 3 Banner Card
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
                            Brush.verticalGradient(
                                listOf(Color(0xFFE6F8F0), Color(0xFFF0FDF8), SurfaceWhite)
                            )
                        )
                        .padding(20.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(48.dp)
                                .clip(RoundedCornerShape(14.dp))
                                .background(Color(0xFF4C9F38)), // Official UN SDG 3 Green
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = "3", color = Color.White, fontWeight = FontWeight.Black, fontSize = 26.sp)
                        }
                        Spacer(modifier = Modifier.width(14.dp))
                        Column {
                            Text(
                                text = "SDG 3: Target 3.4",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.ExtraBold,
                                    color = TextCharcoal
                                )
                            )
                            Text(
                                text = "Kesehatan Fisik & Kesejahteraan Mental",
                                style = MaterialTheme.typography.bodySmall.copy(color = EmeraldDark)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Text(
                        text = "\"Mempromosikan kesehatan mental dan kesejahteraan, serta mengurangi risiko penyakit tidak menular melalui pencegahan dan kebiasaan hidup sehat sedini mungkin.\"",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = TextCharcoal,
                            lineHeight = 22.sp,
                            fontWeight = FontWeight.Medium
                        )
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    Text(
                        text = "Manifesto SehatYuk:",
                        style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold, color = EmeraldDark)
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = "SehatYuk hadir sebagai wujud nyata inovasi digital mahasiswa Indonesia dalam mendukung agenda PBB melalui teknologi pembiasaan hidup sehat yang terjangkau, ramah, dan berkelanjutan.",
                        style = MaterialTheme.typography.bodySmall.copy(color = TextSecondary, lineHeight = 18.sp)
                    )
                }
            }
        }

        // Real Impact Statistics
        item {
            Text(
                text = "Dampak Komunitas Mahasiswa",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = TextCharcoal
                )
            )

            Spacer(modifier = Modifier.height(10.dp))

            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    SDGMetricCard(
                        modifier = Modifier.weight(1f),
                        number = "1.200+",
                        label = "Mahasiswa Terhubung",
                        icon = Icons.Default.School,
                        color = EmeraldPrimary
                    )
                    SDGMetricCard(
                        modifier = Modifier.weight(1f),
                        number = "85%",
                        label = "Konsistensi Kebiasaan",
                        icon = Icons.Default.CheckCircle,
                        color = WaterBlue
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    SDGMetricCard(
                        modifier = Modifier.weight(1f),
                        number = "142.500+",
                        label = "Menit Aktivitas Fisik",
                        icon = Icons.Default.DirectionsRun,
                        color = WarmYellow
                    )
                    SDGMetricCard(
                        modifier = Modifier.weight(1f),
                        number = "96.000+",
                        label = "Gelas Air Tercatat",
                        icon = Icons.Default.WaterDrop,
                        color = TealSecondary
                    )
                }
            }
        }

        // 6 Action Pillars
        item {
            Text(
                text = "6 Pilar Aksi Nyata SehatYuk",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = TextCharcoal
                )
            )

            Spacer(modifier = Modifier.height(10.dp))

            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                PillarItem(
                    title = "1. Hidrasi Seimbang Terjadwal",
                    desc = "Pencegahan dehidrasi kognitif dengan target 2.000 ml dan pengingat ramah tiap 2 jam.",
                    tag = "Target 3.4 & 3.9"
                )
                PillarItem(
                    title = "2. Aktivitas Langkah Ringan Kampus",
                    desc = "Mendorong 6.000 langkah tanpa mengganggu mobilitas antar ruang kelas dan lab.",
                    tag = "Target 3.4"
                )
                PillarItem(
                    title = "3. Kesehatan Mental & Check-In Emosi",
                    desc = "Latihan pernapasan 4-4-4, jurnal syukur harian, dan akses langsung hotline konseling kampus.",
                    tag = "Target 3.4 Jiwa"
                )
                PillarItem(
                    title = "4. Ritme Tidur & Sirkadian Mahasiswa",
                    desc = "Edukasi pembatasan begadang tugas dan persiapan tidur 7-8 jam per malam.",
                    tag = "Target 3.4"
                )
                PillarItem(
                    title = "5. Nutrisi Seimbang Ramah Kantong",
                    desc = "Panduan menu sehat kost murah kaya zat besi, serat, dan protein nabati.",
                    tag = "Target 2.2 & 3.4"
                )
                PillarItem(
                    title = "6. Literasi Kesehatan Berbasis Bukti",
                    desc = "Artikel edukasi ringkas dari dokter dan praktisi medis resmi bebas hoax.",
                    tag = "Target 3.7 & 3.D"
                )
            }
        }

        // UN Learn More CTA
        item {
            OutlinedButton(
                onClick = { showDetailDialog = true },
                modifier = Modifier.fillMaxWidth().height(48.dp).testTag("un_learn_more_btn"),
                shape = RoundedCornerShape(12.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, EmeraldPrimary)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("Pelajari Dokumen Resmi SDG 3 PBB", color = EmeraldDark, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.width(6.dp))
                    Icon(Icons.Outlined.OpenInNew, contentDescription = null, tint = EmeraldDark, modifier = Modifier.size(16.dp))
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
        }
    }

    if (showDetailDialog) {
        AlertDialog(
            onDismissRequest = { showDetailDialog = false },
            title = { Text("Tentang Target SDG 3 PBB") },
            text = {
                Text(
                    "Sustainable Development Goal 3 (SDG 3) adalah komitmen global 193 negara anggota PBB untuk menjamin kehidupan yang sehat dan meningkatkan kesejahteraan seluruh penduduk di segala usia.\n\nFokus generasi muda Indonesia terletak pada Target 3.4 (pencegahan penyakit tidak menular & promosi kesehatan mental) dan Target 3.8 (kesehatan universal). SehatYuk memberdayakan mahasiswa agar memulai pencegahan secara mandiri sejak masa perkuliahan."
                )
            },
            confirmButton = {
                Button(
                    onClick = { showDetailDialog = false },
                    colors = ButtonDefaults.buttonColors(containerColor = EmeraldPrimary)
                ) {
                    Text("Tutup")
                }
            }
        )
    }
}

@Composable
fun SDGMetricCard(
    modifier: Modifier = Modifier,
    number: String,
    label: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    color: Color
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
        border = androidx.compose.foundation.BorderStroke(1.dp, CardBorder),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Icon(imageVector = icon, contentDescription = null, tint = color, modifier = Modifier.size(22.dp))
            Spacer(modifier = Modifier.height(6.dp))
            Text(number, style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.ExtraBold, color = TextCharcoal))
            Spacer(modifier = Modifier.height(2.dp))
            Text(label, style = MaterialTheme.typography.bodySmall.copy(color = TextSecondary, fontSize = 11.sp))
        }
    }
}

@Composable
fun PillarItem(
    title: String,
    desc: String,
    tag: String
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
        border = androidx.compose.foundation.BorderStroke(1.dp, CardBorder)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(title, style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold, color = TextCharcoal))
                Surface(shape = RoundedCornerShape(50), color = MintLight) {
                    Text(tag, style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, color = EmeraldDark), modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp))
                }
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(desc, style = MaterialTheme.typography.bodySmall.copy(color = TextSecondary, lineHeight = 18.sp))
        }
    }
}
