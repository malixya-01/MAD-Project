package com.example.supportapp.Fragments.Requests.MyRequestsTabs

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.SearchView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.supportapp.Adapters.myPublishedReqsAdapter
import com.example.supportapp.DataClasses.RequestsData
import com.example.supportapp.Fragments.Requests.myRequestsFragmentDirections
import com.example.supportapp.R
import com.example.supportapp.databinding.FragmentMyPublishedReqsBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import androidx.navigation.fragment.findNavController
import com.google.firebase.database.ValueEventListener

class myPublishedReqsFragment : Fragment() {
    private lateinit var binding: FragmentMyPublishedReqsBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var databaseRef: DatabaseReference
    private lateinit var recyclerView: RecyclerView
    private lateinit var searchView: SearchView
    private var mList = ArrayList<RequestsData>()
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
        retrieveReqs()
        registerEvents()
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

    private fun retrieveReqs() {
        if (mList.isEmpty()){
            showProgressBar()
        }
        val query = databaseRef.orderByChild("uid").equalTo(uid)

        query.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                mList.clear()
                for ( frSnapshot in snapshot.children){
                    val req = frSnapshot.getValue(RequestsData::class.java)!!
                    if( req != null){
                        mList.add(req)
                    }
                }
                adapter.notifyDataSetChanged()
                hideProgressBar()
            }

            override fun onCancelled(error: DatabaseError) {
                hideProgressBar()
                Toast.makeText(context, error.message, Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun registerEvents() {
        adapter.setOnItemClickListener(object : myPublishedReqsAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                val reqData = mList[position]
                val action = myRequestsFragmentDirections.actionMyRequestsFragmentToViewSingleRequestFragment(reqData)
                findNavController().navigate(action)
            }
        })
    }

    private fun showProgressBar(){
        dialog = Dialog(this@myPublishedReqsFragment.requireContext())
        dialog.requestWindowFeature (Window. FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_wait)
        dialog.setCanceledOnTouchOutside(false)
        dialog.show()
    }
    private fun hideProgressBar(){
        dialog.dismiss()
    }

   /*private fun addDataToList() {
        mList.add(RequestsData("My request 1...", "2022/10/30", "10"))
        mList.add(RequestsData("My request 2...", "2022/10/30", "10"))
        mList.add(RequestsData("My request 3...", "2022/10/30", "10"))
        mList.add(RequestsData("My request 4...", "2022/10/30", "10"))
    }*/

}