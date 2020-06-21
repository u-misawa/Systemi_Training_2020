package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import exception.SQLRuntimeException;

/**
 * DB(コネクション関係)のユーティリティー
 */
public class DBUtil {

    //クラスフィールドに定数として、DBとの接続するための情報を定義
    private static final String DRIVER = "com.mysql.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost/simple_twitter";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    static {
        //クラスをロードすることで、DBドライバクラスを登録できる
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * コネクションを取得します。
     *
     * @return
     */
    public static Connection getConnection() {

        try {
            //URL、USER、PASSWORDを使ってコネクションを取得
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            //オートコミットをOFFにする
            connection.setAutoCommit(false);

            return connection;
        } catch (SQLException e) {
            throw new SQLRuntimeException(e);
        }
    }

    /**
     * コミットします。
     *
     * @param connection
     */
    public static void commit(Connection connection) {

        try {
            connection.commit();
        } catch (SQLException e) {
            throw new SQLRuntimeException(e);
        }
    }

    /**
     * ロールバックします。
     *
     * @param connection
     */
    public static void rollback(Connection connection) {

        if (connection == null) {
            return;
        }

        try {
            connection.rollback();
        } catch (SQLException e) {
            throw new SQLRuntimeException(e);
        }
    }
}