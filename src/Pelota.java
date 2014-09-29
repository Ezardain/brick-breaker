/* Clase Pelota
 * 
 * Eduardo Zardain Canabal
 * Mildred Eunice Catalina Sosa
 *
 */


import java.awt.Image;
import java.awt.Toolkit;

public class Pelota extends Base {

    private int iVel;

    /**
     * Metodo <I>Pelota</I> constructor de la clase <code>Pelota</code>, En este
     * metodo se construye el objeto.
     *
     * @paramposX tipo de dato <code>Entero</code> es el valor utilizado para
     * manejar la posicion en x.
     * @paramposY tipo de dato <code>Entero</code> es el valor utilizado para
     * manejar la posicion en y.
     * @parageimage tipo de dato <code>Imagen</code> es la imagen utilizada para
     * construir el objeto.
     */
    public Pelota(int posX, int posY, Image image) {
        super(posX, posY, image);
        iVel = 0;
    }

    /**
     * Metodo <I>getVelX</I> de la clase <code>Pelota</code>.
     *
     * @return regresa la velocidad en x del objeto.
     *
     */
    public double getVel() {
        return iVel;
    }
    
    /**
     * Metodo <I>setVelX</I> de la clase <code>Pelota</code>, En este metodo
     * se modifica la velocidad en x del objeto pelota.
     *
     * @paramcant tipo de dato <code> double </code> es el valor utilizado para
     * manejar la velocidad en x.
     */
    public void setVel(int iCant) {
        iVel = iCant;
    }
}