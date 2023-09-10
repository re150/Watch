import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Calendar;

public class Treads extends JPanel  implements Runnable {
    private BufferedImage buffer;
    private Calendar calendario;
    private ImageIcon imagenFondo;
    ImageIcon imagenFondo2;
    private ImageIcon caratulaReloj;
    ImageIcon caratulaReloj2;
    public  Boolean estado = true;
    private ImageIcon manecillaHoras;
    private ImageIcon manecillaMinutos;
    private ImageIcon manecillaSegundos;
    ImageIcon misilmg ;
    Boolean Misil = true;
    boolean fondo1Activo = true;
    int animacionX = 0;
    Timer animacionTimer;
    Clip Musicplay;
    public Treads() {
        calendario = Calendar.getInstance();

        buffer = new BufferedImage(716, 739, BufferedImage.TYPE_INT_ARGB);

        imagenFondo = new ImageIcon("C:\\Users\\angel\\Documents\\6M\\Imagen_2D_3D\\P1\\Proyecto_Primer_Parcial\\imagenes\\fondo2.png");
        imagenFondo2 = new ImageIcon("C:\\Users\\angel\\Documents\\6M\\Imagen_2D_3D\\P1\\Proyecto_Primer_Parcial\\imagenes\\fondo4.jpg");

        manecillaHoras = new ImageIcon("C:\\Users\\angel\\Documents\\6M\\Imagen_2D_3D\\P1\\Proyecto_Primer_Parcial\\imagenes\\Horas.png");
        manecillaMinutos = new ImageIcon("C:\\Users\\angel\\Documents\\6M\\Imagen_2D_3D\\P1\\Proyecto_Primer_Parcial\\imagenes\\minutero2.png");
        manecillaSegundos = new ImageIcon("C:\\Users\\angel\\Documents\\6M\\Imagen_2D_3D\\P1\\Proyecto_Primer_Parcial\\imagenes\\segundos.png");

        caratulaReloj = new ImageIcon("C:\\Users\\angel\\Documents\\6M\\Imagen_2D_3D\\P1\\Proyecto_Primer_Parcial\\imagenes\\watch4.png");
        caratulaReloj2 = new ImageIcon("C:\\Users\\angel\\Documents\\6M\\Imagen_2D_3D\\P1\\Proyecto_Primer_Parcial\\imagenes\\watch2.png");

        misilmg = new ImageIcon( "C:\\Users\\angel\\Documents\\6M\\Imagen_2D_3D\\P1\\Proyecto_Primer_Parcial\\imagenes\\disparo-removebg-preview.png");
        playAudio("C:\\Users\\angel\\Documents\\6M\\Imagen_2D_3D\\P1\\Proyecto_Primer_Parcial\\imagenes\\Ekko vs Jinx - Dynasties & Dystopia ｜ Arcane MV.wav", true);

        Thread thread = new Thread(this);
        thread.start();

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                fondo1Activo = !fondo1Activo;
                estado =!estado;
                if(estado){
                    Musicplay.stop();
                    playAudio("C:\\Users\\angel\\Documents\\6M\\Imagen_2D_3D\\P1\\Proyecto_Primer_Parcial\\imagenes\\Ekko vs Jinx - Dynasties & Dystopia ｜ Arcane MV.wav", true);

                }else {
                    Musicplay.stop();

                    playAudio("C:\\Users\\angel\\Documents\\6M\\Imagen_2D_3D\\P1\\Proyecto_Primer_Parcial\\imagenes\\Awaken ｜ Season 2019 Cinematic - League of Legends (ft. Valerie Broussard).wav", false);
                }

                    repaint();
            }
        });
    }



    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = buffer.createGraphics();

        // Limpia
        g2d.setColor(getBackground());
        g2d.fillRect(0, 0, getWidth(), getHeight());

        if(fondo1Activo){
            g2d.drawImage(imagenFondo.getImage(), 0, 0, getWidth(), getHeight() , null);
            // Dibuja la carátula del reloj
            g2d.drawImage(caratulaReloj.getImage(), 0, 0, getWidth() , getHeight(), null);
        }else {
            g2d.drawImage(imagenFondo2.getImage(),0,0,getWidth(),getHeight(),null);
            g2d.drawImage(caratulaReloj2.getImage(),0,0,getWidth(),getHeight(), null);
        }

        // Obtiene la hora actual
        calendario.setTimeInMillis(System.currentTimeMillis());
        int segundos = calendario.get(Calendar.SECOND);
        int minutos = calendario.get(Calendar.MINUTE);
        int horas = calendario.get(Calendar.HOUR_OF_DAY) -1 ;
        System.out.println("horas = " + horas);

        // posiciom para las manecillas
        double anguloSegundos = Math.toRadians(6 * segundos);
        double anguloMinutos = Math.toRadians(6 * minutos );
        double anguloHoras = Math.toRadians(30 * horas  );

        System.out.println("anguloHoras = " + anguloHoras);
        System.out.println("anguloMinutos = " + anguloMinutos);
        System.out.println(anguloSegundos);

        // Dibuja las manecillas centradas en la carátula del reloj
        dibujarManecilla(g2d, manecillaSegundos.getImage(), getWidth() / 2, getHeight() / 2, anguloSegundos);
        dibujarManecilla(g2d, manecillaMinutos.getImage(), getWidth() / 2, getHeight() / 2, anguloMinutos);
        dibujarManecilla(g2d, manecillaHoras.getImage(), getWidth() / 2, getHeight() / 2, anguloHoras);

     //   g2d.drawImage(misilmg.getImage(), getWidth() - 100, 0, 100, 100, null);

        // Dibuja el buffer en la ventana
        g.drawImage(buffer, 0, 0, this);
    }

    private  void playAudio(String audioFilePath, boolean estado) {
        try {

            if(estado){
                AudioInputStream audioStream = AudioSystem.getAudioInputStream(new File(audioFilePath));
                Clip clip = AudioSystem.getClip();
                clip.open(audioStream);
                Musicplay = clip;
                Musicplay.loop(Musicplay.LOOP_CONTINUOUSLY);
                Musicplay.start();
            }else {
                Musicplay.stop();
                AudioInputStream audioStream = AudioSystem.getAudioInputStream(new File(audioFilePath));
                Clip clip = AudioSystem.getClip();
                clip.open(audioStream);
                Musicplay = clip;
                Musicplay.loop(Musicplay.LOOP_CONTINUOUSLY);
                Musicplay.start();
            }




        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void dibujarManecilla(Graphics2D g, Image imagen, int x, int y, double angulo) {
        int ancho = imagen.getWidth(null);
        int alto = imagen.getHeight(null);

        // Calcula las coordenadas para centrar la manecilla en la carátula del reloj
        int centroX = getWidth() / 2 - ancho / 2;
        int centroY = getHeight() / 2 - alto / 2;


        AffineTransform at = AffineTransform.getTranslateInstance(centroX, centroY);
        at.rotate(angulo, (double) ancho / 2, (double) alto / 2);
        g.drawImage(imagen, at, null);
    }

    @Override
    public void run() {
        while (true) {
            repaint();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
