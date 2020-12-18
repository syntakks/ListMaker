package io.syntaks.listmaker.detail

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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import io.syntaks.listmaker.R
import io.syntaks.listmaker.model.ListDataManager
import io.syntaks.listmaker.model.TaskList
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_detail.*

class DetailFragment : Fragment() {

    private lateinit var list: TaskList
    private lateinit var listRecyclerView: RecyclerView
    private lateinit var addTaskFab: FloatingActionButton
    private lateinit var dataManager: ListDataManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataManager = ViewModelProvider(this).get(ListDataManager::class.java)

        // Pass the name of the TaskList to look up from the shared view model.
        // Instead of trying to pass data, just look it up on demand.
        arguments?.let {
            val args = DetailFragmentArgs.fromBundle(it)
            list = dataManager.readLists().filter { list -> list.name == args.listString }[0]
        }

        activity?.let {
            // Setup RecyclerView
            listRecyclerView = taskListRecyclerView
            listRecyclerView.layoutManager = LinearLayoutManager(it)
            listRecyclerView.adapter = TaskListAdapter(list)

            // Title
            it.toolbar?.title = list.name

            // Setup Fab
            addTaskFab = addTaskButton
            addTaskFab.setOnClickListener {
                showCreateTaskDialog()
            }
        }

    }

    private fun showCreateTaskDialog() {
        activity?.let {
            val taskEditText = EditText(it)
            taskEditText.inputType =
                InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_CAP_WORDS
            val dialog = AlertDialog.Builder(it)
                .setTitle(getString(R.string.task_to_add))
                .setView(taskEditText)
                .setPositiveButton(getString(R.string.add_task)) { dialog, _ ->
                    if (taskEditText.text.isNotEmpty()) {
                        val task = taskEditText.text.toString()
                        list.tasks.add(task)
                        dataManager.saveList(list)
                    } else {
                        dialog.dismiss()
                    }
                }
                .create()
            dialog.show()
            taskEditText.requestFocus()
            dialog.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)
        }
    }

}