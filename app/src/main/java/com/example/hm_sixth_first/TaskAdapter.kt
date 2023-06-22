package com.example.hm_sixth_first

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.hm_sixth_first.databinding.ItemTaskBinding

class TaskAdapter(
     var taskList: List<Model>,
    private var onClick: (Model) -> Unit
) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        return TaskViewHolder(
            ItemTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.onBind(taskList[position])
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

            itemView.setOnLongClickListener {
                onClick(model)
                false
            }
        }

       private fun onTaskCheckChanged(position: Int, isChecked: Boolean) {
           taskList[position].done()
           notifyItemChanged(position,isChecked)
       }
   }
}
