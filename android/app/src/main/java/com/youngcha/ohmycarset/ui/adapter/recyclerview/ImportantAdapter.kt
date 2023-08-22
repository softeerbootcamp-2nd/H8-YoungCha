package com.youngcha.ohmycarset.ui.adapter.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.youngcha.ohmycarset.databinding.PreliminariesShortButtonBinding
import com.youngcha.ohmycarset.model.tag.Tag
import com.youngcha.ohmycarset.viewmodel.UserTagViewModel

class ImportantAdapter(private val viewModel: UserTagViewModel) :
    RecyclerView.Adapter<ImportantAdapter.ImportantViewHolder>() {

    var importantList = emptyList<Tag>()
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

    inner class ImportantViewHolder(private val binding: PreliminariesShortButtonBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(tag: Tag) {
            binding.tag = tag
            binding.viewModel = viewModel
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImportantViewHolder {
        return ImportantViewHolder(
            PreliminariesShortButtonBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ImportantAdapter.ImportantViewHolder, position: Int) {
        holder.bind(importantList[position])
    }

    override fun getItemCount(): Int = importantList.size
}
