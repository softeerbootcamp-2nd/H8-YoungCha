package com.youngcha.ohmycarset.ui.adapter.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.youngcha.ohmycarset.R
import com.youngcha.ohmycarset.databinding.PreliminariesShortButtonBinding
import com.youngcha.ohmycarset.model.tag.Use
import com.youngcha.ohmycarset.viewmodel.UserTagViewModel

class UseAdapter(
    private val viewModel: UserTagViewModel,
    private val onItemClick: (Use) -> Unit
) :
    RecyclerView.Adapter<UseAdapter.UseViewHolder>() {

    var useList = emptyList<Use>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    inner class UseViewHolder(private val binding: PreliminariesShortButtonBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(pos: Int, use: Use) {
            binding.tvData.text = use.toString()

            binding.root.setOnClickListener {
                use.isSelected = !use.isSelected
                notifyDataSetChanged()
                onItemClick.invoke(use)
            }

            if (useList[pos].isSelected) {
                binding.vBackground.setBackgroundResource(R.drawable.btn_select_style)
                binding.tvData.setTextColor(
                    ContextCompat.getColor(
                        itemView.context,
                        R.color.cool_grey_black
                    )
                )
                binding.ivSelect.setImageResource(R.drawable.ic_check_on)
            } else {
                binding.vBackground.setBackgroundResource(R.drawable.btn_unselect_style)
                binding.tvData.setTextColor(
                    ContextCompat.getColor(
                        itemView.context,
                        R.color.cool_grey_003
                    )
                )
                binding.ivSelect.setImageResource(R.drawable.ic_check)
            }
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
        holder.bind(position, useList[position])
    }

    override fun getItemCount(): Int = useList.size
}
