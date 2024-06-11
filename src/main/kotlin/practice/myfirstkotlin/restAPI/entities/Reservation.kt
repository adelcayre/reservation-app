package practice.myfirstkotlin.restAPI.entities

import jakarta.persistence.*

@Entity
class Reservation(@Id @GeneratedValue(strategy = GenerationType.IDENTITY) val id: Long =0,
        var name: String,
        var partySize: Int) {
}