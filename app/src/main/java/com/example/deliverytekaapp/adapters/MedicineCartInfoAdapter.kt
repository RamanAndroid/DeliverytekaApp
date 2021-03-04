package com.example.deliverytekaapp.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.deliverytekaapp.R
import com.example.deliverytekaapp.pojo.MedicineCart
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_cart.view.*

class MedicineCartInfoAdapter(
    private val context: FragmentActivity?,
    private val mutableList: MutableList<MedicineCart>
) :
    RecyclerView.Adapter<MedicineCartInfoAdapter.MedicineCartInfoViewHolder>() {
    var price = 0.00f
    var count = 1

    inner class MedicineCartInfoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivLogoCoin: ImageView = itemView.product_cart_image
        val tvTitle: TextView = itemView.product_cart_title
        val tvPack: TextView = itemView.product_cart_pack
        val tvCountry: TextView = itemView.product_cart_country
        val tvRecipe: TextView = itemView.product_cart_recipe
        val tvPrice: TextView = itemView.product_cart_price
        val tvCount: TextView = itemView.product_count
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MedicineCartInfoAdapter.MedicineCartInfoViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_cart, parent, false)
        return MedicineCartInfoViewHolder(view)
    }

    override fun onBindViewHolder(holder: MedicineCartInfoViewHolder, position: Int) {
        val medicine = mutableList[position]
        context?.let {
            val dosageFormat = context.resources.getString(R.string.dosage)
            val manufactureFormat = context.resources.getString(R.string.manufacture)
            holder.tvPack.text =
                String.format(dosageFormat, medicine.form, medicine.dosage, medicine.pack)
            holder.tvCountry.text = String.format(manufactureFormat, medicine.country)
            Picasso.get().load(medicine.getFullImageUrl()).into(holder.ivLogoCoin)
            if (isRecipe(medicine.isrecipe)) {
                holder.tvRecipe.text = "-Без рецепта врача"
                holder.tvRecipe.setTextColor(Color.parseColor("#45827A"))
            } else {
                holder.tvRecipe.text = "-По рецепту врача"
                holder.tvRecipe.setTextColor(Color.parseColor("#F44336"))
            }
            holder.tvTitle.text = medicine.name
            price = medicine.price!!.toDouble().toFloat()
            count = medicine.count!!.toInt()
            holder.tvPrice.text = roundOffTo2DecPlaces(price * count)
            holder.tvCount.text = count.toString()

        }
    }

    override fun getItemCount(): Int = mutableList.size

    private fun isRecipe(isrecipe: String?): Boolean {
        isrecipe?.let {
            if (it.toInt() == 0) return true
        }
        return false
    }


    private fun roundOffTo2DecPlaces(number: Float): String {
        return String.format("%.2f" + " ₽", number)
    }
}