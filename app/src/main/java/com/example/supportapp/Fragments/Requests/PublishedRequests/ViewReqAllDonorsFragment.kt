package com.example.supportapp.Fragments.Requests.PublishedRequests

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.supportapp.Adapters.viewMessagesAdapter
import com.example.supportapp.Adapters.viewReqAllDonorsAdapter
import com.example.supportapp.DataClasses.supportFundraiserData
import com.example.supportapp.databinding.FragmentViewAllReqsBinding
import com.example.supportapp.databinding.FragmentViewReqAllDonorsBinding

class ViewReqAllDonorsFragment: Fragment() {

    private lateinit var binding: FragmentViewReqAllDonorsBinding


    private lateinit var recyclerView: RecyclerView
    private var mList = ArrayList<supportFundraiserData>()
    private lateinit var adapter: viewReqAllDonorsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        binding = FragmentViewReqAllDonorsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        //registerEvents()
    }

    private fun init() {

        //initialize recyclerview
        recyclerView = binding.recyclerView
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(activity);
        addData()
        adapter = viewReqAllDonorsAdapter(mList)
        recyclerView.adapter = adapter

    }

    private fun addData() {
        mList.clear()
        mList.add(supportFundraiserData("", "Nuwan Thushara", "kk@kk.com", "0712345678", "Yoooo yooo what's up", "2023/04/11"))
    }



}