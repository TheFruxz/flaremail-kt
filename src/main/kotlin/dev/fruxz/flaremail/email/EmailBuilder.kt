package dev.fruxz.flaremail.email

import kotlinx.html.HTML

data class EmailBuilder(
    var from: Email.Sender? = null,
    var to: Email.Recipient? = null,
    var subject: String? = null,
    var body: Email.Body? = null,
    var attachments: List<Nothing> = emptyList(), // TODO
    var bbc: Email.Recipient? = null,
    var cc: Email.Recipient? = null,
    var replyTo: Email.Recipient? = null,
    var headers: Map<String, String> = emptyMap()
) {

    fun from(sender: Email.Sender) = apply { this.from = sender }
    fun to(recipient: Email.Recipient) = apply { this.to = recipient }
    fun subject(subject: String) = apply { this.subject = subject }

    fun body(body: Email.Body) = apply { this.body = body }
    fun body(text: String) = apply { this.body = Email.Body.text(text) }
    fun bodyHtmlRaw(html: String) = apply { this.body = Email.Body.htmlRaw(html) }
    fun bodyHtmlDsl(builder: HTML.() -> Unit) = apply { this.body = Email.Body.htmlDsl(builder) }

    fun attachments(attachments: List<Nothing>) = apply { this.attachments = attachments }
    fun bbc(recipient: Email.Recipient) = apply { this.bbc = recipient }
    fun cc(recipient: Email.Recipient) = apply { this.cc = recipient }
    fun replyTo(recipient: Email.Recipient) = apply { this.replyTo = recipient }
    fun header(key: String, value: String) = apply { this.headers += (key to value) }

    fun build(): Email = Email(
        from = from ?: throw IllegalStateException("Email must have a sender"),
        to = to ?: throw IllegalStateException("Email must have a recipient"),
        subject = subject ?: throw IllegalStateException("Email must have a subject"),
        body = body ?: throw IllegalStateException("Email must have a body"),
        attachments = attachments,
        bbc = bbc,
        cc = cc,
        replyTo = replyTo,
        headers = headers
    )

}
