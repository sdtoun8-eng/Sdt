package com.example.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import com.example.model.DeliveryType
import com.example.model.Order
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object OrderUtils {

    fun formatDZD(amount: Double): String {
        val format = NumberFormat.getNumberInstance(Locale.FRANCE)
        format.maximumFractionDigits = 0
        return "${format.format(amount)} DA"
    }

    fun formatDate(timestamp: Long): String {
        val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.FRANCE)
        return sdf.format(Date(timestamp))
    }

    fun buildWhatsAppOrderText(order: Order): String {
        val sb = StringBuilder()
        sb.append("🥞 *NOUVELLE COMMANDE - CRÊPE ALGÉRIE* 🇩🇿\n")
        sb.append("━━━━━━━━━━━━━━━━━━━━\n")
        sb.append("📋 *Commande #:* ${order.id}\n")
        sb.append("📅 *Date:* ${formatDate(order.timestamp)}\n")
        sb.append("📍 *Succursale:* ${order.branchName}\n")
        sb.append("━━━━━━━━━━━━━━━━━━━━\n\n")

        sb.append("👤 *CLIENT:*\n")
        sb.append("• Nom: ${order.customerName}\n")
        sb.append("• Tél: ${order.customerPhone}\n")
        sb.append("• Mode: ${order.deliveryType.label}\n")

        if (order.deliveryType == DeliveryType.DELIVERY) {
            sb.append("• Wilaya: ${order.wilaya}\n")
            sb.append("• Commune: ${order.commune}\n")
            sb.append("• Adresse: ${order.streetAddress}\n")
        }
        sb.append("\n🍽️ *DÉTAIL DES ARTICLES:*\n")

        order.items.forEachIndexed { index, item ->
            val name = item.customName ?: item.product.name
            sb.append("${index + 1}. *${item.quantity}x $name* (${formatDZD(item.totalPrice)})\n")
            if (item.selectedToppings.isNotEmpty()) {
                val toppingsStr = item.selectedToppings.joinToString(", ") { "${it.name} (+${formatDZD(it.price)})" }
                sb.append("   ↳ _Suppléments:_ $toppingsStr\n")
            }
            if (item.specialInstructions.isNotBlank()) {
                sb.append("   ↳ _Note:_ ${item.specialInstructions}\n")
            }
        }

        sb.append("\n━━━━━━━━━━━━━━━━━━━━\n")
        sb.append("🧾 *Sous-total:* ${formatDZD(order.subtotal)}\n")
        if (order.deliveryType == DeliveryType.DELIVERY) {
            sb.append("🛵 *Frais de livraison:* ${formatDZD(order.deliveryFee)}\n")
        }
        sb.append("💰 *TOTAL À PAYER:* *${formatDZD(order.total)}*\n")
        if (order.notes.isNotBlank()) {
            sb.append("📝 *Remarques:* ${order.notes}\n")
        }
        sb.append("━━━━━━━━━━━━━━━━━━━━\n")
        sb.append("✨ _Merci pour votre fidélité chez Crêpe Algérie !_ 🥞")

        return sb.toString()
    }

    fun sendOrderViaWhatsApp(context: Context, whatsAppNumber: String, order: Order) {
        val message = buildWhatsAppOrderText(order)
        try {
            val encodedMessage = URLEncoder.encode(message, StandardCharsets.UTF_8.toString())
            // Clean phone number (e.g. 213555123456)
            val cleanPhone = whatsAppNumber.replace("+", "").replace(" ", "")
            val url = "https://api.whatsapp.com/send?phone=$cleanPhone&text=$encodedMessage"
            val intent = Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse(url)
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            context.startActivity(intent)
        } catch (e: Exception) {
            // Fallback to generic share intent
            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, message)
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            context.startActivity(Intent.createChooser(shareIntent, "Envoyer la commande via"))
        }
    }

    fun makeCall(context: Context, phoneNumber: String) {
        try {
            val intent = Intent(Intent.ACTION_DIAL).apply {
                data = Uri.parse("tel:$phoneNumber")
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            context.startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(context, "Impossible de composer le numéro", Toast.LENGTH_SHORT).show()
        }
    }

    fun shareOrderText(context: Context, order: Order) {
        val text = buildWhatsAppOrderText(order)
        val sendIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, text)
            type = "text/plain"
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }
        val shareIntent = Intent.createChooser(sendIntent, "Partager la commande")
        context.startActivity(shareIntent)
    }
}
