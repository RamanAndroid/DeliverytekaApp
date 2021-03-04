package com.example.deliverytekaapp

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.deliverytekaapp.api.ApiFactory
import com.google.android.material.textfield.TextInputEditText
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_registration.*
import ru.tinkoff.decoro.MaskImpl
import ru.tinkoff.decoro.parser.UnderscoreDigitSlotsParser
import ru.tinkoff.decoro.watchers.FormatWatcher
import ru.tinkoff.decoro.watchers.MaskFormatWatcher


class RegistrationActivity : AppCompatActivity() {
    private lateinit var registerButton: Button
    private lateinit var phoneUser: TextInputEditText
    private lateinit var passwordUser: TextInputEditText
    private lateinit var passwordRepeat: TextInputEditText
    private val compositeDisposable = CompositeDisposable()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)
        registerButton = register_btn
        phoneUser = phone_input
        passwordUser = password_input
        passwordRepeat = password_repeat_input

        val slots = UnderscoreDigitSlotsParser().parseSlots("+375 (__)__-__-___")
        val formatWatcher: FormatWatcher = MaskFormatWatcher( // форматировать текст будет вот он
            MaskImpl.createTerminated(slots)
        )
        formatWatcher.installOn(phoneUser)

        registerButton.setOnClickListener {
            if (passwordRepeat.text.toString() != passwordUser.text.toString()) {
                Toast.makeText(this, "Пароль не совпадает", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            if (phoneUser.text.toString().length == 17) {
                Toast.makeText(this, "Введите номер телефона", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            if (phoneUser.text.toString().trim().isNotEmpty()
                && passwordUser.text.toString().trim().isNotEmpty()
                && passwordRepeat.text.toString() == passwordUser.text.toString()
            ) {
                val disposable = ApiFactory.apiService.signUpUser(
                    "228",
                    phoneUser.text.toString(),
                    passwordUser.text.toString()
                )
                    .subscribeOn(Schedulers.io()).subscribe({
                        if (it[0].userPhone.isNullOrEmpty()) {
                            responseOnFailed()
                        } else {
                            val EXTRA_ID = "id"
                            val EXTRA_PHONE = "phone"
                            val intent = Intent(
                                this@RegistrationActivity,
                                MedicinePriceListActivity::class.java
                            )
                            intent.putExtra(EXTRA_ID, it[0].userId)
                            Log.d("TEST_BUNDLE_REGESTARION", it[0].userId.toString())
                            intent.putExtra(EXTRA_PHONE, it[0].userPhone)
                            startActivity(intent)
                            finish()
                        }
                        Log.d("TEST_ACCEPTED", it.toString())
                    }, {
                        Log.d("TEST_FAILURE", it.toString())
                    })
                compositeDisposable.add(disposable)
            } else {
                Toast.makeText(this, "Вы ввели данные не во все поля", Toast.LENGTH_LONG).show()
            }
        }

    }

    private fun responseOnFailed() {
        runOnUiThread {
            Toast.makeText(
                applicationContext,
                "Пользователь с таким номером телефона уже существует",
                Toast.LENGTH_SHORT
            ).show()
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }
}