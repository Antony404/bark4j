import com.fasterxml.jackson.databind.ObjectMapper
import okhttp3.OkHttpClient

val objectMapper: ObjectMapper by lazy { ObjectMapper() }
val okHttpClient: OkHttpClient by lazy {
    OkHttpClient.Builder()
        .build()
}
const val DEFAULT_HOST: String = "api.day.app"
