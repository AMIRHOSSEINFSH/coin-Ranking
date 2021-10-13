package com.code_chabok.coinranking.common
import android.util.Log
import java.io.IOException
import java.net.InetSocketAddress
import javax.net.SocketFactory

/**
 * Send a ping to googles primary DNS.
 * If successful, that means we have internet.
 */
object DoesNetworkHaveInternet {

    // Make sure to execute this on a background thread.
    fun execute(socketFactory: SocketFactory): Boolean {
        return try {
            Log.i("Logging ...", "PINGING google.")
            //Timber.d("PINGING google.")
            val socket = socketFactory.createSocket() ?: throw IOException("Socket is null.")
            socket.connect(InetSocketAddress("8.8.8.8", 53), 1500)
            socket.close()
            Log.i("Logging ...", "PING success.")
            //Timber.d("PING success.")
            true
        } catch (e: IOException) {
            Log.i("Logging ...", "No internet connection. ${e}")
            //Timber.e("No internet connection. ${e}")
            false
        }
    }
}