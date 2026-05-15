package dev.fruxz.flaremail.extension

import io.ktor.http.Url
import io.ktor.http.fullPath

operator fun Url.div(other: String): Url = Url(urlString = this.fullPath + other)