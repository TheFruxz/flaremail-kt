package dev.fruxz.flaremail

import io.ktor.http.Url

object Cloudflare {

    const val DEFAULT_API_ENDPOINT = "https://api.cloudflare.com/client/v4"
    const val DEFAULT_API_SEND_PATH = "accounts/{account_id}/email/sending/send"

    var apiEndpoint: Url = Url(urlString = DEFAULT_API_ENDPOINT)
    var apiSendPath: String = DEFAULT_API_SEND_PATH

}