package com.sporting.hokkey

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.sporting.hokkey.databinding.FragmentStartBinding


class StartFragment : Fragment() {

    private lateinit var binding: FragmentStartBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentStartBinding.inflate(inflater,container,false)
        binding.button2.setOnClickListener {
            val navController = Navigation.findNavController(requireActivity(),R.id.fragmentContainerView)
            navController.navigate(R.id.scoreFragment)
        }
        binding.button3.setOnClickListener {
            val navController = Navigation.findNavController(requireActivity(),R.id.fragmentContainerView)
            navController.navigate(R.id.settingsFragment)
        }
        binding.button.setOnClickListener {
            val navController = Navigation.findNavController(requireActivity(),R.id.fragmentContainerView)
            navController.navigate(R.id.gameFragment)
        }
        return binding.root
    }


}