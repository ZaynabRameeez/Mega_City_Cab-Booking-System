package Controller;

import DAO.UserDAO;
import Model.User;
import Utils.PasswordUtils;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.io.IOException;
import static org.mockito.Mockito.*;

public class LoginServletIT {

    private LoginServlet loginServlet;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;
    private UserDAO userDAO;
    private RequestDispatcher dispatcher;

    @BeforeEach
    public void setUp() {
        userDAO = mock(UserDAO.class);
        loginServlet = new LoginServlet(userDAO); // Inject mock DAO

        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        session = mock(HttpSession.class);
        dispatcher = mock(RequestDispatcher.class);
    }

    @Test
    public void testValidLogin() throws Exception {
        User mockUser = new User();
        mockUser.setEmail("admin@megacitycab.com");
        mockUser.setPassword(PasswordUtils.hashPassword("isAdmin@123"));
        mockUser.setRole("admin");

        when(request.getParameter("email")).thenReturn("admin@megacitycab.com");
        when(request.getParameter("password")).thenReturn("isAdmin@123");
        when(request.getSession()).thenReturn(session);
        when(userDAO.getUserByEmail("admin@megacitycab.com")).thenReturn(mockUser);

        // Mock password verification
        when(PasswordUtils.verifyPassword("isAdmin@123", mockUser.getPassword())).thenReturn(true);

        doNothing().when(response).sendRedirect("AdminDashboard.jsp");

        loginServlet.doPost(request, response);

        verify(response).sendRedirect("AdminDashboard.jsp");
    }

    @Test
    public void testInvalidPassword() throws Exception {
        User mockUser = new User();
        mockUser.setEmail("admin@megacitycab.com");
        mockUser.setPassword(PasswordUtils.hashPassword("isAdmin@123"));

        when(request.getParameter("email")).thenReturn("admin@megacitycab.com");
        when(request.getParameter("password")).thenReturn("wrongPass");
        when(userDAO.getUserByEmail("admin@megacitycab.com")).thenReturn(mockUser);
        when(request.getSession()).thenReturn(session);

        when(PasswordUtils.verifyPassword("wrongPass", mockUser.getPassword())).thenReturn(false);

        when(request.getRequestDispatcher("login.jsp")).thenReturn(dispatcher);
        doNothing().when(dispatcher).forward(request, response);

        loginServlet.doPost(request, response);

        verify(request).setAttribute(eq("errorMessage"), eq("Invalid email or password."));
        verify(dispatcher).forward(request, response);
    }
}
