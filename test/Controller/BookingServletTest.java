package Controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class BookingServletTest {

    private BookingServlet instance;
    private TestHttpServletRequest request;
    private TestHttpServletResponse response;
    private HttpSession session;

    @Before
    public void setUp() {
        instance = new BookingServlet();
        request = new TestHttpServletRequest();
        response = new TestHttpServletResponse();
    }

    @After
    public void tearDown() {
        instance = null;
    }

    @Test
    public void testDoPostWithInvalidSession() throws IOException, ServletException {
        instance.doPost(request, response);
        assertTrue(response.toString().contains("Login.jsp?error=Session Expired"));
    }

    @Test
    public void testDoPostWithValidData() throws IOException, ServletException {
        session = request.getSession(true);
        session.setAttribute("email", "test@example.com");

        request.setParameter("pickup", "Location A");
        request.setParameter("dropoff", "Location B");
        request.setParameter("vehicleType", "Car");
        request.setParameter("rideDate", "2025-03-15");
        request.setParameter("rideTime", "12:00");
        request.setParameter("calculatedPrice", "1500.0");

        instance.doPost(request, response);
        assertTrue(response.toString().contains("myBookings.jsp?success=Ride Booked Successfully"));
    }

    // ✅ Simulated HttpServletRequest
    private static class TestHttpServletRequest implements HttpServletRequest {
        private final Map<String, String> parameters = new HashMap<>();
        private final HttpSession session = new TestHttpSession();

        public void setParameter(String key, String value) {
            parameters.put(key, value);
        }

        @Override
        public String getParameter(String name) {
            return parameters.get(name);
        }

        @Override
        public HttpSession getSession(boolean create) {
            return session;
        }

        @Override
        public HttpSession getSession() {
            return session;
        }

        // Implementing other required methods
        @Override public String getAuthType() { return null; }
        @Override public String getContextPath() { return null; }
        @Override public Cookie[] getCookies() { return null; }
        @Override public long getDateHeader(String name) { return 0; }
        @Override public String getHeader(String name) { return null; }
        @Override public Enumeration<String> getHeaderNames() { return null; }
        @Override public Enumeration<String> getHeaders(String name) { return null; }
        @Override public int getIntHeader(String name) { return 0; }
        @Override public String getMethod() { return null; }
        @Override public String getPathInfo() { return null; }
        @Override public String getPathTranslated() { return null; }
        @Override public String getQueryString() { return null; }
        @Override public String getRemoteAddr() { return null; }
        @Override public String getRemoteHost() { return null; }
        @Override public String getRequestedSessionId() { return null; }
        @Override public String getRequestURI() { return null; }
        @Override public StringBuffer getRequestURL() { return null; }
        @Override public String getServletPath() { return null; }
        @Override public boolean isSecure() { return false; }
        @Override public void removeAttribute(String name) { }
        @Override public void setAttribute(String name, Object o) { }
        @Override public void setCharacterEncoding(String env) throws UnsupportedEncodingException { }
        @Override public void setContentLength(int len) { }
        @Override public void setContentType(String type) { }
        @Override public void setIntHeader(String name, int value) { }
        @Override public void setStatus(int sc) { }
        @Override public void setStatus(int sc, String sm) { }
    }

    // ✅ Simulated HttpServletResponse
    private static class TestHttpServletResponse implements HttpServletResponse {
        private final StringWriter responseWriter = new StringWriter();

        @Override
        public PrintWriter getWriter() throws IOException {
            return new PrintWriter(responseWriter);
        }

        @Override
        public void sendRedirect(String location) throws IOException {
            responseWriter.write(location);
        }

        @Override
        public String toString() {
            return responseWriter.toString();
        }

        // Implementing other required methods
        @Override public void addCookie(Cookie cookie) { }
        @Override public void addDateHeader(String name, long date) { }
        @Override public void addHeader(String name, String value) { }
        @Override public void addIntHeader(String name, int value) { }
        @Override public boolean containsHeader(String name) { return false; }
        @Override public String encodeRedirectURL(String url) { return null; }
        @Override public String encodeRedirectUrl(String url) { return null; }
        @Override public String encodeURL(String url) { return null; }
        @Override public String encodeUrl(String url) { return null; }
        @Override public void flushBuffer() throws IOException { }
        @Override public int getBufferSize() { return 0; }
        @Override public String getCharacterEncoding() { return null; }
        @Override public String getContentType() { return null; }
        @Override public void setBufferSize(int size) { }
        @Override public void setCharacterEncoding(String charset) { }
        @Override public void setContentLength(int len) { }
        @Override public void setContentType(String type) { }
        @Override public void setLocale(Locale loc) { }
        @Override public void setStatus(int sc) { }
        @Override public void setStatus(int sc, String sm) { }
    }

    // ✅ Simulated HttpSession
    private static class TestHttpSession implements HttpSession {
        private final Map<String, Object> attributes = new HashMap<>();

        @Override
        public void setAttribute(String name, Object value) {
            attributes.put(name, value);
        }

        @Override
        public Object getAttribute(String name) {
            return attributes.get(name);
        }

        // Implementing other required methods
        @Override public long getCreationTime() { return 0; }
        @Override public String getId() { return null; }
        @Override public long getLastAccessedTime() { return 0; }
        @Override public int getMaxInactiveInterval() { return 0; }
        @Override public void invalidate() { }
        @Override public boolean isNew() { return false; }
        @Override public void removeAttribute(String name) { }
        @Override public void setMaxInactiveInterval(int interval) { }
    }
}
