package com.youngcha.ohmycarset.ui.adapter.recyclerview

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.youngcha.ohmycarset.databinding.ItemTrimSelectBinding
import com.youngcha.ohmycarset.data.model.TrimCategory
import com.youngcha.ohmycarset.viewmodel.TrimSelectViewModel

class TrimSelectAdapter(
    private val viewModel: TrimSelectViewModel
) : RecyclerView.Adapter<TrimSelectAdapter.TrimSelectViewHolder>() {

    private var trimCategories: List<TrimCategory> = emptyList()

    @SuppressLint("NotifyDataSetChanged")
    fun updateTrims(trimCategories: List<TrimCategory>) {
        this.trimCategories = trimCategories
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrimSelectViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemTrimSelectBinding.inflate(inflater, parent, false)
        return TrimSelectViewHolder(binding, viewModel)
    }

    override fun onBindViewHolder(holder: TrimSelectViewHolder, position: Int) {
        holder.bind(trimCategories[position])
    }

    override fun getItemCount(): Int = trimCategories.size

    class TrimSelectViewHolder(
        private val binding: ItemTrimSelectBinding,
        viewModel: TrimSelectViewModel
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.viewmodel = viewModel
        }

        fun bind(trimCategory: TrimCategory) {
            binding.trimCategory = trimCategory
            binding.executePendingBindings()
        }
    }
}