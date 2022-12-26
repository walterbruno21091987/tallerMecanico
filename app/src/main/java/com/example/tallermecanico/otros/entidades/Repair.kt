package com.example.tallermecanico.otros.entidades

import java.time.LocalDate

class Repair(val code:Int, val clienteCode:Int, val completionDate:LocalDate, val spareParUsed:Map<Int,Int>, val hoursWorked:Int,val laborPrice:Double=500.0) {
    fun totalSparePartsCost(): Double {
        var total: Double = 0.0
        try {
            for ((key, value) in spareParUsed) {
                total += SparePartRepository.get(key)?.price?.times(value)!!
            }
        } catch (exc: java.lang.NullPointerException) {
            println("no se pudo obtener el precio")
        }
        return total
    }

    private val totalLaborCost: (Int, Double) -> Double = { hours, price -> hours.times(price) }
    fun laborCost() = totalLaborCost(hoursWorked, laborPrice)
    fun totalCost(sparePart: Double, laborCost: Double, function: ((Double, Double) -> Double)): Double {

        return function(sparePart, laborCost)

    }

    companion object {
        fun total_profit_workShop(): Double {
            var totalHistoricalLaborCost = 0.0
            for (repair in RepairRepository.get()) {
                totalHistoricalLaborCost += repair.totalLaborCost(repair.hoursWorked, repair.laborPrice)
            }
            return totalHistoricalLaborCost

        }//NO SE TOMA EN CUENTA EL COSTO DE REPUESTO POR QUE NO SE CONSIDERA GANANCIA
        fun existingCode(code: Int): Boolean {
            var contain=false
            for (sparePart in SparePartRepository.get()){
                if(sparePart.code.equals(code)){
                    contain=true
                }
            }
            return contain
        }


    }
}


