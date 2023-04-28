package com.example.supportapp.Fragments.Requests.PublishedRequests

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.supportapp.Fragments.Donations.supportMsgToReqFragment
import com.example.supportapp.Fragments.Fundraisings.sendSupportMsgToAFrFragment
import com.example.supportapp.Fragments.Fundraisings.viewAFrAllUsersFragmentArgs
import com.example.supportapp.R
import com.example.supportapp.databinding.FragmentNewRequestBinding
import com.example.supportapp.databinding.FragmentRequestsBinding
import com.example.supportapp.databinding.FragmentViewASingleReqAllUsersBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton

class viewARequestAllUsersFragment : Fragment(), supportMsgToReqFragment.dialogSubmitButtonClickedListner {

    private lateinit var popupFragment: supportMsgToReqFragment
    private val args by navArgs<viewARequestAllUsersFragmentArgs>()
    private lateinit var binding:FragmentViewASingleReqAllUsersBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentViewASingleReqAllUsersBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        registerEvents()

    }

    private fun init() {
        binding.viewAReqCollapsingToolbar.title = args.currentReq.title
        binding.tvName.text = args.currentReq.username
    }

    private fun registerEvents() {
        binding.btnSup.setOnClickListener {
            popupFragment = supportMsgToReqFragment()   //instantiate pop up fragment
            //popupFragment.setListner(this)  //connect pop up fragment and host fragment
            popupFragment.show(childFragmentManager, "sendSupportMsgToAFrFragment") //display fragment

        }
    }


    override fun onSave(phone: String?, email: String?, message: String) {
        TODO("Not yet implemented")
    }
}