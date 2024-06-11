package practice.myfirstkotlin.restAPI.repositories

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import practice.myfirstkotlin.restAPI.entities.Tables

@Repository
interface TablesRepository : JpaRepository<Tables,Long> {
    fun findTablesByCapacityIn(capacities: Collection<Int>): List<Tables>
    fun findTablesByResId(id: Long): Tables
}