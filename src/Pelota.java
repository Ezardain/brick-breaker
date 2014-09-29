/* Clase Pelota
 * 
 * Eduardo Zardain Canabal
 * Mildred Eunice Catalina Sosa
 *
 */


import java.awt.Image;
import java.awt.Toolkit;

public class Pelota extends Base {

    private static int iConteo;
    private double dVelX;
    private double dVelY;

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
        dVelX = 0;
        dVelY = 0;
        iConteo = 0;

    }

    /**
     * Metodo <I>Pelota</I> metodo <code>getConteo</code> de la clase Pelota
     * regresa el valor de la variable de clase iConteo utilizado para el score.
     *
     * @return regresa el valor <I>Entero<I> de la variable de clase iConteo.
     */
    public static int getConteo() {
        return iConteo;
    }

    /**
     * Metodo <I>Pelota</I> metodo <code>setConteo</code> de la clase Pelota
     * modifica el score de la variable de clase iConteo.
     *
     * @paramcont valor de <I>Entero</I> para modificar el valor de la variable
     * de clase iConteo.
     */
    public static void setConteo(int iCont) {
        iConteo = iCont;
    }

    /**
     * Metodo <I>getVelX</I> de la clase <code>Pelota</code>.
     *
     * @return regresa la velocidad en x del objeto.
     *
     */
    public double getVelX() {
        return dVelX;
    }

    /**
     * Metodo <I>getVelX</I> de la clase <code>Pelota</code>.
     *
     * @return regresa la velocidad en y del objeto.
     *
     */
    public double getVelY() {
        return dVelY;
    }

    /**
     * Metodo <I>setVelX</I> de la clase <code>Pelota</code>, En este metodo
     * se modifica la velocidad en x del objeto pelota.
     *
     * @paramcant tipo de dato <code> double </code> es el valor utilizado para
     * manejar la velocidad en x.
     */
    public void setVelX(double dCant) {
        dVelX = dCant;
    }

    /**
     * Metodo <I>setVelY</I> de la clase <code>Pelota</code>, En este metodo
     * se modifica la velocidad en y del objeto pelota.
     *
     * @paramcant tipo de dato <code> double </code> es el valor utilizado para
     * manejar la velocidad en y.
     */
    public void setVelY(double dCant) {
        dVelY = dCant;
    }
}