import com.grievance.dao.GrievanceDAO;
import com.grievance.dao.UserDAO;
import com.grievance.model.Grievance;
import com.grievance.model.User;

public class TestDAO {
    public static void main(String[] args) {

        // Register User
        User u = new User();
        u.setName("Test User");
        u.setEmail("test@gmail.com");
        u.setPassword("1234");

        UserDAO userDAO = new UserDAO();
        boolean registered = userDAO.register(u);
        System.out.println("User Registered: " + registered);

        // Login User
        User loginUser = userDAO.login("test@gmail.com", "1234");
        if (loginUser != null) {
            System.out.println("Login Success: " + loginUser.getName());

            // Insert Grievance
            Grievance g = new Grievance();
            g.setUserId(loginUser.getId());
            g.setTitle("Water Problem");
            g.setDescription("No water supply in my area");
            g.setDepartment("Water Supply");

            GrievanceDAO gdao = new GrievanceDAO();
            boolean inserted = gdao.insertGrievance(g);
            System.out.println("Grievance Inserted: " + inserted);
        } else {
            System.out.println("Login Failed");
        }
    }
}
