package io.syntaks.listmaker.master

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import io.syntaks.listmaker.model.TaskList
import kotlinx.android.synthetic.main.todo_list_view_holder.view.*

class TodoListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private var itemNumberLabel = itemView.itemNumber
    private var itemNameLabel = itemView.itemLabel

    fun bind(
        position: Int,
        taskList: TaskList,
        clickListener: TodoListAdapter.TodoListClickListener
    ) {
        val numberText = "${position + 1}:"
        itemNumberLabel.text = numberText
        itemNameLabel.text = taskList.name
        itemView.setOnClickListener {
            clickListener.listItemClicked(taskList)
        }
    }
}