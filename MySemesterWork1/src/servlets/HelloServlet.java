package servlets;

import services.Helper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


@WebServlet("/hello")
public class HelloServlet extends HttpServlet {
    @Override
    public void init() throws ServletException {
        helper = new Helper();
    }

    private Helper helper;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String email = req.getParameter("email");

        resp.setContentType("text/html");


        Map<String, Object> objectMap = new HashMap<>();
        objectMap.put("name", name);
        objectMap.put("email", email);
        helper.render(req, resp, "hello.ftl", objectMap);
    }
}
