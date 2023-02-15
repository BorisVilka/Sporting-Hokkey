package com.sporting.hokkey

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sporting.hokkey.databinding.ListItemBinding

class MyAdapter(val ctx: Context): RecyclerView.Adapter<MyAdapter.Companion.MyHolder>() {

    var data = mutableListOf<String>()

    init {
        data = ctx.getSharedPreferences("prefs",Context.MODE_PRIVATE).getStringSet("score",HashSet<String>())!!.sortedBy { it.toInt() }.toMutableList()
        notifyDataSetChanged()
    }

    companion object {

        class MyHolder(val binding: ListItemBinding): RecyclerView.ViewHolder(binding.root) {

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
       return MyHolder(ListItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        holder.binding.textView.text = "${position+1}. ${data[position]}"
    }

    override fun getItemCount(): Int {
        return  data.size
    }


}