/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.URL;
import java.util.LinkedList;
import javax.swing.JFrame;

/**
 *
 * @author Lalo
 */
public final class BrickBreaker extends JFrame implements Runnable,
        KeyListener {

        /* objetos para manejar el buffer del Applet y este no parpadee */
    private Image    imaImagenApplet;   // Imagen a proyectar en Applet	
    private Graphics graGraficaApplet;  // Objeto grafico de la Imagen
    private Personaje perNena;
    private LinkedList lliCorredores;
    private LinkedList lliCaminadores;
    private int iVidas;
    private int iScore;
    private int iDireccion;
    private int iVelCorredores;
    private int iCorredorCont;
    private int iNumCorredores;
    private int iNumCaminadores;
    private SoundClip sonCorredor;
    private SoundClip sonCaminador;
    private Personaje perCorredor;
    private Personaje perCaminador;
    private Image imaGameOver;
    private boolean bPausa;
    
    /**
     *
     */
    public BrickBreaker() {
        init();
        start();
    }
    
    
    /** 
     * init
     * 
     * Metodo sobrescrito de la clase <code>Applet</code>.<P>
     * En este metodo se inizializan las variables o se crean los objetos
     * a usarse en el <code>Applet</code> y se definen funcionalidades.
     */
    public void init() {
        // hago el applet de un tamaño 500,500
        setSize(800, 600);
        
        iVidas = (int) (Math.random() * 3 + 4);
        
        
        URL urlImagenNena = this.getClass().getResource("nena.gif");
        
        // se crea a Nena
	perNena = new Personaje(0, 0, Toolkit.getDefaultToolkit()
                .getImage(urlImagenNena));
        
        perNena.setX(getWidth() / 2 - perNena.getAncho() / 2);
        perNena.setY(getHeight() - perNena.getAlto());
        
        perNena.setVelocidad(3);
        iDireccion = 2;
        
        iVelCorredores = 3;
        lliCorredores = new LinkedList();
        
        iNumCorredores = (int) (Math.random() * 4 + 12); 
        for (int iI = 1; iI <= iNumCorredores; iI++) {
            URL urlImagenCorredor = this.getClass()
                .getResource("alien2Corre.gif");

            // se crea el objeto corredor 
            perCorredor = new Personaje(0, 0,
                Toolkit.getDefaultToolkit().getImage(urlImagenCorredor));
            perCorredor.setX((int) ( Math.random() * -getWidth() ));
            perCorredor.setY((int) (Math.random() * (getHeight() - 
                    perCorredor.getAlto())));
            lliCorredores.add(perCorredor);
        }
        iCorredorCont = 0;
        
        lliCaminadores = new LinkedList();
        
        iNumCaminadores = (int) (Math.random() * 3 + 10); 
        for (int iI = 1; iI <= iNumCaminadores; iI++) { 
            URL urlImagenChango = this.getClass()
                    .getResource("alien1Camina.gif");

             // se crea el chango
            perCaminador = new Personaje(0, 0,
                Toolkit.getDefaultToolkit().getImage(urlImagenChango));
            // se posiciona suzy en alguna parte al azar del lado derecho
            perCaminador.setX((int) (Math.random() * (getWidth() - 
                    perCaminador.getAncho())));
            perCaminador.setY((int) ( Math.random() * -getHeight() ));
            perCaminador.setVelocidad((int) (Math.random() * 3 + 3));
            lliCaminadores.add(perCaminador);
        }
        
        sonCorredor = new SoundClip("bump.wav");
        sonCaminador = new SoundClip("coin.wav");

        URL urlImagenFin = this.getClass().getResource("game_over.jpeg");
	imaGameOver = Toolkit.getDefaultToolkit().getImage(urlImagenFin);
        
        addKeyListener(this);
    }
	
    /** 
     * start
     * 
     * Metodo sobrescrito de la clase <code>Applet</code>.<P>
     * En este metodo se crea e inicializa el hilo
     * para la animacion este metodo es llamado despues del init o 
     * cuando el usuario visita otra pagina y luego regresa a la pagina
     * en donde esta este <code>Applet</code>
     * 
     */
    public void start () {
        // Declaras un hilo
        Thread th = new Thread (this);
        // Empieza el hilo
        th.start ();
    }
	
    /** 
     * run
     * 
     * Metodo sobrescrito de la clase <code>Thread</code>.<P>
     * En este metodo se ejecuta el hilo, que contendrá las instrucciones
     * de nuestro juego.
     * 
     */
    public void run () {
        // se realiza el ciclo del juego en este caso nunca termina
        while (iVidas > 0) {
            /* mientras dure el juego, se actualizan posiciones de jugadores
               se checa si hubo colisiones para desaparecer jugadores o corregir
               movimientos y se vuelve a pintar todo
            */ 
            if (!bPausa) {
            actualiza();
            checaColision();
            }
            repaint();

            try	{
                // El thread se duerme.
                Thread.sleep (20);
            }
            catch (InterruptedException iexError)	{
                System.out.println("Hubo un error en el juego " + 
                        iexError.toString());
            }
	}
    }
	
    /** 
     * actualiza
     * 
     * Metodo que actualiza la posicion del objeto elefante 
     * 
     */
    public void actualiza(){
        switch(iDireccion) {
            case 1: { //se mueve hacia arriba
                perNena.arriba();
                break;    
            }
            case 2: { //se mueve hacia abajo
                perNena.abajo();
                break;    
            }
            case 3: { //se mueve hacia izquierda
                perNena.izquierda();
                break;    
            }
            case 4: { //se mueve hacia derecha
                perNena.derecha();
                break;    	
            }
        }
        
        for (Object objCorredor : lliCorredores) {
            Personaje perCorredor = (Personaje) objCorredor;
            perCorredor.setVelocidad(iVelCorredores);
            perCorredor.derecha();
            
        }
        for (Object objCaminador : lliCaminadores) {
            Personaje perCaminador = (Personaje) objCaminador;
            perCaminador.setVelocidad((int) (Math.random() * 3 + 3));
            perCaminador.abajo();
        } 
        
        if (iCorredorCont >=5) {
            iVidas--;
            iCorredorCont = 0;
            iVelCorredores++;
        }
    }
	
    /**
     * checaColision
     * 
     * Metodo usado para checar la colision del objeto elefante
     * con las orillas del <code>Applet</code>.
     * 
     */
    public void checaColision(){
        // instrucciones para checar colision y reacomodar personajes si 
        // es necesario
        if(perNena.getY() < 0 || 
                perNena.getY() + perNena.getAlto() > getHeight() ||
                perNena.getX() < 0 ||
                perNena.getX() + perNena.getAncho() > getWidth()) {
            perNena.setVelocidad(0);
        }
        
        for (Object objCaminador : lliCaminadores) {
            Personaje perCaminador = (Personaje) objCaminador;
            //Detecta colisiones en cada elemento de la lista encadenada
            if (perCaminador.getY() + perCaminador.getAlto() > getHeight()) {
                perCaminador.setX((int) (Math.random() * (getWidth() - 
                    perCaminador.getAncho())));
                perCaminador.setY((int) ( Math.random() * -getHeight() ));
                sonCaminador.play();
            }
            if (perNena.colisiona(perCaminador)) {
                sonCaminador.play();
                perCaminador.setX((int) (Math.random() * (getWidth() - 
                    perCaminador.getAncho())));
                perCaminador.setY((int) ( Math.random() * -getHeight() ));
                iScore++;
            }
        }
        for (Object objCorredor : lliCorredores) {
            Personaje perCorredor = (Personaje) objCorredor;
            //Detecta colisiones en cada elemento de la lista encadenada
            if (perCorredor.getX() + perCorredor.getAncho() > getWidth()) {
                perCorredor.setX(0);
                perCorredor.setY((int) (Math.random() * (getHeight() - 
                    perCorredor.getAlto())));
            }
            if (perNena.colisiona(perCorredor)) {
                sonCorredor.play();
                perCorredor.setX((int) ( Math.random() * -getWidth() ));
                perCorredor.setY((int) (Math.random() * (getHeight() - 
                    perCorredor.getAlto())));
                iCorredorCont++;
            }
        }
    }
	
    /**
     * paint
     * 
     * Metodo sobrescrito de la clase <code>Applet</code>,
     * heredado de la clase Container.<P>
     * En este metodo lo que hace es actualizar el contenedor y 
     * define cuando usar ahora el paint
     * @param graGrafico es el <code>objeto grafico</code> usado para dibujar.
     * 
     */
    public void paint (Graphics graGrafico){
        // Inicializan el DoubleBuffer
        if (imaImagenApplet == null){
                imaImagenApplet = createImage (this.getSize().width, 
                        this.getSize().height);
                graGraficaApplet = imaImagenApplet.getGraphics ();
        }

        // Actualiza la imagen de fondo.
        URL urlImagenFondo = this.getClass().getResource("espacio.jpg");
        Image imaImagenEspacio = Toolkit.getDefaultToolkit()
                .getImage(urlImagenFondo);
        

        // Despliego la imagen
        graGraficaApplet.drawImage(imaImagenEspacio, 0, 0, 
                getWidth(), getHeight(), this);
      
        
        // Actualiza el Foreground.
        graGraficaApplet.setColor (getForeground());
        paint1(graGraficaApplet);

        // Dibuja la imagen actualizada
        graGrafico.drawImage (imaImagenApplet, 0, 0, this);
    }
    
    /**
     * paint1
     * 
     * Metodo sobrescrito de la clase <code>Applet</code>,
     * heredado de la clase Container.<P>
     * En este metodo se dibuja la imagen con la posicion actualizada,
     * ademas que cuando la imagen es cargada te despliega una advertencia.
     * @param g es el <code>objeto grafico</code> usado para dibujar.
     * 
     */
    public void paint1(Graphics g) {
        // si la imagen ya se cargo
        if (lliCorredores != null & lliCaminadores != null & perNena != null) {              
                g.setColor(Color.white);
                for (Object objCorredor : lliCorredores) {
                    Personaje perCorredor = (Personaje) objCorredor;
                    //Dibuja la imagen del corredor en la posicion actualizada
                    g.drawImage(perCorredor.getImagen(), perCorredor.getX(),
                        perCorredor.getY(), this);
                }
                for (Object objCaminador : lliCaminadores) {
                    Personaje perCaminador = (Personaje) objCaminador;
                    //Dibuja la imagen del caminador en la posicion actualizada
                    g.drawImage(perCaminador.getImagen(), perCaminador.getX(),
                        perCaminador.getY(), this);
                } 
                //Dibuja la imagen de Nena en la posicion actualizada
                g.drawImage(perNena.getImagen(), perNena.getX(),
                        perNena.getY(), this);
                g.drawString("Score:", 10, 40);
                g.drawString(String.valueOf(iScore), 60, 40);
                g.drawString("Vidas:", 120, 40);
                g.drawString(String.valueOf(iVidas), 180, 40);
                if (iVidas < 1) {
                    g.drawImage(imaGameOver, 0, 0, this);
                }
             
        } // sino se ha cargado se dibuja un mensaje 
        else {
                //Da un mensaje mientras se carga el dibujo	
                g.drawString("No se cargo la imagen..", 20, 20);
        }
    }
    
    /**
     * toString
     * 
     * Obtiene el valor del objeto Juego que trae Score y vidas
     * 
     * @returns un String con los datos del objeto
     */
    public String toString() {
        return " Score = " + iScore + " Vidas = " + iVidas;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyPressed(KeyEvent e) {
        //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
        if (keyEvent.getKeyCode() == KeyEvent.VK_P) {
            bPausa = !bPausa;
        }
    }
}
