package Utils;

import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseTest {
    public static void main(String[] args) {
        try {
            Connection conn = DBConfig.getConnection();
            if (conn != null) {
                System.out.println("✅ Successfully connected to MySQL!");
                conn.close();
            }
        } catch (SQLException e) {
            System.out.println("❌ Database Connection Failed!");
            e.printStackTrace();
        }
    }
}
