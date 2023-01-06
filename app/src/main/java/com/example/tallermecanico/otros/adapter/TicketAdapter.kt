package com.example.tallermecanico.otros.adapter

import SparePart
import android.app.Activity
import android.content.Context
import android .os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.tallermecanico.R
import com.example.tallermecanico.databinding.ItemFacturaBinding
import com.example.tallermecanico.otros.entidades.Repair
import java.time.LocalDate


class TicketAdapter(val ticketList:List<Repair>): RecyclerView.Adapter<TicketViewHolder>() {
    lateinit var binding: ItemFacturaBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TicketViewHolder {

        val layoutInflater = LayoutInflater.from(parent.context)
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.item_factura, parent, false)
        return TicketViewHolder(layoutInflater.inflate(R.layout.item_factura, parent, false))
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: TicketViewHolder, position: Int) {
        val repair = ticketList[position]
        val client = ClientRepository.get(repair.clienteCode)
        val vehicle = client?.code?.let { VehicleRepository.get(it) }
        val totalcost =  repair.totalSparePartsCost().plus(repair.laborCost())
        val customerDiscount = client?.customerDiscount(totalcost)?:0.0
        val insuranceDiscounnt = vehicle?.insuranceDiscounnt(totalcost)?:0.0

        binding.tvFechaEmicion.text = LocalDate.now().toString()
        binding.tvFechaReparacion.text = repair.completionDate.toString()
        binding.tvPatente.text = vehicle?.numberPlate.toString()
        binding.tvManoDeObra.text = repair.laborCost().toString()
        binding.tvCodCliente.text = repair.clienteCode.toString()
        binding.tvCodReparacion.text = repair.code.toString()
        binding.tvDescuentoCliente.text = client?.customerDiscount(totalcost).toString()
        binding.tvDescuentoSeguro.text = vehicle?.insuranceDiscounnt(totalcost).toString()
        binding.tvAsegurado.text = vehicle?.haveInsuranse()
        binding.tvRepuesto.text =repair.totalSparePartsCost().toString()
        binding.tvTotal.text= totalcost.minus(customerDiscount).minus(insuranceDiscounnt).toString()
    }


    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }


}