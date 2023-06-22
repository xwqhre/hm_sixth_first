package com.example.hm_sixth_first

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hm_sixth_first.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel:  ViewModel
    private lateinit var taskAdapter: TaskAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(ViewModel::class.java)
        taskAdapter = TaskAdapter(emptyList(), this::onLongClik)// { position, isChecked ->
           // viewModel.markTaskAsDone(position) }

        binding.recycleView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = taskAdapter
        }

        viewModel.taskList.observe(this) { taskList ->
            if (taskList != null) {
                taskAdapter.taskList = taskList
            }
            binding.recycleView.post {
                taskAdapter.notifyDataSetChanged()
            }
        }

        binding.btnNext.setOnClickListener {
            val intent = Intent(this, TaskActivity::class.java)
            addTaskLauncher.launch(intent)
        }
    }

    private fun onLongClik(task: Model) {
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.setTitle("Вы точно хотите удалить?")
        alertDialog.setNegativeButton("Нет", object : DialogInterface.OnClickListener {
            override fun onClick(dialog: DialogInterface?, which: Int) {
                dialog?.cancel()
            }
        })

        alertDialog.setPositiveButton("Да", object : DialogInterface.OnClickListener {
            override fun onClick(dialog: DialogInterface?, with: Int) {
                viewModel.deleteTask(0)
            }
        })
        alertDialog.create().show()
    }

    private val addTaskLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            val taskTitle = data?.getStringExtra("title")
            if (!taskTitle.isNullOrEmpty()) {
                viewModel.addTask(taskTitle)
            }
        }
    }

    fun launchAddTaskActivity() {
        val intent = Intent(this, TaskActivity::class.java)
        addTaskLauncher.launch(intent)
    }
//
//
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (requestCode == REQUEST_CODE_ADD_TASK && resultCode == Activity.RESULT_OK) {
//            val taskTitle = data?.getStringExtra("title")
//            if (!taskTitle.isNullOrEmpty()) {
//                viewModel.addTask(taskTitle)
//            }
//
//        }
//    }

    companion object {
        private const val REQUEST_CODE_ADD_TASK = 1
    }
}