package com.example.deliverytekaapp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.deliverytekaapp.R
import com.squareup.picasso.Picasso

class MedecineDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_medecine_detail)
        if(!intent.hasExtra(EXTRA_FROM_SYMBOL)) {
            finish()
            return
        }
        val medicineId = intent.getStringExtra(EXTRA_FROM_SYMBOL)
        }

    companion object{
        private const val EXTRA_FROM_SYMBOL = "id"

        fun newIntent(context : Context, fromSymbol:String): Intent {
            val intent = Intent(context,MedecineDetailActivity::class.java)
            intent.putExtra(EXTRA_FROM_SYMBOL, fromSymbol)
            return intent
        }
    }
    }

/*
    viewModel = ViewModelProvider.AndroidViewModelFactory(application).create(MedicineViewModel::class.java)
    fromSymbol?.let {
        viewModel.getDetailInfo(it).observe(this, Observer {
            tvPrice.text = it.price
            tvMinPrice.text = it.lowDay
            tvMaxPrice.text = it.highDay
            tvLastMarket.text = it.lastMarket
            tvLastUpdate.text = it.getFormattedTime()
            tvFromSymbol.text = it.fromSymbol
            tvToSymbol.text = it.toSymbol
            Picasso.get().load(it.getFullImageUrl()).into(tvLogoCoin)
        })

     */