package com.example.supportapp.Fragments.Requests.PublishedRequests

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.supportapp.Adapters.DonationsAdapter
import com.example.supportapp.Adapters.viewMessagesAdapter
import com.example.supportapp.DataClasses.DonationsData
import com.example.supportapp.DataClasses.supportFundraiserData
import com.example.supportapp.R
import com.example.supportapp.databinding.FragmentViewARequestBinding
import com.example.supportapp.databinding.FragmentViewAllReqsBinding

class viewAllReqsFragment : Fragment() {

    private lateinit var binding: FragmentViewAllReqsBinding
    private lateinit var recyclerView: RecyclerView

    private var mList = ArrayList<supportFundraiserData>()
    private lateinit var adapter: viewMessagesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentViewAllReqsBinding.inflate(inflater, container, false)
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
        adapter = viewMessagesAdapter(mList)
        recyclerView.adapter = adapter



    }

    private fun registerEvents() {
        TODO("Not yet implemented")
    }

    private fun addData() {
        mList.clear()
        mList.add(supportFundraiserData("", "Nuwan Thushara", "kk@kk.com", "0712345678", "Yoooo yooo what's up", "2023/04/11"))
    }

}