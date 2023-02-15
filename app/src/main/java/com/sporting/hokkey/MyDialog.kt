package com.sporting.hokkey

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment
import androidx.navigation.Navigation

class MyDialog(val score: Int, val code: Int, var onD: () -> Unit): DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        var builder = AlertDialog.Builder(requireContext())
        var binding = com.sporting.hokkey.databinding.DialogBinding.inflate(layoutInflater,null,false)
        if(code>0) {
            binding.textView6.background = requireContext().getDrawable(R.drawable.round_gret)
            binding.textView6.text = "PAUSED"
            binding.button7.text = "CONTINUE"
        }
        binding.textView7.text = "$score"
         binding.button7.setOnClickListener {
            dismiss()
             if(code<0) {
                 onD()
             }
        }
        binding.button8.setOnClickListener {
            val navController = Navigation.findNavController(requireActivity(),R.id.fragmentContainerView)
            navController.popBackStack()
            dismiss()
        }
        builder = builder.setView(binding.root)
        val dialog = builder.create()
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        return dialog
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        if(code>0) onD()
        else {
            val navController = Navigation.findNavController(requireActivity(),R.id.fragmentContainerView)
            navController.popBackStack()
        }
    }
}