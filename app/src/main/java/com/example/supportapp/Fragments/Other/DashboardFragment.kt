package com.example.supportapp.Fragments.Other

import android.app.Dialog
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ImageView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.fragment.findNavController
import com.example.supportapp.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.File

class DashboardFragment : Fragment() {


    private lateinit var auth: FirebaseAuth
    private lateinit var databaseReference: DatabaseReference
    private lateinit var storageReference: StorageReference
    private lateinit var uid: String
    private  lateinit var ivDashboardProfileDp: ImageView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_dashboard, container, false)

        //get current user id
        auth = FirebaseAuth.getInstance()
        uid = auth.currentUser?.uid.toString()

        //retrieve user dp
        getUserProfilePicture()

        //buttons
        var myRequetsBtn = view.findViewById<ConstraintLayout>(R.id.myRequstsBtn)
        var myDonationsBtn = view.findViewById<ConstraintLayout>(R.id.myDonationsBtn)
        var myFundrasingsBtn = view.findViewById<ConstraintLayout>(R.id.myFundrasingsBtn)
        ivDashboardProfileDp = view.findViewById(R.id.ivDashboardProfileDp)


        //rederecting
        myRequetsBtn.setOnClickListener {
            findNavController().navigate(R.id.action_dashboardFragment_to_myRequestsFragment)
        }

        myDonationsBtn.setOnClickListener {
            findNavController().navigate(R.id.action_dashboardFragment_to_myDonationsFragment)
        }

        myFundrasingsBtn.setOnClickListener {
            findNavController().navigate(R.id.action_dashboardFragment_to_myFundraisingsFragment)
        }

        ivDashboardProfileDp.setOnClickListener {
            findNavController().navigate(R.id.action_dashboardFragment_to_viewProfileFragment)
        }

        return view

    }

    private fun getUserProfilePicture() {
        //find image named with the current uid
        storageReference = FirebaseStorage.getInstance().reference.child("Users/$uid")

        //create temporary local file to store the retrieved image
        val localFile = File.createTempFile("tempImage", ".jpg")

        //retrieve image and store it to created temp file
        storageReference.getFile(localFile).addOnSuccessListener {

            //covert temp file to bitmap
            val bitmap = BitmapFactory.decodeFile(localFile.absolutePath)

            //bind image
            ivDashboardProfileDp.setImageBitmap(bitmap)

            hideProgressBar()

        }.addOnFailureListener{
            hideProgressBar()
            Toast.makeText(activity, "Failed to retrieve image", Toast.LENGTH_SHORT).show()
        }
    }

    private fun showProgressBar(){
        val dialog = Dialog(this@DashboardFragment.requireContext())
        dialog.requestWindowFeature (Window. FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_wait)
        dialog.setCanceledOnTouchOutside(false)
        dialog.show()
    }
    private fun hideProgressBar(){
        val dialog = Dialog(this@DashboardFragment.requireContext())
        dialog.dismiss()
    }


}