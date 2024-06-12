package practice.myfirstkotlin.restAPI.reservationService

import jakarta.persistence.*

@Entity
class ReservationEntity(@Id @GeneratedValue(strategy = GenerationType.IDENTITY) val id: Long =0,
                        var name: String,
                        var partySize: Int) {
}