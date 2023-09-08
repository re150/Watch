import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class Watch  extends JFrame{
    private BufferedImage imagenDeFondo;
    private ImageIcon imagenSuperpuesta;
    public Watch(String url){

        try {
            imagenDeFondo = ImageIO.read(new File(url));
        } catch (Exception e) {
            e.printStackTrace();
        }

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setSize(850, 650);
        setLocationRelativeTo(null);

        JPanel panelConFondo = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(imagenDeFondo, 0, 0, this.getWidth(), this.getHeight(), this);
            }
        };

        // Carga la imagen que deseas superponer
        imagenSuperpuesta = new ImageIcon("C:\\Users\\angel\\Documents\\6M\\Imagen_2D_3D\\P1\\Proyecto_Primer_Parcial\\imagenes\\watch.png");
        JLabel labelSuperpuesto = new JLabel(imagenSuperpuesta);

        // panel principal
        panelConFondo.setLayout(new BorderLayout());

        panelConFondo.add(labelSuperpuesto, BorderLayout.CENTER);
        //Fondo a la ventana
        add(panelConFondo);

        setVisible(true);
    }

    public static void main (String[] args){
        String url = "C:\\Users\\angel\\Documents\\6M\\Imagen_2D_3D\\P1\\Proyecto_Primer_Parcial\\imagenes\\fondo1.jpg";
        SwingUtilities.invokeLater(() -> {
            new Watch(url);
            new Graphis_Watch();
        });
    }



}
