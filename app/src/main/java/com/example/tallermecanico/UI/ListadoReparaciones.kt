package com.example.tallermecanico.UI

import RepairRepository
import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tallermecanico.R
import com.example.tallermecanico.databinding.FragmentListadoReparacionesBinding
import com.example.tallermecanico.otros.adapter.TicketAdapter

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val CODCLIENT= "param1"

lateinit var  binding:FragmentListadoReparacionesBinding

/**
 * A simple [Fragment] subclass.
 * Use the [ListadoReparaciones.newInstance] factory method to
 * create an instance of this fragment.
 */
class ListadoReparaciones : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null

    lateinit var contexto:Context
    lateinit var binding: FragmentListadoReparacionesBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(CODCLIENT)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.contexto=context
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=DataBindingUtil.inflate<FragmentListadoReparacionesBinding>(inflater,R.layout.fragment_listado_reparaciones,container,false)
        return binding.root
    }

    @SuppressLint("SuspiciousIndentation")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    var codCliente= arguments?.getString("COD_CLIENT")?.toInt()
    binding.RecyclerRepair.layoutManager=LinearLayoutManager(contexto)
        if(codCliente==0){

            binding.RecyclerRepair.adapter=TicketAdapter(RepairRepository.get())}
        else{
            if (codCliente != null) {
                binding.RecyclerRepair.adapter=TicketAdapter(RepairRepository.get().filter { it.clienteCode==codCliente.toInt() })
            }
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ListadoReparaciones.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String) =
            ListadoReparaciones().apply {
                arguments = Bundle().apply {
                    putString(CODCLIENT, param1)
                }
            }
    }
}