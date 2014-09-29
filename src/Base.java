
/**
 * Personaje
 *
 * Modela la definición de todos los objetos de tipo
 * <code>Personaje</code>
 *
 * @author Eduardo Zardain Canabal A00813391
 * @author Mildred Gatica Sosa A00813946
 * @version 1.00 2008/6/13
 */
import javax.swing.ImageIcon;
import java.awt.Image;
import java.awt.Rectangle;

public class Base {

    private int iPosX;    //posicion en x.       
    private int iPosY;	//posicion en y.
    private ImageIcon imaIcono;    //imaIcono.

    /**
     * Metodo <I>Base</I> constructor de la clase <code>Base</code>, En este
     * metodo se construye el objet.
     *
     * @paramiPosX tipo de dato <code>Entero</code> es el valor utilizado para
     * manejar la posicion en x.
     * @paramiPosY tipo de dato <code>Entero</code> es el valor utilizado para
     * manejar la posicion en y.
     * @parageimage tipo de dato <code>Imagen</code> es la imagen utilizada para
     * construir el objeto.
     */
    public Base(int iPosX, int iPosY, Image imaImagen) {
        this.iPosX = iPosX;
        this.iPosY = iPosY;
        imaIcono = new ImageIcon(imaImagen);

    }

    /**
     * Metodo <I>setPosX</I> de la clase <code>Base</code>, En este metodo se
     * construye el objeto.
     *
     * @iPosX tipo de dato <code>Entero</code> es el valor utilizado para
     * manejar la posicion en x.
     */
    public void setPosX(int iPosX) {
        this.iPosX = iPosX;
    }

    /**
     * Metodo <I>setPosy</I> de la clase <code>Base</code>, En este metodo se
     * construye el objeto.
     *
     * @paramiPosX tipo de dato <code> Entero </code> es el valor utilizado para
     * manejar la posicion en y.
     */
    public void setPosY(int iPosY) {
        this.iPosY = iPosY;
    }

    /**
     * Metodo <I>getPosX</I> de la clase <code>Base</code>.
     *
     * @return regresa la posicion en x del objeto.
     *
     */
    public int getPosX() {
        return iPosX;
    }

    /**
     * Metodo <I>getPosY</I> de la clase <code>Base</code>,
     *
     * @return regresa el valor de la posicion en y.
     *
     */
    public int getPosY() {
        return iPosY;
    }

    /**
     * Metodo <I>setImageIcon</I> de la clase <code>Base</code>, En este metodo
     * se modifica la imagen del objeto.
     *
     * @paramsetImageIcon contiene la imagen para modificar.
     */
    public void setImageIcon(ImageIcon imaIcono) {
        this.imaIcono = imaIcono;
    }

    /**
     * Metodo <I>getImageIcon</I> de la clase <code>Base</code>, En este metodo
     * se
     *
     * @return regresa la imagen del objeto
     *
     */
    public ImageIcon getImageIcon() {
        return imaIcono;
    }

    /**
     * Metodo <I>getAncho<I> metodo de acceso que regresa el ancho del imaIcono
     *
     * @return un objeto de la clase <code>ImageIcon</code> que es el ancho del
     * imaIcono.
     */
    public int getAncho() {
        return imaIcono.getIconWidth();
    }

    /**
     * Metodo <I>getIconHeight<I> metodo de acceso que regresa la altura del
     * imaIcono
     *
     * @return un objeto de la clase <code>ImageIcon</code> que es la altura del
     * imaIcono.
     */
    public int getAlto() {
        return imaIcono.getIconHeight();
    }

    /**
     * Metodo <I>getImagen</I> metodo de acceso que regresa la imagen del
     * objeto.
     *
     * @return un objeto de la clase <code>Image</code> que es la imagen del
     * imaIcono.
     */
    public Image getImagenI() {
        return imaIcono.getImage();
    }

    /**
     * Metodo <I>getPerimetro</I> metodo de acceso que regresa el rectangulo del
     * objeto.
     *
     * @return un objeto de la clase <code>Rectangle</code> que es el perimetro
     * del rectangulo
     */
    public Rectangle getPerimetro() {
        return new Rectangle(getPosX(), getPosY(), getAncho(), getAlto());
    }

    /**
     * Metodo <I>intersecta2</I> metodo de acceso que regresa un valor booleano
     * si intersecta con ciertos puntos.
     *
     * @paramiPosX tipo de dato <code>Entero</code> objeto mandado para verificar
     * si intersecta.
     * @paramiPosY tipo de dato <code>Entero</code> objeto mandado para verificar
     * si intersecta.
     * @return un booleano que determina si los objetos se intersectan.
     */
    public boolean intersectaPuntos(int iPosX, int iPosY) {
        return getPerimetro().contains(iPosX, iPosY);
    }

    /**
     * Metodo <I>intersecta</I> metodo de acceso que regresa un valor booleano
     * si intersecta con cierto objeto.
     *
     * @obj tipo de dato <code>Entero</code> objeto mandado para verificar si
     * intersecta.
     * @return un booleano que determina si los objetos se intersectan.
     */
    public boolean intersecta(Base obj) {
        // return getPerimRec().intersects(obj.getPerimetro());
        return getPerimetro().intersects(obj.getPerimetro());
    }

}