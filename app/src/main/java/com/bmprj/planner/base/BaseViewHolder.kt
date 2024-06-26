package com.bmprj.planner.base

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

class BaseViewHolder<DB:ViewDataBinding>(val binder:DB): RecyclerView.ViewHolder(binder.root) {
}