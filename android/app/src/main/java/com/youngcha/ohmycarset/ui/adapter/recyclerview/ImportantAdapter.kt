package com.youngcha.ohmycarset.ui.adapter.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.youngcha.ohmycarset.R
import com.youngcha.ohmycarset.databinding.PreliminariesShortButtonBinding
import com.youngcha.ohmycarset.model.tag.Important
import com.youngcha.ohmycarset.viewmodel.UserTagViewModel

class ImportantAdapter(private val viewModel: UserTagViewModel) :
    RecyclerView.Adapter<ImportantAdapter.ImportantViewHolder>() {

    var importantList = emptyList<Important>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    private var listener: ItemClickListener? = null

    interface ItemClickListener {
        fun onItemClickListener(important: Important)
    }

    inner class ImportantViewHolder(private val binding: PreliminariesShortButtonBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(pos: Int, important: Important) {
            binding.tvData.text = important.toString()

            binding.root.setOnClickListener {
                listener?.onItemClickListener(important)
                notifyItemChanged(pos)
            }

            if (importantList[pos].isSelected) {
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
        holder.bind(position,importantList[position])
    }

    override fun getItemCount(): Int = importantList.size

    fun setImportantClickEvent(onClick: (Important) -> Unit) {
        listener = object : ItemClickListener {
            override fun onItemClickListener(important: Important) {
                important.isSelected=!important.isSelected
                notifyDataSetChanged()
                onClick.invoke(important)
            }
        }
    }
}