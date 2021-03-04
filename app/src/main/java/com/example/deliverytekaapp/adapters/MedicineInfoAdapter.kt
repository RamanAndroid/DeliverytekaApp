package com.example.deliverytekaapp.adapters

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.deliverytekaapp.R
import com.example.deliverytekaapp.pojo.Medicine
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_medicine_info.view.*

class MedicineInfoAdapter(private val context: FragmentActivity?) :
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
            LayoutInflater.from(parent.context).inflate(R.layout.item_medicine_info, parent, false)
        return MedicineInfoViewHolder(view)
    }


    override fun onBindViewHolder(holder: MedicineInfoViewHolder, position: Int) {
        val medicine = medicineInfoList[position]
        context?.let {
            val dosageFormat = context.resources.getString(R.string.dosage)
            val manufactureFormat = context.resources.getString(R.string.manufacture)
            holder.tvPack.text = String.format(dosageFormat,medicine.form, medicine.dosage, medicine.pack)
            holder.tvCountry.text = String.format(manufactureFormat, medicine.country)
            holder.tvTitle.text = medicine.name
            holder.tvPrice.text = medicine.price

            Picasso.get().load(medicine.getFullImageUrl()).into(holder.ivLogoCoin)
            if(isRecipe(medicine.isrecipe)){
                holder.tvRecipe.text ="-Без рецепта врача"
                holder.tvRecipe.setTextColor(Color.parseColor("#45827A"))
            }else{
                holder.tvRecipe.text ="-По рецепту врача"
                holder.tvRecipe.setTextColor(Color.parseColor("#F44336"))
            }
        }

        holder.itemView.setOnClickListener {
            onMedicineClickListener?.onMedicineClick(medicine)
        }

    }

    interface OnMedicineClickListener {
        fun onMedicineClick(medicine: Medicine)
    }

    override fun getItemCount(): Int = medicineInfoList.size

    private fun isRecipe(isrecipe: String?): Boolean {
        isrecipe?.let {
            if (it.toInt() == 0) return true
        }
        return false
    }

}