import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class ProgressForm extends JFrame {

    private int index;
    private User user;
    private BufferedImage Picture;

    public ProgressForm(int index){

        this.index = index;
        user = DataBase.users[index];

        try {
            Picture = ImageIO.read(this.getClass().getResource("Images/Student_icon.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLayout(null);
        this.setResizable(false);
        this.setSize(900, 600);
        this.setTitle("Progress Form");
        this.setIconImage(Picture);
        this.setLocationRelativeTo(null);


        this.add(new ProgressPanel(index, 0,0,900, 600));
        this.setVisible(true);
    }
}


class ProgressPanel extends JPanel{

    private int index;
    private User user;
    int width, height;
    private Graphics2D Graphics2D;

    public ProgressPanel(int index, int a, int b, int x, int y){
        this.index = index;
        width = x;
        height = y;
        user = DataBase.users[index];

        this.setLayout(null);
        this.setBackground(new Color(74,78,105));
        this.setBounds(a,b,x,y);

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(3));

        g2.setColor(new Color(224,251,252));

        g2.drawLine(50, 20, 50, 530);
        g2.drawLine(50, 530, 850, 530);


        g2.drawString("20", 15, 70 );
        g2.drawString("15", 15, 185);
        g2.drawString("10", 15, 300);
        g2.drawString("5", 15, 415);


        g2.setColor(new Color(173,181,189));
        g2.setStroke(new BasicStroke(6));

        g2.drawLine(53, 70, 850, 70);
        g2.drawLine(53, 185, 850, 185);
        g2.drawLine(53, 300, 850, 300);
        g2.drawLine(53, 415, 850, 415);


        g2.setStroke(new BasicStroke(7));
        g2.setColor(new Color(22,138,173));

        int delay = 460 / 20;

        int wid = getWidth() - 100;
        int delay2 = wid/ (user.getcAttempts() + 1);
        int x = 50 + delay2;

        for (int i = 0; i < user.getcAttempts()-1; i++){
            g2.drawLine(x,(20 - user.getAttempts()[i]) * delay + 70, x + delay2, (20 - user.getAttempts()[i+1]) * delay + 70);
            x += delay2;
            System.out.println(user.getAttempts()[i]*delay);
            System.out.println(user.getAttempts()[i+1]*delay);
        }
    }
}
