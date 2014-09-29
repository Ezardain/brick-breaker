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
    
    private Paleta paPaleta;
    private Pelota pePelota;
    private Base basBloque;
    private LinkedList lliBloques;
    
    private int iVidas;
    private int iScore;
    private int iDireccion;
    private int iDirPelota;
    
    
    private int iColumnas;
    private int iRenglones;
    private int iBloqX;
    private int iBloqY;
    
    private int iVelCorredores;
    private int iCorredorCont;
    
    
    private SoundClip sonCorredor;
    private SoundClip sonCaminador;
    
    private Image imaGameOver;
    private boolean bPausa;
    private boolean bActivo;
    
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
        setSize(576, 800);
        setBackground (Color.black);
        bActivo = true;
        
        iVidas = 3;
        
        
        URL urlImPal = this.getClass().getResource("images/block.png");
        
        // se crea la Paleta
	paPaleta = new Paleta(0, 0, Toolkit.getDefaultToolkit()
                .getImage(urlImPal));
        
        paPaleta.setX(getWidth() / 2 - paPaleta.getAncho() / 2);
        paPaleta.setY(getHeight() - 100);
        
        paPaleta.setVel(6);
        
        // se crea la Pelota
        URL urlImPel = this.getClass().getResource("images/ball1.png");
	pePelota = new Pelota(0, 0, Toolkit.getDefaultToolkit()
                .getImage(urlImPel));
        
        pePelota.setX(getWidth() / 2 - pePelota.getAncho() / 2);
        pePelota.setY(paPaleta.getY() - pePelota.getAlto());
        
        iDirPelota = 1;
        pePelota.setVel(4);
        
        lliBloques = new LinkedList();
        
        iColumnas = 8;
        iRenglones = 8;
        iBloqY = 48;
        iBloqX = 16;
        
        for (int iI = 1; iI <= iRenglones; iI++) {
            
            for (int iJ = 1; iJ <= iColumnas; iJ++) {
                URL urlImBloq = this.getClass()
                .getResource("images/block.png");
                
                basBloque = new Base(0, 0,
                Toolkit.getDefaultToolkit().getImage(urlImBloq));
                basBloque.setY(iBloqY);
                basBloque.setX(iBloqX);
                iBloqX += basBloque.getAncho();
                lliBloques.add(basBloque); 
            }
            iBloqY += basBloque.getAlto();
            iBloqX = 16;
        }
        
        //sonCorredor = new SoundClip("bump.wav");
        //sonCaminador = new SoundClip("coin.wav");
        
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
        if (iDireccion == 1) {
            paPaleta.setX(paPaleta.getX() - paPaleta.getVel());
        }
        if (iDireccion == 2) {
            paPaleta.setX(paPaleta.getX() + paPaleta.getVel());
        }
        
        if (bActivo) {
            if (iDirPelota == 1) {
                pePelota.setX(pePelota.getX() + pePelota.getVel());
                pePelota.setY(pePelota.getY() - pePelota.getVel());
            }
            if (iDirPelota == 2) {
                pePelota.setX(pePelota.getX() - pePelota.getVel());
                pePelota.setY(pePelota.getY() - pePelota.getVel());
            }
            if (iDirPelota == 3) {
                pePelota.setX(pePelota.getX() - pePelota.getVel());
                pePelota.setY(pePelota.getY() + pePelota.getVel());
            }
            if (iDirPelota == 4) {
                pePelota.setX(pePelota.getX() + pePelota.getVel());
                pePelota.setY(pePelota.getY() + pePelota.getVel());
            }
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
        if (paPaleta.getX() < 0) {
            paPaleta.setX(0);
            //bColPaleta = true;
        }
        if (paPaleta.getX() + paPaleta.getAncho() > getWidth()) {
            paPaleta.setX(getWidth() - paPaleta.getAncho());
            //bColPaleta = true;
        }
        
        
        if (pePelota.getX() + pePelota.getAncho() > getWidth() && iDirPelota == 1) {
            iDirPelota = 2;
        }
        if (pePelota.getX() + pePelota.getAncho() > getWidth() && iDirPelota == 4) {
            iDirPelota = 3;
        }
        if (pePelota.getX() < 0 && iDirPelota == 2) {
            iDirPelota = 1;
        }
        if (pePelota.getX() < 0 && iDirPelota == 3) {
            iDirPelota = 4;
        }
        if (pePelota.getY() < 0 && iDirPelota == 2) {
            iDirPelota = 3;
        }
        if (pePelota.getY() < 0 && iDirPelota == 1) {
            iDirPelota = 4;
        }
        
        if (pePelota.intersecta(paPaleta)) {
            if (iDirPelota == 3) {
                iDirPelota = 2;
            }
            if (iDirPelota == 4) {
                iDirPelota = 1;
            }
        }
        
        
        for (Object objBloque : lliBloques) {
            Base basBloque = (Base) objBloque;
            if (pePelota.intersecta(basBloque) && iDirPelota == 1) {
                iDirPelota = 4;
                basBloque.setX(-1000);
                basBloque.setY(-1000);
                iScore += 100;
            }

            if (pePelota.intersecta(basBloque) && iDirPelota == 2) {
                iDirPelota = 3;
                basBloque.setX(-1000);
                basBloque.setY(-1000);
                iScore += 100;
            }

            if (pePelota.intersecta(basBloque) && iDirPelota == 3) {
                iDirPelota = 2;
                basBloque.setX(-1000);
                basBloque.setY(-1000);
                iScore += 100;
            }

            if (pePelota.intersecta(basBloque) && iDirPelota == 4) {
                iDirPelota = 1;
                basBloque.setX(-1000);
                basBloque.setY(-1000);
                iScore += 100;
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
        
        graGraficaApplet.setColor (getBackground ());
        graGraficaApplet.fillRect (0, 0, this.getSize().width, 
                this.getSize().height);
        
        
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
        if (lliBloques != null & paPaleta != null & pePelota != null) {              
            g.setColor(Color.white);
            for (Object objBloque : lliBloques) {
                Base basBloque = (Base) objBloque;
                //Dibuja la imagen del bloque en la posicion actualizada
                g.drawImage(basBloque.getImagen(), basBloque.getX(),
                    basBloque.getY(), this);
            }
            //Dibuja la imagen de Nena en la posicion actualizada
            g.drawImage(paPaleta.getImagen(), paPaleta.getX(),
                    paPaleta.getY(), this);
            g.drawImage(pePelota.getImagen(), pePelota.getX(),
                    pePelota.getY(), this);
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
    public void keyPressed(KeyEvent keyEvent) {
        if (!bPausa) {
            if (keyEvent.getKeyCode() == KeyEvent.VK_LEFT) {
                iDireccion = 1;
            }
            if (keyEvent.getKeyCode() == KeyEvent.VK_RIGHT) {
                iDireccion = 2;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
        if (keyEvent.getKeyCode() == KeyEvent.VK_P) {
            bPausa = !bPausa;
        }
    }
}
