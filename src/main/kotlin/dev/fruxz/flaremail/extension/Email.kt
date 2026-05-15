package dev.fruxz.flaremail.extension

import dev.fruxz.flaremail.email.Email
import dev.fruxz.flaremail.email.EmailBuilder

fun buildEmail(builder: EmailBuilder.() -> Unit): Email = EmailBuilder().apply(builder).build()