package com.example.supportapp.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.supportapp.DataClasses.myPublishedReqsData
import com.example.supportapp.R

class myPublishedReqsAdapter(var mList: List<myPublishedReqsData>) :
    RecyclerView.Adapter<myPublishedReqsAdapter.myPublishedReqsViewHolder>() {

    private var listener: OnItemClickListener? = null

    fun setOnItemClickListener(listener: OnItemClickListener?) {
        this.listener = listener
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    inner class myPublishedReqsViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.tvTitle)
        val location: TextView = itemView.findViewById(R.id.tvLocation)
        val des: TextView = itemView.findViewById(R.id.tvDes)

        init{
            itemView.setOnClickListener {
                listener?.onItemClick(adapterPosition)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myPublishedReqsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.each_item, parent, false)
        return myPublishedReqsViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: myPublishedReqsViewHolder, position: Int) {
        holder.title.text = mList[position].title
        holder.location.text = mList[position].location
        holder.des.text = mList[position].description
    }
}