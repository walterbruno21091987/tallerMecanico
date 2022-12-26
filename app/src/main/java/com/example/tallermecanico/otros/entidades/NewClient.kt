class NewClient(code:Int,name:String,surname:String):Client(code,name,surname){
    override fun customerDiscount( repairCost:Double ):Double{
        changeNewClientToClient()//cambia de nuevo cliente a cliente debido a  que el descuento se debe hacer solo por unica vez y si permanece como nuevo en el repositorio lo haria siempre
        return(repairCost.times(40.0)).div(100)

    }

    private fun changeNewClientToClient() {
        ClientRepository.remove(code)
        val client: Client
        client = Client(code, name, surname)
        ClientRepository.add(client)
    }
}