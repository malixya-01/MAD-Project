package com.example.supportapp.Fragments.Donations

import android.app.Dialog
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.supportapp.R
import com.example.supportapp.databinding.FragmentViewADonationBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.File


class viewADonationFragment : Fragment() {
    private lateinit var binding: FragmentViewADonationBinding
    private lateinit var storageReference : StorageReference
    private lateinit var databaseReference: DatabaseReference
    private lateinit var auth: FirebaseAuth
    private lateinit var dialog: Dialog
    private var uid : String? = null
    private val args by navArgs<viewADonationFragmentArgs>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentViewADonationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        registerEvents()
    }

    private fun init() {
        databaseReference = FirebaseDatabase.getInstance().getReference("donations")

        auth = FirebaseAuth.getInstance()   //initialize auth
        uid = auth.currentUser?.uid //initialize current user
        //retrieve user profile pic
        storageReference = FirebaseStorage.getInstance().reference
            .child("Users/$uid")
        getUserProfilePicture()

        binding.toolbar.title = args.currentDonation.title
        binding.tvName.text = args.currentDonation.username
        binding.tvLoc.text = args.currentDonation.location
        binding.tvDate.text = args.currentDonation.date
        binding.tvDes.text = args.currentDonation.description
        binding.tvPhone.text = args.currentDonation.phoneNo
    }

    private fun registerEvents() {
        val btnEdit = binding.btnEdit
        btnEdit.setOnClickListener {
           // findNavController().navigate(R.id.action_viewADonationFragment_to_updateDonationFragment)
            var action = viewADonationFragmentDirections.actionViewADonationFragmentToUpdateDonationFragment(args.currentDonation)
            findNavController().navigate(action)

        }


        val viewReqs = binding.viewReqs
        viewReqs.setOnClickListener {
            findNavController().navigate(R.id.action_viewADonationFragment_to_viewAllReqsFragment)
        }

        val deleteBtn = binding.btnDlt
        deleteBtn.setOnClickListener {
            showDeleteConfirmationDialog()
        }
    }
    private fun getUserProfilePicture() {
        showProgressBar()

        //create temporary local file to store the retrieved image
        val localFile = File.createTempFile("tempImage", ".jpg")

        //retrieve image and store it to created temp file
        storageReference.getFile(localFile).addOnSuccessListener {

            //covert temp file to bitmap
            val bitmap = BitmapFactory.decodeFile(localFile.absolutePath)

            //bind image
            binding.ivViewFrDp.setImageBitmap(bitmap)

            hideProgressBar()

        }.addOnFailureListener{
            hideProgressBar()
            Toast.makeText(activity, "Failed to retrieve user image", Toast.LENGTH_SHORT).show()
        }
    }

    private fun showDeleteConfirmationDialog() {
        val dialog = Dialog(requireContext())
        dialog.setContentView(R.layout.dialog_delete_confirmation)
        dialog.setCancelable(false)

        val cancelButton = dialog.findViewById<Button>(R.id.btn_cancel)
        val deleteButton = dialog.findViewById<Button>(R.id.btn_delete)


        cancelButton.setOnClickListener {
            dialog.dismiss()
        }

        deleteButton.setOnClickListener {
            databaseReference.child(args.currentDonation.donId!!).removeValue().addOnCompleteListener {
                if( it.isSuccessful){
                    Toast.makeText(requireContext(), "Item deleted", Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.action_viewADonationFragment_to_myDonationsFragment)
                }
            }
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun showProgressBar(){
        dialog = Dialog(this@viewADonationFragment.requireContext())
        dialog.requestWindowFeature (Window. FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_wait)
        dialog.setCanceledOnTouchOutside(false)
        dialog.show()
    }
    private fun hideProgressBar(){
        dialog.dismiss()
    }



}