package dev.fruxz.flaremail

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames

@OptIn(ExperimentalSerializationApi::class)
data class EmailResponse(
    val errors: List<Error>,
    val messages: List<Message>,
    val result: Result,
    @JsonNames("success")
    val isSuccess: Boolean,
    @JsonNames("result_info")
    val resultInfo: ResultInfo
) {

    @Serializable
    sealed interface Status {
        val code: Int
        val message: String
    }

    @Serializable
    data class Error(
        override val code: Int,
        override val message: String
    ) : Status

    @Serializable
    data class Message(
        override val code: Int,
        override val message: String
    ) : Status

    @Serializable
    data class Result(
        val delivered: List<EmailAddress>,
        @JsonNames("permanent_bounces")
        val permanentBounces: List<EmailAddress>,
        val queued: List<EmailAddress>,
    )

    @Serializable
    data class ResultInfo(
        val count: Int,
        @JsonNames("per_page")
        val perPage: Int,
        @JsonNames("total_count")
        val totalCount: Int,
        val cursor: String?,
        val page: Int,
    )

}
