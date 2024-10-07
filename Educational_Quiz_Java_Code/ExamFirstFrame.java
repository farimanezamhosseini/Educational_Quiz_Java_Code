import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class ExamFirstFrame extends JFrame implements ActionListener {


    /********* variables ********/
    private JPanel          panel;
    private BufferedImage   picture;
    private JLabel          picLB, titleLB;
    private JTextArea      guidTF;
    private JButton         startBTN;
    private int             index;


    public ExamFirstFrame(int index) {

        this.index = index;

        try {
            picture = ImageIO.read(this.getClass().getResource("Images/download.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.setSize(600 , 400);
        this.setLayout(null);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setTitle("Login");
        this.setIconImage(picture);

        panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(new Color(84, 110, 122));
        panel.setBounds(0 , 0 , this.getWidth() , this.getHeight());

        titleLB = new JLabel("آزمون");
        titleLB.setFont(new Font("Arial", Font.BOLD, 26));
        titleLB.setBounds(320, 10, 300, 50);
        titleLB.setForeground(Color.white);
        panel.add(titleLB);

        picLB = new JLabel(new ImageIcon(picture));
        picLB.setBounds(20, 60,200,200);
        panel.add(picLB);

        startBTN = new JButton("شروع آزمون");
        startBTN.setBorder(null);
        startBTN.setBounds(300, 300, 120,40);
        startBTN.setBackground(new Color(105, 240, 174));
        startBTN.setFont(new Font("Arial",Font.BOLD, 16));
        startBTN.addActionListener(this);
        panel.add(startBTN);

        guidTF = new JTextArea();
        guidTF.setBounds(250, 120, 320, 100);
        guidTF.setBorder(null);
        guidTF.setFont(new Font("Arial", Font.BOLD, 16));
        guidTF.setBackground(new Color(84, 110, 122));
        guidTF.setForeground(Color.pink);
        guidTF.setEditable(false);
        guidTF.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        guidTF.setText("آزمون دارای 10 سوال میباشد. و هر سوال دو نمره دارد.\n آزمون داری دو نوع سوال تستی و انتخابی است که در سوال های\n انتخابی میتوان بیشتر از یک گزینه را انتخاب کرد.\n آزمون زماندار است و در پایان زمان بسته خواهد شد.");

        panel.add(guidTF);


        this.add(panel);
        this.setVisible(true);
    }


    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == startBTN){
            new ExamFrame(index, 10);
            dispose();
        }

    }


}
