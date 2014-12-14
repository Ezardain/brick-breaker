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
import javax.swing.ImageIcon;
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
    private Paleta paPaletaLarga;
    private Pelota pePelota;
    private Bloque bloBloque;
    private LinkedList lliBloques;
    
    private int iVidas;
    private int iScore;
    private int iDireccion;
    private int iDirPelota;
    private int iContTiempo; //Cuenta tiempo de slow motion
    private int iContTiempo2; //Cuenta tiempo que dura el bus largo
    
    
    private int iColumnas;
    private int iRenglones;
    private int iTotalBloques;
    private int iBloqX;
    private int iBloqY;
    private int iEliminados;
    
    private SoundClip sonIntro;
    private SoundClip sonJuego;
    private SoundClip sonChoque;
    private SoundClip sonSelect;
    private SoundClip sonGameOver;
    private SoundClip sonPierde;
    
    private Image imaGameOver;
    private Image imaTitulo;
    private Image imaVictoria;
    private Image imaBloque1;
    private Image imaBloque2;
    private Image imaBloque3;
    private Image imaPausa;
    private Image imaFondo2;
    private Image imaLetreroEspacio;
    private Image imaVidas;
    private Image imaScore;
    private Image imaFlask;
    private Image imaBee;
    
    private ImageIcon imaBus;
    private ImageIcon imaBusLargo;
    
    private boolean bPausa;
    private boolean bActivo;
    private boolean bInicio;
    private boolean bVictoria;
    private boolean bNivel2;
    private boolean bSonido;
    private boolean bColision;
    private boolean bSlowMotion;
    private boolean bPaletaLarga;
    
    private long tiempoActual;
    private long tiempoInicial;
    
    private Animacion aniPelota;
    private Animacion aniHumo;
    private Animacion aniLetrero;
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
        bInicio = false;
        bActivo = false;
        
        iVidas = 3;
        iEliminados = 7;
        
        URL urlImPal = this.getClass().getResource("images/truck.png");
        
        // se crea la Paleta
	paPaleta = new Paleta(0, 0, Toolkit.getDefaultToolkit()
                .getImage(urlImPal));
        
        paPaleta.setX(getWidth() / 2 - paPaleta.getAncho() / 2);
        paPaleta.setY(getHeight() - paPaleta.getAlto() - 50);
        
        urlImPal = this.getClass().getResource("images/truck.png");
        imaBus =  new ImageIcon(urlImPal);
        
        urlImPal = this.getClass().getResource("images/truck_large.png");
        imaBusLargo =  new ImageIcon(urlImPal);
        
        paPaleta.setVel(8);
        
        // se crea la Pelota
        URL urlImPel = this.getClass().getResource("images/ball1.png");
	pePelota = new Pelota(0, 0, Toolkit.getDefaultToolkit()
                .getImage(urlImPel));
        
        pePelota.setX(getWidth() / 2 - pePelota.getAncho() / 2);
        pePelota.setY(paPaleta.getY() - pePelota.getAlto());
        
        iDirPelota = 1;
        pePelota.setVel(8);
        
        lliBloques = new LinkedList();
        
        iTotalBloques = 64;
        iColumnas = 8;
        iRenglones = 8;
        iBloqY = 64;
        iBloqX = 16;
        
        for (int iI = 1; iI <= iRenglones; iI++) {
            
            for (int iJ = 1; iJ <= iColumnas; iJ++) {
                URL urlImBloq = this.getClass()
                .getResource("images/block.png");
                
                bloBloque = new Bloque(0, 0,
                Toolkit.getDefaultToolkit().getImage(urlImBloq));
                bloBloque.setY(iBloqY);
                bloBloque.setX(iBloqX);
                iBloqX += bloBloque.getAncho();
                lliBloques.add(bloBloque);
                if (iI % 2 == 0) {
                    if(iI == iJ) {
                        bloBloque.setPowerUp(true);
                    }
                }
                if(iI == 2 && iJ == 7) {
                    bloBloque.setPowerLong (true);
                }
                if(iI == 3 && iJ == 5) {
                    bloBloque.setPowerLong (true);
                }
                if(iI == 6 && iJ == 2) {
                    bloBloque.setPowerLong (true);
                }
                if(iI == 8 && iJ == 1){
                    bloBloque.setPowerLong (true);
                }
                if(iI == 7 && iJ == 4){
                    bloBloque.setPowerLong (true);
                }
            }
            iBloqY += bloBloque.getAlto();
            iBloqX = 16;
        }
        
        //Inicializacion de las imagenes del juego
        URL urlImTitulo = this.getClass()
                .getResource("images/title_screen.png");
	imaTitulo = Toolkit.getDefaultToolkit().getImage(urlImTitulo);
        
        URL urlImGaOv = this.getClass()
                .getResource("images/game_over.png");
	imaGameOver = Toolkit.getDefaultToolkit().getImage(urlImGaOv);
        
        URL urlImVic = this.getClass()
                .getResource("images/success.png");
	imaVictoria = Toolkit.getDefaultToolkit().getImage(urlImVic);
        
        
        URL urlImBloque1 = this.getClass()
                .getResource("images/block.png");
	imaBloque1 = Toolkit.getDefaultToolkit().getImage(urlImBloque1);
        URL urlImBloque2 = this.getClass()
                .getResource("images/block2.png");
	imaBloque2 = Toolkit.getDefaultToolkit().getImage(urlImBloque2);
        URL urlImBloque3 = this.getClass()
                .getResource("images/block3.png");
	imaBloque3 = Toolkit.getDefaultToolkit().getImage(urlImBloque3);
        
        URL urlImPausa = this.getClass()
                .getResource("images/pause.png");
	imaPausa = Toolkit.getDefaultToolkit().getImage(urlImPausa);
        
        URL urlImEspacio = this.getClass()
                .getResource("images/espacio.png");
	imaLetreroEspacio = Toolkit.getDefaultToolkit().getImage(urlImEspacio);
        URL urlImVidas = this.getClass()
                .getResource("images/letrero_vidas.png");
	imaVidas = Toolkit.getDefaultToolkit().getImage(urlImVidas);
        URL urlImScore = this.getClass()
                .getResource("images/letrero_score.png");
	imaScore = Toolkit.getDefaultToolkit().getImage(urlImScore);
        
        URL urlImFlask = this.getClass()
                .getResource("images/flask.png");
	imaFlask = Toolkit.getDefaultToolkit().getImage(urlImFlask);
        URL urlImBee = this.getClass()
                .getResource("images/bee.png");
	imaBee = Toolkit.getDefaultToolkit().getImage(urlImBee);
        
        
        //Incializacion de animaciones
        Image imaPel1 = Toolkit.getDefaultToolkit().
                getImage(this.getClass().getResource("images/ball1.png"));
        Image imaPel2 = Toolkit.getDefaultToolkit().
                getImage(this.getClass().getResource("images/ball2.png"));
        Image imaPel3 = Toolkit.getDefaultToolkit().
                getImage(this.getClass().getResource("images/ball3.png"));
        Image imaPel4 = Toolkit.getDefaultToolkit().
                getImage(this.getClass().getResource("images/ball4.png"));
        
        aniPelota = new Animacion();
	aniPelota.sumaCuadro(imaPel1, 100);
	aniPelota.sumaCuadro(imaPel2, 100);
	aniPelota.sumaCuadro(imaPel3, 100);
	aniPelota.sumaCuadro(imaPel4, 100);
        
        
        Image imaHumo1 = Toolkit.getDefaultToolkit().
                getImage(this.getClass().getResource("images/humo1.png"));
        Image imaHumo2 = Toolkit.getDefaultToolkit().
                getImage(this.getClass().getResource("images/humo2.png"));
        Image imaHumo3 = Toolkit.getDefaultToolkit().
                getImage(this.getClass().getResource("images/humo3.png"));
        Image imaHumo4 = Toolkit.getDefaultToolkit().
                getImage(this.getClass().getResource("images/humo4.png"));
        
        aniHumo = new Animacion();
	aniHumo.sumaCuadro(imaHumo1, 100);
	aniHumo.sumaCuadro(imaHumo2, 100);
	aniHumo.sumaCuadro(imaHumo3, 100);
	aniHumo.sumaCuadro(imaHumo4, 100);
        
        
        Image imaLetrero1 = Toolkit.getDefaultToolkit().
                getImage(this.getClass().getResource("images/letrero_inicio1.png"));
        Image imaLetrero2 = Toolkit.getDefaultToolkit().
                getImage(this.getClass().getResource("images/letrero_inicio2.png"));
        
        aniLetrero = new Animacion();
	aniLetrero.sumaCuadro(imaLetrero1, 50);
	aniLetrero.sumaCuadro(imaLetrero2, 50);
        
        //Sonidos
        sonIntro = new SoundClip("sounds/intro.wav");
        sonIntro.setLooping(true);
        sonJuego = new SoundClip("sounds/game.wav");
        sonJuego.setLooping(true);
        sonChoque = new SoundClip("sounds/bump.wav");
        sonSelect = new SoundClip("sounds/select.wav");
        sonGameOver = new SoundClip("sounds/game_over.wav");
        sonPierde = new SoundClip("sounds/lose.wav");
        
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
        while (true) {
            /* mientras dure el juego, se actualizan posiciones de jugadores
               se checa si hubo colisiones para desaparecer jugadores o corregir
               movimientos y se vuelve a pintar todo
            */ 
            if (bInicio) {
                if (!bPausa) {
                actualiza();
                checaColision();
                }
                repaint();
            }

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
        
        if (!bActivo) {
            pePelota.setX(paPaleta.getX() + paPaleta.getAncho() / 2
                    - pePelota.getAncho() / 2);
        }
        
        if (iTotalBloques == 0) {
            bInicio = false;
            if (!bNivel2) {
                bNivel2 = true;
                loadNivel2();
            } else {
                bVictoria = true;
            }
        }
        
        long tiempoTranscurrido = System.currentTimeMillis() - tiempoActual;
            
         //Guarda el tiempo actual
       	 tiempoActual += tiempoTranscurrido;
         aniPelota.actualiza(tiempoTranscurrido);
         aniHumo.actualiza(tiempoTranscurrido);
         aniLetrero.actualiza(tiempoTranscurrido);
    }
	
    /**
     * checaColision
     * 
     * Metodo usado para checar la colision del objeto elefante
     * con las orillas del <code>Applet</code>.
     * 
     */
    public void checaColision(){
        //Si hay slow motion cuenta el tiempo en el que va con esa caract.
        if(bSlowMotion) {
            iContTiempo++;
            if(iContTiempo == 5000/20) { //Si es igual a 5 segundos
                bSlowMotion = false;
                pePelota.setVel(8);
            }
        }
        
        //Si hay paleta larga cuenta el tiempo en el que va con esa caract.
        if(bPaletaLarga) {
            iContTiempo2++;
            if(iContTiempo2 == 5000/20) { //Si es igual a 5 segundos
                bPaletaLarga = false;
                paPaleta.setImageIcon(imaBus);
            }
        }

        //Colision de la paleta con las orillas
        if (paPaleta.getX() < 0) {
            paPaleta.setX(0);
        }
        if (paPaleta.getX() + paPaleta.getAncho() > getWidth()) {
            paPaleta.setX(getWidth() - paPaleta.getAncho());
        }
        
        //Colision de la pelota con las orillas
        if (pePelota.getX() + pePelota.getAncho() > getWidth() &&
                iDirPelota == 1) {
            iDirPelota = 2;
        }
        if (pePelota.getX() + pePelota.getAncho() > getWidth() &&
                iDirPelota == 4) {
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
        
        //Interseccion de la pelota con la paleta
        if (pePelota.intersecta(paPaleta)) {
            //Si colisiona con los lados de la paleta debe de perder
            if (pePelota.getY() + pePelota.getAlto() >
                    getHeight() - paPaleta.getAlto() + 10) {
                if (iVidas > 0) {
                    perderVida();
                }
            } else {
                if (iDirPelota == 3) {
                    iDirPelota = 2;
                }
                if (iDirPelota == 4) {
                    iDirPelota = 1;
                }
            }
        }
        
        
        //Colision de la pelota con los bloques
        for (Object objBloque : lliBloques) {
            Bloque bloBloque = (Bloque) objBloque;
            if (pePelota.intersecta(bloBloque)) {
                sonChoque.play();
                bloBloque.setX(-200);
                bloBloque.setY(-200);
                iScore += 50;
                iTotalBloques--;
                iEliminados++;

                if (!bColision) {
                    bColision = true;
                    switch (iDirPelota) {
                        case 1: iDirPelota = 4;
                                break;
                        case 2: iDirPelota = 3;
                                break;
                        case 3: iDirPelota = 2;
                                break;
                        case 4: iDirPelota = 1;
                                break; 
                    }
                }
                if(bloBloque.getPowerUp()){
                    bSlowMotion = true;
                    pePelota.setVel(3);
                    iContTiempo = 0;
                }
                if(bloBloque.getPowerLong()){
                        bPaletaLarga = true;
                        paPaleta.setImageIcon(imaBusLargo);
                        iContTiempo2 = 0;
                }
            }   
        }
        bColision = false;
        
        //Colision con el fondo del juego
        if (pePelota.getY() + pePelota.getAlto() > getHeight()) {
            if (!bNivel2) {
                pePelota.setVel(8);
            } else {
                pePelota.setVel(10);
            }
            if (iVidas > 0) {
                perderVida();
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
        
        
        // Actualiza la imagen de fondo.
        URL urlImagenFondo = this.getClass()
                .getResource("images/background1.png");
        Image imaFondo = Toolkit.getDefaultToolkit()
                .getImage(urlImagenFondo);
        
        // Despliego la imagen
        graGraficaApplet.drawImage(imaFondo, 0, 0, 
                getWidth(), getHeight(), this);
        
        if (bNivel2) {
            urlImagenFondo = this.getClass()
                .getResource("images/background2.png");
            Image imaFondo2 = Toolkit.getDefaultToolkit()
                .getImage(urlImagenFondo);
            graGraficaApplet.drawImage(imaFondo2, 0, 0, 
                getWidth(), getHeight(), this);
        }
        

        
        
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
            if (!bInicio && iVidas > 0) {
                g.drawImage(imaTitulo, 0, 0, this);
                g.drawImage(aniLetrero.getImagen(), 0, 0, this);
                sonIntro.play();
            }
            
            if (iVidas < 1) {
                g.drawImage(imaGameOver, 0, 0, this);
                bSonido = false;
                sonJuego.stop();
                sonGameOver.play();
            }
            
            if (iVidas > 0 && bInicio) {
                g.setColor(Color.white);
                if (bSonido) {
                    sonJuego.play();
                    bSonido = false;
                }
                for (Object objBloque : lliBloques) {
                    Bloque bloBloque = (Bloque) objBloque;
                    //Dibuja la imagen del bloque en la posicion actualizada
                    g.drawImage(imaBloque1, bloBloque.getX(), 
                                bloBloque.getY(), this);
                    if (bloBloque.getPowerUp()) {
                        g.drawImage(imaFlask, bloBloque.getX() + 20, 
                                bloBloque.getY() + 2, this);
                    }
                    if (bloBloque.getPowerLong()) {
                        g.drawImage(imaBee, bloBloque.getX() + 20, 
                                bloBloque.getY() + 2, this);
                    }
                }
                //Dibuja la imagen en la posicion actualizada
                g.drawImage(paPaleta.getImagen(), paPaleta.getX(),
                        paPaleta.getY(), this);
                g.drawImage(aniPelota.getImagen(), pePelota.getX(),
                        pePelota.getY(), this);
                g.drawImage(aniHumo.getImagen(), paPaleta.getX() - 170,
                        paPaleta.getY() + 40, this);
                g.drawImage(imaVidas, 10, 20, this);
                g.drawImage(imaScore, 140, 20, this);
                g.drawString(String.valueOf(iVidas), 115, 55);
                g.drawString(String.valueOf(iScore), 245, 55);
                if (!bActivo) {
                    g.drawImage(imaLetreroEspacio, 0, 0, this);
                }
            }
            
            if (bPausa) {
                g.drawImage(imaPausa, 0, 0, this);
            }
            
            if (bVictoria) {
                g.drawImage(imaVictoria, 0, 0, this);
            }
        } // sino se ha cargado se dibuja un mensaje 
        else {
                //Da un mensaje mientras se carga el dibujo	
                g.drawString("No se cargo la imagen..", 20, 20);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        //To change body of generated methods, choose Tools | Templates.
    }
    
    /**
     * keyPressed
     * Metodo sobrescrito de la interface <code>KeyListener</code>.<P>
     * En este metodo maneja el evento que se genera al presionar la tecla.
     * @param keyEvent es el <code>evento</code> que se genera en al soltar las teclas.
     */
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
    
    /**
     * keyReleased
     * Metodo sobrescrito de la interface <code>KeyListener</code>.<P>
     * En este metodo maneja el evento que se genera al soltar la tecla.
     * @param keyEvent es el <code>evento</code> que se genera en al soltar las teclas.
     */
    @Override
    public void keyReleased(KeyEvent keyEvent) {
        if (keyEvent.getKeyCode() == KeyEvent.VK_P) {
            bPausa = !bPausa;
        }
        if (keyEvent.getKeyCode() == KeyEvent.VK_I) {
           if (!bInicio) {
               bInicio = true;
               sonSelect.play();
               sonIntro.stop();
               bSonido = true;
           }
           if (iVidas < 1 || bVictoria) {
               sonGameOver.stop();
               reload();
               sonSelect.play();
               bSonido = true;
           }
        }
        if (keyEvent.getKeyCode() == KeyEvent.VK_SPACE) {
            if (bInicio && !bActivo) {
               bActivo = true;
           }
        }
        if (keyEvent.getKeyCode() == KeyEvent.VK_V) {
            bVictoria = true;
        }
    }
    
    
    /**
     * perderVida
     * Metodo que quita una vida al usuario y resetea la posicion de la pelota
     */
    public void perderVida() {
        iVidas--;
        pePelota.setX(getWidth() / 2 - pePelota.getAncho() / 2);
        pePelota.setY(paPaleta.getY() - pePelota.getAlto());
        bActivo = false;
        sonPierde.play();
    }
    
    /**
     * reload
     * Metodo que regresa el juego a su estado inicial cuando el usuario desea
     * volver a empezar
     */
    public void reload() {
        bNivel2 = false;
        bVictoria = false;
        lliBloques.clear();
        iTotalBloques = 64;
        iColumnas = 8;
        iRenglones = 8;
        iBloqY = 64;
        iBloqX = 16;
        for (int iI = 1; iI <= iRenglones; iI++) {
            
            for (int iJ = 1; iJ <= iColumnas; iJ++) {
                URL urlImBloq = this.getClass()
                .getResource("images/block.png");
                
                bloBloque = new Bloque(0, 0,
                Toolkit.getDefaultToolkit().getImage(urlImBloq));
                bloBloque.setY(iBloqY);
                bloBloque.setX(iBloqX);
                iBloqX += bloBloque.getAncho();
                lliBloques.add(bloBloque);
                if (iI % 2 == 0) {
                    if(iI == iJ) {
                        bloBloque.setPowerUp(true);
                    }
                }
                if(iI == 2 && iJ == 7) {
                    bloBloque.setPowerLong (true);
                }
                if(iI == 3 && iJ == 5) {
                    bloBloque.setPowerLong (true);
                }
                if(iI == 6 && iJ == 2) {
                    bloBloque.setPowerLong (true);
                }
                if(iI == 8 && iJ == 1){
                    bloBloque.setPowerLong (true);
                }
                if(iI == 7 && iJ == 4){
                    bloBloque.setPowerLong (true);
                }
            }
            iBloqY += bloBloque.getAlto();
            iBloqX = 16;
        }
        pePelota.setX(getWidth() / 2 - pePelota.getAncho() / 2);
        pePelota.setY(paPaleta.getY() - pePelota.getAlto());
        iVidas = 3;
        iScore = 0;
        bInicio = true;
        bActivo = false;
    }
    
    /**
     * loadNivel2
     * Metodo que regresa carga el nivel 2 cuando el usuario ha eliminado
     * todos los bloques del nivel 1
     */
    public void loadNivel2() {
        bVictoria = false;
        lliBloques.clear();
        iTotalBloques = 72;
        iColumnas = 8;
        iRenglones = 10;
        iBloqY = 64;
        iBloqX = 16;
        for (int iI = 1; iI <= iRenglones; iI++) {
            
            for (int iJ = 1; iJ <= iColumnas; iJ++) {
                URL urlImBloq = this.getClass()
                .getResource("images/block.png");
                
                bloBloque = new Bloque(0, 0,
                Toolkit.getDefaultToolkit().getImage(urlImBloq));
                bloBloque.setY(iBloqY);
                bloBloque.setX(iBloqX);
                iBloqX += bloBloque.getAncho();
                lliBloques.add(bloBloque);
                if (iI % 2 == 0) {
                    if(iI == iJ) {
                        bloBloque.setPowerUp(true);
                    }
                }
                if(iI == 2 && iJ == 7) {
                    bloBloque.setPowerLong (true);
                }
                if(iI == 3 && iJ == 5) {
                    bloBloque.setPowerLong (true);
                }
                if(iI == 6 && iJ == 2) {
                    bloBloque.setPowerLong (true);
                }
                if(iI == 8 && iJ == 1){
                    bloBloque.setPowerLong (true);
                }
                if(iI == 7 && iJ == 4){
                    bloBloque.setPowerLong (true);
                }
            }
            iBloqY += bloBloque.getAlto();
            iBloqX = 16;
        }
        pePelota.setX(getWidth() / 2 - pePelota.getAncho() / 2);
        pePelota.setY(paPaleta.getY() - pePelota.getAlto());
        bInicio = true;
        bActivo = false;
        pePelota.setVel(10);
    }
}
