import javax.imageio.ImageIO;
import javax.swing.*;
import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class ResultForm extends JFrame {

    private JPanel          panel;
    private BufferedImage   loginPicture;
    private JLabel          picLB,  titleLB, scoreLB;
    private JScrollPane     scrollPane;
    private JTextArea       textArea;
    private int score;
    private Question question[];


    public ResultForm(int index, int score, Question question[]){

        this.score = score;
        this.question = question;


        try {
            loginPicture = ImageIO.read(this.getClass().getResource("Images/download.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.setSize(800 , 600);
        this.setLayout(null);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setTitle("result form");
        this.setIconImage(loginPicture);
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                new UserMainFrame(index);
            }
        });

        panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(new Color(84, 110, 122));
        panel.setBounds(0 , 0 , this.getWidth() , this.getHeight());

        titleLB = new JLabel("نتیجه آزمون");
        titleLB.setFont(new Font("Arial", Font.BOLD, 26));
        titleLB.setBounds(220, 10, 300, 50);
        titleLB.setForeground(Color.white);
        titleLB.setHorizontalAlignment(SwingConstants.RIGHT);
        panel.add(titleLB);

        picLB = new JLabel(new ImageIcon(loginPicture));
        picLB.setBounds(50, 100,200,200);
        panel.add(picLB);

        scoreLB = new JLabel("امتیاز شما در این آزمون: " + score,  SwingConstants.RIGHT);
        scoreLB.setBounds(500, 100, 200, 50);
        scoreLB.setFont(new Font("Arial", Font.BOLD, 20));
        scoreLB.setForeground(Color.pink);
        panel.add(scoreLB);

        textArea = new JTextArea();
        textArea.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        textArea.setBorder(null);
        textArea.setEditable(false);
        textArea.setFont(new Font("Arial", Font.CENTER_BASELINE, 18));

        for (int i = 0; i < question.length; i++){
            if (question[i] instanceof Four_choiceQuestion) {
                if (((Four_choiceQuestion) question[i]).getSelectedAnswer() == 0){
                    textArea.setText(textArea.getText() +" سوال " + (i+1) +  " پاسخ نداده شده است." + "\n");
                }else if (((Four_choiceQuestion) question[i]).isCorrect(((Four_choiceQuestion) question[i]).getSelectedAnswer())) {
                    textArea.setText(textArea.getText() +"پاسخ سوال " + (i+1) + "درست بود." + "\n");
                }else{
                    textArea.setText(textArea.getText() +"پاسخ سوال " + (i+1) + "نادرست بود." + "\n");
                }
            }if (question[i] instanceof SelectionQuestion) {
                if (!((SelectionQuestion) question[i]).isAnswered()){
                    textArea.setText(textArea.getText() +" سوال " + (i+1) +  " پاسخ نداده شده است." + "\n");
                }else if (((SelectionQuestion) question[i]).isCorrect()) {
                    textArea.setText(textArea.getText() +"پاسخ سوال " + (i+1) + "درست بود." + "\n");
                }else{
                    textArea.setText(textArea.getText() +"پاسخ سوال " + (i+1) + "نادرست بود." + "\n");
                }
            }
        }


        scrollPane = new JScrollPane(textArea);
        scrollPane.setBounds(300, 150, 400, 300);
        panel.add(scrollPane);

        this.add(panel);
        this.setVisible(true);

        SoundStore soundStore = new SoundStore();
        soundStore.LoadSounds();

        if (score >= 12){
            soundStore.Play(Sounds.win);
        }else{
            soundStore.Play(Sounds.lose);
        }
    }


}
