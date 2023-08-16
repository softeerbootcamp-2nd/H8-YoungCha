package com.youngcha.ohmycarset.ui.adapter.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.youngcha.ohmycarset.R
import com.youngcha.ohmycarset.databinding.PreliminariesLongButtonBinding
import com.youngcha.ohmycarset.model.tag.Gender
import com.youngcha.ohmycarset.viewmodel.UserTagViewModel

class GenderAdapter(
    private val viewModel: UserTagViewModel,
    private val onItemClick: (Gender) -> Unit
) :
    RecyclerView.Adapter<GenderAdapter.GenderViewHolder>() {

    var genderList = emptyList<Gender>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    inner class GenderViewHolder(private val binding: PreliminariesLongButtonBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(pos: Int, gender: Gender) {
            binding.tvData.text = gender.toString()

            binding.root.setOnClickListener {
                genderList.forEach { it.isSelected = false }
                gender.isSelected = true
                notifyDataSetChanged()
                onItemClick.invoke(gender)
            }

            if (genderList[pos].isSelected) {
                binding.vBackground.setBackgroundResource(R.drawable.btn_select_style)
                binding.tvData.setTextColor(
                    ContextCompat.getColor(
                        itemView.context,
                        R.color.cool_grey_black
                    )
                )
                binding.ivCheck.setImageResource(R.drawable.ic_check_on)
            } else {
                binding.vBackground.setBackgroundResource(R.drawable.btn_unselect_style)
                binding.tvData.setTextColor(
                    ContextCompat.getColor(
                        itemView.context,
                        R.color.cool_grey_003
                    )
                )
                binding.ivCheck.setImageResource(R.drawable.ic_check)
            }
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
        holder.bind(position, genderList[position])
    }

    override fun getItemCount(): Int = genderList.size
}
