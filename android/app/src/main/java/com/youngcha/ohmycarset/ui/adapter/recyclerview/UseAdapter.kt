package com.youngcha.ohmycarset.ui.adapter.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.youngcha.ohmycarset.databinding.PreliminariesShortButtonBinding
import com.youngcha.ohmycarset.data.model.tag.Tag
import com.youngcha.ohmycarset.viewmodel.UserTagViewModel

class UseAdapter(private val viewModel: UserTagViewModel) :
    RecyclerView.Adapter<UseAdapter.UseViewHolder>() {

    var useList = emptyList<Tag>()
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

    inner class UseViewHolder(private val binding: PreliminariesShortButtonBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(tag: Tag) {
            binding.tag = tag
            binding.viewModel = viewModel
            binding.executePendingBindings()
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UseViewHolder {
        return UseViewHolder(
            PreliminariesShortButtonBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: UseAdapter.UseViewHolder, position: Int) {
        holder.bind(useList[position])
    }

    override fun getItemCount(): Int = useList.size
}
