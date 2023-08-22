package com.youngcha.ohmycarset.ui.adapter.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.youngcha.ohmycarset.databinding.PreliminariesLongButtonBinding
import com.youngcha.ohmycarset.model.tag.Tag
import com.youngcha.ohmycarset.viewmodel.UserTagViewModel

class GenderAdapter(
    private val viewModel: UserTagViewModel,
) :
    RecyclerView.Adapter<GenderAdapter.GenderViewHolder>() {

    var genderList = emptyList<Tag>()
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

    inner class GenderViewHolder(private val binding: PreliminariesLongButtonBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(tag: Tag) {
            binding.tag = tag
            binding.viewModel = viewModel
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenderViewHolder {
        return GenderViewHolder(
            PreliminariesLongButtonBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: GenderAdapter.GenderViewHolder, position: Int) {
        holder.bind(genderList[position])
    }

    override fun getItemCount(): Int = genderList.size
}
