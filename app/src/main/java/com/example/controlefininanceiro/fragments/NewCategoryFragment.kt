package com.example.controlefininanceiro.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.lifecycleScope
import com.example.controlefininanceiro.dao.AppDatabase
import com.example.controlefininanceiro.databinding.FragmentNewCategoryBinding
import com.example.controlefininanceiro.model.Category
import kotlinx.coroutines.launch

class NewCategoryFragment: Fragment() {

    private var _binding: FragmentNewCategoryBinding? = null
    private val binding: FragmentNewCategoryBinding get() = _binding!!
    private var database = AppDatabase
    private var categoryId = 0L


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentNewCategoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListener()
        setFragmentResultListener("CATEGORY_RESULT") { _, bundle ->
            val category = bundle.getSerializable("CATEGORY") as Category
            categoryId = category.id
            binding.edtName.setText(category.name)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun onSubmit(): Category {

        val name = binding.edtName.text.toString()

        return Category(
            id = categoryId,
            name = name
        )
    }

    private fun submitInfoToFragment() {
        parentFragmentManager.popBackStack()
    }


    private fun setListener() {
        val db = database.getInstance(requireContext())
        val categoryDao = db.categoryDao()

        val btnSave = binding.btnSave
        btnSave.setOnClickListener {
            val newCategory = onSubmit()
            lifecycleScope.launch {

                if (categoryId > 0) {
                    categoryDao.update(newCategory)
                } else {
                    categoryDao.save(newCategory)

                }
            }
            submitInfoToFragment()
        }
    }
}