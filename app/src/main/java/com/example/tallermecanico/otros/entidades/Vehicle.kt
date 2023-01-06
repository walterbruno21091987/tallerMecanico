import java.time.LocalDate

class Vehicle(val numberPlate:String, val clientCode:Int, val insuranceCap: Double? =0.0, val insuranceCoverageFrom: Double? =0.0){
    fun insuranceDiscounnt(repairCost:Double):Double {
        var discount=0.0
        if(insuranceCap!=null &&insuranceCoverageFrom!=null){
        if (MonthRepair()) return 0.0
        when (repairCost){
            in 0.0..insuranceCoverageFrom-> discount=0.0
            in insuranceCoverageFrom..insuranceCap -> discount=repairCost
            else  ->discount=insuranceCap


        }} //entendi que si aca lanzo una excepcion no se podria contemplar la posibilidad de que el auto no tenga seguro
        return discount
    }

    private fun MonthRepair(): Boolean {
        var repairs = RepairRepository.get()
        for (rep in repairs) {
            if (rep.clienteCode.equals(clientCode) == true && rep.completionDate.month.equals(LocalDate.now().month) == true && rep.completionDate.month.equals(
                    LocalDate.now().year) == true
            ) {
                return true
            }
        }
        return false
    }
    fun haveInsuranse():String{
        val request:String
        if(insuranceCap!=null &&insuranceCoverageFrom!=null)request="si"
        else request="no"
        return request
    }

}


