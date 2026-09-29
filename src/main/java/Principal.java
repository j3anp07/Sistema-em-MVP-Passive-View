
import javax.swing.SwingUtilities;
import presenter.MainPresenter;

public class Principal {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainPresenter mainPresenter = new MainPresenter();
            mainPresenter.iniciar();
        });
    }

}
