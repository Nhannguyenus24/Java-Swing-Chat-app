import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/usermanagement";
    private static final String USER = "root";  // Thay bằng tên người dùng MySQL của bạn
    private static final String PASSWORD = "";  // Thay bằng mật khẩu của bạn

    public static Connection getConnection() throws SQLException {
        try {
            // Đăng ký driver JDBC
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Kết nối đến cơ sở dữ liệu
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new SQLException("JDBC Driver not found.", e);
        }
    }
}
