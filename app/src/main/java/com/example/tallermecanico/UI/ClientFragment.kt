package com.example.tallermecanico.UI

import ClientRepository
import NewClient
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.example.tallermecanico.R
import com.example.tallermecanico.databinding.FragmentClientBinding
import existingCodeExcepcion

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ClientFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ClientFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var binding: FragmentClientBinding
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
        binding= DataBindingUtil.inflate<FragmentClientBinding>(inflater,R.layout.fragment_client,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
      binding.btAgregarCliente.setOnClickListener {
          val clientAdd = addClient()
          if (clientAdd == true) {
        Toast.makeText(context,"SE AH AGREGRADO UN NUEVO CLIENTE",Toast.LENGTH_LONG).show()
           findNavController().navigate(R.id.action_clientFragment_to_menuFragment)
          }

      }

    }
    private fun addClient(): Boolean {
        var agregado=false
       try{ try{
        val codClient= binding.etCodCliente.text.toString().toInt()
        val nameClient=binding.etNombre.text.toString()
        val surnameClient=binding.etApellido.text.toString()
            if(ClientRepository.get(codClient)==null){
        val newClient = NewClient(codClient, nameClient, surnameClient)
        agregado = ClientRepository.add(newClient)}
            else throw existingCodeExcepcion("codigo de cliente existente")
        }catch (ex:java.lang.NumberFormatException){
            Toast.makeText(context,"DEBE INGRESAR TODOS LOS VALORES",Toast.LENGTH_LONG).show()
       }}catch (e:existingCodeExcepcion){
           Toast.makeText(context,e.message,Toast.LENGTH_LONG).show()
       }

        return agregado
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ClientFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ClientFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}