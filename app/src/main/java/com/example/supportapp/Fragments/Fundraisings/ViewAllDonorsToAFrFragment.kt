package com.example.supportapp.Fragments.Fundraisings

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.supportapp.Adapters.FundraisingAdapter
import com.example.supportapp.Adapters.viewAllDonorsFrAdapter
import com.example.supportapp.DataClasses.supportFundraiserData
import com.example.supportapp.databinding.FragmentViewAllDonorsToAFrBinding

class viewAllDonorsToAFrFragment : Fragment() {

    private lateinit var binding : FragmentViewAllDonorsToAFrBinding
    private lateinit var recyclerView: RecyclerView
    private var mList = ArrayList<supportFundraiserData>()
    private lateinit var adapter: viewAllDonorsFrAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentViewAllDonorsToAFrBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
        registerEbvents()
    }

    private fun init() {


        recyclerView = binding.recyclerView
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(context);

        adapter = viewAllDonorsFrAdapter(mList)
        recyclerView.adapter = adapter

        addData()

    }

    private fun registerEbvents() {

        //Setting onclick on recyclerView each item
        adapter.setOnItemClickListner(object: viewAllDonorsFrAdapter.onItemClickListner {
            override fun onItemClick(position: Int) {
                Toast.makeText(context, "position", Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun addData() {
        mList.clear()
        mList.add(supportFundraiserData(
            "",
            "Nuwan Thushara",
            "kk@kk.com",
            "0712345678",
            "Yoooo yooo what's up",
        ))
    }
}