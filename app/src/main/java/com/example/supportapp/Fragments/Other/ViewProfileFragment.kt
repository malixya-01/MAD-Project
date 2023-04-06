package com.example.supportapp.Fragments.Other

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.supportapp.LoginActivity
import com.example.supportapp.R
import com.example.supportapp.databinding.FragmentViewProfileBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class ViewProfileFragment : Fragment() {

    private lateinit var binding: FragmentViewProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //val view = inflater.inflate(R.layout.fragment_view_profile, container, false)
        binding = FragmentViewProfileBinding.inflate(inflater,container,false);
        val view = binding.root;

        //implementing logout
        binding.btnLogout.setOnClickListener {

            /*Firebase.auth.signOut()

            //redirect user to login page
            val intent = Intent (getActivity(), LoginActivity::class.java)
            getActivity()?.startActivity(intent)
            */

            //toast message
            Toast.makeText(getActivity(), "Successfully logged out", Toast.LENGTH_SHORT).show()
        }


        binding.btnResetPwd.setOnClickListener {
            findNavController().navigate(R.id.action_viewProfileFragment_to_resetPasswordActivity)
        }


        return view
    }
}