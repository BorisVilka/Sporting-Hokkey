package com.sporting.hokkey

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.sporting.hokkey.databinding.FragmentGameBinding


class GameFragment : Fragment() {

    private lateinit var binding:FragmentGameBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentGameBinding.inflate(inflater,container,false)
        binding.button5.setOnClickListener {
            binding.gameView.togglePause()
            var dialog = MyDialog(binding.gameView.score, 1) {
                binding.gameView.togglePause()
            }
            dialog.show(requireActivity().supportFragmentManager,"TAG")

        }
        binding.gameView.setEndListener(object : GameView.Companion.EndListener {
            override fun end() {
                val set = requireContext().getSharedPreferences("prefs",Context.MODE_PRIVATE).getStringSet("score",HashSet<String>())!!.map { it.toInt() }.toMutableSet()
                if(!set.contains(binding.gameView.score)) {
                    set.add(binding.gameView.score)
                    requireContext().getSharedPreferences("prefs",Context.MODE_PRIVATE).edit().putStringSet("score",set.map { it.toString() }.toSet()).apply()
                }
                var dialog = MyDialog(binding.gameView.score, -1) {
                    val navController = Navigation.findNavController(requireActivity(),R.id.fragmentContainerView)
                    navController.navigate(R.id.gameFragment)
                }
                dialog.show(requireActivity().supportFragmentManager,"TAG")
            }
        })
        binding.button6.setOnClickListener {
            binding.gameView.throwBall()
        }
        return binding.root
    }


}