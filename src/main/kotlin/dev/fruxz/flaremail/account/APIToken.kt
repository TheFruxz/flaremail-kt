package dev.fruxz.flaremail.account

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.serializer


@JvmInline
@Serializable(with = APIToken.Serializer::class)
value class APIToken(
    val token: String
) {

    init {
        require(token.isNotBlank()) { "API token cannot be blank" }
    }

    object Serializer : KSerializer<APIToken> {
        override val descriptor = String.serializer().descriptor

        override fun serialize(
            encoder: kotlinx.serialization.encoding.Encoder,
            value: APIToken
        ) = encoder.encodeString(value.token)

        override fun deserialize(decoder: kotlinx.serialization.encoding.Decoder) = APIToken(decoder.decodeString())
    }

}
