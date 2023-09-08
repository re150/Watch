import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.sound.sampled.*;

public class Pruevas  extends JPanel implements Runnable {
    private BufferedImage buffer;
    private Calendar calendario;
    private ImageIcon imagenFondo;
    private ImageIcon caratulaReloj;
    private ImageIcon manecillaHoras;
    private ImageIcon manecillaMinutos;
    private ImageIcon manecillaSegundos;

    public Pruevas() {
        // Inicializa el calendario
        calendario = Calendar.getInstance();

        // Crea el búfer de imagen
        buffer = new BufferedImage(716, 739, BufferedImage.TYPE_INT_ARGB);


        imagenFondo = new ImageIcon("C:\\Users\\angel\\Documents\\6M\\Imagen_2D_3D\\P1\\Proyecto_Primer_Parcial\\imagenes\\fondo1.jpg");
        manecillaHoras = new ImageIcon("C:\\Users\\angel\\Documents\\6M\\Imagen_2D_3D\\P1\\Proyecto_Primer_Parcial\\imagenes\\Horas.png");
        manecillaMinutos = new ImageIcon("C:\\Users\\angel\\Documents\\6M\\Imagen_2D_3D\\P1\\Proyecto_Primer_Parcial\\imagenes\\minutero2.png");
        manecillaSegundos = new ImageIcon("C:\\Users\\angel\\Documents\\6M\\Imagen_2D_3D\\P1\\Proyecto_Primer_Parcial\\imagenes\\segundos.png");
        caratulaReloj = new ImageIcon("C:\\Users\\angel\\Documents\\6M\\Imagen_2D_3D\\P1\\Proyecto_Primer_Parcial\\imagenes\\watch2.png");

        // Inicia el hilo para actualizar el reloj
        Thread thread = new Thread(this);
        thread.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = buffer.createGraphics();

        // Limpia el buffer
        g2d.setColor(getBackground());
        g2d.fillRect(0, 0, getWidth(), getHeight());


        g2d.drawImage(imagenFondo.getImage(), 0, 0, getWidth(), getHeight() , null);

        // Dibuja la carátula del reloj
        g2d.drawImage(caratulaReloj.getImage(), 0, 0, getWidth() , getHeight(), null);

        // Obtiene la hora actual
        calendario.setTimeInMillis(System.currentTimeMillis());
        int segundos = calendario.get(Calendar.SECOND);
        int minutos = calendario.get(Calendar.MINUTE);
        int horas = calendario.get(Calendar.HOUR_OF_DAY) -1 ;
        System.out.println("horas = " + horas);
        // Calcula los ángulos para las manecillas
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

        // Dibuja el buffer en la ventana
        g.drawImage(buffer, 0, 0, this);
    }

    private static void playAudio(String audioFilePath) {
        try {
            // Open the audio file
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(new File(audioFilePath));

            // Get a clip to play the audio
            Clip clip = AudioSystem.getClip();

            // Open the audio clip with the audio stream
            clip.open(audioStream);

            // Loop the audio indefinitely
            clip.loop(Clip.LOOP_CONTINUOUSLY);

            // Start playing the audio
            clip.start();
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            playAudio("C:\\Users\\angel\\Documents\\6M\\Imagen_2D_3D\\P1\\Proyecto_Primer_Parcial\\imagenes\\Ekko vs Jinx - Dynasties & Dystopia ｜ Arcane MV.wav");

            JFrame frame = new JFrame("Reloj Analógico");
            Pruevas reloj = new Pruevas();
            frame.add(reloj);
            frame.setSize(716, 739);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
