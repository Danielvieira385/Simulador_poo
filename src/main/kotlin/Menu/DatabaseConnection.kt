package Menu
import java.sql.*

fun main() {
    val jdbcUrl = "jdbc:mysql://127.0.0.1:3306/"
    val usuario = "user_simulador"
    val senha = "abcd1234"

    try {
        println("Tentativa de conectar à base de dados...")
        val conexao: Connection = DriverManager.getConnection(jdbcUrl, usuario, senha)
        println("Conexão bem-sucedida!")

        val sql = "SELECT * FROM sch_simulador_poo.personagens"
        val statement = conexao.createStatement()
        val resultSet: ResultSet = statement.executeQuery(sql)

        while (resultSet.next()) {
            val id_personagem = resultSet.getInt("idpersonagens")
            val nome_personagem = resultSet.getString("Nome")
            val idade_personagem  = resultSet.getInt("Idade")
            println("ID da Personagem: $id_personagem, Nome: $nome_personagem , Idade: $idade_personagem")
        }

        resultSet.close()
        statement.close()
        conexao.close()

    } catch (e: SQLException) { println("Erro ao conectar: ${e.message}") }

    fun inserirPersonagem(nome: String, idade: Int) {

            val jdbcUrl = "jdbc:mysql://127.0.0.1:3306/"
            val usuario = "user_simulador"
            val senha = "abcd1234"

            try {
                println("Tentativa de conectar à base de dados...")
                val conexao: Connection = DriverManager.getConnection(jdbcUrl, usuario, senha)
                println("Conexão bem-sucedida!")

                val sql = "INSERT INTO sch_simulador_poo.personagens (idpersonagens, Nome, Idade) VALUES (?, ?, ?)"
                val preparedStatement: PreparedStatement = conexao.prepareStatement(sql)

                // Exemplo de dados a serem inseridos
                // Buscar o próximo ID disponível
                val idQuery = "SELECT COALESCE(MAX(idpersonagens), 0) + 1 AS nextId FROM sch_simulador_poo.personagens"
                val idResultSet = conexao.createStatement().executeQuery(idQuery)
                idResultSet.next()
                val nextId = idResultSet.getInt("nextId")
                idResultSet.close()

                // Inserir os dados com o próximo ID
                preparedStatement.setInt(1, nextId) // idpersonagens
                preparedStatement.setString(2, "Personagem Exemplo") // Nome
                preparedStatement.setInt(3, 25) // Idade

                val linhasAfetadas = preparedStatement.executeUpdate()
                println("Inserção concluída! Linhas afetadas: $linhasAfetadas")

                preparedStatement.close()
                conexao.close()

            } catch (e: SQLException) {
                println("Erro ao conectar ou inserir: ${e.message}")
            }
        }
    val teste = inserirPersonagem("Personagem Teste", 99)
}
