package com.example.deliverytekaapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.deliverytekaapp.api.ApiFactory
import com.google.android.material.textfield.TextInputEditText
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_login.*
import ru.tinkoff.decoro.MaskImpl
import ru.tinkoff.decoro.parser.UnderscoreDigitSlotsParser
import ru.tinkoff.decoro.watchers.FormatWatcher
import ru.tinkoff.decoro.watchers.MaskFormatWatcher

class LoginActivity : AppCompatActivity() {
    private lateinit var loginButton: Button
    private lateinit var phoneUser: TextInputEditText
    private lateinit var passwordUser: TextInputEditText
    lateinit var registerButton: Button
    private val compositeDisposable = CompositeDisposable()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        phoneUser = phones_input
        passwordUser = passwords_input
        loginButton = login_btn
        registerButton = registers_btn
        val slots = UnderscoreDigitSlotsParser().parseSlots("+375 (__)__-__-___")
        val formatWatcher: FormatWatcher = MaskFormatWatcher(
            MaskImpl.createTerminated(slots)
        )
        formatWatcher.installOn(phoneUser)
        loginButton.setOnClickListener {
            if(phoneUser.text.toString().length==17){
                Toast.makeText(this, "Введите номер телефона", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            if (phoneUser.text.toString().trim().isNotEmpty()
                && passwordUser.text.toString().trim().isNotEmpty()
            ) {
                val disposable = ApiFactory.apiService.loginUser(
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
                                this@LoginActivity,
                                MedicinePriceListActivity::class.java
                            )
                            intent.putExtra(EXTRA_ID, it[0].userId)
                            intent.putExtra(EXTRA_PHONE, it[0].userPhone)
                            startActivity(intent)
                            finish()
                        }
                    }, {
                        Log.d("TEST_FAILURE", it.toString())
                    })
                Log.d("TEST", disposable.toString())
                compositeDisposable.add(disposable)

            } else {
                Toast.makeText(this, "Вы не заполнили все поля", Toast.LENGTH_LONG).show()
            }
        }
        registerButton.setOnClickListener {
            val intent = Intent(this, RegistrationActivity::class.java)
            startActivity(intent)
        }


    }

    private fun responseOnFailed() {
        runOnUiThread {
            Toast.makeText(
                applicationContext,
                "Вы ввели неправильный номер телефона или пароль",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }
}