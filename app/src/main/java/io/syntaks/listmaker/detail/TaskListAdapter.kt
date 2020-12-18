package io.syntaks.listmaker.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.syntaks.listmaker.R
import io.syntaks.listmaker.model.TaskList

class TaskListAdapter(private val taskList: TaskList) : RecyclerView.Adapter<TaskListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskListViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.task_list_view_holder, parent, false)
        return TaskListViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskListViewHolder, position: Int) {
        holder.bind(position, taskList.tasks[position])
    }

    override fun getItemCount(): Int {
        return taskList.tasks.size
    }

}