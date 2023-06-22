package com.example.controlefininanceiro.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.room.Room
import com.example.controlefininanceiro.dao.AppDatabase
import com.example.controlefininanceiro.model.Register
import com.example.controlefininanceiro.databinding.FragmentNewRegisterBinding

class NewRegisterFragment : Fragment() {

    private lateinit var binding: FragmentNewRegisterBinding
    private var database = AppDatabase
    private var registerId = 0L

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentNewRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        saveButton()
        setFragmentResultListener("REGISTER_RESULT") { _, bundle ->
            val register = bundle.getSerializable("REGISTER") as Register
            registerId = register.id
            binding.edtTitle.setText(register.title)
            binding.edtValue.setText(register.value.toString())
        }
    }

    private fun onSubmit(): Register {

        val title = binding.edtTitle.text.toString()
        val value = binding.edtValue.text.toString()

        submitInfoToFragment()

        return Register(
            id = registerId,
            title = title,
            value = value.toLong()
        )
    }

    private fun submitInfoToFragment() {
        parentFragmentManager.popBackStack()
    }

    private fun saveButton() {
        val db = database.getInstance(requireContext())
        val registerDao = db.registerDao()

        val btnSave = binding.btnSave
        btnSave.setOnClickListener {
            val newRegister = onSubmit()
            if (registerId > 0) {
                registerDao.update(newRegister)
            } else {
                registerDao.save(newRegister)

            }
        }
    }
}