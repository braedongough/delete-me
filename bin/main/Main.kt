import com.google.gson.GsonBuilder
import io.javalin.Javalin
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

object Todos : Table() {
    val id = integer("id")
    val description = varchar("description", 250)
    val createdAt = text("created_at")
}

data class MyTodo(val description: Int, val id: Int, val createdAt: Int)

fun main() {
    val app = Javalin.create().start(7000)
    Database.connect(
        "jdbc:postgresql://localhost:5432/joyous-jellyfish", driver = "org.postgresql.Driver",
        user = "postgres", password = ""
    )

    val gson = GsonBuilder().create()


    app.get("/todo") { ctx ->
        val param = ctx.queryParam("id")

        val todo = transaction {
            Todos.select {
                Todos.id eq 2
            }
        }

        val dataTodo = MyTodo(
            id = todo.toList().indexOf(1),
            description = todo.toList().indexOf(2),
            createdAt = todo.toList().indexOf(0)
        )
        println(dataTodo)
        ctx.result(todo.toString())
    }
}