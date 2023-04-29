package com.example.supportapp.Fragments.Requests.MyRequestsTabs

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.supportapp.Adapters.RequestsAdapter
import com.example.supportapp.Adapters.myPublishedReqsAdapter
import com.example.supportapp.DataClasses.myPublishedReqsData
import com.example.supportapp.R
import com.example.supportapp.databinding.FragmentMyPublishedReqsBinding
import com.example.supportapp.databinding.FragmentRequestsBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class myPublishedReqsFragment : Fragment() {
    private lateinit var binding: FragmentMyPublishedReqsBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var databaseRef: DatabaseReference
    private lateinit var recyclerView: RecyclerView
    private lateinit var searchView: SearchView
    private var mList = ArrayList<myPublishedReqsData>()
    private lateinit var adapter: myPublishedReqsAdapter
    private lateinit var uid: String
    private lateinit var dialog: Dialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMyPublishedReqsBinding.inflate(inflater,container,false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
        addDataToList()
        /*retrieveReqs()
        registerEvents()*/
    }

    private fun init() {
        auth = FirebaseAuth.getInstance()
        uid = auth.currentUser?.uid.toString()
        databaseRef = FirebaseDatabase.getInstance().reference.child("requests")

        recyclerView = binding.recyclerView
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(getActivity());

        //Passing data to adapter
        adapter = myPublishedReqsAdapter(mList)
        recyclerView.adapter = adapter
    }

    /*private fun retrieveReqs() {
        TODO("Not yet implemented")
    }

    private fun registerEvents() {
        TODO("Not yet implemented")
    }*/

   private fun addDataToList() {
        mList.add(myPublishedReqsData("My request 1...", "2022/10/30", "10"))
        mList.add(myPublishedReqsData("My request 2...", "2022/10/30", "10"))
        mList.add(myPublishedReqsData("My request 3...", "2022/10/30", "10"))
        mList.add(myPublishedReqsData("My request 4...", "2022/10/30", "10"))
    }

}