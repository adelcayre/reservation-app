package practice.myfirstkotlin.restAPI

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class MyFirstKotlinApplication

fun main(args: Array<String>) {
    runApplication<MyFirstKotlinApplication>(*args)
}
