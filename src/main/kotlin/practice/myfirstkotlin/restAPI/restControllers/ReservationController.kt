package practice.myfirstkotlin.restAPI.restControllers

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.Optional
import practice.myfirstkotlin.restAPI.entities.Reservation
import practice.myfirstkotlin.restAPI.entities.ScenarioInitializer
import practice.myfirstkotlin.restAPI.services.ReservationService


@RestController
class ReservationController(private val reservationService: ReservationService,
                            private val tablesInitializer: ScenarioInitializer
) {
    init {
        tablesInitializer.initializeTables()
    }

    //send request to reserve a table
    @PostMapping("/reserve")
    fun reserve(@RequestBody reservation: Reservation): ResponseEntity<String> {
        val confirmation=reservationService.tryReservation(reservation)
        return when{
            //200 table was available and reservation id is sent back in response body
            confirmation!=-1L -> ResponseEntity.ok("confirmed:$confirmation")
            //422 no table available for requested party size
            else -> ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("No table available")
        }

    }
    @DeleteMapping("/reserve/{id}")
    fun deleteReservation(@PathVariable id : Long): ResponseEntity<Any>{
        return if(reservationService.deleteReservation(id)){
            ResponseEntity.status(HttpStatus.NO_CONTENT).build()
        }
        else{
            ResponseEntity.status(HttpStatus.NOT_FOUND).build()
        }
    }

    @PutMapping("/reserve")
    fun editReservation( @RequestParam name: String,@RequestParam resId: Long,
        @RequestBody reservationUpdate: Reservation): ResponseEntity<String>{
        val confirmation= reservationService.updateReservation(name,resId,reservationUpdate)
        return when{
            //422 no table available for requested party size
            confirmation==-1L -> ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("No table available")
            //404 reservation not found, unable to update
            confirmation == -2L -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("No table found with that name and id, unable to update reservation")
            //200 table was available and reservation id is sent back in response body
            else -> ResponseEntity.ok("update confirmed:$confirmation")

        }


    }

    //get a reservation by ID
    @GetMapping("/reservations/{id}")
    fun checkReservation(@PathVariable id : Long): ResponseEntity<Any>{
        //reserve service call
        val reservation: Optional<Reservation> = reservationService.findReservation(id)
        //200 reservation update accommodated and saved, sent back in response body
        return if(reservation.isPresent){
            ResponseEntity.ok(reservation.get())
        }
        //422 reservation not found
        else{
            ResponseEntity.status(HttpStatus.NOT_FOUND).body("Reservation not found")
        }
    }

    //get all reservations
    @GetMapping("/reservations")
    fun getReservations(): ResponseEntity<Any>{
        //reserve service call
        return ResponseEntity.ok(reservationService.findAllReservations())
    }

    //get all tables
    @GetMapping("/tables")
    fun getTables(): ResponseEntity<Any>{
        //reserve service call
        return ResponseEntity.ok(reservationService.findAllTables())

    }
}