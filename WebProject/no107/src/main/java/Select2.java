import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@WebServlet("/HelloWorld")
public class Select2 extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String driver = "com.mysql.jdbc.Driver";
        // 「database_name」は各環境に合わせます
        String url = "jdbc:mysql://localhost/practice_db";
        // MySQLに接続する際のユーザー名(デフォルトはroot)
        String user = "root";
        // MySQLに接続する際のパスワード(今回はroot)
        String password = "root";

        try {

            Class.forName(driver);

            Connection connection = DriverManager.getConnection(url, user, password);
            connection.setAutoCommit(false);

            Statement statement = connection.createStatement();

            String sql = "SELECT * FROM authors";
            ResultSet rs = statement.executeQuery(sql);

            System.out.println("SELECTの結果（ここから）");

            while (rs.next()) {
                int userId = rs.getInt("id");
                String userName = rs.getString("name");
                String userKana = rs.getString("kana");
                String userGender = rs.getString("gender");

                System.out.println(userId + ", " + userName + ", " + userKana + ", " + userGender);

                PrintWriter out = resp.getWriter();
                out.println();
            }

            System.out.println("SELECTの結果（ここまで）");

            rs.close();
            statement.close();

            connection.commit();
            connection.close();

            System.out.println("BEGIN Hello, World! " + getCurrentTime());

            RequestDispatcher dispatcher = req.getRequestDispatcher("/HelloWorld.jsp");
            dispatcher.forward(req, resp);
        } catch (ClassNotFoundException | SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            System.out.println("END Hello, World! " + getCurrentTime());
        }
    }

    private String getCurrentTime() {
        return new SimpleDateFormat("HH:mm:ss").format(new Date());
    }

    public static ResultSet select(Connection connection) throws SQLException {

        Statement statement = connection.createStatement();

        String sql = "SELECT * FROM authors";
        ResultSet rs = statement.executeQuery(sql);

        System.out.println("SELECTの結果（ここから）");

        while (rs.next()) {
            int userId = rs.getInt("id");
            String userName = rs.getString("name");
            String userKana = rs.getString("kana");
            String userGender = rs.getString("gender");

            System.out.println(userId + ", " + userName + ", " + userKana + ", " + userGender);

        }

        System.out.println("SELECTの結果（ここまで）");

        rs.close();
        statement.close();

        return rs;
    }
}