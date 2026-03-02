package game;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet(name = "GameServlet", urlPatterns = {"/gameServlet"})
public class GameServlet extends HttpServlet {
    protected void doProcess(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        GameBean game = (GameBean) req.getSession().getAttribute("gameBean");
        try {
            int line = Integer.parseInt(req.getParameter("Line"));
            int col = Integer.parseInt(req.getParameter("Col"));
            game.playPlayerTurn(line, col);
            
            if (game.getWinner() == GameBean.GamePlayer.NOBODY && game.hasEmptyCell()) {
                game.playComputerTurn();
            }
            
            GameBean.GamePlayer winner = game.getWinner();
            if (winner != GameBean.GamePlayer.NOBODY) req.setAttribute("winner", winner.toString());
        } catch (Exception e) {}
        req.getRequestDispatcher("/game.jsp").forward(req, resp);
    }
    @Override protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException { doProcess(req, resp); }
    @Override protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException { doProcess(req, resp); }
}