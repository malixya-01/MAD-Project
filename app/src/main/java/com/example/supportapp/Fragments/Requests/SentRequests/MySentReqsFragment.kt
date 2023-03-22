package com.example.supportapp.Fragments.Requests.SentRequests

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.supportapp.Adapters.mySentReqsAdapter
import com.example.supportapp.DataClasses.mySentReqsData
import com.example.supportapp.R

class mySentReqsFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private var mList = ArrayList<mySentReqsData>()
    private lateinit var adapter: mySentReqsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_my_published_reqs, container, false)

        recyclerView = view.findViewById(R.id.recyclerView)

        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(getActivity());

        addDataToList()
        adapter = mySentReqsAdapter(mList)

        //setting up listner
        adapter.setOnItemClickListener(object : mySentReqsAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                val navController = Navigation.findNavController(requireActivity(), R.id.viewPager)
                navController.navigate(R.id.action_myRequestsFragment_to_readAreqSentToDonorFragment)
            }
        })

        recyclerView.adapter = adapter


        return view
    }

    private fun addDataToList() {
        mList.add(mySentReqsData("My request 1...", "Colombo", "simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book."))
        mList.add(mySentReqsData("My request 2...", "Colombo", "simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book."))
        mList.add(mySentReqsData("My request 3...", "Colombo", "simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book."))
        mList.add(mySentReqsData("My request 4...", "Colombo", "simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book."))
        mList.add(mySentReqsData("My request 5...", "Colombo", "simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book."))
    }

}