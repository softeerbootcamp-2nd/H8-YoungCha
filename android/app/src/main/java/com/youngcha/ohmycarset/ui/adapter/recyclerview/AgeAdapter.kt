package com.youngcha.ohmycarset.ui.adapter.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.youngcha.ohmycarset.R
import com.youngcha.ohmycarset.databinding.PreliminariesLongButtonBinding
import com.youngcha.ohmycarset.model.tag.Age
import com.youngcha.ohmycarset.viewmodel.UserTagViewModel

class AgeAdapter(private val viewModel: UserTagViewModel) :
    RecyclerView.Adapter<AgeAdapter.AgeViewHolder>() {

    var ageList = emptyList<Age>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    private var listener: ItemClickListener? = null

    interface ItemClickListener {
        fun onItemClickListener(age: Age)
    }

    inner class AgeViewHolder(private val binding: PreliminariesLongButtonBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(pos: Int, age: Age) {
            binding.tvData.text = age.toString()

            binding.root.setOnClickListener {
                listener?.onItemClickListener(age)
                notifyItemChanged(pos)
            }

            if (ageList[pos].isSelected) {

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
        holder.bind(position, ageList[position])
    }

    fun setAgeClickEvent(onClick: (Age) -> Unit) {
        listener = object : ItemClickListener {
            override fun onItemClickListener(age: Age) {
                ageList.forEach { it.isSelected = false }
                age.isSelected = true
                notifyDataSetChanged()
                onClick.invoke(age)
            }
        }
    }
}