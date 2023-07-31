package com.example.controlefininanceiro.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.controlefininanceiro.databinding.ResItemListBinding
import com.example.controlefininanceiro.model.Register


class RegisterAdapter(
    private val onClick: (Register) -> Unit,
    private val onDeleteClick: (Register) -> Unit
) : RecyclerView.Adapter<RegisterAdapter.RegisterViewHolder>() {
    private val registers = mutableListOf<Register>()

    inner class RegisterViewHolder(
        itemView: ResItemListBinding
    ) :
        RecyclerView.ViewHolder(itemView.root) {

        private val tvTitleRegister: TextView
        private val tvValue: TextView
        private val imgBtnDelete: ImageButton
        private val imgBtnEdit: ImageButton
        private val tvCategory: TextView

        init {
            tvTitleRegister = itemView.tvTitle
            tvValue = itemView.tvValue
            imgBtnDelete = itemView.imgBtnDelete
            imgBtnEdit = itemView.imgBtnEdit
            tvCategory = itemView.tvCategory
        }

        fun bind(
            register: Register,
            onDeleteClick: (Register) -> Unit,
            onClick: (Register) -> Unit
        ) {
            tvTitleRegister.text = register.title
            val size = register.value.toString().length
            val valueMaskBefore = register.value.toString().substring(0, size - 2)
            val valueMaskAfter = register.value.toString().substring(size - 2, size)
            tvValue.text = "R$ ${valueMaskBefore},${valueMaskAfter}"
            tvCategory.text = "Categoria: ${register.categoryId}"
            imgBtnDelete.setOnClickListener {
                onDeleteClick(register)
            }
            imgBtnEdit.setOnClickListener {
                onClick(register)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RegisterViewHolder {
        return RegisterViewHolder(
            ResItemListBinding.inflate(
                LayoutInflater.from
                    (parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: RegisterViewHolder, position: Int) {
        holder.bind(
            registers[position], onDeleteClick, onClick
        )
    }

    override fun getItemCount(): Int = registers.size

    fun deleteRegister(register: Register) {
        val deletedPosition = registers.indexOf(register)
        registers.remove(register)
        notifyItemRemoved(deletedPosition)
    }

    fun update(registers: List<Register>) {
        this.registers.clear()
        this.registers.addAll(registers)
        notifyDataSetChanged()
    }

    fun isEmpty() = registers.isEmpty()
}





