/**
 * Brick Breaker
 *
 * @author Eduardo Zardain Canabal A00813391
 * @author Mildred Gatica Sosa A00813946
 *
 * @version 1.00 2008/6/13
 */

import java.io.IOException;
import javax.swing.JFrame;


public class Main {
    public static void main(String[] args) {
        BrickBreaker brkJuego = new BrickBreaker();
        brkJuego.setVisible(true);
        brkJuego.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}