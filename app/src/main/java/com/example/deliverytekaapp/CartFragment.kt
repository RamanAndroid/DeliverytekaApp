package com.example.deliverytekaapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.deliverytekaapp.adapters.MedicineCartInfoAdapter
import com.example.deliverytekaapp.api.ApiFactory
import com.example.deliverytekaapp.pojo.MedicineCart
import io.reactivex.schedulers.Schedulers


class CartFragment : Fragment() {
    lateinit var adapter: MedicineCartInfoAdapter
    lateinit var recyclerview: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_cart, container, false)
        recyclerview = view.findViewById(R.id.rvMedecineCartPriceList) as RecyclerView
        recyclerview.layoutManager = LinearLayoutManager(activity)
        val userId = arguments?.getString("ID_USER", "")
        Log.d("TEST_FAILURE_CART_FRAG", userId.toString())
        userId?.let {
            ApiFactory.apiService.getInfoUserCart(userId)
                .subscribeOn(Schedulers.io()).subscribe({
                    it.also {
                        fillCart(it)
                    }
                }, {
                    Log.d("TEST_FAILURE_CART_FRAG", it.toString())
                })
        }
        return view
    }

    private fun fillCart(list: List<MedicineCart>) {
        activity?.runOnUiThread {
            adapter = MedicineCartInfoAdapter(activity, list as MutableList<MedicineCart>)
            adapter.notifyDataSetChanged()
            recyclerview.adapter = adapter
        }
    }
}