import io.javalin.Javalin
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

object Actor : Table() {
    val actorId = integer("actor_id")
    val firstName = varchar("first_name", 45)
    val lastName = varchar("last_name", 45)
}

fun main() {

    Database.connect(
        "jdbc:postgresql://localhost:5432/dvdrental", driver = "org.postgresql.Driver",
        user = "postgres", password = ""
    )

    var actorName = ""
    transaction {
        addLogger(StdOutSqlLogger)
        SchemaUtils.create(Actor)

        val something = Actor.select {
            Actor.actorId eq 1
        }.single()[Actor.firstName]


        actorName = something
    }

    val app = Javalin.create().start(7000)
    app.get("/") { ctx -> ctx.result(actorName) }
}