import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class UserMainFrame extends JFrame implements ActionListener{

    private     JPanel          panel;
    private     JLabel          nameLb, averageLB, picLB;
    private     BufferedImage   Picture;
    private     JButton         btn[];
    private     Font            font;
    private     int             index;
    private     User            user;

    public UserMainFrame(int index){

        this.index = index;
        this.user  = DataBase.users[index];

        try {
            Picture = ImageIO.read(this.getClass().getResource("Images/Student_icon.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.setDefaultCloseOperation(3);
        this.setLayout(null);
        this.setResizable(false);
        this.setSize(800, 600);
        this.setTitle("Main Form");
        this.setIconImage(Picture);
        this.setLocationRelativeTo(null);

        font = new Font("Arial",Font.CENTER_BASELINE, 16);

        panel = new JPanel();
        panel.setBounds(0, 0, 800, 600);
        panel.setBackground(new Color(81, 59, 86));
        panel.setLayout(null);


        picLB = new JLabel(new ImageIcon(Picture));
        picLB.setBounds(500, 100,200,200);
        panel.add(picLB);


        nameLb = new JLabel(user.getUsername());
        nameLb.setBounds(500, 300, 200, 40);
        nameLb.setFont(new Font("Arial", Font.BOLD, 48));
        nameLb.setForeground(new Color(188, 231, 132));
        panel.add(nameLb);

        averageLB = new JLabel("میانگین نمرات: " + String.format("%.2f", user.getAverage()));
        averageLB.setBounds(500, 350, 200, 40);
        averageLB.setFont(new Font("Arial", Font.BOLD, 20));
        averageLB.setForeground(new Color(93, 211, 158));
        panel.add(averageLB);

        btn = new JButton[4];

        btn[0] = new JButton("آزمون جدید");
        btn[0].setBounds(100, 150, 130, 40);
        btn[0].setBorder(null);
        btn[0].setBackground(new Color(52, 138, 167));
        btn[0].setForeground(new Color(81, 59, 86));
        btn[0].setFont(font);
        btn[0].addActionListener(this);
        panel.add(btn[0]);

        btn[1] = new JButton("نمودار پیشرفت");
        btn[1].setBounds(100, 200, 130, 40);
        btn[1].setBorder(null);
        btn[1].setBackground(new Color(52, 138, 167));
        btn[1].setForeground(new Color(81, 59, 86));
        btn[1].setFont(font);
        btn[1].addActionListener(this);
        panel.add(btn[1]);

        btn[2] = new JButton("خروج از حساب کاربری");
        btn[2].setBounds(100, 250, 130, 40);
        btn[2].setBorder(null);
        btn[2].setBackground(new Color(52, 138, 167));
        btn[2].setForeground(new Color(81, 59, 86));
        btn[2].setFont(font);
        btn[2].addActionListener(this);
        panel.add(btn[2]);

        btn[3] = new JButton("خروج");
        btn[3].setBounds(100, 300, 130, 40);
        btn[3].setBorder(null);
        btn[3].setBackground(new Color(52, 138, 167));
        btn[3].setForeground(new Color(81, 59, 86));
        btn[3].setFont(font);
        btn[3].addActionListener(this);
        panel.add(btn[3]);

        this.add(panel);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btn[0]){
            new ExamFirstFrame(index);
            dispose();
        }else if (e.getSource() == btn[1]){
            new ProgressForm(index);
        }else if (e.getSource() == btn[2]){
            new LoginForm();
            dispose();
        }else if (e.getSource() == btn[3]){
            System.exit(0);
        }
    }
}
