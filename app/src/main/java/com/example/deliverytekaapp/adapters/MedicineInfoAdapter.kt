package com.example.deliverytekaapp.adapters

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.deliverytekaapp.R
import com.example.deliverytekaapp.pojo.Medicine
import kotlinx.android.synthetic.main.item_medecine_info.view.*

class MedicineInfoAdapter(private val context: Context) :
    RecyclerView.Adapter<MedicineInfoAdapter.MedicineInfoViewHolder>() {

    inner class MedicineInfoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivLogoCoin: ImageView = itemView.product_image
        val tvTitle: TextView = itemView.product_title
        val tvPack: TextView = itemView.product_pack
        val tvCountry: TextView = itemView.product_country
        val tvRecipe: TextView = itemView.product_recipe
        val tvPrice: TextView = itemView.product_price
    }

    var medicineInfoList: List<Medicine> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    var onMedicineClickListener: OnMedicineClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MedicineInfoViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_medecine_info, parent, false)
        return MedicineInfoViewHolder(view)
    }



    override fun onBindViewHolder(holder: MedicineInfoViewHolder, position: Int) {
        val dosageFormat = context.resources.getString(R.string.dosage)
        val manufactureFormat = context.resources.getString(R.string.manufacture)
        val medicine = medicineInfoList[position]
        holder.tvTitle.text = medicine.name
        holder.tvPrice.text= medicine.price
        holder.tvPack.text = String.format(dosageFormat,medicine.dosage,medicine.pack)
        holder.tvCountry.text = String.format(manufactureFormat,medicine.country)
        holder.tvRecipe.text =isRecipe(medicine.isrecipe)
        holder.itemView.setOnClickListener {
            onMedicineClickListener?.onMedicineClick(medicine)
        }

    }

    interface OnMedicineClickListener {
        fun onMedicineClick(medicine: Medicine)
    }

    override fun getItemCount(): Int = medicineInfoList.size

    private fun isRecipe(isrecipe: String?):String {
        isrecipe?.let {
            if (it.toInt() == 1) return "-Без рецепта врача"
        }
        return "-По рецепту врача"
    }

}