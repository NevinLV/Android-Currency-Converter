package com.example.test

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.round


class RecyclerAdapter(private var charcode : List<String>,
                      private var name: List<String>,
                      private var volue: List<Double>,
                      private var previous: List<Double>,
                      private var nominal: List<Int>,
                      private val onClicListener: CurrencyOnClickListener) : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

                          inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
                              val itemCharCode: TextView = itemView.findViewById(R.id.CharCodeId)
                              val itemName: TextView = itemView.findViewById(R.id.NameId)
                              val itemVolue: TextView = itemView.findViewById(R.id.VolueId)
                              val itemPrevius: TextView = itemView.findViewById(R.id.PreviousId)

                              init {
                                  itemView.setOnClickListener {v: View ->
                                      val position : Int = adapterPosition
                                      onClicListener.onClicked(v,position)
                                  }
                              }

                          }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
        return  ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val roundValue = round(volue[position]*10000)/10000
        val roundPrevious = round(previous[position]*10000)/10000

        holder.itemCharCode.text = charcode[position]
        holder.itemName.text = name[position]
        holder.itemVolue.text = roundValue.toString()

        val difference = round((roundValue - roundPrevious)*10000)/10000

        if ( difference >= 0.0){
            holder.itemPrevius.text = "+" + difference.toString()
            holder.itemPrevius.setTextColor(Color.parseColor("#6ABD47"))
        }
        else{
            holder.itemPrevius.text = difference.toString()
            holder.itemPrevius.setTextColor(Color.parseColor("#BD4141"))
        }

    }

    override fun getItemCount(): Int {
        return charcode.size
    }
}