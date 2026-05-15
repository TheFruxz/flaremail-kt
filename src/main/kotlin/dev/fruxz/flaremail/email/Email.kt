package dev.fruxz.flaremail

import kotlinx.html.HTML
import kotlinx.html.html
import kotlinx.html.stream.createHTML
import kotlinx.serialization.Serializable
import org.intellij.lang.annotations.Language

@Serializable
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

    sealed interface Body {
        data class Text(val text: String) : Body
        data class Html(val html: String) : Body

        companion object {

            fun text(text: String) = Text(text)
            fun htmlRaw(@Language("html") html: String) = Html(html)
            fun htmlDsl(builder: HTML.() -> Unit) = Html(createHTML().html(block = builder))

        }

    }

}