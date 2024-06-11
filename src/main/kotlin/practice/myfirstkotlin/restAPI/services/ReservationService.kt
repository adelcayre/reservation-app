package practice.myfirstkotlin.restAPI.services

import org.springframework.stereotype.Service
import practice.myfirstkotlin.restAPI.entities.Reservation
import practice.myfirstkotlin.restAPI.entities.Tables
import practice.myfirstkotlin.restAPI.repositories.ReservationRepository
import practice.myfirstkotlin.restAPI.repositories.TablesRepository
import java.util.*

@Service
class ReservationService(
    private val tablesRepository: TablesRepository,
    private val reservationRepository: ReservationRepository
) {

    //reserve a table
    fun tryReservation(reservation: Reservation) : Long {
        tablesRepository.findTablesByCapacityIn(listOf(reservation.partySize,reservation.partySize+1)).forEach {
            //find table not yet reserved
            if(it.resId==null){
                //save reservation to table and to reservation repository
                it.setReservation(reservationRepository.save(reservation).id)
                tablesRepository.save(it)
                return it.resId?:-1L
            }

        }
        //if unable to find table return -1
        return -1
    }

    //search for reservation by ID
    fun findReservation(id : Long) : Optional<Reservation> {
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
    fun updateReservation(name : String, id : Long, reservationUpdate: Reservation) : Long {
        val reservation: Optional<Reservation> = reservationRepository.findByIdAndName(id, name)
        //reservation with name and id found
        if(reservation.isPresent){
            val table = tablesRepository.findTablesByResId(id)

            //check if current table can accommodate updated party size
            if(table.capacity==reservationUpdate.partySize || table.capacity == (reservationUpdate.partySize+1)){
                //update info
                reservation.get().partySize = reservationUpdate.partySize
                reservation.get().name = reservationUpdate.name
                reservationRepository.save(reservation.get())
                return reservation.get().id
            }
            //check if another table can take the reservation
            val newId = tryReservation(reservationUpdate)
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
    fun findAllReservations(): MutableList<Reservation> {
        return reservationRepository.findAll()
    }

    //return all tables
    fun findAllTables(): MutableList<Tables> {
        return tablesRepository.findAll()
    }
}