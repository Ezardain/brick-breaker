/*
 * Eduardo Zardain Canabal
 * Mildred Eunice Catalina Sosa
 +
 */

import java.awt.Image;

public class Paleta extends Base {

    private int iVel;

    public Paleta(int posX, int posY, Image image) {
        super(posX, posY, image);
        iVel = 0;
    }
    
    /**
     * Metodo <I>iVel</I> de la clase <code>Base</code>.
     *
     * @return regresa la velocidad del objeto paddle.
     *
     */
    public int getVel() {
        return iVel;
    }

    /**
     * Metodo <I>setVel</I> de la clase <code>Base</code>,
     *
     * Modifica el valos de iVel
     *
     */
    public void setVel( int num) {
        iVel = num;
    }
    
    /**
     * Metodo <I>setVel</I> de la clase <code>Base</code>,
     *
     * Incrementa el valor de iVel
     *
     */
    public void incVel( int num) {
        iVel += num;
    }
    

}