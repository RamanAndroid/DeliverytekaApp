package com.example.deliverytekaapp

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_problems_with_internet.*

class ProblemsWithInternetActivity : AppCompatActivity() {
    private lateinit var refresherButton: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_problems_with_internet)
        refresherButton = refresh_btn
        refresherButton.setOnClickListener {
            if(!haveInternet(this@ProblemsWithInternetActivity)){
                Toast.makeText(this@ProblemsWithInternetActivity,"Интернет соединение всё ещё не восстановлено",Toast.LENGTH_SHORT).show()
            }else{
                val intent = Intent(
                    this@ProblemsWithInternetActivity,
                    LoginActivity::class.java
                )
                startActivity(intent)
                finish()
            }
        }

    }
    fun haveInternet(ctx: Context): Boolean {
        var result = false
        val connectivityManager =
            ctx.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val networkCapabilities = connectivityManager.activeNetwork ?: return false
            val actNw =
                connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
            result = when {
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } else {
            connectivityManager.run {
                connectivityManager.activeNetworkInfo?.run {
                    result = when (type) {
                        ConnectivityManager.TYPE_WIFI -> true
                        ConnectivityManager.TYPE_MOBILE -> true
                        ConnectivityManager.TYPE_ETHERNET -> true
                        else -> false
                    }

                }
            }
        }

        return result
    }
}