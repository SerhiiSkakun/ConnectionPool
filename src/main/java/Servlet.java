import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "Servlet", urlPatterns = {"/names"})
public class Servlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Person> result = new ArrayList<>();
        try (Connection connection = DataSource.getConnection()) {
            PreparedStatement ps = connection.prepareStatement("select * from schema_test.table_test");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                Person person = new Person(id, name);
                result.add(person);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        response.setContentType("text/html");
        PrintWriter writer = response.getWriter();
        writeHtml(writer, result);
    }

    private void writeHtml(PrintWriter writer, List<Person> result){
        writer.println("<html>");
        writer.println("<head>");
        writer.println("<title>Persons</title>");
        writer.println("<link rel=\"stylesheet\" type=\"text/css\" href=\"resources/styles.css\">");
        writer.println("</head>");
        writer.println("<body>");
        writer.println("<h1>Persons</h1>");
        writer.println("<table>");
        writer.println("<thead>");
        writer.println("<tr>");
        writer.println("<th>ID</th><th>Person</th>");
        writer.println("</tr>");
        writer.println("</thead>");
        writer.println("<tbody>");
        for (Person person : result) {
            writer.println("<tr>");
            writer.print("<td>");
            writer.print(person.id);
            writer.print("</td>");
            writer.println("<td>");
            writer.print(person.name);
            writer.println("</td>");
        }
        writer.println("</tbody>");
        writer.println("</table>");
        writer.println("</body>");
        writer.println("</html>");
    }
}
