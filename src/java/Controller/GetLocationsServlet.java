/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import Utils.DBConfig;

public class GetLocationsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        StringBuilder json = new StringBuilder("[");
        try (Connection con = DBConfig.getConnection();
             PreparedStatement ps = con.prepareStatement("SELECT locationname FROM location");
             ResultSet rs = ps.executeQuery()) {

            boolean first = true;
            while (rs.next()) {
                if (!first) {
                    json.append(",");
                }
                json.append("\"").append(rs.getString("locationname")).append("\"");
                first = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        json.append("]");

        PrintWriter out = response.getWriter();
        out.write(json.toString());
        out.flush();
    }
}









//package Controller;
//
//
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.util.ArrayList;
//import java.util.List;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServlet;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//
//import Utils.DBConfig;
//
//public class GetLocationsServlet extends HttpServlet {
//    private static final long serialVersionUID = 1L;
//
//    protected void doGet(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        response.setContentType("application/json");
//        response.setCharacterEncoding("UTF-8");
//
//        List<String> locations = new ArrayList<>();
//        try (Connection con = DBConfig.getConnection();
//             PreparedStatement ps = con.prepareStatement("SELECT name FROM location");
//             ResultSet rs = ps.executeQuery()) {
//            
//            while (rs.next()) {
//                locations.add(rs.getString("name"));
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        // Convert list to JSON and send response
//       StringBuilder json = new StringBuilder("[");
//      for (int i = 0; i < locations.size(); i++) {
//       json.append("\"").append(locations.get(i)).append("\"");
//       if (i < locations.size() - 1) {
//        json.append(",");
//    }
//}
//       json.append("]");
//
//       PrintWriter out = response.getWriter();
//       out.write(json.toString());
//       out.flush();
//
//    }
//}
