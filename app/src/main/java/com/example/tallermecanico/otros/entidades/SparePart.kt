class SparePart(val code:Int,val name:String,val price:Double,val unitinStock:Int) {
    companion object {
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