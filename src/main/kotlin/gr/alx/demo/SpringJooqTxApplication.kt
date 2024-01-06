package gr.alx.demo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication//(exclude = [JooqAutoConfiguration::class])
class SpringJooqTxApplication

fun main(args: Array<String>) {
    runApplication<SpringJooqTxApplication>(*args)
}
