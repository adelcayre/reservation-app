package practice.myfirstkotlin.restAPI.tablesMicroservice
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import practice.myfirstkotlin.restAPI.ScenarioInitializer

@RestController
class TablesController(tablesInitializer: ScenarioInitializer,
                       private val tablesService: TablesService
) {
    init {
        tablesInitializer.initializeTables()
    }

    @GetMapping("/tables")
    fun getTables() : ResponseEntity<List<Tables>> {
            return ResponseEntity.ok(tablesService.getAllTables())
    }

    @PostMapping("/tables")
    fun tryReservation(resId: Long, partySize: Int) : ResponseEntity<Any>  {
        return if(tablesService.makeReservation(resId, partySize)) ResponseEntity.ok().build()
        else ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build()
    }

    @PutMapping("/tables")
    fun tryUpdateReservation(resId: Long, partySize: Int) : ResponseEntity<Any>  {
        return if(tablesService.updateReservation(resId, partySize)) ResponseEntity.ok().build()
        else ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build()
    }

    @DeleteMapping("/tables")
    fun deleteReservation(resId: Long) : ResponseEntity<Any>  {
        tablesService.deleteReservation(resId)
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build()
    }

}