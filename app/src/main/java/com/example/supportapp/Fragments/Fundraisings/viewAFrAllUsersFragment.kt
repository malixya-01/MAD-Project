package com.example.supportapp.Fragments.Fundraisings

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.navigation.NavArgs
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.supportapp.R
import com.example.supportapp.databinding.FragmentViewAFrAllUsersBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton

class viewAFrAllUsersFragment : Fragment() {

    private lateinit var binding: FragmentViewAFrAllUsersBinding

    //safe args
    private val args by navArgs<viewAFrAllUsersFragmentArgs>()

    // variables to function progress bar
    lateinit var progressBar: ProgressBar
    private lateinit var fab: FloatingActionButton
    private var unselectedIcon = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentViewAFrAllUsersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        registerEvents()
    }
    private fun init() {

        //binding data
        binding.vFrACollapsingToolbar.title = args.currentFr.title
        binding.tvName.text = args.currentFr.username
        binding.tvDate.text = args.currentFr.date
        binding.tvTotAmt.text = args.currentFr.expectedAmt
        binding.tvReqAmt.text = args.currentFr.collectedAmt
        binding.tvDes.text = args.currentFr.description
        binding.tvPhone.text = args.currentFr.contactNo
        binding.tvWeb.text = args.currentFr.website
        binding.tvEmail.text = args.currentFr.email
        binding.tvBankDetails.text = args.currentFr.bankDetails

        //handle progressbar
        var max = args.currentFr.expectedAmt!!.toInt()
        var progress = args.currentFr.collectedAmt!!.toInt()
        progressBar = binding.frProgressBar
        progressBar.max = max
        progressBar.progress = progress

        //handle verified status
        binding.ivIsVerified.visibility = View.GONE;
        if (args.currentFr.verStatus == true) {
            binding.ivIsVerified.visibility = View.VISIBLE;

        }
    }






    private fun registerEvents() {

        //toggle saved items
        fab = binding.btnSave
        fab.setOnClickListener {
            //varibles to hold toast msgs
            val itemAdded = resources.getString(R.string.itemAdded)
            val itemRemoved = resources.getString(R.string.itemRemoved)

            // unselectedIcon true if unselected_bookmark icon is visible, false if selected_bookmark icon is visible.
            if (unselectedIcon) {
                fab.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.selected_bookmark))
                Toast.makeText(requireContext(), itemAdded, Toast.LENGTH_SHORT).show()
                unselectedIcon = false

            } else {
                fab.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.unselected_bookmark))
                unselectedIcon = true
                Toast.makeText(requireContext(), itemRemoved, Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnSup.setOnClickListener {
            findNavController().navigate(R.id.action_viewAFrAllUsersFragment_to_sendSupportMsgToAFrFragment)
        }
    }


}