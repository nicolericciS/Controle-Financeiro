package com.example.controlefininanceiro.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.controlefininanceiro.databinding.CategoryItemListBinding
import com.example.controlefininanceiro.model.Category

class CategoryAdapter(
    private val onClick: (Category) -> Unit,
    private val onDeleteClick: (Category) -> Unit
) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {
    private val categories = mutableListOf<Category>()

    inner class CategoryViewHolder(
        itemView: CategoryItemListBinding
    ) :
        RecyclerView.ViewHolder(itemView.root) {

        private val tvName: TextView
        private val imgBtnDelete: ImageButton
        private val imgBtnEdit: ImageButton

        init {
            tvName = itemView.tvName
            imgBtnDelete = itemView.imgBtnDelete
            imgBtnEdit = itemView.imgBtnEdit
        }

        fun bind(
            category: Category,
            onDeleteClick: (Category) -> Unit,
            onClick: (Category) -> Unit
        ) {
            tvName.text = category.name
            imgBtnDelete.setOnClickListener {
                onDeleteClick(category)
            }
            imgBtnEdit.setOnClickListener {
                onClick(category)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CategoryAdapter.CategoryViewHolder {
        return CategoryViewHolder(
            CategoryItemListBinding.inflate(
                LayoutInflater.from
                    (parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: CategoryAdapter.CategoryViewHolder, position: Int) {
        holder.bind(
            categories[position], onDeleteClick, onClick
        )
    }

    override fun getItemCount(): Int = categories.size

    fun addCategory(category: Category) {
        categories.add(category)
        notifyItemInserted(categories.size - 1)
    }


    fun deleteCategory(category: Category) {
        val deletedPosition = categories.indexOf(category)
        categories.remove(category)
        notifyItemRemoved(deletedPosition)
    }

    fun updateCategory(categories: List<Category>) {
        this.categories.clear()
        this.categories.addAll(categories)
        notifyDataSetChanged()
    }


}