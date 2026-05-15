package dev.fruxz.flaremail.account

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames

@OptIn(ExperimentalSerializationApi::class)
@Serializable
data class CloudflareContext(
    @JsonNames("account_id")
    val accountId: AccountID,
    @JsonNames("api_token")
    val apiToken: APIToken
) {

    constructor(accountId: String, rawApiToken: String, boolean: Boolean) : this(AccountID(accountId), APIToken(rawApiToken))

}