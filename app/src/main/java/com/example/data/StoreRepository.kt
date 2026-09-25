package com.example.data

import com.example.model.Category
import com.example.model.Product
import com.example.model.Topping
import com.example.model.ToppingType

object StoreRepository {

    // Common toppings for customization
    val standardToppings = listOf(
        // Sauces & Nappages
        Topping("top_nutella", "Nutella Fondant", 150.0, ToppingType.SPREAD),
        Topping("top_lotus", "Pâte de Spéculoos Lotus", 180.0, ToppingType.SPREAD),
        Topping("top_pistache", "Crème de Pistache Pure", 250.0, ToppingType.SPREAD),
        Topping("top_choc_blanc", "Chocolat Blanc Belge", 140.0, ToppingType.SPREAD),
        Topping("top_caramel", "Caramel Beurre Salé Maison", 120.0, ToppingType.SPREAD),
        Topping("top_miel", "Miel Pur d'Algérie", 150.0, ToppingType.SPREAD),
        Topping("top_sauce_fromage", "Sauce Fromagère Onctueuse", 120.0, ToppingType.SPREAD),
        Topping("top_harissa", "Harissa Traditionnelle Algérienne", 50.0, ToppingType.SPREAD),

        // Fruits Frais
        Topping("top_banane", "Bananes Fraîches Tranchées", 100.0, ToppingType.FRUIT),
        Topping("top_fraise", "Fraises Fraîches Sucrées", 150.0, ToppingType.FRUIT),
        Topping("top_kiwi", "Morceaux de Kiwi Frais", 120.0, ToppingType.FRUIT),
        Topping("top_framboise", "Framboises Entières", 200.0, ToppingType.FRUIT),

        // Crunch & Biscuits
        Topping("top_kinder", "Morceaux de Kinder Bueno", 180.0, ToppingType.CRUNCH),
        Topping("top_oreo", "Brisures d'Oreo Croustillantes", 120.0, ToppingType.CRUNCH),
        Topping("top_amandes", "Amandes Effilées Grillées", 120.0, ToppingType.CRUNCH),
        Topping("top_noisettes", "Noisettes Concassées", 140.0, ToppingType.CRUNCH),
        Topping("top_pistaches_nuts", "Pistaches Concassées d'Iran", 220.0, ToppingType.CRUNCH),
        Topping("top_lotus_crumb", "Brisures de Biscuits Lotus", 120.0, ToppingType.CRUNCH),

        // Extras
        Topping("top_chantilly", "Crème Chantilly Aérienne", 100.0, ToppingType.EXTRA),
        Topping("top_glace_vanille", "Boule de Glace Vanille Bourbon", 150.0, ToppingType.EXTRA),
        Topping("top_fromage_extra", "Extra Fromage Fondu Emmental", 150.0, ToppingType.EXTRA),
        Topping("top_viande_extra", "Supplément Viande Hachée", 200.0, ToppingType.EXTRA)
    )

    // Base options for custom crêpe builder
    val customBases = listOf(
        Topping("base_sucree", "Pâte à Crêpe Dorée Traditionnelle", 250.0, ToppingType.BASE, isSelectedByDefault = true),
        Topping("base_sarrazin", "Galette Bretonne au Sarrasin (Salée)", 280.0, ToppingType.BASE),
        Topping("base_gaufre", "Gaufre Croustillante et Moelleuse", 300.0, ToppingType.BASE),
        Topping("base_pancake", "Assiette 3 Fluffy Pancakes", 320.0, ToppingType.BASE)
    )

    val products = listOf(
        // Crêpes Sucrées
        Product(
            id = "cr_nutella_royale",
            name = "Crêpe Nutella Royale",
            description = "Généreusement nappée de Nutella, tranches de bananes et fraises fraîches, amandes grillées effilées.",
            price = 650.0,
            category = Category.SWEET,
            imageResName = "crepe_hero_banner",
            isPopular = true,
            isChefSpecial = true,
            prepTimeMin = 10
        ),
        Product(
            id = "cr_pistache_miel",
            name = "Crêpe Pistache & Miel d'Algérie",
            description = "Crème de pistache artisanale onctueuse, miel pur algérien, pistaches croquantes d'Iran concassées.",
            price = 850.0,
            category = Category.SWEET,
            isPopular = true,
            isChefSpecial = true,
            prepTimeMin = 12
        ),
        Product(
            id = "cr_bueno_oreo",
            name = "Crêpe Bueno & Oreo Crunch",
            description = "Chocolat au lait fondant, barre de Kinder Bueno, crumble de biscuits Oreo noirs et filet de chocolat blanc.",
            price = 750.0,
            category = Category.SWEET,
            isPopular = true,
            prepTimeMin = 10
        ),
        Product(
            id = "cr_lotus_speculoos",
            name = "Crêpe Spéculoos Lotus",
            description = "Pâte à tartiner Spéculoos originale, brisures dorées de biscuits Lotus et dôme de crème chantilly maison.",
            price = 700.0,
            category = Category.SWEET,
            isPopular = true,
            prepTimeMin = 10
        ),
        Product(
            id = "cr_caramel_sale",
            name = "Crêpe Caramel Beurre Salé",
            description = "Caramel breton au beurre salé fait maison, éclats de noisettes caramélisées et boule de glace vanille.",
            price = 600.0,
            category = Category.SWEET,
            prepTimeMin = 10
        ),
        Product(
            id = "cr_fruits_rouges",
            name = "Crêpe Chocolat Blanc & Fruits Rouges",
            description = "Chocolat blanc belge crémeux, fraises, framboises fraîches et coulis acidulé.",
            price = 750.0,
            category = Category.SWEET,
            prepTimeMin = 10
        ),

        // Crêpes Salées
        Product(
            id = "cr_algeroise",
            name = "Crêpe L'Algéroise Épicée",
            description = "Viande hachée 100% pur bœuf assaisonnée aux épices locales, oignons caramélisés, fromage râpé et touche de harissa.",
            price = 750.0,
            category = Category.SAVORY,
            isPopular = true,
            isChefSpecial = true,
            prepTimeMin = 12
        ),
        Product(
            id = "cr_poulet_champignon",
            name = "Crêpe Poulet & Béchamel",
            description = "Émincé de poulet fermier mariné, champignons de Paris frais dorés, sauce béchamel onctueuse et emmental fondu.",
            price = 700.0,
            category = Category.SAVORY,
            isPopular = true,
            prepTimeMin = 12
        ),
        Product(
            id = "cr_4_fromages",
            name = "Crêpe 4 Fromages Suprême",
            description = "Mélange gourmand de Mozzarella fondante, Cheddar affiné, Camembert de caractère et sauce fromagère maison.",
            price = 650.0,
            category = Category.SAVORY,
            prepTimeMin = 10
        ),
        Product(
            id = "cr_saumon_aneth",
            name = "Crêpe Saumon Fumé & Crème Citronnée",
            description = "Tranches nobles de saumon fumé, crème fraîche légère parfumée à l'aneth et zeste de citron frais.",
            price = 950.0,
            category = Category.SAVORY,
            isChefSpecial = true,
            prepTimeMin = 12
        ),
        Product(
            id = "cr_thon_oeuf",
            name = "Crêpe Thon, Œuf & Olives",
            description = "Thon à l'huile d'olive, œuf coulant au centre, olives noires tranchées et sauce gruyère.",
            price = 600.0,
            category = Category.SAVORY,
            prepTimeMin = 10
        ),

        // Gaufres
        Product(
            id = "gf_liegeoise_royale",
            name = "Gaufre Liégeoise Fraise & Choco",
            description = "Gaufre belge caramélisée au sucre perlé, fraises fraîches parfumées et filet généreux de chocolat noir intense.",
            price = 550.0,
            category = Category.WAFFLES,
            isPopular = true,
            prepTimeMin = 8
        ),
        Product(
            id = "gf_bubble_waffle",
            name = "Bubble Waffle XXL Gourmande",
            description = "Gaufre alvéolée garnie de Nutella, 2 boules de glace au choix, M&M's, chantilly et coulis de fraise.",
            price = 850.0,
            category = Category.WAFFLES,
            isChefSpecial = true,
            prepTimeMin = 12
        ),
        Product(
            id = "gf_pistache_lotus",
            name = "Gaufre Duo Pistache & Spéculoos",
            description = "Moitié crème de pistache et noisettes, moitié pâte Lotus et spéculoos concassés.",
            price = 750.0,
            category = Category.WAFFLES,
            prepTimeMin = 10
        ),

        // Pancakes
        Product(
            id = "pc_box_mini",
            name = "Box 16 Mini Pancakes aux 3 Chocolats",
            description = "Plateau de 16 mini pancakes ultra moelleux avec sauces Nutella, Chocolat Blanc et Pistache servies en pipettes.",
            price = 950.0,
            category = Category.PANCAKES,
            isPopular = true,
            isChefSpecial = true,
            prepTimeMin = 12
        ),
        Product(
            id = "pc_stack_fruits",
            name = "Tower 4 Pancakes & Sirop d'Érable",
            description = "Pancakes épais américains servis chauds, beurre fermier, fruits frais de saison et sirop d'érable authentique.",
            price = 650.0,
            category = Category.PANCAKES,
            prepTimeMin = 10
        ),

        // Boissons & Desserts
        Product(
            id = "dr_shake_nutella",
            name = "Milkshake Nutella Oreo Géant (500ml)",
            description = "Glace artisanale vanille, Nutella, lait frais entier, biscuits Oreo mixés et montagne de chantilly.",
            price = 550.0,
            category = Category.DRINKS,
            isPopular = true,
            prepTimeMin = 5
        ),
        Product(
            id = "dr_shake_pistache",
            name = "Milkshake Pistache Gourmand (500ml)",
            description = "Glace pistache premium, crème de pistache, lait velouté et éclats croquants de pistache verte.",
            price = 600.0,
            category = Category.DRINKS,
            prepTimeMin = 5
        ),
        Product(
            id = "dr_mojito_fraise",
            name = "Mojito Fraise & Menthe Fraîche",
            description = "Purée de fraises fraîches, feuilles de menthe pilées, quartier de citron vert et eau pétillante fraîche.",
            price = 450.0,
            category = Category.DRINKS,
            prepTimeMin = 5
        ),
        Product(
            id = "dr_jus_orange",
            name = "Jus d'Orange Pressé Minute",
            description = "Oranges d'Algérie fraîchement pressées sous vos yeux, 100% pur jus naturel sans sucre ajouté.",
            price = 350.0,
            category = Category.DRINKS,
            prepTimeMin = 5
        )
    )

    fun getProductById(id: String): Product? = products.find { it.id == id }

    fun getProductsByCategory(category: Category): List<Product> {
        return if (category == Category.ALL) products else products.filter { it.category == category }
    }

    fun searchProducts(query: String, category: Category = Category.ALL): List<Product> {
        val filtered = getProductsByCategory(category)
        if (query.isBlank()) return filtered
        val lower = query.lowercase().trim()
        return filtered.filter {
            it.name.lowercase().contains(lower) || it.description.lowercase().contains(lower)
        }
    }
}
