package practice.myfirstkotlin.restAPI.tablesMicroservice
import jakarta.persistence.*

@Entity
class Tables(@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
            val id: Long = 0,
            val capacity: Int) {

    var resId: Long? = null

    // Setter for reservation ID
    fun setReservation(resId: Long) {
        this.resId = resId
    }
}