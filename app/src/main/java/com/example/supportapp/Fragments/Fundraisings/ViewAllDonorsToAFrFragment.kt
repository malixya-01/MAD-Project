package com.example.supportapp.Fragments.Fundraisings

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Toast
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.supportapp.Adapters.viewAllDonorsFrAdapter
import com.example.supportapp.DataClasses.FundraisingData
import com.example.supportapp.DataClasses.supportFundraiserData
import com.example.supportapp.R
import com.example.supportapp.databinding.FragmentEditRecyclerItemBinding
import com.example.supportapp.databinding.FragmentViewAllDonorsToAFrBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class viewAllDonorsToAFrFragment : Fragment(),
    viewAllDonorsFrAdapter.popupMenuOnItemClickInterface {

    private lateinit var binding : FragmentViewAllDonorsToAFrBinding
    private val args: viewAllDonorsToAFrFragmentArgs by navArgs()
    private var currFrId : String? = null
    private lateinit var databaseRef: DatabaseReference
    private lateinit var dialog: Dialog

    private lateinit var recyclerView: RecyclerView
    private var mList = ArrayList<supportFundraiserData>()
    private lateinit var adapter: viewAllDonorsFrAdapter

    private lateinit var popupFragment: editRecyclerItem

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
        retrieveData()
        registerEvents()
    }

    private fun init() {

        currFrId = args.currFrId
        databaseRef = FirebaseDatabase.getInstance().reference.child("supportFundraiser").child(currFrId!!)
        recyclerView = binding.recyclerView
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(context);

        adapter = viewAllDonorsFrAdapter(requireContext(), mList)
        recyclerView.adapter = adapter
        adapter.setPopMenulistner(this)
    }

    private fun retrieveData() {
        if (mList.isEmpty()){
            showProgressBar()
        }

        databaseRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                mList.clear()
                for ( myFrSnapshot in snapshot.children){
                    val data = myFrSnapshot.getValue(supportFundraiserData::class.java)!!
                    mList.add(data)
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
        adapter.setOnItemClickListner(object: viewAllDonorsFrAdapter.onItemClickListner {
            override fun onItemClick(position: Int) {
            }

        })


    }

    private fun showProgressBar(){
        dialog = Dialog(this@viewAllDonorsToAFrFragment.requireContext())
        dialog.requestWindowFeature (Window. FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_wait)
        dialog.setCanceledOnTouchOutside(false)
        dialog.show()
    }
    private fun hideProgressBar(){
        dialog.dismiss()
    }

    override fun onEditBtnClicked(supFrData: supportFundraiserData) {
        popupFragment = editRecyclerItem()   //instantiate pop up fragment
        popupFragment.show(childFragmentManager, "editRecyclerItem") //display fragment
    }

    override fun onDeleteBtnClicked(supFrData: supportFundraiserData) {
        Toast.makeText(context, "Delete ${supFrData.message}", Toast.LENGTH_SHORT).show()
    }

    /*private fun addData() {
        mList.clear()
        mList.add(supportFundraiserData("", "Nuwan Thushara", "kk@kk.com", "0712345678", "Yoooo yooo what's up", "2023/04/11"))
    }*/
}