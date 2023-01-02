package com.example.tallermecanico.UI

import NoStockExcepcion
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import com.example.tallermecanico.R
import com.example.tallermecanico.databinding.FragmentReparacionBinding
import com.example.tallermecanico.databinding.FragmentSparePartBinding
import com.example.tallermecanico.otros.entidades.Repair
import existingCodeExcepcion
import java.time.DateTimeException
import java.time.LocalDate

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ReparacionFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ReparacionFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var binding: FragmentReparacionBinding
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
        // Inflate the layout for this fragment
       binding=DataBindingUtil.inflate(inflater,R.layout.fragment_reparacion,container,false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val totalListSparePart: MutableMap<Int, Int> = mutableMapOf()
        binding.btAgregarRepuesto.setOnClickListener {
      try{  val codSpare = binding.etCodigoRepuesto.text.toString().toInt()
        val
                sparePart = SparePartRepository.get(codSpare)
        if (sparePart != null) {

            val quantity = binding.etCantidad.text.toString().toInt()
            if (quantity <= sparePart.unitinStock){ totalListSparePart.put(codSpare, quantity)
            Toast.makeText(context,"REPUESTO AGREGADO A LA REPARACION",Toast.LENGTH_LONG).show()}
            else {

                throw NoStockExcepcion("no existe la cantidad necesaria")
            }
        }

    }catch (e:NoStockExcepcion){
    Toast.makeText(context,e.message,Toast.LENGTH_LONG).show()
    }
        }
   binding.btAgregarReparacion.setOnClickListener {
      if (addRepair(totalListSparePart)){
          Toast.makeText(context,"reparacion agregada correctamente",Toast.LENGTH_LONG).show()
      }
   }

    }
    @RequiresApi(Build.VERSION_CODES.O)
    private fun addRepair(totalListSparePart:MutableMap<Int,Int>):Boolean {
        try{try{ try {

            val codReair:Int=binding.etCodigoReparacion.text.toString().toInt()
            if(Repair.existingCode(codReair))throw existingCodeExcepcion("codigo de reparacion existente")
            val codClient = binding.etCodClienteReparacion.text.toString().toInt()
            val year =binding.etAnio.text.toString().toInt()
            val mhont =binding.etMes.text.toString().toInt()
            val day=binding.etDia.text.toString().toInt()
            val completionDate = LocalDate.of(year, mhont, day)
            val laborPrice =binding.etPrecioManoDeObra.text.toString().toDouble()
            val hoursWorked =binding.etHorasTrabajadas.text.toString().toInt()
            val newRepair = Repair(codReair, codClient, completionDate, totalListSparePart, hoursWorked, laborPrice)
            return RepairRepository.add(newRepair)
        } catch (exc: java.lang.NumberFormatException) {
            Toast.makeText(context,"no le ah dado un valor correcto a algun atributo debe ingresar un numero ${exc.printStackTrace()}",Toast.LENGTH_LONG).show()
            return false
        }}catch (e: DateTimeException){
            Toast.makeText(context,"ah ingresado un mes o un dia incorrecto",Toast.LENGTH_LONG).show()
            return  false
        }}catch (e:existingCodeExcepcion) {
            Toast.makeText(context,e.message,Toast.LENGTH_LONG).show()
            return false
        }

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ReparacionFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ReparacionFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}