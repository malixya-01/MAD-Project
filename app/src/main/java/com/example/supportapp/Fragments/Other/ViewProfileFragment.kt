package com.example.supportapp.Fragments.Other

import android.app.Dialog
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.supportapp.DataClasses.User
import com.example.supportapp.LoginActivity
import com.example.supportapp.R
import com.example.supportapp.databinding.FragmentViewProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.File

class ViewProfileFragment : Fragment() {

    private lateinit var binding: FragmentViewProfileBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var databaseReference: DatabaseReference
    private lateinit var storageReference: StorageReference
    private lateinit var dialog: Dialog
    private lateinit var user: User
    private lateinit var uid: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentViewProfileBinding.inflate(inflater,container,false);
        val view = binding.root;

        //get current user id
        auth = FirebaseAuth.getInstance()
        uid = auth.currentUser?.uid.toString()

        //retrieve data
        databaseReference = FirebaseDatabase.getInstance().getReference("users")
        if (uid.isNotEmpty()){
            getUserData()

        }

        //implementing logout
        binding.btnLogout.setOnClickListener {

            Firebase.auth.signOut()

            //redirect user to login page
            val intent = Intent (getActivity(), LoginActivity::class.java)
            getActivity()?.startActivity(intent)


            //toast message
            Toast.makeText(getActivity(), "Successfully logged out", Toast.LENGTH_SHORT).show()
        }

        //onclick listener to redirect to re-set password
        binding.btnResetPwd.setOnClickListener {
            findNavController().navigate(R.id.action_viewProfileFragment_to_resetPasswordActivity)
        }

        //onclick listener to redirect to editProfile
        binding.btnUpdateProfileDetails.setOnClickListener {
            Toast.makeText(getActivity(), "Update account", Toast.LENGTH_SHORT).show()
        }

        //onclick listener to delete account
        binding.btnDeleteAcc.setOnClickListener {
            Toast.makeText(getActivity(), "Account deleted", Toast.LENGTH_SHORT).show()
        }


        return view
    }

    private fun getUserData() {

        showProgressBar()
        databaseReference.child(uid).addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                //retrieve values from the db and convert them to user data class
                user = snapshot.getValue(User::class.java)!!

                //binding data
                binding.tvProfileUserName.setText(user.name)
                binding.tvProfilePhoneNo.setText(user.phone)
                binding.tvProfileUserEmail.setText(user.email)
                binding.tvProfileAddress.setText(user.address)

                //retrieve user profile picture
                getUserProfilePicture()
            }

            override fun onCancelled(error: DatabaseError) {
                hideProgressBar()
                Toast.makeText(activity, "Failed to retrieve user data", Toast.LENGTH_SHORT).show()
            }


        })

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
            binding.ivViewProfileDp.setImageBitmap(bitmap)

            hideProgressBar()

        }.addOnFailureListener{
            hideProgressBar()
            Toast.makeText(activity, "Failed to retrieve image", Toast.LENGTH_SHORT).show()
        }
    }

    private fun showProgressBar(){
        dialog = Dialog(this@ViewProfileFragment.requireContext())
        dialog.requestWindowFeature (Window. FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_wait)
        dialog.setCanceledOnTouchOutside(false)
        dialog.show()
    }
    private fun hideProgressBar(){
        dialog.dismiss()
    }

}