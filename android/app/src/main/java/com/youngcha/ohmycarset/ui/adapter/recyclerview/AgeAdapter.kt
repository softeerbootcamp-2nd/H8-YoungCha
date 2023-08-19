package com.youngcha.ohmycarset.ui.adapter.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.youngcha.ohmycarset.databinding.PreliminariesLongButtonBinding
import com.youngcha.ohmycarset.model.tag.Tag
import com.youngcha.ohmycarset.viewmodel.UserTagViewModel

class AgeAdapter(private val viewModel: UserTagViewModel) :
    RecyclerView.Adapter<AgeAdapter.AgeViewHolder>() {

    var ageList = emptyList<Tag>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    inner class AgeViewHolder(private val binding: PreliminariesLongButtonBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(tag: Tag) {
            binding.tag = tag
            binding.viewModel = viewModel
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AgeViewHolder {
        return AgeViewHolder(
            PreliminariesLongButtonBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = ageList.size

    override fun onBindViewHolder(holder: AgeViewHolder, position: Int) {
        holder.bind(ageList[position])
    }
}
