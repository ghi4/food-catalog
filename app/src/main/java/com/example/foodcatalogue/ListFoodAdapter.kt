package com.example.foodcatalogue

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.foodcatalogue.data.Food
import com.example.foodcatalogue.databinding.ItemRowFoodBinding

class ListFoodAdapter(private val listFood: ArrayList<Food>) :
    RecyclerView.Adapter<ListFoodAdapter.ListViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemRowFoodBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun getItemCount(): Int = listFood.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val food = listFood[position]
        val transitionName = "transition_$position"
        holder.bind(food, transitionName)
        holder.binding.root.setOnClickListener {
            onItemClickCallback.onItemClicked(food, transitionName, position)
        }
    }

    inner class ListViewHolder(val binding: ItemRowFoodBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(food: Food, transitionName: String) {
            binding.apply {
                binding.tvRowfoodTitle.text = food.name
                binding.tvRowfoodDescription.text = food.description
                binding.imgRowfoodPhoto.setImageResource(food.photoUrl)
                binding.imgRowfoodPhoto.transitionName = transitionName
            }
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(food: Food, transitionName: String, position: Int)
    }
}