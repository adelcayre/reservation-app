package practice.myfirstkotlin.restAPI.reservationService

import org.springframework.stereotype.Service
import java.util.*
import khttp.post
import khttp.responses.Response
import khttp.structures.authorization.Authorization

@Service
class ReservationService(
    private val reservationRepository: ReservationRepository
) {

    //reserve a table
    fun tryReservation(reservationEntity: ReservationEntity): Long {
        //send post request to /tables endpoint
        val response: Response = khttp.post(
            "/tables",
            params = mapOf(
                "resId" to reservationEntity.id.toString(),
                "partySize" to reservationEntity.partySize.toString()
            )
        )
        return if (response.statusCode == 200) reservationRepository.save(reservationEntity).id else -1L
    }

    //search for reservation by ID
    fun findReservation(id: Long): Optional<ReservationEntity> {
        return reservationRepository.findById(id)
    }

    //deleteReservation
    fun deleteReservation(id: Long, authorization: String): Boolean {
        //reservation is present
        if (reservationRepository.findById(id).isPresent) {
            //remove reservation
            reservationRepository.deleteById(id)
            //reset table resId
            val token = authorization.substring(7)
            khttp.delete(
                "/tables",
                params = mapOf("id" to id.toString()),
                headers = mapOf("Authorization" to "Bearer $token")
            )
            return true
        }
        //reservation not present
        else {
            return false
        }
    }

    //update reservation
    fun updateReservation(
        name: String,
        resId: Long,
        reservationEntityUpdate: ReservationEntity,
        authorization: String
    ): Long {
        val reservationEntity: Optional<ReservationEntity> = reservationRepository.findByIdAndName(resId, name)
        //reservation with name and id found
        if (reservationEntity.isPresent) {
            val reservation = reservationEntity.get()
            val token = authorization.substring(7)
            val response = khttp.put(
                "/tables",
                params = mapOf(
                    "resId" to resId.toString(),
                    "partySize" to reservationEntityUpdate.partySize.toString()
                ),
                headers = mapOf("Authorization" to "Bearer $token")
            )
            //if response 200, update current reservation
            return if (response.statusCode == 200) resId.also {
                reservation.name = reservationEntityUpdate.name
                reservation.partySize = reservationEntityUpdate.partySize
                reservationRepository.save(reservation)
            } else -1L //reservation cannot be accommodated
        }
        //no reservation with name and id found
        else {
            return -2L
        }
    }

    //return all reservations
    fun findAllReservations(): MutableList<ReservationEntity> {
        return reservationRepository.findAll()
    }
}

