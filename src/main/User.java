
package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
/**
 * Classe que representa um usuário no sistema.
 * Realiza operações de verificação de login e senha no banco de dados.
 * 
 * @author Murilo Floriano
 * @version 1.0
 */
public class User {
	/**
	 * Construtor padrão da classe User.
	 * Inicializa o objeto sem parâmetros, definindo as variáveis 'nome' e 'result' para seus valores iniciais.
	 */ 
    public User() {
    }

    /**
     * Estabelece uma conexão com o banco de dados.
     * 
     * @return A conexão com o banco de dados ou null caso não consiga conectar.
     */
    private Connection conectarBD() {
        Connection conn = null;
        try {
            // Carrega o driver JDBC para MySQL.
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Define a URL de conexão com o banco de dados.
            String url = "jdbc:mysql://127.0.0.1/test";

            // Define o nome de usuário e a senha para o banco de dados.
            String user = "lopes";
            String password = "123";

            // Estabelece a conexão com o banco de dados.
            conn = DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            // Imprime a pilha de erros caso a conexão falhe.
            e.printStackTrace();
        }

        return conn;
    }

    /**
     * Armazena o nome do usuário autenticado.
     */
    public String nome = "";

    /**
     * Armazena o resultado da verificação de login e senha do usuário.
     */
    public boolean result = false;

    /**
     * Verifica se o usuário com o login e senha fornecidos existe no banco de dados.
     * 
     * @param login O login do usuário a ser verificado.
     * @param senha A senha do usuário a ser verificada.
     * @return true se o usuário for autenticado com sucesso, false caso contrário.
     */
    public boolean verificarUsuario(String login, String senha) {
        String sql = "SELECT nome FROM usuarios WHERE login = ? AND senha = ?";
        Connection conn = conectarBD();
        if (conn == null) {
            return false;
        }

        try {
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, login);
            st.setString(2, senha);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                result = true;
                nome = rs.getString("nome");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

}

