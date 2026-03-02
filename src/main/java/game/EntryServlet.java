package game;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet(name = "EntryServlet", urlPatterns = {"/entryServlet"})
public class EntryServlet extends HttpServlet {
    protected void doProcess(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(true);
        GameBean game = (GameBean) session.getAttribute("gameBean");
        if (game == null) {
            game = new GameBean();
            session.setAttribute("gameBean", game);
        }
        game.setStartByUser(req.getParameter("User") != null);
        game.startGame();
        req.getRequestDispatcher("/game.jsp").forward(req, resp);
    }
    @Override protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException { doProcess(req, resp); }
    @Override protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException { doProcess(req, resp); }
}