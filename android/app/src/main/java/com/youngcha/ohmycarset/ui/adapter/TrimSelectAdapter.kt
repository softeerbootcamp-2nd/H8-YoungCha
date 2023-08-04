package com.youngcha.ohmycarset.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.youngcha.ohmycarset.databinding.ItemTrimSelectBinding
import com.youngcha.ohmycarset.model.Trim
import com.youngcha.ohmycarset.viewmodel.TrimSelectViewModel

class TrimSelectAdapter(
    private var trims: List<Trim>,
    private val viewModel: TrimSelectViewModel
) : RecyclerView.Adapter<TrimSelectAdapter.TrimSelectViewHolder>() {

    @SuppressLint("NotifyDataSetChanged")
    fun updateTrims(trims: List<Trim>) {
        this.trims = trims
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrimSelectViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemTrimSelectBinding.inflate(inflater, parent, false)
        return TrimSelectViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TrimSelectViewHolder, position: Int) {
        val trim = trims[position]
        holder.binding.trim = trim
        holder.binding.viewmodel = viewModel
        holder.binding.executePendingBindings()
    }

    override fun getItemCount(): Int = trims.size

    class TrimSelectViewHolder(val binding: ItemTrimSelectBinding) :
        RecyclerView.ViewHolder(binding.root)
}