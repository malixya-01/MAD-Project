package com.example.supportapp.Fragments.Donations

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.supportapp.Fragments.Requests.SentRequests.addReqtoTheDonorFragment
import com.example.supportapp.R
import com.example.supportapp.databinding.FragmentViewADonationAllUserBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ViewADonationAllUserFragment : Fragment(),
    addReqtoTheDonorFragment.dialogSubmitButtonClickedListner {

    private lateinit var binding : FragmentViewADonationAllUserBinding
    private lateinit var popupFragment: addReqtoTheDonorFragment
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentViewADonationAllUserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //init()
        registerEvents()

    }

    private fun init() {
        TODO("Not yet implemented")
    }

    private fun registerEvents() {

        //request btn
        binding.btnReq.setOnClickListener {

            popupFragment = addReqtoTheDonorFragment()  //instantiate pop up fragment
            popupFragment!!.setListner(this)    //connect pop up fragment and host fragment
            popupFragment.show(childFragmentManager, "addReqtoTheDonorFragment")    //display fragment
        }





        //save item btn
        binding.btnSave.setOnClickListener {

            //Variables to set up FAB onclick icon change
            val fab = binding.btnSave
            var flag = true // true if first icon is visible, false if second one is visible.

            //varibles to hold toast msgs
            val itemAdded = resources.getString(R.string.itemAdded)
            val itemRemoved = resources.getString(R.string.itemRemoved)
            if (flag) {
                fab.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.selected_bookmark))
                flag = false
                Toast.makeText(requireContext(), itemAdded, Toast.LENGTH_SHORT).show()

            } else {
                fab.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.unselected_bookmark))
                flag = true
                Toast.makeText(requireContext(), itemRemoved, Toast.LENGTH_SHORT).show()

            }
        }


    }

    override fun onSave(phone: String?, email: String?, message: String) {
        TODO("Not yet implemented")
    }

}