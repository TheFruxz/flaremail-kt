package dev.fruxz.flaremail.email

import kotlinx.html.HTML
import kotlinx.html.html
import kotlinx.html.stream.createHTML
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonEncoder
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.buildJsonArray
import kotlinx.serialization.json.buildJsonObject
import org.intellij.lang.annotations.Language

@Serializable(with = Email.Serializer::class)
data class Email(
    val from: Sender,
    val to: Recipient,
    val subject: String,
    val body: Body,
    val attachments: List<Nothing> = emptyList(), // TODO
    val bbc: Recipient? = null,
    val cc: Recipient? = null,
    val replyTo: Recipient? = null,
    val headers: Map<String, String> = emptyMap()
) {

    @Serializable sealed interface Sender
    @Serializable sealed interface Recipient
    @Serializable sealed interface Contact {
        val address: EmailAddress

        @Serializable data class NamedContact(val name: String, override val address: EmailAddress) : Contact, Sender
        @Serializable data class UnnamedContact(override val address: EmailAddress) : Contact, Sender, Recipient
        @Serializable data class UnnamedContacts(val addresses: List<EmailAddress>) : List<EmailAddress> by addresses, Recipient

    }

    @Serializable
    sealed interface Body {
        @Serializable data class Text(val text: String) : Body
        @Serializable data class Html(val html: String) : Body

        companion object {

            fun text(text: String) = Text(text)
            fun htmlRaw(@Language("html") html: String) = Html(html)
            fun htmlDsl(builder: HTML.() -> Unit) = Html(createHTML().html(block = builder))

        }

    }

    companion object {
        fun build() = EmailBuilder()
        fun build(builder: EmailBuilder.() -> Unit): Email = EmailBuilder().apply(builder).build()
    }

    object Serializer: KSerializer<Email> {
        override val descriptor: SerialDescriptor = buildClassSerialDescriptor("Email")

        override fun serialize(encoder: Encoder, value: Email) {
            val jsonEncoder = encoder as? JsonEncoder
                ?: throw SerializationException("Email.Serializer can only be used with JSON")

            val payload = buildJsonObject {
                put("from", value.from.toJsonFrom())
                put("subject", JsonPrimitive(value.subject))
                put("to", value.to.toJsonRecipient())

                when (val currentBody = value.body) {
                    is Body.Text -> put("text", JsonPrimitive(currentBody.text))
                    is Body.Html -> put("html", JsonPrimitive(currentBody.html))
                }

                // Cloudflare expects `bcc`; model currently exposes it as `bbc`.
                value.bbc?.let { put("bcc", it.toJsonRecipient()) }
                value.cc?.let { put("cc", it.toJsonRecipient()) }
                value.replyTo?.let { put("reply_to", it.toJsonReplyTo()) }

                if (value.headers.isNotEmpty()) {
                    put(
                        "headers",
                        JsonObject(value.headers.mapValues { (_, headerValue) -> JsonPrimitive(headerValue) })
                    )
                }

                if (value.attachments.isNotEmpty()) {
                    // Attachments are modeled as `List<Nothing>` for now, so only an empty JSON array can be emitted.
                    put("attachments", JsonArray(emptyList()))
                }
            }

            jsonEncoder.encodeJsonElement(payload)
        }

        override fun deserialize(decoder: Decoder): Email =
            throw SerializationException("Email deserialization is not supported")

        private fun Sender.toJsonFrom(): JsonElement = when (this) {
            is Contact.NamedContact -> buildJsonObject {
                put("address", JsonPrimitive(address.address))
                put("name", JsonPrimitive(name))
            }
            is Contact.UnnamedContact -> JsonPrimitive(address.address)
        }

        private fun Recipient.toJsonRecipient(): JsonElement = when (this) {
            is Contact.UnnamedContact -> JsonPrimitive(address.address)
            is Contact.UnnamedContacts -> buildJsonArray {
                addresses.forEach { contactAddress -> add(JsonPrimitive(contactAddress.address)) }
            }
        }

        private fun Recipient.toJsonReplyTo(): JsonElement = when (this) {
            is Contact.UnnamedContact -> JsonPrimitive(address.address)
            is Contact.UnnamedContacts -> {
                if (addresses.size != 1) {
                    throw SerializationException("replyTo must contain exactly one email address")
                }
                JsonPrimitive(addresses.first().address)
            }
        }
    }

}