package com.example

data class StoreBranch(
    val id: String,
    val name: String,
    val city: String,
    val address: String,
    val phone: String,
    val whatsAppNumber: String,
    val openingHours: String,
    val isMainBranch: Boolean = false
)

data class WilayaDelivery(
    val code: String,
    val name: String,
    val deliveryFee: Double,
    val communes: List<String>
)

object StoreConfig {
    const val APP_NAME = "Crêpe Algérie"
    const val SLOGAN = "L'Art de la Crêpe Gourmande en Algérie 🇩🇿"
    const val CURRENCY = "DA"
    const val DEFAULT_PHONE = "+213555123456"
    const val DEFAULT_WHATSAPP = "213555123456"
    const val INSTAGRAM_HANDLE = "@crepe.algerie.dz"
    const val MIN_ORDER_AMOUNT = 500.0 // 500 DA minimum for delivery

    val BRANCHES = listOf(
        StoreBranch(
            id = "alger_centre",
            name = "Crêpe Algérie - Alger Centre",
            city = "Alger",
            address = "42 Rue Didouche Mourad, Alger Centre",
            phone = "+213 555 12 34 56",
            whatsAppNumber = "213555123456",
            openingHours = "10:30 - 00:30 (7j/7)",
            isMainBranch = true
        ),
        StoreBranch(
            id = "hydra",
            name = "Crêpe Algérie - Hydra",
            city = "Alger",
            address = "Boulevard Sidi Yahia, Hydra, Alger",
            phone = "+213 550 98 76 54",
            whatsAppNumber = "213550987654",
            openingHours = "11:00 - 01:00 (7j/7)"
        ),
        StoreBranch(
            id = "bab_ezzouar",
            name = "Crêpe Algérie - Bab Ezzouar",
            city = "Alger",
            address = "Centre Commercial Bab Ezzouar, Niveau 2",
            phone = "+213 560 11 22 33",
            whatsAppNumber = "213560112233",
            openingHours = "10:00 - 23:00 (7j/7)"
        ),
        StoreBranch(
            id = "oran_akid",
            name = "Crêpe Algérie - Oran",
            city = "Oran",
            address = "Boulevard Akid Lotfi, Oran",
            phone = "+213 540 44 55 66",
            whatsAppNumber = "213540445566",
            openingHours = "12:00 - 01:00 (7j/7)"
        )
    )

    val WILAYAS = listOf(
        WilayaDelivery(
            code = "16",
            name = "16 - Alger",
            deliveryFee = 250.0,
            communes = listOf(
                "Alger Centre", "Sidi M'Hamed", "El Madania", "Belouizdad",
                "Bab El Oued", "Kouba", "Hydra", "Bir Mourad Raïs", "El Biar",
                "Bouzareah", "Bab Ezzouar", "Dar El Beïda", "Bordj El Kiffan",
                "Mohammadia", "Dely Ibrahim", "Cheraga", "Ain Benian", "Zeralda"
            )
        ),
        WilayaDelivery(
            code = "09",
            name = "09 - Blida",
            deliveryFee = 400.0,
            communes = listOf("Blida Centre", "Ouled Yaïch", "Boufarik", "Beni Mered", "Soumaa")
        ),
        WilayaDelivery(
            code = "35",
            name = "35 - Boumerdès",
            deliveryFee = 450.0,
            communes = listOf("Boumerdès Centre", "Corso", "Boudouaou", "Zemmouri", "Thénia")
        ),
        WilayaDelivery(
            code = "42",
            name = "42 - Tipaza",
            deliveryFee = 450.0,
            communes = listOf("Tipaza Ville", "Kolea", "Douaouda", "Fouka", "Bou Ismail")
        ),
        WilayaDelivery(
            code = "31",
            name = "31 - Oran",
            deliveryFee = 300.0,
            communes = listOf("Oran Centre", "Akid Lotfi", "Bir El Djir", "Es Senia", "Ain El Turk")
        )
    )
}
