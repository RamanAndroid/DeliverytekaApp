package com.example.deliverytekaapp


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.example.deliverytekaapp.adapters.MedicineInfoAdapter
import com.example.deliverytekaapp.pojo.Medicine
import kotlinx.android.synthetic.main.activity_medecine_price_list.*

class MedicinePriceListActivity : AppCompatActivity() {

    private lateinit var medicineViewModel: MedicineViewModel
    //private lateinit var addButtonToFavouriteMedicine: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_medecine_price_list)
        val adapter = MedicineInfoAdapter(this)
        rvMedecinePriceList.adapter = adapter
        adapter.onMedicineClickListener = object : MedicineInfoAdapter.OnMedicineClickListener{
            override fun onMedicineClick(medicine: Medicine) {
                val intent = MedicineDetailActivity.newIntent(
                    this@MedicinePriceListActivity,
                    medicine.id
                )
                startActivity(intent)
            }

        }
        medicineViewModel = ViewModelProvider.AndroidViewModelFactory(application).create(MedicineViewModel::class.java)
        medicineViewModel.medicine.observe(this,{
            adapter.medicineInfoList = it
        })
        medicineViewModel.favouriteMedicine.observe(this,{
            Log.d("TEST", it.toString())
        })




    }
}