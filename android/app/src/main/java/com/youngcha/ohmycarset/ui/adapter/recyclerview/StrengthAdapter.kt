package com.youngcha.ohmycarset.ui.adapter.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.youngcha.ohmycarset.R
import com.youngcha.ohmycarset.databinding.PreliminariesShortButtonBinding
import com.youngcha.ohmycarset.model.tag.Strength
import com.youngcha.ohmycarset.viewmodel.UserTagViewModel

class StrengthAdapter(private val viewModel: UserTagViewModel) :
    RecyclerView.Adapter<StrengthAdapter.StrengthViewHolder>() {

    var strengthList = emptyList<Strength>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    private var listener: ItemClickListener? = null

    interface ItemClickListener {
        fun onItemClickListener(strength: Strength)
    }

    inner class StrengthViewHolder(private val binding: PreliminariesShortButtonBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(pos: Int, strength: Strength) {
            binding.tvData.text = strength.toString()

            binding.root.setOnClickListener {
                listener?.onItemClickListener(strength)
                notifyItemChanged(pos)
            }

            if (strengthList[pos].isSelected) {

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
        holder.bind(position,strengthList[position])
    }

    override fun getItemCount(): Int = strengthList.size

    fun setStrengthClickEvent(onClick: (Strength) -> Unit) {
        listener = object : ItemClickListener {
            override fun onItemClickListener(strength: Strength) {
                strength.isSelected=!strength.isSelected
                notifyDataSetChanged()
                onClick.invoke(strength)
            }
        }
    }
}