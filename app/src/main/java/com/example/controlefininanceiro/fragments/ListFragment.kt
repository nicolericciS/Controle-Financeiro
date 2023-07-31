package com.example.controlefininanceiro.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.fragment.app.setFragmentResult
import com.example.controlefininanceiro.R
import com.example.controlefininanceiro.adapter.RegisterAdapter
import com.example.controlefininanceiro.dao.AppDatabase
import com.example.controlefininanceiro.databinding.FragmentListBinding
import com.example.controlefininanceiro.model.Register

class ListFragment : Fragment() {

    private var _binding: FragmentListBinding? = null
    private val binding: FragmentListBinding get() = _binding!!
    private lateinit var adapter: RegisterAdapter
    private var database = AppDatabase
    private lateinit var register: Register

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupAdapter()
        setupLayout()
        onDataUpdate()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun onDataUpdate() = if (adapter.isEmpty()) {
        binding.rvRegisters.visibility = View.GONE
        binding.tvNoData.visibility = View.VISIBLE
    } else {
        binding.rvRegisters.visibility = View.VISIBLE
        binding.tvNoData.visibility = View.GONE
    }

    private fun setupLayout() {
        binding.fabNewRegister.setOnClickListener {

            parentFragmentManager.commit {
                setReorderingAllowed(true)
                replace<NewRegisterFragment>(R.id.fragment_container_view)
                addToBackStack(null)
            }
        }
    }

    private fun setupAdapter() {
        val db = database.getInstance(requireContext())
        val registerDao = db.registerDao()

        adapter = RegisterAdapter(
            onDeleteClick = { registerTobeDeleted ->
                showDeleteConfirmation(registerTobeDeleted) { register ->
                    adapter.deleteRegister(register)
                    registerDao.delete(register)
                }
            },
            onClick = { registerTobeEdited ->
                register = registerTobeEdited

                val bundle = bundleOf(
                    "REGISTER" to Register(
                        id = register.id,
                        title = register.title,
                        value = register.value,
                        categoryId = register.categoryId
                    )
                )
                setFragmentResult("REGISTER_RESULT", bundle)
                parentFragmentManager.commit {
                    setReorderingAllowed(true)
                    replace<NewRegisterFragment>(R.id.fragment_container_view)
                    addToBackStack(null)
                }
            }
        )

        binding.rvRegisters.adapter = adapter
        adapter.update(registerDao.searchAll())
    }

    private fun showDeleteConfirmation(register: Register, onConfirm: (Register) -> Unit) {
        val builder = AlertDialog.Builder(requireContext())
        builder.apply {
            setTitle("Confirmação")
            setMessage("Deseja excluir esse registro? ")
            setPositiveButton("Sim") { _, _ ->
                onConfirm(register)
            }
            setNegativeButton("Não") { dialog, _ ->
                dialog.dismiss()
            }
        }
        builder.show()
    }


}
