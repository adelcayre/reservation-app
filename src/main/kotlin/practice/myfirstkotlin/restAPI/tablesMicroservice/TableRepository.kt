package practice.myfirstkotlin.restAPI.tablesMicroservice

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TablesRepository : JpaRepository<Tables,Long> {
    fun findTablesByCapacityIn(capacities: Collection<Int>): List<Tables>
    fun findTablesByResId(id: Long): Tables
}