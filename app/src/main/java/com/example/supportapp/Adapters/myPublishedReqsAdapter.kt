package com.example.supportapp.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.supportapp.DataClasses.RequestsData
import com.example.supportapp.DataClasses.myPublishedReqsData
import com.example.supportapp.R

class myPublishedReqsAdapter(var mList: List<RequestsData>) :
    RecyclerView.Adapter<myPublishedReqsAdapter.myPublishedReqsViewHolder>() {

    private var listener: OnItemClickListener? = null

    fun setOnItemClickListener(listener: OnItemClickListener?) {
        this.listener = listener
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    inner class myPublishedReqsViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView) {
        val count: TextView = itemView.findViewById(R.id.tvCount)
        val titleTv : TextView = itemView.findViewById(R.id.titleTv)
        val dateTv : TextView = itemView.findViewById(R.id.tvDate)

        init{
            itemView.setOnClickListener {
                listener?.onItemClick(adapterPosition)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myPublishedReqsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.each_item_my_reqs_and_dons_test, parent, false)
        return myPublishedReqsViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: myPublishedReqsViewHolder, position: Int) {
        holder.titleTv.text = mList[position].title
        holder.dateTv.text = mList[position].date
    }
}