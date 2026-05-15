package dev.fruxz.flaremail

import dev.fruxz.ascend.json.globalJson
import dev.fruxz.flaremail.account.CloudflareContext
import dev.fruxz.flaremail.email.Email
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.util.reflect.*

object HTTP {

    var client = HttpClient(engineFactory = CIO) {
        install(ContentNegotiation) {
            json(json = globalJson)
        }
    }

    suspend fun sendEmail(
        context: CloudflareContext,
        email: Email,
        apiEndpoint: Url = Url((Cloudflare.apiEndpoint.toString() + "/" + Cloudflare.apiSendPath).replace("{account_id}", context.accountId.id)),
        client: HttpClient = HTTP.client
    ) = client.post(url = apiEndpoint) {
        contentType(ContentType.Application.Json)
        bearerAuth(token = context.apiToken.token)
        setBody(email, bodyType = TypeInfo(Email::class))
    }

}