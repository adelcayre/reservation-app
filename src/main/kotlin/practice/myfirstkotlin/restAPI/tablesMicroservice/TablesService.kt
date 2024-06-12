package practice.myfirstkotlin.restAPI.tablesMicroservice

import org.springframework.stereotype.Service

@Service
class TablesService(private val tablesRepository: TablesRepository) {




    fun makeReservation(resId: Long, partySize: Int):Boolean{
        tablesRepository.findTablesByCapacityIn(listOf(partySize, partySize+1))
            .forEach { table ->
                if (table.resId == null) {
                    table.resId = resId
                    tablesRepository.save(table)
                    return true
                }
            }
        return false

    }

    fun getAllTables(): List<Tables> {
        return tablesRepository.findAll()
    }

    fun deleteReservation(id: Long) {
        val table=tablesRepository.findTablesByResId(id)
        table.resId=null
        tablesRepository.save(table)
    }

    fun updateReservation(resId:Long,partySize:Int) :Boolean {
        val table=tablesRepository.findTablesByResId(resId)
        //check if current table can accommodate updated party size
        return if(table.capacity==partySize || table.capacity == partySize+1) true
        //else attempt new reservation
        else makeReservation(resId, partySize).also{success->
            if(success)table.resId=null
        }
    }
}