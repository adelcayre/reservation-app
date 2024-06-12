package practice.myfirstkotlin.restAPI.reservationService

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface ReservationRepository : JpaRepository<ReservationEntity, Long>{
    fun findByIdAndName(id: Long, name: String) : Optional<ReservationEntity>
}