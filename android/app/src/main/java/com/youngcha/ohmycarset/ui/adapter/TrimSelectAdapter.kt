package com.youngcha.ohmycarset.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.youngcha.ohmycarset.databinding.ItemTrimSelectBinding
import com.youngcha.ohmycarset.model.Trim
import com.youngcha.ohmycarset.viewmodel.TrimSelectViewModel

class TrimSelectAdapter(
    private val viewModel: TrimSelectViewModel
) : RecyclerView.Adapter<TrimSelectAdapter.TrimSelectViewHolder>() {

    private var trims: List<Trim> = emptyList()

    @SuppressLint("NotifyDataSetChanged")
    fun updateTrims(trims: List<Trim>) {
        this.trims = trims
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrimSelectViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemTrimSelectBinding.inflate(inflater, parent, false)
        return TrimSelectViewHolder(binding, viewModel)
    }

    override fun onBindViewHolder(holder: TrimSelectViewHolder, position: Int) {
        holder.bind(trims[position])
    }

    override fun getItemCount(): Int = trims.size

    class TrimSelectViewHolder(
        private val binding: ItemTrimSelectBinding,
        viewModel: TrimSelectViewModel
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.viewmodel = viewModel
        }

        fun bind(trim: Trim) {
            binding.trim = trim
            binding.executePendingBindings()
        }
    }
}