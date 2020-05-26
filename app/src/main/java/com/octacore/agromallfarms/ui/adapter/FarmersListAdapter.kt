package com.octacore.agromallfarms.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textview.MaterialTextView
import com.octacore.agromallfarms.R
import com.octacore.agromallfarms.model.Farmer
import com.octacore.agromallfarms.model.FarmerAndFarm

class FarmersListAdapter constructor(context: Context) :
    RecyclerView.Adapter<FarmersListAdapter.ViewHolder>() {
    private val inflater = LayoutInflater.from(context)
    private var farmersAndFarm = emptyList<FarmerAndFarm>()

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val idTextView: MaterialTextView = itemView.findViewById(R.id.farmerId)
        val farmerNameTextView: MaterialTextView = itemView.findViewById(R.id.farmerName)
        val farmNameTextView: MaterialTextView = itemView.findViewById(R.id.farmName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = inflater.inflate(R.layout.list_item_recycler, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount() = farmersAndFarm.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val current = farmersAndFarm[position]
        holder.idTextView.text = current.farmer.farmerId.toString()
        holder.farmNameTextView.text = current.farm.name
        holder.farmerNameTextView.text = generateFarmerName(current.farmer)
    }

    fun loadFarmersAndFarm(farmersAndFarm: List<FarmerAndFarm>) {
        this.farmersAndFarm = farmersAndFarm
        notifyDataSetChanged()
    }

    private fun generateFarmerName(farmer: Farmer) = if (farmer.otherNames?.isNotEmpty()!!) {
        "${farmer.firstName} ${farmer.otherNames} ${farmer.lastName}"
    } else {
        "${farmer.firstName} ${farmer.lastName}"
    }
}