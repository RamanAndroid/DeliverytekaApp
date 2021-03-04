package com.example.deliverytekaapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.deliverytekaapp.adapters.MedicineInfoAdapter
import com.example.deliverytekaapp.pojo.Medicine


class CatalogFragment : Fragment() {
    private lateinit var medicineViewModel: MedicineViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val userId = arguments?.getString("ID_USER","")
        val view = inflater.inflate(R.layout.fragment_catalog, container, false)
        val recyclerview = view.findViewById(R.id.rvMedecinePriceList) as RecyclerView
        recyclerview.layoutManager = LinearLayoutManager(activity)
        val adapter = MedicineInfoAdapter(activity)
        recyclerview.adapter = adapter
        adapter.onMedicineClickListener = object : MedicineInfoAdapter.OnMedicineClickListener {
            override fun onMedicineClick(medicine: Medicine) {
                val intent = view.context.let {
                    it?.let { it1 ->
                        MedicineDetailActivity.newIntent(
                            it1,
                            medicine.id,
                            userId.toString()
                        )
                    }
                }
                startActivity(intent)
            }

        }
        medicineViewModel = ViewModelProvider(requireActivity())[MedicineViewModel::class.java]
        activity?.let { it ->
            medicineViewModel.medicine.observe(it, {
                it.also { adapter.medicineInfoList = it }
            })
        }
        return view
    }


}