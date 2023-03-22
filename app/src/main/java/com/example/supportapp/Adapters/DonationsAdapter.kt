package com.example.supportapp.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.supportapp.DataClasses.DonationsData
import com.example.supportapp.R


class DonationsAdapter(var mList: List<DonationsData>) :
    RecyclerView.Adapter<DonationsAdapter.DonationsViewHolder>() {

    private lateinit var mListner : onItemClickListner

    //Setting up onClick listner interface
    interface onItemClickListner{
        fun onItemClick( position: Int)
    }

    fun setOnItemClickListner(listner: onItemClickListner){
        mListner = listner
    }

    inner class DonationsViewHolder(itemView: View, listner: onItemClickListner) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.tvTitle)
        val location: TextView = itemView.findViewById(R.id.tvLocation)
        val des: TextView = itemView.findViewById(R.id.tvDes)

        init{
            itemView.setOnClickListener {
                listner.onItemClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DonationsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.each_item, parent, false)



        return DonationsViewHolder(view, mListner)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: DonationsViewHolder, position: Int) {
        holder.title.text = mList[position].title
        holder.location.text = mList[position].location
        holder.des.text = mList[position].description
    }


}