open class Client(val code:Int,  val name:String,  val surname:String){
    open fun customerDiscount( repairCost:Double ):Double{
        var discount=0.0
        if(repairCost.compareTo(15000.0)>=0){
            discount=(repairCost.times(15.0)).div(100)
        }
        return discount
    }

    override fun toString(): String {
        return "Client(code=$code, name='$name', surname='$surname')"
    }
    companion object {
        fun totalCostOfrepairsPerCustomer() {
            for (client in ClientRepository.get()) {
               var totalCostRepair=0.0
              for(repair in RepairRepository.get()){
                  if(client.code.equals(repair.code)){
                      var totalCost=repair.laborCost().plus(repair.totalSparePartsCost())
                      var customerDiscount = client.customerDiscount(totalCost)
                      var insuranceDiscounnt = VehicleRepository.get(client.code)?.insuranceDiscounnt(totalCost)
                      totalCostRepair=totalCost.minus(customerDiscount).minus(customerDiscount)
                  }
              }
                println("el cliente ${client.name} ${client.surname} tiene costo total de reparaciones de ${totalCostRepair}")
            }

        }
    }
}
