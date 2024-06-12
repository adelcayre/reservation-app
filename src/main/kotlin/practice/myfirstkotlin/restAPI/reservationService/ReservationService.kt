package practice.myfirstkotlin.restAPI.reservationService

import org.springframework.stereotype.Service
import practice.myfirstkotlin.restAPI.tablesMicroservice.Tables
import practice.myfirstkotlin.restAPI.tablesMicroservice.TablesRepository
import java.util.*

@Service
class ReservationService(
    private val tablesRepository: TablesRepository,
    private val reservationRepository: ReservationRepository
) {

    //reserve a table
    fun tryReservation(reservationEntity: ReservationEntity) : Long {
        tablesRepository.findTablesByCapacityIn(listOf(reservationEntity.partySize,reservationEntity.partySize+1)).forEach {
            //find table not yet reserved
            if(it.resId==null){
                //save reservationEntity to table and to reservationEntity repository
                it.setReservation(reservationRepository.save(reservationEntity).id)
                tablesRepository.save(it)
                return it.resId?:-1L
            }

        }
        //if unable to find table return -1
        return -1
    }

    //search for reservation by ID
    fun findReservation(id : Long) : Optional<ReservationEntity> {
        return reservationRepository.findById(id)
    }

    //deleteReservation
    fun deleteReservation(id : Long) : Boolean {
        //reservation is present
        if (reservationRepository.findById(id).isPresent){
            //remove reservation
            reservationRepository.deleteById(id)
            //reset table resId
            val table=tablesRepository.findTablesByResId(id)
            table.resId=null;
            tablesRepository.save(table)
            return true
        }
        //reservation not present
        else {
            return false
        }
    }

    //update reservation
    fun updateReservation(name : String, id : Long, reservationEntityUpdate: ReservationEntity) : Long {
        val reservationEntity: Optional<ReservationEntity> = reservationRepository.findByIdAndName(id, name)
        //reservation with name and id found
        if(reservationEntity.isPresent){
            val table = tablesRepository.findTablesByResId(id)

            //check if current table can accommodate updated party size
            if(table.capacity==reservationEntityUpdate.partySize || table.capacity == (reservationEntityUpdate.partySize+1)){
                //update info
                reservationEntity.get().partySize = reservationEntityUpdate.partySize
                reservationEntity.get().name = reservationEntityUpdate.name
                reservationRepository.save(reservationEntity.get())
                return reservationEntity.get().id
            }
            //check if another table can take the reservation
            val newId = tryReservation(reservationEntityUpdate)
            if(newId!=-1L){
                //remove reservation from previous table
                table.resId=null
                tablesRepository.save(table)
                //remove previous saved reservation
                reservationRepository.deleteById(id)
            }
            //return newId or -1 if unable to make reservation
            return newId
        }
        //no reservation with name and id found
        else{
            return -2L
        }
    }

    //return all reservations
    fun findAllReservations(): MutableList<ReservationEntity> {
        return reservationRepository.findAll()
    }

    //return all tables
    fun findAllTables(): MutableList<Tables> {
        return tablesRepository.findAll()
    }
}