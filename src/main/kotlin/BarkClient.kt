import okhttp3.HttpUrl
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response

@Suppress("MemberVisibilityCanBePrivate")
class BarkClient(
    private val token: String
) {
    var host: String = DEFAULT_HOST
    var title: String? = null
    var body: String? = null
    var level: PushLevel? = null
    var badge: Int? = null
    var autoCopy: Boolean? = null
    var copy: String? = null
    var sound: String? = null
    var icon: String? = null
    var group: String? = null
    var isArchive: String? = null
    var url: String? = null

    fun send(): String {
        val requestBody = mapOf(
            "device_key" to token,
            "title" to title,
            "body" to body,
            "level" to level?.paramStr,
            "badge" to badge,
            "autoCopy" to autoCopy,
            "copy" to copy,
            "sound" to sound,
            "icon" to icon,
            "group" to group,
            "isArchive" to isArchive,
            "url" to url
        )
            .filter { it.value != null }
            .let { objectMapper.writeValueAsString(it) }
            .toRequestBody()


        val response = Request.Builder()
            .url(
                HttpUrl.Builder()
                    .scheme("https")
                    .host(host)
                    .addPathSegment("push")
                    .build()
            )
            .post(requestBody)
            .header("Content-Type", "application/json; charset=utf-8")
            .build()
            .let { okHttpClient.newCall(it).execute() }

        return response.body?.string() ?: ""
    }
}

enum class PushLevel(val paramStr: String) {
    ACTIVE("active"),
    TIME_SENSITIVE("timeSensitive"),
    PASSIVE("passive"),
    ;
}
