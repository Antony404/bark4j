import okhttp3.HttpUrl
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody

@Suppress("MemberVisibilityCanBePrivate")
class BarkClient(
    private val token: String
) {
    var host: String = DEFAULT_HOST

    @Suppress("unused")
    fun send(
         title: String? = null,
         body: String? = null,
         level: PushLevel? = null,
         badge: Int? = null,
         autoCopy: Boolean? = null,
         copy: String? = null,
         sound: String? = null,
         icon: String? = null,
         group: String? = null,
         isArchive: String? = null,
         url: String? = null,
    ): String {
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
