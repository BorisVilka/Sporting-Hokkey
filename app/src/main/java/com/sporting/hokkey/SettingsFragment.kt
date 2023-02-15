package com.sporting.hokkey

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.sporting.hokkey.databinding.FragmentSettingsBinding
import kotlin.properties.Delegates


class SettingsFragment : Fragment() {

    private lateinit var binding: FragmentSettingsBinding
    private var music by Delegates.notNull<Boolean>()
    private var sounds by Delegates.notNull<Boolean>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSettingsBinding.inflate(inflater,container,false)
        music = requireContext().getSharedPreferences("prefs",Context.MODE_PRIVATE).getBoolean("music",true)
        sounds = requireContext().getSharedPreferences("prefs",Context.MODE_PRIVATE).getBoolean("sounds",true)
        binding.textView4.text = if(music) "ON" else "OFF"
        binding.textView41.text = if(sounds) "ON" else "OFF"
        binding.imageView5.setOnClickListener {
            music = false
            requireContext().getSharedPreferences("prefs",Context.MODE_PRIVATE).edit().putBoolean("music",music).apply()
            binding.textView4.text = if(music) "ON" else "OFF"
        }
        binding.imageView6.setOnClickListener {
            music = true
            requireContext().getSharedPreferences("prefs",Context.MODE_PRIVATE).edit().putBoolean("music",music).apply()
            binding.textView4.text = if(music) "ON" else "OFF"
        }

        binding.imageView51.setOnClickListener {
            sounds = false
            requireContext().getSharedPreferences("prefs",Context.MODE_PRIVATE).edit().putBoolean("sounds",sounds).apply()
            binding.textView41.text = if(sounds) "ON" else "OFF"
        }
        binding.imageView61.setOnClickListener {
            sounds = true
            requireContext().getSharedPreferences("prefs",Context.MODE_PRIVATE).edit().putBoolean("sounds",sounds).apply()
            binding.textView41.text = if(sounds) "ON" else "OFF"
        }
        binding.button4.setOnClickListener {
            val navController = Navigation.findNavController(requireActivity(),R.id.fragmentContainerView)
            navController.popBackStack()
        }
        return binding.root
    }


}