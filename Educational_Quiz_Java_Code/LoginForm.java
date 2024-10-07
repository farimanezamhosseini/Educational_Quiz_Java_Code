import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class LoginForm extends JFrame implements ActionListener, MouseListener {


    /********* variables ********/
    private JPanel          panel;
    private BufferedImage   loginPicture;
    private JLabel          picLB, usernameLB, passLB, titleLB, register, register2;
    private JTextField      userText;
    private JPasswordField  passwordField;
    private JButton         logButton;


    public LoginForm() {

        try {
            loginPicture = ImageIO.read(this.getClass().getResource("Images/Login.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.setSize(600 , 400);
        this.setLayout(null);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(3);
        this.setTitle("Login");
        this.setIconImage(loginPicture);

        panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(new Color(84, 110, 122));
        panel.setBounds(0 , 0 , this.getWidth() , this.getHeight());

        titleLB = new JLabel("ورود");
        titleLB.setFont(new Font("Arial", Font.BOLD, 26));
        titleLB.setBounds(320, 10, 300, 50);
        titleLB.setForeground(Color.white);
        panel.add(titleLB);

        picLB = new JLabel(new ImageIcon(loginPicture));
        picLB.setBounds(20, 60,200,200);
        panel.add(picLB);

        usernameLB = new JLabel("نام کاربری:");
        usernameLB.setForeground(Color.white);
        usernameLB.setBounds(450, 110, 100, 40);
        usernameLB.setFont(new Font("Arial",Font.BOLD, 16));
        usernameLB.setHorizontalAlignment(JLabel.RIGHT);
        panel.add(usernameLB);

        userText = new JTextField();
        userText.setBounds(260, 115,200,30);
        userText.setBorder(null);
        userText.setBackground(new Color(0, 172, 193));
        userText.setFont(new Font("Arial",Font.CENTER_BASELINE, 14));
        panel.add(userText);

        passLB = new JLabel("رمز عبور:");
        passLB.setForeground(Color.white);
        passLB.setBounds(450, 150, 100,40);
        passLB.setFont(new Font("Arial",Font.BOLD, 16));
        passLB.setHorizontalAlignment(JLabel.RIGHT);
        panel.add(passLB);

        passwordField = new JPasswordField();
        passwordField.setBorder(null);
        passwordField.setBounds(260, 155, 200,30);
        passwordField.setBackground(new Color(0, 172, 193));
        passwordField.setFont(new Font("Arial",Font.CENTER_BASELINE, 14));
        panel.add(passwordField);

        logButton = new JButton("ورود");
        logButton.setBorder(null);
        logButton.setBounds(300, 210, 120,40);
        logButton.setBackground(new Color(105, 240, 174));
        logButton.setFont(new Font("Arial",Font.BOLD, 16));
        logButton.addActionListener(this);
        panel.add(logButton);

        register2 = new JLabel("حساب کاربری ندارید؟");
        register2.setBounds(350, 280, 120, 40);
        register2.setFont(new Font("Arial",Font.BOLD, 16));
        register2.setForeground(Color.white);
        panel.add(register2);

        register = new JLabel("ثبت نام");
        register.setBounds(300, 280, 50, 40);
        register.setFont(new Font("Arial",Font.BOLD, 16));
        register.setForeground(Color.cyan);
        register.addMouseListener(this);
        panel.add(register);

        this.add(panel);
        this.setVisible(true);

    }


    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == logButton){

            String name = userText.getText();
            String pass = String.valueOf(passwordField.getPassword());

            if (name.equals("") || pass.equals("")){
                JOptionPane.showMessageDialog(null, "همه خانه ها را پر کنید.", "empty Fields", JOptionPane.ERROR_MESSAGE);
            }else {
                boolean s = false;
                for (int i = 0; i < DataBase.cUsers; i++){
                    if (name.equals(DataBase.users[i].getUsername())){
                        s = true;
                        if (pass.equals(DataBase.users[i].getPassword())){
                            new UserMainFrame(i);
                            dispose();
                        }else {
                            JOptionPane.showMessageDialog(null, "رمز ورود شما اشتباه است.", "wrong pass", JOptionPane.ERROR_MESSAGE);
                            passwordField.setText("");
                        }
                        break;
                    }
                }
                if (!s) {
                    JOptionPane.showMessageDialog(null, "نام کاربری وجود ندارد.", "invalid username", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

        if (e.getSource() == register){
            new RegisterForm();
            dispose();
        }

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

        if (e.getSource() == register){
            this.setCursor(Cursor.HAND_CURSOR);
        }

    }

    @Override
    public void mouseExited(MouseEvent e) {

        if (e.getSource() == register){
            this.setCursor(Cursor.getDefaultCursor());
        }

    }
}
