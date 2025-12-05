package cl.MyMGroup.rentify.data.api

object ApiConfig {
    // Emulador Android Studio
    private const val EMULATOR_BASE_URL = "http://10.0.2.2:8080/"

    // IP de tu PC en la red local (para celular físico)
    private const val LOCAL_NETWORK_IP = "http://192.168.1.86:8080/"

    val BASE_URL: String
        get() {
            return if (isEmulator()) EMULATOR_BASE_URL else LOCAL_NETWORK_IP
        }

    private fun isEmulator(): Boolean {
        return android.os.Build.FINGERPRINT.contains("generic")
                || android.os.Build.MODEL.contains("Emulator")
                || android.os.Build.MODEL.contains("Android SDK built for x86")
    }
}
