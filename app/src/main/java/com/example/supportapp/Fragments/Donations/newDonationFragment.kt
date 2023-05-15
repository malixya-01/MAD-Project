package com.example.supportapp.Fragments.Donations

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.supportapp.DataClasses.DonationsData
import com.example.supportapp.DataClasses.RequestsData
import com.example.supportapp.DataClasses.User
import com.example.supportapp.R
import com.example.supportapp.databinding.FragmentNewDonationBinding
import com.example.supportapp.databinding.FragmentNewRequestBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class newDonationFragment : Fragment() {
    private lateinit var binding: FragmentNewDonationBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var databaseRef: DatabaseReference
    private lateinit var databaseRefUsers : DatabaseReference
    private lateinit var dialog: Dialog
    private lateinit var uid: String
    private lateinit var user: User
    private var isValidationSuccess = false
    private var userName: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentNewDonationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        registerEvents()
    }

    private fun init() {
        auth = FirebaseAuth.getInstance()
        uid = auth.currentUser?.uid.toString()

        //to access the databse
        databaseRef = FirebaseDatabase.getInstance().reference
            .child("donations")

        //to access the users
        databaseRefUsers = FirebaseDatabase.getInstance().getReference("users")

        //retrieve the username
        if (uid.isNotEmpty()){
            getUserData()
        }
    }

    private fun registerEvents(){
        binding.btnNewDonSubmit.setOnClickListener {
            var title = binding.newDonTitletv.text.toString()
            var city = binding.newDonCitytv.text.toString()
            var contactNo = binding.newDonConNumtv.text.toString()
            var description = binding.newDonDesctv.text.toString()

            if(title.isEmpty() || city.isEmpty() || contactNo.isEmpty() || description.isEmpty()){

                if(title.isEmpty()){
                    binding.newDonTitletv.error = "Enter title"
                }
                if(city.isEmpty()){
                    binding.newDonCitytv.error = "Enter location"
                }
                if(contactNo.isEmpty()){
                    binding.newDonConNumtv.error = "Enter contact number"
                }
                if(description.isEmpty()){
                    binding.newDonDesctv.error = "Enter donation description"
                }
            } else {
                showProgressBar()
                var date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))

                //Id for new fr
                var donId = databaseRef.push().key!!
                //create a FundraisingData object
                val reqData = DonationsData( title, userName, city, description, contactNo, uid, date, donId)
                databaseRef.child(donId).setValue(reqData).addOnCompleteListener {
                    if (it.isSuccessful){
                        hideProgressBar()
                        findNavController().navigate(R.id.action_newDonationFragment_to_donationsFragment)
                        Toast.makeText(context, "Your donation added successfully", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context, it.exception?.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun getUserData() {
        databaseRefUsers.child(uid).addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                //retrieve values from the db and convert them to user data class
                user = snapshot.getValue(User::class.java)!!
                userName = user.name
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(activity, "Failed to retrieve user name", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun showProgressBar(){
        dialog = Dialog( this@newDonationFragment.requireContext())
        dialog.requestWindowFeature (Window. FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_wait)
        dialog.setCanceledOnTouchOutside(false)
        dialog.show()
    }
    private fun hideProgressBar(){
        dialog.dismiss()
    }


}