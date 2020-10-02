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

@WebServlet("/dog")
public class DogServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String dog = req.getParameter("dog");
        String name = req.getParameter("name");
        resp.setContentType("text/html");
        Map<String, Object> objectMap = new HashMap<>();
        objectMap.put("name", name);
        objectMap.put("dog", dog);
        helper.render(req, resp, "dog.ftl", objectMap);
    }


    @Override
    public void init() throws ServletException {
        helper = new Helper();
    }
    Helper helper;
}
