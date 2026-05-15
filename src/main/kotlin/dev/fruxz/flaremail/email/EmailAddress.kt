package dev.fruxz.flaremail.email

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

@JvmInline
@Serializable
value class EmailAddress(val address: String) {

    init {
        require(address.isNotBlank()) { "Email address cannot be blank" }
        require(address.contains('@')) { "Email address must contain '@'" }
        require(address.matches(EMAIL_REGEX.toRegex())) { "Email address is not valid" }
    }

    object Serializer : KSerializer<EmailAddress> {
        override val descriptor = String.serializer().descriptor

        override fun serialize(
            encoder: Encoder,
            value: EmailAddress
        ) = encoder.encodeString(value.address)

        override fun deserialize(decoder: Decoder) = EmailAddress(decoder.decodeString())

    }

    companion object {
        const val EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"
    }

}
