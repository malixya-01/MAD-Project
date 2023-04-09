package com.example.supportapp.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.supportapp.DataClasses.mySentReqsData
import com.example.supportapp.R

class mySentReqsAdapter(var mList: List<mySentReqsData>) :
    RecyclerView.Adapter<mySentReqsAdapter.mySentReqsViewHolder>() {

    private var listener: OnItemClickListener? = null

    fun setOnItemClickListener(listener: OnItemClickListener?) {
        this.listener = listener
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    inner class mySentReqsViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView) {
        val titleTv : TextView = itemView.findViewById(R.id.titleTv)

        init{
            itemView.setOnClickListener {
                listener?.onItemClick(adapterPosition)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): mySentReqsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.each_item_my_reqs_and_dons_test, parent, false)
        return mySentReqsViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: mySentReqsViewHolder, position: Int) {
        holder.titleTv.text = mList[position].title
    }
}