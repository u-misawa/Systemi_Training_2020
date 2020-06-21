package utils;

import java.io.Closeable;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import exception.IORuntimeException;
import exception.SQLRuntimeException;

public class CloseableUtil {

	public static void close(Closeable closeable) {

		//中身がnullなら何も返さない
		if (closeable == null) 	{
			return;
		}

		//closeableをクローズする
		try {
			closeable.close();
		} catch (IOException e) {
			throw new IORuntimeException(e);
		}
	}

	public static void close(Connection connection) {
		if (connection == null) {
			return;
		}
		try {
			connection.close();
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		}
	}

	public static void close(Statement statement) {
		if (statement == null) {
			return;
		}
		try {
			statement.close();
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		}
	}

	public static void close(ResultSet rs) {
		if (rs == null) {
			return;
		}
		try {
			rs.close();
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		}
	}
}
