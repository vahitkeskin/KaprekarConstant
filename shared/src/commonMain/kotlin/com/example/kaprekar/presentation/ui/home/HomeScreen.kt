package com.example.kaprekar.presentation.ui.home

import androidx.compose.animation.*
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import com.example.kaprekar.domain.model.MathScreen
import com.example.kaprekar.presentation.KaprekarUiIntent
import com.example.kaprekar.presentation.KaprekarUiState
import com.example.kaprekar.presentation.ui.common.BrandCyan
import com.example.kaprekar.presentation.ui.common.BrandPink
import com.example.kaprekar.presentation.ui.common.TopGradientAppBar

data class MathTopicCardItem(
    val screen: MathScreen,
    val title: String,
    val description: String,
    val badgeSymbol: String,
    val categoryTag: String,
    val accentColor: Color
)

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    state: KaprekarUiState,
    onIntent: (KaprekarUiIntent) -> Unit
) {
    val strings = state.strings
    var searchQuery by rememberSaveable { mutableStateOf("") }
    var selectedCategory by rememberSaveable { mutableStateOf(strings.catAll) }

    LaunchedEffect(strings.catAll) {
        selectedCategory = strings.catAll
    }

    val allTopics = listOf(
        MathTopicCardItem(MathScreen.KAPREKAR, strings.topicKaprekarTitle, strings.topicKaprekarDesc, "6174", strings.catConstants, BrandPink),
        MathTopicCardItem(MathScreen.FIBONACCI, strings.topicFibonacciTitle, strings.topicFibonacciDesc, "Fn", strings.catSequences, BrandCyan),
        MathTopicCardItem(MathScreen.SUPER_NUMBER, strings.topicSuperNumberTitle, strings.topicSuperNumberDesc, "∑", strings.catAlgebra, Color(0xFFFFB74D)),
        MathTopicCardItem(MathScreen.GOLDEN_RATIO, strings.topicGoldenRatioTitle, strings.topicGoldenRatioDesc, "Φ", strings.catGeometry, Color(0xFFAB47BC)),
        MathTopicCardItem(MathScreen.COLLATZ, strings.topicCollatzTitle, strings.topicCollatzDesc, "3n+1", strings.catSequences, Color(0xFF43A047)),
        MathTopicCardItem(MathScreen.PRIME, strings.topicPrimeTitle, strings.topicPrimeDesc, "P(n)", strings.catArithmetic, Color(0xFF29B6F6)),
        MathTopicCardItem(MathScreen.PASCAL, strings.topicPascalTitle, strings.topicPascalDesc, "C(n,k)", strings.catCombinatorics, Color(0xFFFF7043)),
        MathTopicCardItem(MathScreen.PI, strings.topicPiTitle, strings.topicPiDesc, "π", strings.catConstants, Color(0xFFE91E63)),
        MathTopicCardItem(MathScreen.EULER, strings.topicEulerTitle, strings.topicEulerDesc, "e", strings.catConstants, Color(0xFF3F51B5)),
        MathTopicCardItem(MathScreen.EUCLID_GCD, strings.topicEuclidTitle, strings.topicEuclidDesc, "GCD", strings.catArithmetic, Color(0xFF009688)),
        MathTopicCardItem(MathScreen.TRIGONOMETRY, strings.topicTrigTitle, strings.topicTrigDesc, "sin/cos", strings.catGeometry, Color(0xFFFF9800)),
        MathTopicCardItem(MathScreen.QUADRATIC, strings.topicQuadraticTitle, strings.topicQuadraticDesc, "ax²+bx", strings.catAlgebra, Color(0xFF9C27B0)),
        MathTopicCardItem(MathScreen.MODULAR, strings.topicModularTitle, strings.topicModularDesc, "a^b mod", strings.catCryptography, Color(0xFF673AB7)),
        MathTopicCardItem(MathScreen.STATISTICS, strings.topicStatsTitle, strings.topicStatsDesc, "σ", strings.catStatistics, Color(0xFF00BCD4)),
        MathTopicCardItem(MathScreen.FRACTAL, strings.topicFractalTitle, strings.topicFractalDesc, "z²+c", strings.catFractals, Color(0xFFD81B60)),
        MathTopicCardItem(MathScreen.PHYLLOTAXIS, strings.topicPhyllotaxisTitle, strings.topicPhyllotaxisDesc, "137.5°", strings.catGeometry, Color(0xFF8E24AA)),
        MathTopicCardItem(MathScreen.TRANSFORMATION, strings.topicTransformationTitle, strings.topicTransformationDesc, "2x2 M", strings.catAlgebra, Color(0xFF1E88E5)),
        MathTopicCardItem(MathScreen.FOURIER, strings.topicFourierTitle, strings.topicFourierDesc, "∑sin", strings.catSequences, Color(0xFF00ACC1)),
        MathTopicCardItem(MathScreen.CHAOS_GAME, strings.topicChaosGameTitle, strings.topicChaosGameDesc, "Kaos", strings.catFractals, Color(0xFF43A047)),
        MathTopicCardItem(MathScreen.NIM_GAME, strings.topicNimGameTitle, strings.topicNimGameDesc, "XOR", strings.catGameTheory, Color(0xFFF4511E)),
        MathTopicCardItem(MathScreen.LOGARITHM, strings.topicLogarithmTitle, strings.topicLogarithmDesc, "log_b", strings.catArithmetic, Color(0xFFE65100)),
        MathTopicCardItem(MathScreen.ARF_INVARIANT, strings.topicArfTitle, strings.topicArfDesc, "Arf(Q)", strings.catAlgebra, Color(0xFFC2185B)),
        MathTopicCardItem(MathScreen.THALES, strings.topicThalesTitle, strings.topicThalesDesc, "△/△", strings.catGeometry, Color(0xFF00796B)),
        MathTopicCardItem(MathScreen.KEPLER_LAWS, strings.topicKeplerTitle, strings.topicKeplerDesc, "T²/a³", strings.catAstronomy, Color(0xFF5E35B1)),
        MathTopicCardItem(MathScreen.BRACHISTOCHRONE, strings.topicBrachistochroneTitle, strings.topicBrachistochroneDesc, "Sikloid", strings.catPhysics, Color(0xFFD81B60)),
        MathTopicCardItem(MathScreen.CANTOR_SET, strings.topicCantorTitle, strings.topicCantorDesc, "ℵ₀", strings.catSets, Color(0xFF0288D1)),
        MathTopicCardItem(MathScreen.ERATOSTHENES, strings.topicEratosthenesTitle, strings.topicEratosthenesDesc, "C=2πr", strings.catGeometry, Color(0xFFF57C00)),
        MathTopicCardItem(MathScreen.CUBIC_EQUATION, strings.topicCubicTitle, strings.topicCubicDesc, "x³+px", strings.catAlgebra, Color(0xFF7B1FA2)),
        MathTopicCardItem(MathScreen.SPHERICAL_TRIG, strings.topicSphericalTrigTitle, strings.topicSphericalTrigDesc, "cos(a)", strings.catGeometry, Color(0xFF0097A7)),
        MathTopicCardItem(MathScreen.GODEL_NUMBERING, strings.topicGodelTitle, strings.topicGodelDesc, "2ᵃ3ᵇ", strings.catCryptography, Color(0xFF388E3C))
    )

    val categories = listOf(strings.catAll, strings.catConstants, strings.catAlgebra, strings.catGeometry, strings.catSequences, strings.catArithmetic, strings.catCombinatorics, strings.catCryptography, strings.catStatistics, strings.catFractals, strings.catGameTheory, strings.catAstronomy, strings.catPhysics, strings.catSets)

    val categoryListState = rememberLazyListState()
    val homeListState = rememberLazyListState()

    LaunchedEffect(selectedCategory) {
        val index = categories.indexOf(selectedCategory)
        if (index >= 0) {
            val layoutInfo = categoryListState.layoutInfo
            val viewportWidth = layoutInfo.viewportSize.width
            val visibleItem = layoutInfo.visibleItemsInfo.firstOrNull { it.index == index }
            if (visibleItem != null) {
                val itemCenter = visibleItem.offset + (visibleItem.size / 2)
                val viewportCenter = viewportWidth / 2
                val delta = (itemCenter - viewportCenter).toFloat()
                categoryListState.animateScrollBy(
                    value = delta,
                    animationSpec = tween(durationMillis = 750, easing = FastOutSlowInEasing)
                )
            } else {
                categoryListState.animateScrollToItem(
                    index = index,
                    scrollOffset = -(viewportWidth / 2 - 60)
                )
            }
        }
    }

    val filteredTopics = remember(searchQuery, selectedCategory) {
        allTopics.filter { topic ->
            val matchesCategory = (selectedCategory == strings.catAll || topic.categoryTag == selectedCategory)
            val matchesSearch = searchQuery.isBlank() ||
                    topic.title.contains(searchQuery, ignoreCase = true) ||
                    topic.description.contains(searchQuery, ignoreCase = true) ||
                    topic.categoryTag.contains(searchQuery, ignoreCase = true)
            matchesCategory && matchesSearch
        }
    }

    val backgroundGradient = Brush.verticalGradient(
        colors = listOf(
            BrandPink.copy(alpha = 0.18f),
            BrandCyan.copy(alpha = 0.10f),
            MaterialTheme.colorScheme.surface
        )
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundGradient)
    ) {
        Scaffold(
            containerColor = Color.Transparent,
            topBar = {
                TopGradientAppBar(
                    title = strings.homeTitle,
                    state = state,
                    onIntent = onIntent,
                    showBackButton = false,
                    badgeText = "${allTopics.size} MATH"
                )
            }
        ) { innerPadding ->
            LazyColumn(
                state = homeListState,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                contentPadding = PaddingValues(
                    top = innerPadding.calculateTopPadding() + 8.dp,
                    bottom = innerPadding.calculateBottomPadding() + 48.dp
                ),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                // Ayrı & Moda Tam Uyumlu Belirgin Başlık Kartı
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(22.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.95f)
                        ),
                        border = BorderStroke(1.5.dp, Brush.horizontalGradient(listOf(BrandPink, BrandCyan)))
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(18.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(14.dp)
                        ) {
                            Surface(
                                shape = CircleShape,
                                color = BrandPink,
                                modifier = Modifier.size(46.dp)
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Text(
                                        "∑",
                                        fontSize = 24.sp,
                                        fontWeight = FontWeight.Black,
                                        color = Color.White
                                    )
                                }
                            }

                            Column(
                                modifier = Modifier.weight(1f),
                                verticalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Text(
                                    text = strings.homeTitle,
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        fontWeight = FontWeight.ExtraBold,
                                        fontSize = 19.sp,
                                        color = MaterialTheme.colorScheme.onPrimaryContainer
                                    )
                                )
                                Text(
                                    text = strings.homeSubtitle,
                                    style = MaterialTheme.typography.bodySmall.copy(
                                        color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.85f),
                                        fontWeight = FontWeight.Medium,
                                        fontSize = 12.5.sp,
                                        lineHeight = 17.sp
                                    )
                                )
                            }
                        }
                    }
                }

                // Search Bar (Moda Göre Belirgin Görünüm)
                item {
                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = { searchQuery = it },
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = {
                            Text(
                                strings.inputHint,
                                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
                            )
                        },
                        leadingIcon = {
                            Icon(
                                Icons.Default.Search,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary
                            )
                        },
                        trailingIcon = if (searchQuery.isNotEmpty()) {
                            {
                                IconButton(onClick = { searchQuery = "" }) {
                                    Icon(
                                        Icons.Default.Clear,
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }
                        } else null,
                        shape = RoundedCornerShape(16.dp),
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = MaterialTheme.colorScheme.surface,
                            unfocusedContainerColor = MaterialTheme.colorScheme.surfaceContainerHigh.copy(alpha = 0.9f),
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            unfocusedBorderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f),
                            focusedTextColor = MaterialTheme.colorScheme.onSurface,
                            unfocusedTextColor = MaterialTheme.colorScheme.onSurface
                        )
                    )
                }

                // Category Chips (Seçilen Çip Ekranın Ortasına Otomatik Animasyonla Kayar)
                item {
                    LazyRow(
                        state = categoryListState,
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        items(categories) { cat ->
                            val isSelected = (selectedCategory == cat)
                            FilterChip(
                                selected = isSelected,
                                onClick = { selectedCategory = cat },
                                label = {
                                    Text(
                                        text = cat,
                                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                                    )
                                },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = MaterialTheme.colorScheme.primary,
                                    selectedLabelColor = MaterialTheme.colorScheme.onPrimary,
                                    containerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
                                    labelColor = MaterialTheme.colorScheme.onSurfaceVariant
                                ),
                                border = FilterChipDefaults.filterChipBorder(
                                    enabled = true,
                                    selected = isSelected,
                                    borderColor = if (isSelected) BrandPink else MaterialTheme.colorScheme.outline.copy(alpha = 0.4f),
                                    selectedBorderColor = BrandPink,
                                    borderWidth = if (isSelected) 1.5.dp else 1.dp
                                ),
                                shape = RoundedCornerShape(12.dp)
                            )
                        }
                    }
                }

                // Topics List Cards (Her Bir Kartın Yer Değiştirmesi ve Giriş/Çıkışı Animasyonlu)
                items(
                    items = filteredTopics,
                    key = { it.screen.name }
                ) { topic ->
                    MathTopicListItemCard(
                        item = topic,
                        openSolverLabel = strings.openSolver,
                        modifier = Modifier.animateItem(
                            fadeInSpec = null,
                            fadeOutSpec = null,
                            placementSpec = tween(400, easing = FastOutSlowInEasing)
                        ),
                        onClick = { onIntent(KaprekarUiIntent.OnNavigateToScreen(topic.screen)) }
                    )
                }
            }
        }
    }
}

@Composable
fun MathTopicListItemCard(
    item: MathTopicCardItem,
    openSolverLabel: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainerHighest.copy(alpha = 0.85f)
        ),
        border = BorderStroke(1.2.dp, item.accentColor.copy(alpha = 0.5f)),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            val badgeFontSize = when {
                item.badgeSymbol.length <= 3 -> 13.5.sp
                item.badgeSymbol.length <= 5 -> 11.5.sp
                item.badgeSymbol.length <= 7 -> 10.sp
                else -> 9.sp
            }

            Surface(
                shape = RoundedCornerShape(14.dp),
                color = item.accentColor.copy(alpha = 0.15f),
                border = BorderStroke(1.dp, item.accentColor.copy(alpha = 0.5f)),
                modifier = Modifier.size(width = 58.dp, height = 52.dp)
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.padding(horizontal = 2.dp)
                ) {
                    Text(
                        text = item.badgeSymbol,
                        fontFamily = FontFamily.Monospace,
                        fontSize = badgeFontSize,
                        fontWeight = FontWeight.ExtraBold,
                        color = item.accentColor,
                        maxLines = 1,
                        softWrap = false
                    )
                }
            }

            Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Surface(shape = RoundedCornerShape(8.dp), color = item.accentColor.copy(alpha = 0.12f)) {
                    Text(item.categoryTag, modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp), style = MaterialTheme.typography.labelSmall.copy(color = item.accentColor, fontWeight = FontWeight.Bold, fontSize = 10.sp))
                }
                Text(item.title, style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold))
                Text(item.description, style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 12.sp), maxLines = 2)
            }

            Surface(shape = CircleShape, color = item.accentColor.copy(alpha = 0.15f), modifier = Modifier.size(36.dp)) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = openSolverLabel, tint = item.accentColor, modifier = Modifier.size(18.dp))
                }
            }
        }
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    MaterialTheme {
        HomeScreen(state = KaprekarUiState(), onIntent = {})
    }
}
