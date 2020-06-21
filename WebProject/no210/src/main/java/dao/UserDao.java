package dao;

import static utils.CloseableUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import beans.User;
import exception.NoRowsUpdatedRuntimeException;
import exception.SQLRuntimeException;

public class UserDao {

    public void insert(Connection connection, User user) {

        PreparedStatement ps = null;
        try {

            //SQL文を構成
            StringBuilder sql = new StringBuilder();
            sql.append("INSERT INTO users ( ");
            sql.append("account");
            sql.append(", name");
            sql.append(", email");
            sql.append(", password");
            sql.append(", description");
            sql.append(", created_date");
            sql.append(", updated_date");
            sql.append(") VALUES (");
            sql.append("?"); // account
            sql.append(", ?"); // name
            sql.append(", ?"); // email
            sql.append(", ?"); // password
            sql.append(", ?"); // description
            sql.append(", CURRENT_TIMESTAMP"); // created_date
            sql.append(", CURRENT_TIMESTAMP"); // updated_date
            sql.append(")");

            //statement：問い合わせを実行したり、問い合わせの結果の取得に関するベースになるものを作成する
            //prepareStatementでインジェクションを防げる
            ps = connection.prepareStatement(sql.toString());

            ps.setString(1, user.getAccount());
            ps.setString(2, user.getName());
            ps.setString(3, user.getEmail());
            ps.setString(4, user.getPassword());
            ps.setString(5, user.getDescription());

            //SQL文を実行
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new SQLRuntimeException(e);
        } finally {
            close(ps);
        }
    }

    //ユーザー情報を取り出したい時のメソッド
    public User getUser(Connection connection, String accountOrEmail,
        String password) {

        PreparedStatement ps = null;
        try {
            //ユーザーが入力した情報を使ってSQLに接続する
            String sql = "SELECT * FROM users WHERE (account = ? OR email = ?) AND password = ?";

            ps = connection.prepareStatement(sql);

            //?はStringBuilderを使えば必要ない
            ps.setString(1, accountOrEmail);
            ps.setString(2, accountOrEmail);
            ps.setString(3, password);

            //SQL文を実行（データベース検索）
            ResultSet rs = ps.executeQuery();
            List<User> userList = toUserList(rs);


            //ログインしようとしているデータが1つだけ見つかるようにしている
            if (userList.isEmpty() == true) {
                return null;
            } else if (2 <= userList.size()) {
                //検索に2つ以上引っかかってしまったら例外処理
                throw new IllegalStateException("2 <= userList.size()");
            } else {
                //1つだけ取り出したインスタンスを返す
                return userList.get(0);
            }

        } catch (SQLException e) {
            throw new SQLRuntimeException(e);
        } finally {
            close(ps);
        }
    }

    private List<User> toUserList(ResultSet rs) throws SQLException {

        List<User> ret = new ArrayList<User>();
        try {
            while (rs.next()) {

                //SELECT文で見つけたユーザー情報を取り出す
                int id = rs.getInt("id");
                String account = rs.getString("account");
                String name = rs.getString("name");
                String email = rs.getString("email");
                String password = rs.getString("password");
                String description = rs.getString("description");
                Timestamp createdDate = rs.getTimestamp("created_date");
                Timestamp updatedDate = rs.getTimestamp("updated_date");

                //取り出したユーザー情報を使ってユーザーインスタンスを返す
                User user = new User();
                user.setId(id);
                user.setAccount(account);
                user.setName(name);
                user.setEmail(email);
                user.setPassword(password);
                user.setDescription(description);
                user.setCreatedDate(createdDate);
                user.setUpdatedDate(updatedDate);

                //ユーザーリストにユーザーを追加
                ret.add(user);
            }
            //ユーザーリストを返す
            return ret;
        } finally {
            close(rs);
        }
    }

    public User getUser(Connection connection, int id) {

    	PreparedStatement ps = null;
    	try {
    		String sql = "SELECT * FROM users WHERE id = ?";

    		ps = connection.prepareStatement(sql);
    		ps.setInt(1, id);

    		ResultSet rs = ps.executeQuery();
    		List<User> userList = toUserList(rs);
    		if (userList.isEmpty() == true) {
    			return null;
    		} else if (2 <= userList.size()) {
    			throw new IllegalStateException("2 <= userList.size()");
    		} else {
    			return userList.get(0);
    		}
    	} catch (SQLException e) {
    		throw new SQLRuntimeException(e);
    	} finally {
    		close(ps);
    	}
    }

    public void update(Connection connection, User user) {

		PreparedStatement ps = null;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("UPDATE users SET");
			sql.append("  account = ?");
			sql.append(", name = ?");
			sql.append(", email = ?");
			sql.append(", password = ?");
			sql.append(", description = ?");
			sql.append(", updated_date = CURRENT_TIMESTAMP");
			sql.append(" WHERE");
			sql.append(" id = ?");

			ps = connection.prepareStatement(sql.toString());

			ps.setString(1, user.getAccount());
			ps.setString(2, user.getName());
			ps.setString(3, user.getEmail());
			ps.setString(4, user.getPassword());
			ps.setString(5, user.getDescription());
			ps.setInt(6, user.getId());

			int count = ps.executeUpdate();
			if (count == 0) {
				throw new NoRowsUpdatedRuntimeException();
			}
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}

	}

}