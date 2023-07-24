package com.example.controlefininanceiro.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import com.example.controlefininanceiro.dao.AppDatabase
import com.example.controlefininanceiro.databinding.FragmentListBinding
import com.example.controlefininanceiro.databinding.FragmentNewRegisterBinding
import com.example.controlefininanceiro.model.Register

class NewRegisterFragment : Fragment() {

    private var _binding: FragmentNewRegisterBinding? = null
    private val binding: FragmentNewRegisterBinding get() = _binding!!
    private var database = AppDatabase
    private var registerId = 0L

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
            value = value.toLong()
        )
    }

    private fun submitInfoToFragment() {
        parentFragmentManager.popBackStack()
    }
    

    private fun setListener() {
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
            submitInfoToFragment()
        }
    }
}