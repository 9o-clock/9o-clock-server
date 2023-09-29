package dreamdiary

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(
    scanBasePackages = [
        "dreamdiary.noc.*",
        "dreamdiary.noc.*.api",
        "dreamdiary.noc.*.app",
        "dreamdiary.noc.*.domain",
        "dreamdiary.noc.*.infra",
    ]
)
class NineOClockInternalApplication

fun main(args: Array<String>) {
    runApplication<NineOClockInternalApplication>(*args)
}
