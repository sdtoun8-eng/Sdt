package com.example.ui

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.StoreBranch
import com.example.StoreConfig
import com.example.WilayaDelivery
import com.example.data.AppPreferences
import com.example.data.StoreRepository
import com.example.model.CartItem
import com.example.model.Category
import com.example.model.DeliveryType
import com.example.model.Order
import com.example.model.OrderStatus
import com.example.model.Product
import com.example.model.Topping
import com.example.model.ToppingType
import com.example.navigation.Screen
import com.example.utils.OrderUtils
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID

data class AppUiState(
    val currentScreen: Screen = Screen.HOME,
    val navigationStack: List<Screen> = listOf(Screen.HOME),
    val selectedCategory: Category = Category.ALL,
    val searchQuery: String = "",
    val cartItems: List<CartItem> = emptyList(),
    val orders: List<Order> = emptyList(),
    val favoriteIds: Set<String> = emptySet(),
    val selectedBranch: StoreBranch = StoreConfig.BRANCHES.first(),
    val selectedWilaya: WilayaDelivery = StoreConfig.WILAYAS.first(),
    val selectedCommune: String = StoreConfig.WILAYAS.first().communes.first(),
    val customerName: String = "",
    val customerPhone: String = "",
    val customerAddress: String = "",
    val deliveryType: DeliveryType = DeliveryType.DELIVERY,
    val orderNotes: String = "",
    val lastSubmittedOrder: Order? = null,
    val showOrderSuccessDialog: Boolean = false,

    // Custom Crepe Builder
    val customBase: Topping = StoreRepository.customBases.first(),
    val selectedCustomToppings: List<Topping> = listOf(
        StoreRepository.standardToppings.first { it.id == "top_nutella" },
        StoreRepository.standardToppings.first { it.id == "top_banane" }
    ),
    val customNotes: String = ""
) {
    val cartCount: Int
        get() = cartItems.sumOf { it.quantity }

    val cartSubtotal: Double
        get() = cartItems.sumOf { it.totalPrice }

    val deliveryFee: Double
        get() = if (deliveryType == DeliveryType.DELIVERY && cartItems.isNotEmpty()) selectedWilaya.deliveryFee else 0.0

    val cartTotal: Double
        get() = cartSubtotal + deliveryFee

    val customCrepePrice: Double
        get() = customBase.price + selectedCustomToppings.sumOf { it.price }
}

class AppViewModel(application: Application) : AndroidViewModel(application) {

    private val prefs = AppPreferences(application)
    private val _uiState = MutableStateFlow(AppUiState())
    val uiState: StateFlow<AppUiState> = _uiState.asStateFlow()

    init {
        // Load initial persisted values
        val savedWilaya = StoreConfig.WILAYAS.find { it.name == prefs.customerWilaya }
            ?: StoreConfig.WILAYAS.first()
        val savedBranch = StoreConfig.BRANCHES.find { it.id == prefs.selectedBranchId }
            ?: StoreConfig.BRANCHES.first()

        _uiState.update { current ->
            current.copy(
                customerName = prefs.customerName,
                customerPhone = prefs.customerPhone,
                customerAddress = prefs.customerAddress,
                selectedWilaya = savedWilaya,
                selectedCommune = if (savedWilaya.communes.contains(prefs.customerCommune)) {
                    prefs.customerCommune
                } else savedWilaya.communes.first(),
                selectedBranch = savedBranch,
                favoriteIds = prefs.getFavoriteIds()
            )
        }
    }

    fun navigateTo(screen: Screen) {
        _uiState.update { current ->
            if (current.currentScreen == screen) current
            else {
                val newStack = current.navigationStack + screen
                current.copy(currentScreen = screen, navigationStack = newStack)
            }
        }
    }

    fun navigateBack(): Boolean {
        val currentStack = _uiState.value.navigationStack
        return if (currentStack.size > 1) {
            val updatedStack = currentStack.dropLast(1)
            val previousScreen = updatedStack.last()
            _uiState.update {
                it.copy(currentScreen = previousScreen, navigationStack = updatedStack)
            }
            true
        } else {
            false
        }
    }

    fun selectCategory(category: Category) {
        _uiState.update { it.copy(selectedCategory = category) }
    }

    fun setSearchQuery(query: String) {
        _uiState.update { it.copy(searchQuery = query) }
    }

    fun addToCart(
        product: Product,
        quantity: Int = 1,
        selectedToppings: List<Topping> = emptyList(),
        notes: String = "",
        customName: String? = null
    ) {
        _uiState.update { current ->
            val newItem = CartItem(
                cartId = UUID.randomUUID().toString(),
                product = product,
                quantity = quantity,
                selectedToppings = selectedToppings,
                specialInstructions = notes,
                customName = customName
            )
            current.copy(cartItems = current.cartItems + newItem)
        }
    }

    fun updateCartQuantity(cartId: String, quantity: Int) {
        _uiState.update { current ->
            if (quantity <= 0) {
                current.copy(cartItems = current.cartItems.filterNot { it.cartId == cartId })
            } else {
                val updated = current.cartItems.map {
                    if (it.cartId == cartId) it.copy(quantity = quantity) else it
                }
                current.copy(cartItems = updated)
            }
        }
    }

    fun removeFromCart(cartId: String) {
        _uiState.update { current ->
            current.copy(cartItems = current.cartItems.filterNot { it.cartId == cartId })
        }
    }

    fun clearCart() {
        _uiState.update { it.copy(cartItems = emptyList()) }
    }

    fun toggleFavorite(productId: String) {
        prefs.toggleFavorite(productId)
        _uiState.update { it.copy(favoriteIds = prefs.getFavoriteIds()) }
    }

    // Custom Crepe Builder Methods
    fun selectCustomBase(base: Topping) {
        _uiState.update { it.copy(customBase = base) }
    }

    fun toggleCustomTopping(topping: Topping) {
        _uiState.update { current ->
            val exists = current.selectedCustomToppings.any { it.id == topping.id }
            val updated = if (exists) {
                current.selectedCustomToppings.filterNot { it.id == topping.id }
            } else {
                current.selectedCustomToppings + topping
            }
            current.copy(selectedCustomToppings = updated)
        }
    }

    fun setCustomNotes(notes: String) {
        _uiState.update { it.copy(customNotes = notes) }
    }

    fun addCustomCrepeToCart() {
        val state = _uiState.value
        val customProduct = Product(
            id = "custom_${System.currentTimeMillis()}",
            name = "Crêpe Personnalisée Gourmande",
            description = "Création sur-mesure (${state.customBase.name})",
            price = state.customBase.price,
            category = Category.CUSTOM
        )
        addToCart(
            product = customProduct,
            quantity = 1,
            selectedToppings = state.selectedCustomToppings,
            notes = state.customNotes,
            customName = "Crêpe Sur-Mesure (${state.customBase.name})"
        )
        navigateTo(Screen.CART)
    }

    // Checkout Details
    fun updateCustomerInfo(
        name: String = _uiState.value.customerName,
        phone: String = _uiState.value.customerPhone,
        address: String = _uiState.value.customerAddress,
        notes: String = _uiState.value.orderNotes
    ) {
        prefs.customerName = name
        prefs.customerPhone = phone
        prefs.customerAddress = address

        _uiState.update {
            it.copy(
                customerName = name,
                customerPhone = phone,
                customerAddress = address,
                orderNotes = notes
            )
        }
    }

    fun setDeliveryType(type: DeliveryType) {
        _uiState.update { it.copy(deliveryType = type) }
    }

    fun selectWilaya(wilaya: WilayaDelivery) {
        prefs.customerWilaya = wilaya.name
        _uiState.update {
            it.copy(
                selectedWilaya = wilaya,
                selectedCommune = wilaya.communes.first()
            )
        }
    }

    fun selectCommune(commune: String) {
        prefs.customerCommune = commune
        _uiState.update { it.copy(selectedCommune = commune) }
    }

    fun selectBranch(branch: StoreBranch) {
        prefs.selectedBranchId = branch.id
        _uiState.update { it.copy(selectedBranch = branch) }
    }

    fun submitOrder(context: Context, viaWhatsApp: Boolean = true) {
        val state = _uiState.value
        if (state.cartItems.isEmpty()) return

        val order = Order(
            id = "DZ-" + (10000..99999).random(),
            items = state.cartItems,
            customerName = state.customerName.ifBlank { "Client Crêpe Algérie" },
            customerPhone = state.customerPhone.ifBlank { "Non renseigné" },
            deliveryType = state.deliveryType,
            branchName = state.selectedBranch.name,
            wilaya = state.selectedWilaya.name,
            commune = state.selectedCommune,
            streetAddress = state.customerAddress,
            deliveryFee = state.deliveryFee,
            subtotal = state.cartSubtotal,
            total = state.cartTotal,
            notes = state.orderNotes,
            status = OrderStatus.CONFIRMED
        )

        // Save order to history
        _uiState.update { current ->
            current.copy(
                orders = listOf(order) + current.orders,
                cartItems = emptyList(),
                lastSubmittedOrder = order,
                showOrderSuccessDialog = true
            )
        }

        if (viaWhatsApp) {
            OrderUtils.sendOrderViaWhatsApp(
                context = context,
                whatsAppNumber = state.selectedBranch.whatsAppNumber,
                order = order
            )
        }
    }

    fun dismissSuccessDialog() {
        _uiState.update { it.copy(showOrderSuccessDialog = false) }
        navigateTo(Screen.ORDERS)
    }

    fun reorder(order: Order) {
        _uiState.update { current ->
            current.copy(cartItems = order.items)
        }
        navigateTo(Screen.CART)
    }
}
