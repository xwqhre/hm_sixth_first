package com

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.hm_sixth_first.databinding.ItemTaskBinding

class TaskAdapter(
    var taskList: List<Model>,
    var onClick: (Model) -> Unit,
    private val onTaskCheckChanged: (position: Int, isChecked: Boolean) -> Unit
) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        return TaskViewHolder(
            ItemTaskBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val model = taskList[position]
        holder.onBind(model)
    }

    override fun getItemCount(): Int {
        return taskList.size
    }


   inner class TaskViewHolder(private val binding: ItemTaskBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(model: Model) {
            binding.tittle.text = model.title
            binding.checkbox.isChecked = model.next

            binding.checkbox.setOnCheckedChangeListener(null)
            binding.checkbox.setOnCheckedChangeListener { _, isChecked ->
                onTaskCheckChanged(adapterPosition, isChecked)
            }

            itemView.setOnClickListener {
                onClick(model)
                false
            }
        }

       private fun onTaskCheckChanged(position: Int, isChecked: Boolean) {
           val task = taskList[position]
           task.done()
           notifyItemChanged(position,isChecked)
       }
   }
    private fun onTaskCheckChanged(position: Int, isChecked: Boolean) {
        val task = taskList[position]
        task.done()
        notifyItemChanged(position,isChecked)
    }
}
