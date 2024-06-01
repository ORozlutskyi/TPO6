package org.example.web2;

import java.io.*;
import java.lang.reflect.Method;
import java.sql.*;

import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "helloServlet", value = "/hello-servlet")
public class HelloServlet extends HttpServlet {
    private String message;

    public void init() {
        message = "Hello World!";
//        try {
//            Class.forName("org.apache.derby.iapi.jdbc.AutoloadedDriver");
////            DriverManager.registerDriver(new org.apache.derby.jdbc.EmbeddedDriver());
//        } catch (ClassNotFoundException e) {
//            throw new RuntimeException(e);
//        }
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");

        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<h1>" + message + "</h1>");

        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            Class.forName("org.apache.derby.iapi.jdbc.AutoloadedDriver");
            con = DriverManager.getConnection("jdbc:derby:C:/Users/orozl/db-derby-10.17.1.0-bin/bin/ksidb");
            stmt = con.createStatement();

            try {
                rs = stmt.executeQuery("SELECT * FROM AUTOR");
            } catch (SQLException e) {
                e.printStackTrace();
                // Table does not exist, create it
//                stmt.executeUpdate("DROP TABLE AUTOR");
            }

            // Process the result set
            while (rs.next()) {
                String authorName = rs.getString("NAME");
                out.println("<p>Author: " + authorName + "</p>");
            }
        } catch (SQLException e) {
            e.printStackTrace(out);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace(out);
            }
        }

        out.println("</body></html>");
        out.close();
    }

    public void destroy() {
    }
}
