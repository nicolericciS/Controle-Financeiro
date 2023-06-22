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

        init {
            tvTitleRegister = itemView.tvTitle
            tvValue = itemView.tvValue
            imgBtnDelete = itemView.imgBtnDelete
            imgBtnEdit = itemView.imgBtnEdit
        }

        fun bind(
            register: Register,
            onDeleteClick: (Register) -> Unit,
            onClick: (Register) -> Unit
        ) {
            tvTitleRegister.text = register.title
            tvValue.text = register.value.toString()
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

    //fun addRegister(register: Register){
    // registers.add(register)
    // notifyItemInserted(registers.size-1)
    //}

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





