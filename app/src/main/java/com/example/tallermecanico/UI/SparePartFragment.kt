package com.example.tallermecanico.UI

import SparePart
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.example.tallermecanico.R
import com.example.tallermecanico.databinding.FragmentSparePartBinding
import existingCodeExcepcion

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SparePartFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SparePartFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var binding:FragmentSparePartBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=DataBindingUtil.inflate(inflater, R.layout.fragment_spare_part,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.fbAgregarRepuesto.setOnClickListener {
            AddSparePart()
        }
    }
    private fun AddSparePart() {
        try {
            try {

                val code =binding.etCodRepuesto.text.toString().toInt()
                if (SparePart.existingCode(code)) throw existingCodeExcepcion("Codigo existente")
                val nameSparePart = binding.etNombreRepuesto.text.toString()
                val price = binding.etPrecio.text.toString().toDouble()
                val unitInStock = binding.etStock.text.toString().toInt()
                val sparePart = SparePart(code, nameSparePart, price, unitInStock)
                val sparePartAdder = SparePartRepository.add(sparePart)
                if (sparePartAdder == true) {
                   Toast.makeText(context,"se agrego el repuesto correctamente",Toast.LENGTH_LONG).show()
                       findNavController().navigate(R.id.action_sparePartFragment_to_menuFragment)
                } else {
                    Toast.makeText(context,"no se pudo agregar el repuesto",Toast.LENGTH_LONG).show()
                }
            } catch (e: existingCodeExcepcion) {
                Toast.makeText(context,e.message,Toast.LENGTH_LONG).show()

            }} catch (e: NumberFormatException) {
            Toast.makeText(context,"no le ah dado un valor correcto a algun atributo debe ingresar un numero ${e.printStackTrace()}",Toast.LENGTH_LONG).show()
        }
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SparePartFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SparePartFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}