package com.youngcha.ohmycarset.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.youngcha.ohmycarset.databinding.ItemTrimSelectBinding
import com.youngcha.ohmycarset.models.Trim
import com.youngcha.ohmycarset.viewmodels.TrimSelectViewModel

class TrimSelectAdapter(
    private val trims: List<Trim>,
    private val viewModel: TrimSelectViewModel): RecyclerView.Adapter<TrimSelectAdapter.TrimSelectViewHolder>() {

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

    class TrimSelectViewHolder(val binding: ItemTrimSelectBinding) : RecyclerView.ViewHolder(binding.root)
}