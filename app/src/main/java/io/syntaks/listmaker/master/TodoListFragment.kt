package io.syntaks.listmaker.master

import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.syntaks.listmaker.R
import io.syntaks.listmaker.model.ListDataManager
import io.syntaks.listmaker.model.TaskList
import kotlinx.android.synthetic.main.fragment_todo_list.*
import kotlinx.android.synthetic.main.fragment_todo_list.view.*


class TodoListFragment : Fragment(), TodoListAdapter.TodoListClickListener {

    private lateinit var dataManager: ListDataManager
    private lateinit var todoListRecyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_todo_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.let {
            dataManager = ViewModelProvider(this).get(ListDataManager::class.java)
        }
        val taskLists = dataManager.readLists()
        todoListRecyclerView = view.recyclerView
        todoListRecyclerView.layoutManager = LinearLayoutManager(activity)
        todoListRecyclerView.adapter = TodoListAdapter(taskLists, this)

        fab.setOnClickListener {
            showCreateTodoListItemDialog()
        }
    }

    override fun listItemClicked(list: TaskList) {
        showTaskListItems(list)
    }

    private fun addList(taskList: TaskList) {
        dataManager.saveList(taskList)
        val todoAdapter = todoListRecyclerView.adapter as TodoListAdapter
        todoAdapter.addList(taskList)
    }

    private fun showCreateTodoListItemDialog() {
        activity?.let {
            val todoTitleEditText = EditText(it)
            todoTitleEditText.inputType =
                InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_CAP_WORDS
            val dialog = AlertDialog.Builder(it)
                .setTitle(getString(R.string.create_todo_item_dialog_title))
                .setView(todoTitleEditText)
                .setPositiveButton(getString(R.string.create_todo_item_dialog_button)) { dialog, _ ->
                    if (todoTitleEditText.text.isNotEmpty()) {
                        val taskList = TaskList(todoTitleEditText.text.toString())
                        addList(taskList)
                        showTaskListItems(taskList)
                    } else {
                        dialog.dismiss()
                    }
                }
                .create()
            dialog.show()
            todoTitleEditText.requestFocus()
            dialog.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)
        }
    }

    private fun showTaskListItems(list: TaskList) {
        view?.let {
            val action =
                TodoListFragmentDirections.actionTodoListFragmentToDetailFragment(list.name)
            it.findNavController().navigate(action)
        }
    }

}