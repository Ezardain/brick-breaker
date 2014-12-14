/*
 * Eduardo Zardain Canabal
 * Mildred Eunice Catalina Sosa
 +
 */

import java.awt.Image;

public class Bloque extends Base {

    private int iCont;
    private boolean bPowerUp; //Al ser golpeados pueden hacer slow motion 
                             //a la pelota
    private boolean bPowerLong; //Al ser golpeados pueden hacer el carrito mas largo 

    public Bloque(int posX, int posY, Image image) {
        super(posX, posY, image);
        iCont = 1;
        bPowerUp = false;
        bPowerLong = false;
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
     * Metodo <I>iVel</I> de la clase <code>Base</code>.
     *
     * @return regresa el power up del objeto bloque.
     *
     */
    public boolean getPowerUp() {
        return bPowerUp;
    }

    /**
     * Metodo <I>setVel</I> de la clase <code>Base</code>,
     *
     * Modifica el valor de bPowerUp
     *
     */
    public void setPowerUp( boolean bPowUp ) {
        bPowerUp = bPowUp;
    }
    
    /**
     * Metodo <I>iVel</I> de la clase <code>Base</code>.
     *
     * @return regresa el power long del objeto bloque.
     *
     */
    public boolean getPowerLong() {
        return bPowerLong;
    }

    /**
     * Metodo <I>setVel</I> de la clase <code>Base</code>,
     *
     * Modifica el valor de bPowerUp
     *
     */
    public void setPowerLong( boolean bPowLong ) {
        bPowerLong = bPowLong;
    }
    
    /**
     * Metodo <I>setVel</I> de la clase <code>Base</code>,
     *
     * Decrementa el valor de iCont en 1
     *
     */
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