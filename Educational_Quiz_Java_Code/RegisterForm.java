import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class RegisterForm extends JFrame implements ActionListener, MouseListener {

    private JPanel          panel;
    private BufferedImage   loginPicture;
    private JLabel          picLB, usernameLB, passLB, titleLB, passConLB, login, login2;
    private JTextField      usernameTF, passwordTF, passwordConTF;
    private JButton         signButton;


    public RegisterForm(){


        try {
            loginPicture = ImageIO.read(this.getClass().getResource("Images/Login.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.setSize(800 , 600);
        this.setLayout(null);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(3);
        this.setTitle("Sign-Up");
        this.setIconImage(loginPicture);

        panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(new Color(84, 110, 122));
        panel.setBounds(0 , 0 , this.getWidth() , this.getHeight());

        titleLB = new JLabel("ثبت نام");
        titleLB.setFont(new Font("Arial", Font.BOLD, 26));
        titleLB.setBounds(220, 10, 300, 50);
        titleLB.setForeground(Color.white);
        titleLB.setHorizontalAlignment(SwingConstants.RIGHT);
        panel.add(titleLB);

        picLB = new JLabel(new ImageIcon(loginPicture));
        picLB.setBounds(50, 60,200,200);
        panel.add(picLB);

        usernameLB = new JLabel("نام کاربری:");
        usernameLB.setForeground(Color.white);
        usernameLB.setBounds(570, 110, 200, 40);
        usernameLB.setFont(new Font("Arial",Font.BOLD, 18));
        usernameLB.setHorizontalAlignment(JLabel.LEFT);
        panel.add(usernameLB);

        usernameTF = new JTextField();
        usernameTF.setBounds(360, 115,200,30);
        usernameTF.setBorder(null);
        usernameTF.setBackground(new Color(0, 172, 193));
        usernameTF.setFont(new Font("Arial",Font.CENTER_BASELINE, 14));
        panel.add(usernameTF);

        passLB = new JLabel("رمز عبور:");
        passLB.setForeground(Color.white);
        passLB.setBounds(570, 180, 200,40);
        passLB.setFont(new Font("Arial",Font.BOLD, 18));
        passLB.setHorizontalAlignment(JLabel.LEFT);
        panel.add(passLB);

        passwordTF = new JTextField();
        passwordTF.setBorder(null);
        passwordTF.setBounds(360, 180, 200,30);
        passwordTF.setBackground(new Color(0, 172, 193));
        passwordTF.setFont(new Font("Arial",Font.CENTER_BASELINE, 14));
        panel.add(passwordTF);

        passConLB = new JLabel("تکرار رمز عبور:");
        passConLB.setForeground(Color.white);
        passConLB.setBounds(570, 245, 200,40);
        passConLB.setFont(new Font("Arial",Font.BOLD, 18));
        passConLB.setHorizontalAlignment(JLabel.LEFT);
        panel.add(passConLB);

        passwordConTF = new JTextField();
        passwordConTF.setBorder(null);
        passwordConTF.setBounds(360, 245, 200,30);
        passwordConTF.setBackground(new Color(0, 172, 193));
        passwordConTF.setFont(new Font("Arial",Font.CENTER_BASELINE, 14));
        panel.add(passwordConTF);

        signButton = new JButton("ثبت نام");
        signButton.setBorder(null);
        signButton.setBounds(400, 350, 120,40);
        signButton.setBackground(new Color(105, 240, 174));
        signButton.setFont(new Font("Arial",Font.BOLD, 16));
        signButton.addActionListener(this);
        panel.add(signButton);


        login2 = new JLabel("حساب کاربری دارید؟");
        login2.setBounds(420, 430, 120, 40);
        login2.setFont(new Font("Arial",Font.BOLD, 16));
        login2.setForeground(Color.white);
        panel.add(login2);

        login = new JLabel("ورود");
        login.setBounds(380, 430, 50, 40);
        login.setFont(new Font("Arial",Font.BOLD, 16));
        login.setForeground(Color.cyan);
        login.addMouseListener(this);
        panel.add(login);

        this.add(panel);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == signButton){
            String name = usernameTF.getText();
            String pass = passwordTF.getText();
            String coPass = passwordConTF.getText();

            if (name.equals("") || pass.equals("") || coPass.equals("")){
                JOptionPane.showMessageDialog(null, "همه خانه ها را پر کنید.", "empty Fields", JOptionPane.ERROR_MESSAGE);
            }else {
                boolean s = false;
                for (int i = 0; i < DataBase.cUsers; i++){
                    if (name.equals(DataBase.users[i].getUsername())) {
                        JOptionPane.showMessageDialog(null, "نام کاربری موجود است.", "username exist", JOptionPane.ERROR_MESSAGE);
                        usernameTF.setText("");
                        s = true;
                        break;
                    }
                }
                if (!s){
                    if (pass.equals(coPass)) {
                        DataBase.users[DataBase.cUsers] = new User(DataBase.users[DataBase.cUsers - 1].getId() + 1, name, pass);
                        DataBase.cUsers++;
                        DataBase.saveAllUsers();
                        JOptionPane.showMessageDialog(null, "اکانت ایجاد سد.", "account created", JOptionPane.INFORMATION_MESSAGE);
                        new UserMainFrame(DataBase.cUsers - 1);
                        dispose();
                    }else{
                        JOptionPane.showMessageDialog(null, "تکرار رمز عبور درست نیست.", "password not match", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == login){
            new LoginForm();
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
        if (e.getSource() == login) {
            this.setCursor(Cursor.HAND_CURSOR);
        }

    }

    @Override
    public void mouseExited(MouseEvent e) {
        if (e.getSource() == login) {
            this.setCursor(Cursor.getDefaultCursor());
        }
    }
}
