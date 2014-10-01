/*
 * Eduardo Zardain Canabal
 * Mildred Eunice Catalina Sosa
 +
 */

import java.awt.Image;

public class Bloque extends Base {

    private int iCont;

    public Bloque(int posX, int posY, Image image) {
        super(posX, posY, image);
        iCont = 1;
    }
    
    /**
     * Metodo <I>iVel</I> de la clase <code>Base</code>.
     *
     * @return regresa el contador del objeto bloque.
     *
     */
    public int getCont() {
        return iCont;
    }

    /**
     * Metodo <I>setVel</I> de la clase <code>Base</code>,
     *
     * Modifica el valos de iCont
     *
     */
    public void setCont( int iNum) {
        iCont = iNum;
    }
    
    /**
     * Metodo <I>setVel</I> de la clase <code>Base</code>,
     *
     * Decrementa el valor de iCont en 1
     *
     */
    public void decCont() {
        iCont--;
    }
    

}