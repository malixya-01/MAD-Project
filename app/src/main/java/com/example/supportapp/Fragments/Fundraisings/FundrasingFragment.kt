package com.example.supportapp.Fragments.Fundraisings

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.SearchView
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.supportapp.Adapters.FundraisingAdapter
import com.example.supportapp.DataClasses.FundraisingData
import com.example.supportapp.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class FundrasingFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var searchView: SearchView
    private var mList = ArrayList<FundraisingData>()
    private lateinit var adapter: FundraisingAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_fundrasing, container, false)

        recyclerView = view.findViewById(R.id.recyclerView)
        searchView = view.findViewById(R.id.searchView)

        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(getActivity());

        addDataToList()


        adapter = FundraisingAdapter(mList)
        recyclerView.adapter = adapter


        val btnAdd = view.findViewById<FloatingActionButton>(R.id.btnAdd)
        btnAdd.setOnClickListener {
            findNavController().navigate(R.id.action_fundrasingFragment_to_newFundraiserFragment)
        }

        //Setting onclick on recyclerView each item
        adapter.setOnItemClickListner(object: FundraisingAdapter.onItemClickListner{
            override fun onItemClick(position: Int) {
                findNavController().navigate(R.id.action_fundrasingFragment_to_viewAFrAllUsersFragment)
            }

        })



        return view
    }

    private fun addDataToList(){
        mList.add(FundraisingData("Fundraiser 1","Manusath Derana","Colombo", "simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book."))
        mList.add(FundraisingData("Fundraiser 2","Gammadda", "Kandy", "simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book."))
        mList.add(FundraisingData("Fundraiser 3","Dialog", "Galle", "simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book."))
        mList.add(FundraisingData("Fundraiser 4","Mobitel", "Negombo", "simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book."))
    }
}