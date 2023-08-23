package com.youngcha.ohmycarset.ui.adapter.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.youngcha.ohmycarset.databinding.PreliminariesShortButtonBinding
import com.youngcha.ohmycarset.data.model.tag.Tag
import com.youngcha.ohmycarset.viewmodel.UserTagViewModel

class StrengthAdapter(private val viewModel: UserTagViewModel) :
    RecyclerView.Adapter<StrengthAdapter.StrengthViewHolder>() {

    var strengthList = emptyList<Tag>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    init {
        setHasStableIds(true)
    }
    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    inner class StrengthViewHolder(private val binding: PreliminariesShortButtonBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(tag: Tag) {
            binding.tag=tag
            binding.viewModel=viewModel
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StrengthViewHolder {
        return StrengthViewHolder(
            PreliminariesShortButtonBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: StrengthAdapter.StrengthViewHolder, position: Int) {
        holder.bind(strengthList[position])
    }

    override fun getItemCount(): Int = strengthList.size
}
