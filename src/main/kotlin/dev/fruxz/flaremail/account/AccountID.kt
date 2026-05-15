package dev.fruxz.flaremail.account

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.serializer

@JvmInline
@Serializable(with = AccountID.Serializer::class)
value class AccountID(
    val id: String
) {

    init {
        require(id.isNotBlank()) { "Account ID cannot be blank" }
    }

    object Serializer : KSerializer<AccountID> {
        override val descriptor = String.serializer().descriptor

        override fun serialize(
            encoder: kotlinx.serialization.encoding.Encoder,
            value: AccountID
        ) = encoder.encodeString(value.id)

        override fun deserialize(decoder: kotlinx.serialization.encoding.Decoder) = AccountID(decoder.decodeString())
    }

}
