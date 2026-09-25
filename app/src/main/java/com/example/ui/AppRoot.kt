package com.example.ui

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material.icons.filled.DeliveryDining
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material.icons.filled.Store
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.StoreBranch
import com.example.StoreConfig
import com.example.WilayaDelivery
import com.example.data.StoreRepository
import com.example.model.CartItem
import com.example.model.Category
import com.example.model.DeliveryType
import com.example.model.Order
import com.example.model.Product
import com.example.model.Topping
import com.example.model.ToppingType
import com.example.navigation.Screen
import com.example.three.ThreeHeroView
import com.example.ui.theme.ChocoBrown
import com.example.ui.theme.CrepeGold
import com.example.utils.OrderUtils

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppRoot(viewModel: AppViewModel) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    var selectedProductForDetail by remember { mutableStateOf<Product?>(null) }

    // Intercept hardware and gesture back navigation
    BackHandler(enabled = uiState.navigationStack.size > 1) {
        viewModel.navigateBack()
    }

    Scaffold(
        contentWindowInsets = WindowInsets.safeDrawing,
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "🥞 Crêpe Algérie",
                            fontWeight = FontWeight.Bold,
                            fontSize = 19.sp,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Surface(
                            color = CrepeGold.copy(alpha = 0.15f),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text(
                                text = "DZ 🇩🇿",
                                color = CrepeGold,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                    }
                },
                navigationIcon = {
                    if (uiState.navigationStack.size > 1) {
                        IconButton(
                            onClick = { viewModel.navigateBack() },
                            modifier = Modifier.testTag("nav_back_button")
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Retour",
                                tint = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    } else {
                        IconButton(
                            onClick = { viewModel.navigateTo(Screen.BRANCHES) },
                            modifier = Modifier.testTag("nav_branches_button")
                        ) {
                            Icon(
                                imageVector = Icons.Default.LocationOn,
                                contentDescription = "Nos Salons",
                                tint = CrepeGold
                            )
                        }
                    }
                },
                actions = {
                    IconButton(
                        onClick = { viewModel.navigateTo(Screen.CART) },
                        modifier = Modifier.testTag("top_cart_button")
                    ) {
                        BadgedBox(
                            badge = {
                                if (uiState.cartCount > 0) {
                                    Badge(
                                        containerColor = MaterialTheme.colorScheme.tertiary,
                                        contentColor = Color.White
                                    ) {
                                        Text(text = "${uiState.cartCount}", fontSize = 10.sp)
                                    }
                                }
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.ShoppingBag,
                                contentDescription = "Panier",
                                tint = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        },
        bottomBar = {
            NavigationBar(
                modifier = Modifier
                    .navigationBarsPadding()
                    .testTag("bottom_nav_bar"),
                containerColor = MaterialTheme.colorScheme.surface,
                tonalElevation = 8.dp
            ) {
                val items = listOf(
                    Screen.HOME,
                    Screen.MENU,
                    Screen.BUILDER,
                    Screen.CART,
                    Screen.ORDERS
                )
                items.forEach { screen ->
                    val isSelected = uiState.currentScreen == screen
                    NavigationBarItem(
                        selected = isSelected,
                        onClick = { viewModel.navigateTo(screen) },
                        icon = {
                            if (screen == Screen.CART && uiState.cartCount > 0) {
                                BadgedBox(
                                    badge = {
                                        Badge(
                                            containerColor = MaterialTheme.colorScheme.tertiary,
                                            contentColor = Color.White
                                        ) {
                                            Text(text = "${uiState.cartCount}")
                                        }
                                    }
                                ) {
                                    Icon(
                                        imageVector = if (isSelected) screen.selectedIcon else screen.unselectedIcon,
                                        contentDescription = screen.title
                                    )
                                }
                            } else {
                                Icon(
                                    imageVector = if (isSelected) screen.selectedIcon else screen.unselectedIcon,
                                    contentDescription = screen.title
                                )
                            }
                        },
                        label = { Text(text = screen.title, fontSize = 11.sp) },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = MaterialTheme.colorScheme.onPrimaryContainer,
                            indicatorColor = MaterialTheme.colorScheme.primaryContainer,
                            selectedTextColor = MaterialTheme.colorScheme.primary,
                            unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
                        ),
                        modifier = Modifier.testTag("nav_item_${screen.route}")
                    )
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(MaterialTheme.colorScheme.background)
        ) {
            when (uiState.currentScreen) {
                Screen.HOME -> HomeScreen(
                    viewModel = viewModel,
                    uiState = uiState,
                    onProductClick = { selectedProductForDetail = it }
                )
                Screen.MENU -> MenuScreen(
                    viewModel = viewModel,
                    uiState = uiState,
                    onProductClick = { selectedProductForDetail = it }
                )
                Screen.BUILDER -> BuilderScreen(
                    viewModel = viewModel,
                    uiState = uiState
                )
                Screen.CART -> CartScreen(
                    viewModel = viewModel,
                    uiState = uiState
                )
                Screen.CHECKOUT -> CheckoutScreen(
                    viewModel = viewModel,
                    uiState = uiState
                )
                Screen.ORDERS -> OrdersScreen(
                    viewModel = viewModel,
                    uiState = uiState
                )
                Screen.BRANCHES -> BranchesScreen(
                    viewModel = viewModel,
                    uiState = uiState
                )
            }
        }
    }

    // Product Detail & Quick Add Dialog
    selectedProductForDetail?.let { product ->
        ProductDetailDialog(
            product = product,
            isFavorite = uiState.favoriteIds.contains(product.id),
            onDismiss = { selectedProductForDetail = null },
            onToggleFavorite = { viewModel.toggleFavorite(product.id) },
            onAddToCart = { qty, toppings, notes ->
                viewModel.addToCart(product, qty, toppings, notes)
                selectedProductForDetail = null
                Toast.makeText(context, "${product.name} ajouté au panier !", Toast.LENGTH_SHORT).show()
            }
        )
    }

    // Order Placed Success Dialog
    if (uiState.showOrderSuccessDialog && uiState.lastSubmittedOrder != null) {
        OrderSuccessDialog(
            order = uiState.lastSubmittedOrder!!,
            onDismiss = { viewModel.dismissSuccessDialog() },
            onSendWhatsApp = {
                OrderUtils.sendOrderViaWhatsApp(
                    context = context,
                    whatsAppNumber = uiState.selectedBranch.whatsAppNumber,
                    order = uiState.lastSubmittedOrder!!
                )
            }
        )
    }
}

// -------------------------------------------------------------
// SCREEN 1: HOME SCREEN
// -------------------------------------------------------------
@Composable
fun HomeScreen(
    viewModel: AppViewModel,
    uiState: AppUiState,
    onProductClick: (Product) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .testTag("home_screen"),
        contentPadding = PaddingValues(bottom = 24.dp)
    ) {
        // Hero Welcome & Active Branch Pill
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Salam & Bienvenue 👋",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            text = "Envie d'une crêpe d'exception ?",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Location branch banner
                Surface(
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { viewModel.navigateTo(Screen.BRANCHES) }
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Store,
                            contentDescription = null,
                            tint = CrepeGold,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = uiState.selectedBranch.name,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 12.sp,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = "Ouvert: ${uiState.selectedBranch.openingHours} • ${uiState.selectedWilaya.name}",
                                fontSize = 11.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        Icon(
                            imageVector = Icons.Default.Tune,
                            contentDescription = "Changer",
                            tint = CrepeGold,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            }
        }

        // 3D Hero View
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 4.dp)
            ) {
                ThreeHeroView(
                    onCustomizeClick = { viewModel.navigateTo(Screen.BUILDER) }
                )
            }
        }

        // Fast Action CTA: Compose your own crepe
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp)
                    .clickable { viewModel.navigateTo(Screen.BUILDER) }
                    .testTag("cta_builder_card"),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = ChocoBrown),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Surface(
                            color = CrepeGold,
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text(
                                text = "ATELIER SUR-MESURE",
                                color = Color.White,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                            )
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "Compose Ta Propre Crêpe !",
                            color = Color.White,
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Pâte dorée, Nutella, Pistache, Fruits frais & toppings au choix",
                            color = Color(0xFFD7CCC8),
                            fontSize = 12.sp,
                            maxLines = 2
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Surface(
                        color = CrepeGold,
                        shape = CircleShape,
                        modifier = Modifier.size(46.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = Icons.Default.Tune,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }
                }
            }
        }

        // Categories Quick Scroll
        item {
            Column(modifier = Modifier.padding(vertical = 6.dp)) {
                Text(
                    text = "Nos Spécialités",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp),
                    color = MaterialTheme.colorScheme.onSurface
                )
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(Category.values().filterNot { it == Category.CUSTOM }) { cat ->
                        CategoryChip(
                            category = cat,
                            isSelected = uiState.selectedCategory == cat,
                            onClick = {
                                viewModel.selectCategory(cat)
                                viewModel.navigateTo(Screen.MENU)
                            }
                        )
                    }
                }
            }
        }

        // Chef's Specials & Best Sellers
        item {
            Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Les Plus Populaires ⭐",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = "Voir tout",
                        color = CrepeGold,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.clickable { viewModel.navigateTo(Screen.MENU) }
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                val popularProducts = StoreRepository.products.filter { it.isPopular }
                popularProducts.forEach { product ->
                    ProductCardHorizontal(
                        product = product,
                        isFavorite = uiState.favoriteIds.contains(product.id),
                        onProductClick = { onProductClick(product) },
                        onQuickAdd = {
                            viewModel.addToCart(product)
                        }
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                }
            }
        }
    }
}

// -------------------------------------------------------------
// SCREEN 2: MENU SCREEN
// -------------------------------------------------------------
@Composable
fun MenuScreen(
    viewModel: AppViewModel,
    uiState: AppUiState,
    onProductClick: (Product) -> Unit
) {
    val filteredProducts = remember(uiState.selectedCategory, uiState.searchQuery) {
        StoreRepository.searchProducts(uiState.searchQuery, uiState.selectedCategory)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .testTag("menu_screen")
    ) {
        // Search Bar
        OutlinedTextField(
            value = uiState.searchQuery,
            onValueChange = { viewModel.setSearchQuery(it) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .testTag("search_text_field"),
            placeholder = { Text("Rechercher crêpe, gaufre, ingrédient...") },
            leadingIcon = {
                Icon(imageVector = Icons.Default.Search, contentDescription = null, tint = CrepeGold)
            },
            trailingIcon = {
                if (uiState.searchQuery.isNotBlank()) {
                    IconButton(onClick = { viewModel.setSearchQuery("") }) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = "Effacer")
                    }
                }
            },
            shape = RoundedCornerShape(16.dp),
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = CrepeGold,
                unfocusedBorderColor = MaterialTheme.colorScheme.outlineVariant
            )
        )

        // Categories Row
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 6.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(Category.values().filterNot { it == Category.CUSTOM }) { cat ->
                CategoryChip(
                    category = cat,
                    isSelected = uiState.selectedCategory == cat,
                    onClick = { viewModel.selectCategory(cat) }
                )
            }
        }

        // Products List
        if (filteredProducts.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.size(48.dp)
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "Aucun produit trouvé",
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = "Essayez un autre mot-clé ou catégorie",
                        fontSize = 13.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(filteredProducts, key = { it.id }) { product ->
                    ProductCardHorizontal(
                        product = product,
                        isFavorite = uiState.favoriteIds.contains(product.id),
                        onProductClick = { onProductClick(product) },
                        onQuickAdd = {
                            viewModel.addToCart(product)
                        }
                    )
                }
            }
        }
    }
}

// -------------------------------------------------------------
// SCREEN 3: BUILDER SCREEN (CUSTOM CREPE DESIGNER)
// -------------------------------------------------------------
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun BuilderScreen(
    viewModel: AppViewModel,
    uiState: AppUiState
) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .testTag("builder_screen")
    ) {
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Column {
                    Text(
                        text = "Atelier Sur-Mesure 🎨",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = "Créez votre crêpe de rêve pas à pas avec vos ingrédients favoris",
                        fontSize = 13.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            // Step 1: Base selection
            item {
                SectionCard(title = "1. Choisissez votre Base") {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        StoreRepository.customBases.forEach { base ->
                            val isSelected = uiState.customBase.id == base.id
                            Surface(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(12.dp))
                                    .clickable { viewModel.selectCustomBase(base) },
                                color = if (isSelected) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surface,
                                border = BorderStroke(
                                    1.dp,
                                    if (isSelected) CrepeGold else MaterialTheme.colorScheme.outlineVariant
                                ),
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Row(
                                    modifier = Modifier.padding(12.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Surface(
                                            color = if (isSelected) CrepeGold else Color.Gray.copy(alpha = 0.2f),
                                            shape = CircleShape,
                                            modifier = Modifier.size(24.dp)
                                        ) {
                                            if (isSelected) {
                                                Icon(
                                                    imageVector = Icons.Default.Check,
                                                    contentDescription = null,
                                                    tint = Color.White,
                                                    modifier = Modifier.padding(4.dp)
                                                )
                                            }
                                        }
                                        Spacer(modifier = Modifier.width(10.dp))
                                        Text(
                                            text = base.name,
                                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                            fontSize = 14.sp
                                        )
                                    }
                                    Text(
                                        text = OrderUtils.formatDZD(base.price),
                                        fontWeight = FontWeight.Bold,
                                        color = CrepeGold,
                                        fontSize = 14.sp
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // Step 2: Spreads & Sauces
            item {
                SectionCard(title = "2. Sauces & Nappages") {
                    val spreads = StoreRepository.standardToppings.filter { it.type == ToppingType.SPREAD }
                    FlowRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        spreads.forEach { topping ->
                            val isSelected = uiState.selectedCustomToppings.any { it.id == topping.id }
                            ToppingSelectChip(
                                topping = topping,
                                isSelected = isSelected,
                                onClick = { viewModel.toggleCustomTopping(topping) }
                            )
                        }
                    }
                }
            }

            // Step 3: Fresh Fruits
            item {
                SectionCard(title = "3. Fruits Frais Tranchés") {
                    val fruits = StoreRepository.standardToppings.filter { it.type == ToppingType.FRUIT }
                    FlowRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        fruits.forEach { topping ->
                            val isSelected = uiState.selectedCustomToppings.any { it.id == topping.id }
                            ToppingSelectChip(
                                topping = topping,
                                isSelected = isSelected,
                                onClick = { viewModel.toggleCustomTopping(topping) }
                            )
                        }
                    }
                }
            }

            // Step 4: Crunch & Biscuits
            item {
                SectionCard(title = "4. Crunch & Biscuits Gourmands") {
                    val crunches = StoreRepository.standardToppings.filter { it.type == ToppingType.CRUNCH }
                    FlowRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        crunches.forEach { topping ->
                            val isSelected = uiState.selectedCustomToppings.any { it.id == topping.id }
                            ToppingSelectChip(
                                topping = topping,
                                isSelected = isSelected,
                                onClick = { viewModel.toggleCustomTopping(topping) }
                            )
                        }
                    }
                }
            }

            // Step 5: Extras
            item {
                SectionCard(title = "5. Extras & Boule de Glace") {
                    val extras = StoreRepository.standardToppings.filter { it.type == ToppingType.EXTRA }
                    FlowRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        extras.forEach { topping ->
                            val isSelected = uiState.selectedCustomToppings.any { it.id == topping.id }
                            ToppingSelectChip(
                                topping = topping,
                                isSelected = isSelected,
                                onClick = { viewModel.toggleCustomTopping(topping) }
                            )
                        }
                    }
                }
            }

            // Notes input
            item {
                SectionCard(title = "Instructions pour le chef crêpier") {
                    OutlinedTextField(
                        value = uiState.customNotes,
                        onValueChange = { viewModel.setCustomNotes(it) },
                        placeholder = { Text("Ex: Bien dorée, sauce à part, harissa légère...") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp)
                    )
                }
            }
        }

        // Sticky Bottom Price Bar & Add Button
        Surface(
            color = MaterialTheme.colorScheme.surface,
            tonalElevation = 8.dp,
            shadowElevation = 8.dp
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = "Prix Total Calculé",
                        fontSize = 11.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = OrderUtils.formatDZD(uiState.customCrepePrice),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = CrepeGold
                    )
                }

                Button(
                    onClick = {
                        viewModel.addCustomCrepeToCart()
                        Toast.makeText(context, "Crêpe sur-mesure ajoutée au panier !", Toast.LENGTH_SHORT).show()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = CrepeGold),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.testTag("add_custom_crepe_button")
                ) {
                    Icon(imageVector = Icons.Default.ShoppingBag, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "Ajouter au Panier", fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

// -------------------------------------------------------------
// SCREEN 4: CART SCREEN
// -------------------------------------------------------------
@Composable
fun CartScreen(
    viewModel: AppViewModel,
    uiState: AppUiState
) {
    if (uiState.cartItems.isEmpty()) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp)
                .testTag("empty_cart_view"),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Surface(
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    shape = CircleShape,
                    modifier = Modifier.size(90.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = Icons.Default.ShoppingBag,
                            contentDescription = null,
                            tint = CrepeGold,
                            modifier = Modifier.size(42.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Votre panier est vide",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "Découvrez nos délicieuses crêpes sucrées et salées et ajoutez-les à votre commande.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(20.dp))
                Button(
                    onClick = { viewModel.navigateTo(Screen.MENU) },
                    colors = ButtonDefaults.buttonColors(containerColor = CrepeGold),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text(text = "Consulter La Carte")
                }
            }
        }
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .testTag("cart_screen")
        ) {
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Mon Panier (${uiState.cartCount} articles)",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Vider",
                            color = MaterialTheme.colorScheme.error,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.clickable { viewModel.clearCart() }
                        )
                    }
                }

                items(uiState.cartItems, key = { it.cartId }) { item ->
                    CartItemCard(
                        item = item,
                        onIncrement = { viewModel.updateCartQuantity(item.cartId, item.quantity + 1) },
                        onDecrement = { viewModel.updateCartQuantity(item.cartId, item.quantity - 1) },
                        onDelete = { viewModel.removeFromCart(item.cartId) }
                    )
                }

                // Delivery mode toggle in cart
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Text(
                                text = "Mode de réception",
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                DeliveryType.values().forEach { type ->
                                    val isSelected = uiState.deliveryType == type
                                    Button(
                                        onClick = { viewModel.setDeliveryType(type) },
                                        modifier = Modifier.weight(1f),
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = if (isSelected) CrepeGold else MaterialTheme.colorScheme.surface,
                                            contentColor = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurface
                                        ),
                                        shape = RoundedCornerShape(12.dp),
                                        border = if (!isSelected) BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant) else null
                                    ) {
                                        Text(text = type.label, fontSize = 11.sp, maxLines = 1)
                                    }
                                }
                            }
                        }
                    }
                }

                // Price Summary
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(text = "Sous-total articles", color = MaterialTheme.colorScheme.onSurfaceVariant)
                                Text(text = OrderUtils.formatDZD(uiState.cartSubtotal), fontWeight = FontWeight.SemiBold)
                            }
                            Spacer(modifier = Modifier.height(6.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = if (uiState.deliveryType == DeliveryType.DELIVERY) "Frais de livraison (${uiState.selectedWilaya.name})" else "Retrait en magasin",
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                Text(
                                    text = if (uiState.deliveryType == DeliveryType.DELIVERY) OrderUtils.formatDZD(uiState.deliveryFee) else "Gratuit",
                                    color = if (uiState.deliveryType == DeliveryType.DELIVERY) MaterialTheme.colorScheme.onSurface else CrepeGold,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                            HorizontalDivider(modifier = Modifier.padding(vertical = 10.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(text = "Total TTC", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                                Text(
                                    text = OrderUtils.formatDZD(uiState.cartTotal),
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 20.sp,
                                    color = CrepeGold
                                )
                            }
                        }
                    }
                }
            }

            // Checkout CTA
            Surface(
                color = MaterialTheme.colorScheme.surface,
                tonalElevation = 8.dp,
                shadowElevation = 8.dp
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Button(
                        onClick = { viewModel.navigateTo(Screen.CHECKOUT) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp)
                            .testTag("go_to_checkout_button"),
                        colors = ButtonDefaults.buttonColors(containerColor = CrepeGold),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Text(
                            text = "Valider la Commande (${OrderUtils.formatDZD(uiState.cartTotal)})",
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp
                        )
                    }
                }
            }
        }
    }
}

// -------------------------------------------------------------
// SCREEN 5: CHECKOUT SCREEN
// -------------------------------------------------------------
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheckoutScreen(
    viewModel: AppViewModel,
    uiState: AppUiState
) {
    val context = LocalContext.current
    var wilayaExpanded by remember { mutableStateOf(false) }
    var communeExpanded by remember { mutableStateOf(false) }
    var branchExpanded by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .testTag("checkout_screen"),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text(
                text = "Finaliser Ma Commande 🛵",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = "Veuillez renseigner vos coordonnées pour la préparation et livraison",
                fontSize = 13.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        // Customer contact information
        item {
            SectionCard(title = "1. Vos Coordonnées") {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    OutlinedTextField(
                        value = uiState.customerName,
                        onValueChange = { viewModel.updateCustomerInfo(name = it) },
                        label = { Text("Nom complet *") },
                        placeholder = { Text("Ex: Amine Benali") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("checkout_name_input"),
                        shape = RoundedCornerShape(12.dp),
                        singleLine = true
                    )

                    OutlinedTextField(
                        value = uiState.customerPhone,
                        onValueChange = { viewModel.updateCustomerInfo(phone = it) },
                        label = { Text("Numéro de Téléphone (Algérie) *") },
                        placeholder = { Text("Ex: 0555 12 34 56 ou 0770...") },
                        leadingIcon = {
                            Text(
                                text = "🇩🇿 +213 ",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(start = 12.dp)
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("checkout_phone_input"),
                        shape = RoundedCornerShape(12.dp),
                        singleLine = true
                    )
                }
            }
        }

        // Branch / Salon selector
        item {
            SectionCard(title = "2. Succursale Crêpe Algérie") {
                ExposedDropdownMenuBox(
                    expanded = branchExpanded,
                    onExpandedChange = { branchExpanded = !branchExpanded }
                ) {
                    OutlinedTextField(
                        value = uiState.selectedBranch.name,
                        onValueChange = {},
                        readOnly = true,
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = branchExpanded) },
                        modifier = Modifier
                            .menuAnchor()
                            .fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp)
                    )
                    ExposedDropdownMenu(
                        expanded = branchExpanded,
                        onDismissRequest = { branchExpanded = false }
                    ) {
                        StoreConfig.BRANCHES.forEach { branch ->
                            DropdownMenuItem(
                                text = {
                                    Column {
                                        Text(text = branch.name, fontWeight = FontWeight.SemiBold)
                                        Text(text = branch.address, fontSize = 11.sp, color = Color.Gray)
                                    }
                                },
                                onClick = {
                                    viewModel.selectBranch(branch)
                                    branchExpanded = false
                                }
                            )
                        }
                    }
                }
            }
        }

        // Delivery details if Delivery
        if (uiState.deliveryType == DeliveryType.DELIVERY) {
            item {
                SectionCard(title = "3. Adresse de Livraison") {
                    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        // Wilaya Selector
                        ExposedDropdownMenuBox(
                            expanded = wilayaExpanded,
                            onExpandedChange = { wilayaExpanded = !wilayaExpanded }
                        ) {
                            OutlinedTextField(
                                value = "${uiState.selectedWilaya.name} (${OrderUtils.formatDZD(uiState.selectedWilaya.deliveryFee)})",
                                onValueChange = {},
                                readOnly = true,
                                label = { Text("Wilaya de livraison") },
                                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = wilayaExpanded) },
                                modifier = Modifier
                                    .menuAnchor()
                                    .fillMaxWidth(),
                                shape = RoundedCornerShape(12.dp)
                            )
                            ExposedDropdownMenu(
                                expanded = wilayaExpanded,
                                onDismissRequest = { wilayaExpanded = false }
                            ) {
                                StoreConfig.WILAYAS.forEach { wilaya ->
                                    DropdownMenuItem(
                                        text = { Text("${wilaya.name} - Frais: ${OrderUtils.formatDZD(wilaya.deliveryFee)}") },
                                        onClick = {
                                            viewModel.selectWilaya(wilaya)
                                            wilayaExpanded = false
                                        }
                                    )
                                }
                            }
                        }

                        // Commune Selector
                        ExposedDropdownMenuBox(
                            expanded = communeExpanded,
                            onExpandedChange = { communeExpanded = !communeExpanded }
                        ) {
                            OutlinedTextField(
                                value = uiState.selectedCommune,
                                onValueChange = {},
                                readOnly = true,
                                label = { Text("Commune") },
                                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = communeExpanded) },
                                modifier = Modifier
                                    .menuAnchor()
                                    .fillMaxWidth(),
                                shape = RoundedCornerShape(12.dp)
                            )
                            ExposedDropdownMenu(
                                expanded = communeExpanded,
                                onDismissRequest = { communeExpanded = false }
                            ) {
                                uiState.selectedWilaya.communes.forEach { comm ->
                                    DropdownMenuItem(
                                        text = { Text(comm) },
                                        onClick = {
                                            viewModel.selectCommune(comm)
                                            communeExpanded = false
                                        }
                                    )
                                }
                            }
                        }

                        // Street & building details
                        OutlinedTextField(
                            value = uiState.customerAddress,
                            onValueChange = { viewModel.updateCustomerInfo(address = it) },
                            label = { Text("Adresse précise / Quartier / Bâtiment") },
                            placeholder = { Text("Ex: Cité 500 logts, Bâtiment B3, en face de la mosquée...") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("checkout_address_input"),
                            shape = RoundedCornerShape(12.dp)
                        )
                    }
                }
            }
        }

        // Special notes
        item {
            SectionCard(title = "Remarques spéciales") {
                OutlinedTextField(
                    value = uiState.orderNotes,
                    onValueChange = { viewModel.updateCustomerInfo(notes = it) },
                    placeholder = { Text("Instructions supplémentaires...") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                )
            }
        }

        // Final Order Total Recap Card
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = "RÉSUMÉ DU PAIEMENT", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = CrepeGold)
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = "Sous-total articles:")
                        Text(text = OrderUtils.formatDZD(uiState.cartSubtotal), fontWeight = FontWeight.SemiBold)
                    }
                    if (uiState.deliveryType == DeliveryType.DELIVERY) {
                        Spacer(modifier = Modifier.height(4.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(text = "Frais de livraison:")
                            Text(text = OrderUtils.formatDZD(uiState.deliveryFee), fontWeight = FontWeight.SemiBold)
                        }
                    }
                    HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "TOTAL À RÉGLER:", fontWeight = FontWeight.Bold, fontSize = 15.sp)
                        Text(
                            text = OrderUtils.formatDZD(uiState.cartTotal),
                            fontWeight = FontWeight.Bold,
                            fontSize = 19.sp,
                            color = CrepeGold
                        )
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "💵 Paiement en espèces à la livraison ou au retrait",
                        fontSize = 11.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }

        // Action Buttons: Send via WhatsApp or Direct Confirm
        item {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Button(
                    onClick = {
                        if (uiState.customerName.isBlank() || uiState.customerPhone.isBlank()) {
                            Toast.makeText(context, "Veuillez renseigner votre nom et numéro de téléphone", Toast.LENGTH_LONG).show()
                        } else {
                            viewModel.submitOrder(context, viaWhatsApp = true)
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(54.dp)
                        .testTag("whatsapp_order_button"),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF25D366)),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Icon(imageVector = Icons.Default.Share, contentDescription = null, tint = Color.White)
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = "Envoyer Commande via WhatsApp 💬",
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        fontSize = 15.sp
                    )
                }

                OutlinedButton(
                    onClick = {
                        if (uiState.customerName.isBlank() || uiState.customerPhone.isBlank()) {
                            Toast.makeText(context, "Veuillez renseigner votre nom et téléphone", Toast.LENGTH_LONG).show()
                        } else {
                            viewModel.submitOrder(context, viaWhatsApp = false)
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .testTag("direct_order_button"),
                    shape = RoundedCornerShape(16.dp),
                    border = BorderStroke(1.5.dp, CrepeGold)
                ) {
                    Text(
                        text = "Confirmer Dans L'Application 🥞",
                        fontWeight = FontWeight.Bold,
                        color = CrepeGold
                    )
                }
            }
        }
    }
}

// -------------------------------------------------------------
// SCREEN 6: ORDERS HISTORY SCREEN
// -------------------------------------------------------------
@Composable
fun OrdersScreen(
    viewModel: AppViewModel,
    uiState: AppUiState
) {
    val context = LocalContext.current

    if (uiState.orders.isEmpty()) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp)
                .testTag("empty_orders_view"),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Surface(
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    shape = CircleShape,
                    modifier = Modifier.size(80.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.List,
                            contentDescription = null,
                            tint = CrepeGold,
                            modifier = Modifier.size(36.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Aucune commande passée",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Vos commandes passées apparaîtront ici avec leur statut en temps réel.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = { viewModel.navigateTo(Screen.MENU) },
                    colors = ButtonDefaults.buttonColors(containerColor = CrepeGold)
                ) {
                    Text("Commander Maintenant")
                }
            }
        }
    } else {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .testTag("orders_screen"),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Text(
                    text = "Historique des Commandes",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
            }

            items(uiState.orders, key = { it.id }) { order ->
                OrderCard(
                    order = order,
                    onReorder = { viewModel.reorder(order) },
                    onWhatsAppContact = {
                        OrderUtils.sendOrderViaWhatsApp(
                            context = context,
                            whatsAppNumber = uiState.selectedBranch.whatsAppNumber,
                            order = order
                        )
                    }
                )
            }
        }
    }
}

// -------------------------------------------------------------
// SCREEN 7: BRANCHES / NOS SALONS
// -------------------------------------------------------------
@Composable
fun BranchesScreen(
    viewModel: AppViewModel,
    uiState: AppUiState
) {
    val context = LocalContext.current

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .testTag("branches_screen"),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Column {
                Text(
                    text = "Nos Salons en Algérie 🇩🇿",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Venez déguster nos crêpes chaudes sur place ou commandez à emporter",
                    fontSize = 13.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        items(StoreConfig.BRANCHES) { branch ->
            val isSelected = uiState.selectedBranch.id == branch.id
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (isSelected) MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f) else MaterialTheme.colorScheme.surface
                ),
                border = if (isSelected) BorderStroke(1.5.dp, CrepeGold) else BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = branch.name, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                        if (branch.isMainBranch) {
                            Surface(color = CrepeGold, shape = RoundedCornerShape(6.dp)) {
                                Text(
                                    text = "Principal",
                                    color = Color.White,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "📍 ${branch.address}",
                        fontSize = 13.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = "⏰ Horaires: ${branch.openingHours}",
                        fontSize = 13.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = "📞 Tél: ${branch.phone}",
                        fontSize = 13.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Spacer(modifier = Modifier.height(12.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Button(
                            onClick = { OrderUtils.makeCall(context, branch.phone) },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
                        ) {
                            Icon(imageVector = Icons.Default.Call, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(text = "Appeler", fontSize = 12.sp)
                        }

                        Button(
                            onClick = {
                                viewModel.selectBranch(branch)
                                Toast.makeText(context, "${branch.name} sélectionné", Toast.LENGTH_SHORT).show()
                            },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (isSelected) CrepeGold else MaterialTheme.colorScheme.surfaceVariant,
                                contentColor = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurface
                            )
                        ) {
                            Text(text = if (isSelected) "Sélectionné ✓" else "Choisir", fontSize = 12.sp)
                        }
                    }
                }
            }
        }
    }
}

// -------------------------------------------------------------
// REUSABLE COMPONENTS
// -------------------------------------------------------------

@Composable
fun SectionCard(title: String, content: @Composable () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = title,
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(10.dp))
            content()
        }
    }
}

@Composable
fun CategoryChip(
    category: Category,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .clickable(onClick = onClick)
            .testTag("category_chip_${category.name}"),
        color = if (isSelected) CrepeGold else MaterialTheme.colorScheme.surfaceVariant,
        shape = RoundedCornerShape(20.dp),
        tonalElevation = if (isSelected) 4.dp else 0.dp
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = category.displayName,
                color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurface,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                fontSize = 13.sp
            )
        }
    }
}

@Composable
fun ProductCardHorizontal(
    product: Product,
    isFavorite: Boolean,
    onProductClick: () -> Unit,
    onQuickAdd: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .clickable(onClick = onProductClick)
            .testTag("product_card_${product.id}"),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Product Icon / Graphic Box
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .background(
                        Brush.radialGradient(
                            colors = listOf(
                                Color(0xFFFFE0B8),
                                Color(0xFFD4811F).copy(alpha = 0.35f)
                            )
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = when (product.category) {
                        Category.SWEET -> "🥞"
                        Category.SAVORY -> "🧀"
                        Category.WAFFLES -> "🧇"
                        Category.PANCAKES -> "🥞"
                        Category.DRINKS -> "🥤"
                        else -> "✨"
                    },
                    fontSize = 36.sp
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top
                ) {
                    Text(
                        text = product.name,
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp,
                        color = MaterialTheme.colorScheme.onSurface,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.weight(1f)
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = product.description,
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = OrderUtils.formatDZD(product.price),
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp,
                        color = CrepeGold
                    )

                    Surface(
                        modifier = Modifier
                            .clip(CircleShape)
                            .clickable(onClick = onQuickAdd)
                            .testTag("quick_add_btn_${product.id}"),
                        color = CrepeGold,
                        shape = CircleShape
                    ) {
                        Box(
                            modifier = Modifier.size(32.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = "Ajouter",
                                tint = Color.White,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ToppingSelectChip(
    topping: Topping,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .clickable(onClick = onClick)
            .testTag("topping_chip_${topping.id}"),
        color = if (isSelected) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surfaceVariant,
        border = BorderStroke(
            1.dp,
            if (isSelected) CrepeGold else Color.Transparent
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 7.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (isSelected) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = null,
                    tint = CrepeGold,
                    modifier = Modifier.size(14.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
            }
            Text(
                text = "${topping.name} (+${OrderUtils.formatDZD(topping.price)})",
                fontSize = 12.sp,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                color = if (isSelected) CrepeGold else MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Composable
fun CartItemCard(
    item: CartItem,
    onIncrement: () -> Unit,
    onDecrement: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = item.customName ?: item.product.name,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
                if (item.selectedToppings.isNotEmpty()) {
                    Text(
                        text = item.selectedToppings.joinToString(", ") { it.name },
                        fontSize = 11.sp,
                        color = CrepeGold,
                        maxLines = 2
                    )
                }
                if (item.specialInstructions.isNotBlank()) {
                    Text(
                        text = "Note: ${item.specialInstructions}",
                        fontSize = 11.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = OrderUtils.formatDZD(item.totalPrice),
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = CrepeGold
                )
            }

            // Quantity adjustments
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = onDecrement, modifier = Modifier.size(32.dp)) {
                    Icon(imageVector = Icons.Default.Remove, contentDescription = "Diminuer", modifier = Modifier.size(16.dp))
                }
                Text(
                    text = "${item.quantity}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(horizontal = 4.dp)
                )
                IconButton(onClick = onIncrement, modifier = Modifier.size(32.dp)) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = "Augmenter", modifier = Modifier.size(16.dp))
                }
                IconButton(onClick = onDelete, modifier = Modifier.size(32.dp)) {
                    Icon(
                        imageVector = Icons.Default.DeleteOutline,
                        contentDescription = "Supprimer",
                        tint = MaterialTheme.colorScheme.error,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun OrderCard(
    order: Order,
    onReorder: () -> Unit,
    onWhatsAppContact: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Commande #${order.id}", fontWeight = FontWeight.Bold, fontSize = 15.sp)
                Surface(
                    color = Color(0xFF2E7D32).copy(alpha = 0.15f),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = order.status.label,
                        color = Color(0xFF2E7D32),
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                    )
                }
            }

            Text(
                text = "${OrderUtils.formatDate(order.timestamp)} • ${order.branchName}",
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            HorizontalDivider(modifier = Modifier.padding(vertical = 10.dp))

            order.items.forEach { item ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "${item.quantity}x ${item.customName ?: item.product.name}",
                        fontSize = 13.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = OrderUtils.formatDZD(item.totalPrice),
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

            HorizontalDivider(modifier = Modifier.padding(vertical = 10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Total payé:", fontWeight = FontWeight.Bold)
                Text(
                    text = OrderUtils.formatDZD(order.total),
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = CrepeGold
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedButton(
                    onClick = onReorder,
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Recommander", fontSize = 12.sp)
                }

                Button(
                    onClick = onWhatsAppContact,
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF25D366))
                ) {
                    Text("Suivre sur WhatsApp", fontSize = 12.sp, color = Color.White)
                }
            }
        }
    }
}

// -------------------------------------------------------------
// DIALOGS
// -------------------------------------------------------------

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ProductDetailDialog(
    product: Product,
    isFavorite: Boolean,
    onDismiss: () -> Unit,
    onToggleFavorite: () -> Unit,
    onAddToCart: (quantity: Int, selectedToppings: List<Topping>, notes: String) -> Unit
) {
    var quantity by remember { mutableStateOf(1) }
    var selectedToppings by remember { mutableStateOf<List<Topping>>(emptyList()) }
    var notes by remember { mutableStateOf("") }

    val calculatedPrice = product.price + selectedToppings.sumOf { it.price }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = product.name, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                IconButton(onClick = onToggleFavorite) {
                    Icon(
                        imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = "Favori",
                        tint = if (isFavorite) MaterialTheme.colorScheme.tertiary else MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        },
        text = {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                item {
                    Text(text = product.description, fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
                item {
                    Text(
                        text = "Prix de base: ${OrderUtils.formatDZD(product.price)}",
                        fontWeight = FontWeight.Bold,
                        color = CrepeGold
                    )
                }

                // Add Toppings if sweet/savory
                if (product.category == Category.SWEET || product.category == Category.WAFFLES || product.category == Category.PANCAKES) {
                    item {
                        Text(text = "Suppléments gourmands au choix:", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                        Spacer(modifier = Modifier.height(4.dp))
                        val available = StoreRepository.standardToppings.take(8)
                        FlowRow(
                            horizontalArrangement = Arrangement.spacedBy(6.dp),
                            verticalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            available.forEach { topping ->
                                val isSelected = selectedToppings.any { it.id == topping.id }
                                ToppingSelectChip(
                                    topping = topping,
                                    isSelected = isSelected,
                                    onClick = {
                                        selectedToppings = if (isSelected) {
                                            selectedToppings.filterNot { it.id == topping.id }
                                        } else {
                                            selectedToppings + topping
                                        }
                                    }
                                )
                            }
                        }
                    }
                }

                item {
                    OutlinedTextField(
                        value = notes,
                        onValueChange = { notes = it },
                        placeholder = { Text("Instructions particulières...") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(10.dp)
                    )
                }

                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "Quantité:", fontWeight = FontWeight.SemiBold)
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            IconButton(onClick = { if (quantity > 1) quantity-- }) {
                                Icon(imageVector = Icons.Default.Remove, contentDescription = "Moins")
                            }
                            Text(text = "$quantity", fontWeight = FontWeight.Bold)
                            IconButton(onClick = { quantity++ }) {
                                Icon(imageVector = Icons.Default.Add, contentDescription = "Plus")
                            }
                        }
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = { onAddToCart(quantity, selectedToppings, notes) },
                colors = ButtonDefaults.buttonColors(containerColor = CrepeGold)
            ) {
                Text("Ajouter (${OrderUtils.formatDZD(calculatedPrice * quantity)})")
            }
        },
        dismissButton = {
            OutlinedButton(onClick = onDismiss) {
                Text("Annuler")
            }
        }
    )
}

@Composable
fun OrderSuccessDialog(
    order: Order,
    onDismiss: () -> Unit,
    onSendWhatsApp: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "🎉 Mabrouk ! 🇩🇿", fontSize = 22.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Commande enregistrée !",
                    fontSize = 15.sp,
                    color = Color(0xFF2E7D32),
                    fontWeight = FontWeight.SemiBold
                )
            }
        },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "N° de Commande: #${order.id}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Total à régler: ${OrderUtils.formatDZD(order.total)}",
                    fontWeight = FontWeight.Bold,
                    color = CrepeGold,
                    fontSize = 17.sp
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Notre équipe commence immédiatement la préparation de vos crêpes.",
                    fontSize = 13.sp,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        },
        confirmButton = {
            Button(
                onClick = onSendWhatsApp,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF25D366))
            ) {
                Text("Ouvrir sur WhatsApp 💬", color = Color.White)
            }
        },
        dismissButton = {
            OutlinedButton(onClick = onDismiss) {
                Text("Voir mes commandes")
            }
        }
    )
}
