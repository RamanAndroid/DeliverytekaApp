package com.example.deliverytekaapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.deliverytekaapp.pojo.FavouriteMedicine
import com.example.deliverytekaapp.pojo.Medicine
import kotlinx.android.synthetic.main.activity_medecine_detail.*
import kotlinx.android.synthetic.main.medicine_info.*

class MedicineDetailActivity : AppCompatActivity() {
    private lateinit var medicineViewModel: MedicineViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_medecine_detail)
        if (!intent.hasExtra(EXTRA_FROM_SYMBOL)) {
            finish()
            return
        }
        val medicineViewModel = ViewModelProvider.AndroidViewModelFactory(application).create(MedicineViewModel::class.java)
        var pill:List<FavouriteMedicine>
        val countryPill = countryPill
        val formPill = formPill
        val dosagePill = dosagePill
        val isRecipe = isRecipe
        val descriptionPill = descriptionPill
        val addToFavouriteMedicine= addButton
        val plusCount = plusCount
        val minusCount = minusCount
        val textCount = countPill
        val priceText = pricePill
        var count = 1
        var price = 0.00f
        val medicineId = intent.getStringExtra(EXTRA_FROM_SYMBOL)
        this@MedicineDetailActivity.medicineViewModel = ViewModelProvider.AndroidViewModelFactory(application).create(MedicineViewModel::class.java)
        medicineId?.let { it ->
            medicineViewModel.getInfoAboutMedicine(it).observe(this, {
                supportActionBar!!.title = it.name
                price = it.price!!.toFloat()
                priceText.text = price.toString()
                textCount.text = count.toString()
                formPill.text = it.form
                dosagePill.text = it.dosage
                isRecipe.text = isRecipe(it.isrecipe)
                descriptionPill.text = it.description
                countryPill.text = it.country
            })
        }

        plusCount.setOnClickListener {
            if(count<99) {
                count++
            }
            priceText.text = roundOffTo2DecPlaces(price*count)
            textCount.text = count.toString()
        }
        minusCount.setOnClickListener {
            if(count>1) {
                count--
            }
            priceText.text = roundOffTo2DecPlaces(price*count)
            textCount.text = count.toString()
        }
        addToFavouriteMedicine.setOnClickListener {
            Log.d("TEST",medicineId.toString())
            Thread(kotlinx.coroutines.Runnable {   medicineId?.let {
                pill = listOf(FavouriteMedicine(medicineId, count))
                medicineViewModel.run { this.insertFavouriteMedicine(medicine = pill) }
                Log.d("TEST",pill.toString())
                finish()
            }  }).start()


        }

    }
    private fun roundOffTo2DecPlaces(number: Float): String {
        return String.format("%.2f", number)
    }
    private fun isRecipe(isrecipe: String?):String {
        isrecipe?.let {
            if (it.toInt() == 1) return "Без рецепта врача"
        }
        return "По рецепту врача"
    }
    companion object{
        private const val EXTRA_FROM_SYMBOL = "id"

        fun newIntent(context: Context, fromSymbol: String): Intent {
            val intent = Intent(context, MedicineDetailActivity::class.java)
            intent.putExtra(EXTRA_FROM_SYMBOL, fromSymbol)
            return intent
        }
    }
}


