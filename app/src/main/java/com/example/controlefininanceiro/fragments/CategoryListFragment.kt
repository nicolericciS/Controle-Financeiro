package com.example.controlefininanceiro.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.text.TextUtils.replace
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import com.example.controlefininanceiro.R
import com.example.controlefininanceiro.adapter.CategoryAdapter
import com.example.controlefininanceiro.dao.AppDatabase
import com.example.controlefininanceiro.databinding.FragmentCategoryListBinding
import com.example.controlefininanceiro.model.Category


class CategoryListFragment : Fragment() {
    private var _binding: FragmentCategoryListBinding? = null
    private val binding: FragmentCategoryListBinding get() = _binding!!
    private lateinit var adapter: CategoryAdapter
    private var database = AppDatabase
    private lateinit var category: Category

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        activity?.title = "Categorias"
        _binding = FragmentCategoryListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupAdapter()
        setupLayout()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private fun setupLayout() {
        binding.fabNewCategory.setOnClickListener {
            findNavController().navigate(R.id.categoryList_to_newCategory)
        }
    }

    private fun setupAdapter() {

        val db = database.getInstance(requireContext())
        val categoryDao = db.categoryDao()

        adapter = CategoryAdapter(
            onClick = { categoryToBeEdited ->
                category = categoryToBeEdited


                val bundle = bundleOf(
                    "CATEGORY" to Category(
                        id = category.id,
                        name = category.name
                    )
                )
                setFragmentResult("CATEGORY_RESULT", bundle)
                findNavController().navigate(R.id.categoryList_to_newCategory)
            },
            onDeleteClick = { categoryToBeDeleted ->
                showDeleteConfirmation(categoryToBeDeleted) { category ->
                    categoryDao.delete(category)
                    adapter.deleteCategory(category)
                }
            }
        )

        binding.rvCategories.adapter = adapter
        adapter.updateCategory(categoryDao.searchAll())
    }

    private fun showDeleteConfirmation(category: Category, onConfirm: (Category) -> Unit) {
        val builder = AlertDialog.Builder(requireContext())
        builder.apply {
            setTitle("Confirmação")
            setMessage("Deseja excluir essa Categoria? ")
            setPositiveButton("Sim") { _, _ ->
                onConfirm(category)
            }
            setNegativeButton("Não") { dialog, _ ->
                dialog.dismiss()
            }
        }
        builder.show()
    }

}