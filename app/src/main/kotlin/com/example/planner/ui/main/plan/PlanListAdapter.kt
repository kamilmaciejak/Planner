package com.example.planner.ui.main.plan

import android.databinding.ViewDataBinding
import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.planner.database.PlanEntity
import com.example.planner.databinding.ItemDeadlinePlanBinding
import com.example.planner.databinding.ItemPlanBinding

class PlanItemCallback : DiffUtil.ItemCallback<PlanEntity>() {
    override fun areItemsTheSame(oldItem: PlanEntity, newItem: PlanEntity): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: PlanEntity, newItem: PlanEntity): Boolean {
        return oldItem == newItem
    }
}

class PlanListAdapter(
    private val onPlanClickListener: OnPlanClickListener
) : ListAdapter<PlanEntity, PlanListAdapter.ViewHolder>(PlanItemCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            DEFAULT_ITEM_VIEW_TYPE -> ViewHolder(ItemPlanBinding.inflate(layoutInflater, parent, false))
            DEADLINE_ITEM_VIEW_TYPE -> ViewHolder(ItemDeadlinePlanBinding.inflate(layoutInflater, parent, false))
            else -> throw IllegalArgumentException(viewType.toString())
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), onPlanClickListener)
    }

    override fun getItemViewType(position: Int): Int {
        return if (getItem(position).deadline == null) DEFAULT_ITEM_VIEW_TYPE else DEADLINE_ITEM_VIEW_TYPE
    }

    class ViewHolder(
        private val binding: ViewDataBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind (plan: PlanEntity, onPlanClickListener: OnPlanClickListener) {
            when (binding) {
                is ItemPlanBinding -> {
                    binding.plan = plan
                    binding.onPlanClickListener = onPlanClickListener
                }
                is ItemDeadlinePlanBinding-> {
                    binding.plan = plan
                    binding.onPlanClickListener = onPlanClickListener
                }
            }
            binding.executePendingBindings()
        }
    }

    companion object {
        private const val DEFAULT_ITEM_VIEW_TYPE = 1
        private const val DEADLINE_ITEM_VIEW_TYPE = 2
    }
}
