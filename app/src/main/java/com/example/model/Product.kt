package com.example.model

enum class Category(val displayName: String, val iconName: String) {
    ALL("Tout", "restaurant"),
    SWEET("Sucrées", "cake"),
    SAVORY("Salées", "lunch_dining"),
    WAFFLES("Gaufres", "breakfast_dining"),
    PANCAKES("Pancakes", "bakery_dining"),
    DRINKS("Boissons", "local_bar"),
    CUSTOM("Personnalisé", "tune")
}

enum class ToppingType(val label: String) {
    BASE("Pâte"),
    SPREAD("Nappage & Sauce"),
    FRUIT("Fruits Frais"),
    CRUNCH("Crunch & Biscuits"),
    EXTRA("Extras Gourmands")
}

data class Topping(
    val id: String,
    val name: String,
    val price: Double,
    val type: ToppingType,
    val isSelectedByDefault: Boolean = false
)

data class Product(
    val id: String,
    val name: String,
    val description: String,
    val price: Double, // in Algerian Dinars (DA)
    val category: Category,
    val imageResName: String? = null,
    val isPopular: Boolean = false,
    val isChefSpecial: Boolean = false,
    val availableToppings: List<Topping> = emptyList(),
    val prepTimeMin: Int = 10
)

data class CartItem(
    val cartId: String,
    val product: Product,
    val quantity: Int = 1,
    val selectedToppings: List<Topping> = emptyList(),
    val specialInstructions: String = "",
    val customName: String? = null
) {
    val unitPrice: Double
        get() = product.price + selectedToppings.sumOf { it.price }

    val totalPrice: Double
        get() = unitPrice * quantity
}

enum class DeliveryType(val label: String) {
    DELIVERY("Livraison à Domicile"),
    PICKUP("À Emporter / Sur Place")
}

enum class OrderStatus(val label: String, val step: Int) {
    PENDING("En attente de confirmation", 1),
    CONFIRMED("Confirmée", 2),
    PREPARING("En préparation en cuisine", 3),
    OUT_FOR_DELIVERY("En cours de livraison", 4),
    DELIVERED("Livrée / Récupérée", 5)
}

data class Order(
    val id: String,
    val items: List<CartItem>,
    val customerName: String,
    val customerPhone: String,
    val deliveryType: DeliveryType,
    val branchName: String,
    val wilaya: String,
    val commune: String,
    val streetAddress: String,
    val deliveryFee: Double,
    val subtotal: Double,
    val total: Double,
    val notes: String = "",
    val timestamp: Long = System.currentTimeMillis(),
    val status: OrderStatus = OrderStatus.CONFIRMED
)
