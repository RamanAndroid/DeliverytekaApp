package com.example.deliverytekaapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.deliverytekaapp.api.ApiFactory
import com.example.deliverytekaapp.pojo.Medicine
import com.squareup.picasso.Picasso
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_medecine_detail.*
import kotlinx.android.synthetic.main.medicine_info.*


class MedicineDetailActivity : AppCompatActivity() {
    private val compositeDisposable = CompositeDisposable()
    private lateinit var countryPill: TextView
    private lateinit var formPill: TextView
    private lateinit var dosagePill: TextView
    private lateinit var isRecipe: TextView
    private lateinit var descriptionPill: TextView
    private lateinit var addToFavouriteMedicine: Button
    private lateinit var plusCount: ImageView
    private lateinit var minusCount: ImageView
    private lateinit var textCount: TextView
    private lateinit var priceText: TextView
    private lateinit var ivLogoCoin: ImageView

    var price = 0.00f
    var count = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_medecine_detail)
        if (!intent.hasExtra(EXTRA_ID_ITEM)) {
            finish()
            return
        }

        countryPill = country_pill
        formPill = form_pill
        dosagePill = dosage_pill
        isRecipe = is_recipe
        descriptionPill = description_pill
        addToFavouriteMedicine = addButton
        plusCount = plus_count
        minusCount = minus_count
        textCount = count_pill
        priceText = price_pill
        ivLogoCoin = product_detail_image
        val medicineId = intent.getStringExtra(EXTRA_ID_ITEM)
        val userId = intent.getStringExtra(EXTRA_ID_USER)
        Log.i("MEDICINE_ID", medicineId.toString())
        medicineId?.let { it ->
            val disposable = ApiFactory.apiService.getInfoAboutMedicine(medicineId)
                .subscribeOn(Schedulers.io()).subscribe({
                    fillCart(it)
                }, {
                    responseOnClick(2)
                    Log.d("TEST_FAILURE", it.toString())
                })
            compositeDisposable.add(disposable)
        }


        plusCount.setOnClickListener {
            if (count < 99) {
                count++
            }else{
                Toast.makeText(this,"Нельзя добавить больше лекарств",Toast.LENGTH_SHORT).show()
            }
            priceText.text = roundOffTo2DecPlaces(price * count)
            textCount.text = count.toString()
        }
        minusCount.setOnClickListener {
            if (count > 1) {
                count--
            }else{
                Toast.makeText(this,"Нельзя добавить меньше лекарств",Toast.LENGTH_SHORT).show()
            }
            priceText.text = roundOffTo2DecPlaces(price * count)
            textCount.text = count.toString()
        }
        addToFavouriteMedicine.setOnClickListener {
            val disposable = ApiFactory.apiService.addToCart(userId, medicineId, count.toString())
                .subscribeOn(Schedulers.io()).subscribe({
                    responseOnClick(1)
                    finish()
                    Log.d("TEST_ACCEPTED", it.toString())
                }, {
                    responseOnClick(2)
                    Log.d("TEST_FAILURE", it.toString())
                })
            compositeDisposable.add(disposable)
        }
    }

    private fun roundOffTo2DecPlaces(number: Float): String {
        return String.format("%.2f" + " ₽", number)
    }

    private fun isRecipe(isrecipe: String?): String {
        isrecipe?.let {
            if (it.toInt() == 0) return "Без рецепта врача"
        }
        return "По рецепту врача"
    }

    private fun responseOnClick(i: Int) {
        runOnUiThread {
            if (i == 1) {
                Toast.makeText(
                    applicationContext,
                    "Лекарственный препарат был добавлен в корзину",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                Toast.makeText(
                    applicationContext,
                    "Товара нет на складе",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun fillCart(medicineInfoList: List<Medicine>) {
        val medicine = medicineInfoList[0]
        runOnUiThread {
            val toolbarDetailActivity = toolbar_detail_activity
            setSupportActionBar(toolbarDetailActivity)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setHomeButtonEnabled(true)
            toolbarDetailActivity.setNavigationOnClickListener {
                finish()
                onBackPressed()
            }
            supportActionBar?.title = medicine.name
            price = medicine.price!!.toDouble().toFloat()
            Picasso.get().load(medicine.getFullImageUrl()).into(ivLogoCoin)
            priceText.text = getString(R.string.price_text, price.toString())
            textCount.text = count.toString()
            formPill.text = medicine.form
            dosagePill.text = medicine.dosage
            isRecipe.text = isRecipe(medicine.isrecipe)
            descriptionPill.text = medicine.description
            countryPill.text = medicine.country
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }

    companion object {
        private const val EXTRA_ID_ITEM = "id"
        private const val EXTRA_ID_USER = "user"

        fun newIntent(context: Context, idItem: String, idUser: String): Intent {
            val intent = Intent(context, MedicineDetailActivity::class.java)
            intent.putExtra(EXTRA_ID_ITEM, idItem)
            intent.putExtra(EXTRA_ID_USER, idUser)
            return intent
        }
    }

}


