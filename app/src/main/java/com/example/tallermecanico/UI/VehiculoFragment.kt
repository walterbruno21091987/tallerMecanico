package com.example.tallermecanico.UI

import Vehicle
import VehicleRepository
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.example.tallermecanico.R
import com.example.tallermecanico.databinding.FragmentMenuBinding
import com.example.tallermecanico.databinding.FragmentVehiculoBinding
import com.example.tallermecanico.otros.exception.ExistingNumberPlateExcepcion

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [VehiculoFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class VehiculoFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var binding: FragmentVehiculoBinding
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
        binding=DataBindingUtil.inflate<FragmentVehiculoBinding>(inflater,R.layout.fragment_vehiculo,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

  binding.fbAgregarVehiculo.setOnClickListener {
      addVehicle()
  }
    }
    private fun addVehicle() {
       try{ try{val vehicleAdd: Boolean
        val numberPlate =binding.etPatente.text.toString()
        val idClient = binding.etCodClienteDueO.text.toString().toInt()
        val insuranceCap = binding.etTopeCobertura.text.toString().toDouble()
        val insuranceCoverageFrom =binding.etMontoMinimo.text.toString().toDouble()
        val newVehicle = Vehicle(numberPlate, idClient, insuranceCap, insuranceCoverageFrom)

       if(VehicleRepository.get(numberPlate)==null){
           vehicleAdd = VehicleRepository.add(newVehicle)
        if (vehicleAdd == true) {
          Toast.makeText(context,"VEHICULO AGREGADO CORRECTAMENTE",Toast.LENGTH_LONG).show()
           findNavController().navigate(R.id.action_vehiculoFragment_to_menuFragment)
        }}
        else {
            throw ExistingNumberPlateExcepcion("PATENTE EXISTENTE")

        }}catch (e:ExistingNumberPlateExcepcion){
           Toast.makeText(context,e.message,Toast.LENGTH_LONG).show()
       }}catch (ex:java.lang.NumberFormatException){
            Toast.makeText(context,"DEBE INGRESAR LOS DATOS REQUERIDOS CORRECTAMENTE",Toast.LENGTH_LONG).show()
        }
    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment VehiculoFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            VehiculoFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}