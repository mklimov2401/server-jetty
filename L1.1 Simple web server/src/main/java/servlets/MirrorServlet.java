package servlets;

import templater.PageGenerator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author v.chibrikov
 * <p>
 * Пример кода для курса на https://stepic.org/
 * <p>
 * Описание курса и лицензия: https://github.com/vitaly-chibrikov/stepic_java_webserver
 */
public class MirrorServlet extends HttpServlet {

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {
        String key = request.getParameter("key");
        Map<String, String[]> requestParameterMap = request.getParameterMap();
        if (requestParameterMap.size() == 1) {
            if (key != null && key != "") {
                response.getWriter().println(request.getParameter("key"));
                response.setContentType("text/html;charset=utf-8");
                response.setStatus(HttpServletResponse.SC_OK);
            } else if (key == "") {
                response.setContentType("text/html;charset=Windows-1251");
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().println("Нет значение у параметра!");
            }
            else{
                response.setContentType("text/html;charset=Windows-1251");
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().println("Параметр не существует!");
            }
        } else {
            response.setContentType("text/html;charset=Windows-1251");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().println("Более одного параметра!");
        }


    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {
        Map<String, Object> pageVariables = createPageVariablesMap(request);

        String message = request.getParameter("message");

        response.setContentType("text/html;charset=utf-8");

        if (message == null || message.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        } else {
            response.setStatus(HttpServletResponse.SC_OK);
        }
        pageVariables.put("message", message == null ? "" : message);

        response.getWriter().println(PageGenerator.instance().getPage("page.html", pageVariables));
    }

    private static Map<String, Object> createPageVariablesMap(HttpServletRequest request) {
        Map<String, Object> pageVariables = new HashMap<>();
        pageVariables.put("method", request.getMethod());
        pageVariables.put("URL", request.getRequestURL().toString());
        pageVariables.put("pathInfo", request.getPathInfo());
        pageVariables.put("sessionId", request.getSession().getId());
        pageVariables.put("parameters", request.getParameterMap().toString());
        pageVariables.put("parameter", request.getParameter("key"));
        return pageVariables;
    }
}
