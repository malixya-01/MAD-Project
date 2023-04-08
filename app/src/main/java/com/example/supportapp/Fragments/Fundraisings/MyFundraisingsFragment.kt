package com.example.supportapp.Fragments.Fundraisings

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.supportapp.Adapters.MyFundraisingsAdapter
import com.example.supportapp.DataClasses.FundraisingData
import com.example.supportapp.DataClasses.MyFundraisingsData
import com.example.supportapp.R
import com.example.supportapp.databinding.FragmentMyFundraisingsBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class MyFundraisingsFragment : Fragment() {

    private lateinit var binding: FragmentMyFundraisingsBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var databaseRef: DatabaseReference
    private lateinit var uid: String
    private lateinit var dialog: Dialog

    private lateinit var recyclerView: RecyclerView
    private var mList = ArrayList<FundraisingData>()
    private lateinit var adapter: MyFundraisingsAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMyFundraisingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
        retrieveFrs()
        registerEvents()

    }

    private fun init() {

        auth = FirebaseAuth.getInstance()
        uid = auth.currentUser?.uid.toString()
        databaseRef = FirebaseDatabase.getInstance().reference.child("fundraising")


        recyclerView = binding.recyclerView

        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(activity);

        //addDataToList()
        adapter = MyFundraisingsAdapter(mList)
        recyclerView.adapter = adapter
    }

    private fun retrieveFrs() {
        if (mList.isEmpty()){
            showProgressBar()
        }

        val query = databaseRef.orderByChild("uid").equalTo(uid)

        query.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                mList.clear()
                for ( myFrSnapshot in snapshot.children){
                    val fr = myFrSnapshot.getValue(FundraisingData::class.java)!!
                    if( fr != null){
                        mList.add(fr)
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
        adapter.setOnItemClickListner(object: MyFundraisingsAdapter.onItemClickListner{
            override fun onItemClick(position: Int) {
                findNavController().navigate(R.id.action_myFundraisingsFragment_to_viewAFrFragment)
            }

        })


    }

   /* private fun addDataToList(){
        mList.add(MyFundraisingsData("My Fundraising 1", R.drawable.unselected_fundraising))
        mList.add(MyFundraisingsData("My Fundraising 2", R.drawable.unselected_fundraising))
    }*/

    private fun showProgressBar(){
        dialog = Dialog(this@MyFundraisingsFragment.requireContext())
        dialog.requestWindowFeature (Window. FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_wait)
        dialog.setCanceledOnTouchOutside(false)
        dialog.show()
    }
    private fun hideProgressBar(){
        dialog.dismiss()
    }


}