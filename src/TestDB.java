import com.grievance.dao.DBConnection;

public class TestDB {
    public static void main(String[] args) {
        try {
            System.out.println(DBConnection.getConnection());
            System.out.println("Connected Successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}