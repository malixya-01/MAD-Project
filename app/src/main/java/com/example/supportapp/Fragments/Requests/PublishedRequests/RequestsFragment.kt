package com.example.supportapp.Fragments.Requests.PublishedRequests

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.supportapp.Adapters.RequestsAdapter
import com.example.supportapp.DataClasses.RequestsData
import com.example.supportapp.R
import com.example.supportapp.databinding.FragmentRequestsBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class RequestsFragment : Fragment() {
    private lateinit var binding:FragmentRequestsBinding

    private lateinit var auth: FirebaseAuth
    private lateinit var databaseRef: DatabaseReference
    private lateinit var recyclerView: RecyclerView
    private lateinit var searchView: SearchView
    private var mList = ArrayList<RequestsData>()
    private lateinit var adapter: RequestsAdapter
    private lateinit var uid: String
    private lateinit var dialog: Dialog


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentRequestsBinding.inflate(inflater,container,false)
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
        adapter = RequestsAdapter(mList)
        recyclerView.adapter = adapter
    }

    private fun retrieveReqs() {
        if (mList.isEmpty()){
            showProgressBar()
        }
        databaseRef.addValueEventListener(object : ValueEventListener {
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
        //Setting onclick on recyclerView each item
        adapter.setOnItemClickListner(object: RequestsAdapter.onItemClickListner{
            override fun onItemClick(position: Int) {
                //findNavController().navigate(R.id.action_requestsFragment_to_viewASingleReqAllUsersFragment2)
                val reqData = mList[position]
                val action = RequestsFragmentDirections.actionRequestsFragmentToViewASingleReqAllUsersFragment2(reqData)
                findNavController().navigate(action)
            }

        })

        binding.btnAdd.setOnClickListener {
            findNavController().navigate(R.id.action_requestsFragment_to_newRequestFragment)
        }

    }

    private fun showProgressBar(){
        dialog = Dialog(this@RequestsFragment.requireContext())
        dialog.requestWindowFeature (Window. FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_wait)
        dialog.setCanceledOnTouchOutside(false)
        dialog.show()
    }
    private fun hideProgressBar(){
        dialog.dismiss()
    }

    private fun addDataToList(){
        mList.add(RequestsData("I need food ...","Nuwan Thushara","Colombo", "simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book."))
        mList.add(RequestsData("I need stationary itemes ...","Kamal Gunarathne", "Kandy", "simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book."))
        mList.add(RequestsData("I need cloths ...","Lahiru Kumara", "Galle", "simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book."))
        mList.add(RequestsData("I need money ...","Ranil Wikramasinghe", "Negombo", "simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book."))
    }

}