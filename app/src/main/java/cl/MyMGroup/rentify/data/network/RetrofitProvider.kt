package cl.MyMGroup.rentify.data.network

import android.os.Build
import cl.MyMGroup.rentify.data.api.ApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitProvider {

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .build()

    // Cambia la base URL según si estás en emulador o en dispositivo físico
    private val BASE_URL: String
        get() {
            // 10.0.2.2 funciona solo en emulador
            return if (Build.MODEL.contains("Emulator") || Build.MODEL.contains("Android SDK built for")) {
                "http://10.0.2.2:8080/"
            } else {
                "http://192.168.1.86:8080/" // IP de tu PC en la red Wi-Fi
            }
        }

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    // Solo necesitas un service, puedes llamarlo apiService
    val apiService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}
