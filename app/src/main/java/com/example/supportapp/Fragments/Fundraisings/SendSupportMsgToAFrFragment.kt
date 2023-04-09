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
import androidx.navigation.fragment.navArgs
import com.example.supportapp.DataClasses.suppportFundraiserData
import com.example.supportapp.R
import com.example.supportapp.databinding.FragmentSendSupportMsgToAFrBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class sendSupportMsgToAFrFragment : Fragment() {

    private lateinit var binding: FragmentSendSupportMsgToAFrBinding
    val args: sendSupportMsgToAFrFragmentArgs by navArgs()
    private lateinit var auth: FirebaseAuth
    private lateinit var databaseReference: DatabaseReference
    private lateinit var dialog: Dialog
    private var uid: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSendSupportMsgToAFrBinding.inflate(inflater, container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        registerEvents()
        //binding.etSupFrMsg.setText(args.currentFrId)
    }



    private fun init(){
        auth = FirebaseAuth.getInstance()
        uid = auth.currentUser?.uid
        //initialize db ref
        databaseReference = FirebaseDatabase.getInstance().reference.child("supportFundraiser").child(args.currentFrId)
    }

    private fun registerEvents(){

        //submit data
        binding.btnSupFrSubmit.setOnClickListener {
            showProgressBar()
            //initialize variables
            var phone = binding.etSupFrPhone.text.toString()
            var email = binding.etSupFrEmail.text.toString()
            var msg = binding.etSupFrMsg.text.toString()

            //Id for new record
            var id = databaseReference.push().key!!

            //supportFr object
            var supFr = suppportFundraiserData(id, uid, email, phone, msg)

            //push created object to the db
            databaseReference.child(id).setValue(supFr).addOnCompleteListener {
                if (it.isSuccessful){
                    hideProgressBar()
                    Toast.makeText(context, "Message sent", Toast.LENGTH_SHORT).show()
                    clearFormData()
                } else {
                    Toast.makeText(context, it.exception?.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun clearFormData() {
        binding.etSupFrPhone.text = null
        binding.etSupFrEmail.text = null
        binding.etSupFrMsg.text = null
    }


    private fun showProgressBar(){
        dialog = Dialog( this@sendSupportMsgToAFrFragment.requireContext())
        dialog.requestWindowFeature (Window. FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_wait)
        dialog.setCanceledOnTouchOutside(false)
        dialog.show()
    }
    private fun hideProgressBar(){
        dialog.dismiss()
    }
}