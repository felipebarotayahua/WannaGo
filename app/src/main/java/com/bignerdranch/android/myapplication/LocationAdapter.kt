package com.bignerdranch.android.myapplication

// LocationAdapter.kt
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.maps.model.LatLng
class CoordinatesAdapter(private val coordinatesList: List<Coordinates>) :
    RecyclerView.Adapter<CoordinatesAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.textView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.coordinate_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = coordinatesList[position]
        holder.textView.text = "Lat: ${item.latitude}, Long: ${item.longitude}"
    }

    override fun getItemCount() = coordinatesList.size
}
