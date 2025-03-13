/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
    package DAO;

    /**
     *
     * @author zainr
     */


    import java.sql.Connection;
    import java.sql.PreparedStatement;
    import java.sql.ResultSet;
    import Model.User;
    import Utils.DBConfig;
    import Utils.PasswordUtils;

    import java.sql.Connection;
    import java.sql.PreparedStatement;
    import java.sql.ResultSet;

    public class LoginDAO {

        // ✅ Fetch User by Email
        public static User getUserByEmail(String email) {
            User user = null;
            try (Connection conn = DBConfig.getConnection()) {
                String sql = "SELECT id, email, role, password FROM users WHERE email = ?";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, email);
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    // ✅ Use the correct constructor (4 parameters for login)
                    user = new User(rs.getInt("id"), rs.getString("email"), rs.getString("role"), rs.getString("password"));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return user;
        }
        public static User authenticateUser(String email, String password) {
        User user = getUserByEmail(email);
        if (user != null && PasswordUtils.verifyPassword(password, user.getPassword())) {
            return user;
        }
        return null;
    }
    }
