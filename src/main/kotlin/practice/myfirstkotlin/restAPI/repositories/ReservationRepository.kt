package practice.myfirstkotlin.restAPI.repositories

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import practice.myfirstkotlin.restAPI.entities.Reservation
import java.util.*

@Repository
interface ReservationRepository : JpaRepository<Reservation, Long>{
    fun findByIdAndName(id: Long, name: String) : Optional<Reservation>
}