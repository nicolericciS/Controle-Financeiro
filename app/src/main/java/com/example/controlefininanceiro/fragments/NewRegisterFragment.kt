package com.example.controlefininanceiro.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import com.example.controlefininanceiro.dao.AppDatabase
import com.example.controlefininanceiro.databinding.FragmentNewRegisterBinding
import com.example.controlefininanceiro.model.Category
import com.example.controlefininanceiro.model.Register

class NewRegisterFragment : Fragment() {

    private var _binding: FragmentNewRegisterBinding? = null
    private val binding: FragmentNewRegisterBinding get() = _binding!!
    private var database = AppDatabase
    private var registerId = 0L
    private lateinit var spinner: Spinner
    //private lateinit var category: Category
    private var categoryId = 0L


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentNewRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setAdapter()
        setListener()

        setFragmentResultListener("REGISTER_RESULT") { _, bundle ->
            val register = bundle.getSerializable("REGISTER") as Register
            registerId = register.id
            binding.edtTitle.setText(register.title)
            binding.edtValue.setText(register.value.toString())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun onSubmit(): Register {

        val title = binding.edtTitle.text.toString()
        val value = binding.edtValue.unMasked


        return Register(
            id = registerId,
            title = title,
            value = value.toLong(),
            categoryId = categoryId
        )
    }

    private fun submitInfoToFragment() {
        parentFragmentManager.popBackStack()
    }


    private fun setListener() {

        val registerDao = database.getInstance(requireContext()).registerDao()

        val btnSave = binding.btnSave
        btnSave.setOnClickListener {
            val newRegister = onSubmit()
            if (registerId > 0) {
                registerDao.update(newRegister)
            } else {
                registerDao.save(newRegister)

            }
            submitInfoToFragment()
        }
    }

    private fun setAdapter() {

        val categoryDao = database.getInstance(requireContext()).categoryDao()
        spinner = binding.spinnerCategory
        val categories = categoryDao.searchAll()

        val arrayAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            categories
        )
        spinner.adapter = arrayAdapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ){
                categoryId = spinner.selectedItemId+1
                Toast.makeText(requireContext(), "Item Selecionado", Toast.LENGTH_SHORT).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }


        }
    }
}