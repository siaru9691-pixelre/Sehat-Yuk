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
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.HealthArticle
import com.example.data.repository.SehatYukRepository
import com.example.ui.components.ArticleDetailDialog
import com.example.ui.components.SDGBadge
import com.example.ui.theme.*

@Composable
fun ArticlesScreen() {
    val articles by SehatYukRepository.articles.collectAsState()
    var searchQuery by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("Semua") }
    var activeArticleForReader by remember { mutableStateOf<HealthArticle?>(null) }

    val categories = listOf("Semua", "Mental & Stres", "Hidrasi", "Nutrisi", "Ergonomi")

    val filteredArticles = articles.filter { article ->
        val matchesCategory = if (selectedCategory == "Semua") true else article.category.contains(selectedCategory.substringBefore(" &"))
        val matchesSearch = if (searchQuery.isBlank()) true else {
            article.title.contains(searchQuery, ignoreCase = true) ||
            article.summary.contains(searchQuery, ignoreCase = true)
        }
        matchesCategory && matchesSearch
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(AppBackground)
            .padding(horizontal = 20.dp)
            .testTag("articles_screen"),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(10.dp))
            SDGBadge(text = "SDG 3.4 LITERASI KESEHATAN")
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "Edukasi & Tips Sehat",
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.ExtraBold,
                    color = TextCharcoal
                )
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Artikel ringkas berbasis bukti medis untuk mahasiswa dan generasi muda.",
                style = MaterialTheme.typography.bodyMedium.copy(color = TextSecondary)
            )
        }

        // Search Bar
        item {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                placeholder = { Text("Cari topik, tips nutrisi, stres...") },
                leadingIcon = { Icon(Icons.Outlined.Search, contentDescription = null, tint = TextMuted) },
                modifier = Modifier.fillMaxWidth().testTag("article_search_input"),
                shape = RoundedCornerShape(16.dp),
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = SurfaceWhite,
                    unfocusedContainerColor = SurfaceWhite,
                    focusedBorderColor = EmeraldPrimary,
                    unfocusedBorderColor = CardBorder
                )
            )
        }

        // Category Filter Chips
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                categories.forEach { cat ->
                    val isSelected = selectedCategory == cat
                    FilterChip(
                        selected = isSelected,
                        onClick = { selectedCategory = cat },
                        label = { Text(cat, fontSize = 12.sp) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = MintSoft,
                            selectedLabelColor = EmeraldDark
                        )
                    )
                }
            }
        }

        // Articles List
        items(filteredArticles) { article ->
            ArticleListItem(
                article = article,
                onClick = { activeArticleForReader = article },
                onBookmarkToggle = { SehatYukRepository.toggleBookmark(article.id) }
            )
        }

        // SDG Fact Card
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = MintLight),
                border = androidx.compose.foundation.BorderStroke(1.dp, EmeraldBorder)
            ) {
                Row(
                    modifier = Modifier.padding(14.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    Text(text = "📚", fontSize = 22.sp)
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = "Pengetahuan Mencegah Sakit",
                            style = MaterialTheme.typography.titleSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = TextCharcoal
                            )
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = "Target SDG 3 mendorong peningkatan literasi kesehatan masyarakat untuk mencegah penyakit tidak menular (PTM) sejak usia muda.",
                            style = MaterialTheme.typography.bodySmall.copy(color = TextSecondary, lineHeight = 18.sp)
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
        }
    }

    activeArticleForReader?.let { article ->
        ArticleDetailDialog(
            article = article,
            onDismiss = { activeArticleForReader = null },
            onBookmarkToggle = { SehatYukRepository.toggleBookmark(article.id) }
        )
    }
}

@Composable
fun ArticleListItem(
    article: HealthArticle,
    onClick: () -> Unit,
    onBookmarkToggle: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .testTag("article_item_${article.id}"),
        shape = RoundedCornerShape(20.dp),
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
                SDGBadge(text = article.sdgTag)
                IconButton(onClick = onBookmarkToggle, modifier = Modifier.size(32.dp)) {
                    Icon(
                        imageVector = if (article.isBookmarked) Icons.Default.Bookmark else Icons.Outlined.BookmarkBorder,
                        contentDescription = "Bookmark",
                        tint = if (article.isBookmarked) EmeraldPrimary else TextMuted
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = article.title,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = TextCharcoal
                )
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = article.summary,
                style = MaterialTheme.typography.bodySmall.copy(
                    color = TextSecondary,
                    lineHeight = 18.sp
                ),
                maxLines = 2
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${article.author} • ${article.readTimeMinutes} mnt",
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = EmeraldDark,
                        fontWeight = FontWeight.SemiBold
                    )
                )

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = null,
                        tint = WarmYellow,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(2.dp))
                    Text(
                        text = "${article.rating}",
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = FontWeight.Bold,
                            color = TextCharcoal
                        )
                    )
                }
            }
        }
    }
}
