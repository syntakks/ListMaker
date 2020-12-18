package io.syntaks.listmaker.detail

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.task_list_view_holder.view.*

class TaskListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private var itemNumberLabel = itemView.taskNumberLabel
    private var itemNameLabel = itemView.taskDescriptionLabel

    fun bind(
        position: Int,
        task: String
    ) {
        val numberText = "${position + 1}:"
        itemNumberLabel.text = numberText
        itemNameLabel.text = task
    }
}