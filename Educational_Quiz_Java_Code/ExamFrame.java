import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class ExamFrame extends JFrame implements ActionListener {

    private JPanel      sidePanel, mainPanel, footerPanel;
    private JButton     qBTN[], nextBTN, previousBTN, endBTN;
    private JTextArea   questionTA;
    private ButtonGroup buttonGroup;
    private JRadioButton    r1, r2, r3, r4;
    private User user;
    private int numberOfQuestions;
    private int map[];
    private int answers[];
    private Question question[];
    private BufferedImage Picture;
    private int index;
    private int timeSeconds, timeMins;
    private JLabel timeLB;
    private Timer timer;
    private int currentQuestionIndex;
    private int score;



    public ExamFrame(int index, int numberOfQuestions)  {
        this.index = index;
        this.numberOfQuestions = numberOfQuestions;
        this.user = DataBase.users[index];
        this.question = DataBase.randomQuestion(this.numberOfQuestions);
        this.map = new int[numberOfQuestions];
        this.answers = new int[numberOfQuestions];
        this.timeMins = 2 * question.length;
        this.timeSeconds = 0;
        this.currentQuestionIndex = 0;
        this.score = 0;


        try {
            Picture = ImageIO.read(this.getClass().getResource("Images/download.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLayout(null);
        this.setResizable(false);
        this.setSize(900, 600);
        this.setTitle("Exam Form");
        this.setIconImage(Picture);
        this.setLocationRelativeTo(null);
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                DataBase.users[index].addNewAttempt(score);
                DataBase.saveAllUsers();
                new ResultForm(index, score, question);
            }
        });


        timer = new Timer(1000, this);

        sidePanel = new JPanel();
        sidePanel.setBounds(600, 0, 300, 600);
        sidePanel.setLayout(null);
        sidePanel.setBackground(new Color(23,86,93));

        timeLB = new JLabel(timeMins + " : " + timeSeconds);
        timeLB.setFont(new Font("Arial", Font.BOLD, 28));
        timeLB.setBounds(100, 40, 100, 40);
        timeLB.setForeground(new Color(188,204,217));
        sidePanel.add(timeLB);


        qBTN = new JButton[numberOfQuestions];
        int x = 60, y = 100;

        for (int i = 0; i < question.length; i++){
            qBTN[i] = new JButton(String.valueOf(i+1));
            qBTN[i].setBounds(x, y, 40, 40);
            qBTN[i].setBorder(null);
            qBTN[i].setBackground(new Color(173,134,245));
            final int k = i;
            qBTN[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    saveAnswer();
                    currentQuestionIndex = k;
                    loadQuestion(k);
                }
            });
            sidePanel.add(qBTN[i]);
            x += 45;
            if ((i+1)%4 == 0){
                y += 45;
                x = 60;
            }
        }

        endBTN = new JButton("اتمام آزمون");
        endBTN.setBounds(100, 450, 100, 40);
        endBTN.setBorder(null);
        endBTN.addActionListener(this);
        endBTN.setBackground(new Color(173,134,245));
        sidePanel.add(endBTN);

        footerPanel = new JPanel();
        footerPanel.setBounds(0, 450, 600, 150);
        footerPanel.setLayout(null);
        footerPanel.setBackground(new Color(115,140,155));

        nextBTN = new JButton("سوال بعدی");
        nextBTN.setBounds(50, 40, 100, 40);
        nextBTN.setBorder(null);
        nextBTN.addActionListener(this);
        nextBTN.setBackground(new Color(118,62,236));
        footerPanel.add(nextBTN);

        previousBTN = new JButton("سوال قبلی");
        previousBTN.setBounds(440, 40, 100 , 40);
        previousBTN.setBorder(null);
        previousBTN.addActionListener(this);
        previousBTN.setBackground(new Color(118,62,236));
        footerPanel.add(previousBTN);


        mainPanel = new JPanel();
        mainPanel.setBounds(0, 0, 600, 450);
        mainPanel.setLayout(null);
        mainPanel.setBackground(new Color(115,140,155));

        loadQuestion(currentQuestionIndex);

        this.add(sidePanel);
        this.add(mainPanel);
        this.add(footerPanel);
        timer.start();
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == timer){
            timeSeconds--;
            if (timeSeconds < 0){
                timeMins--;
                timeSeconds = 59;
            }

            timeLB.setText(timeMins + " : " + timeSeconds);

            if (timeSeconds < 0 && timeMins <= 0){
                DataBase.users[this.index].addNewAttempt(score);
                DataBase.saveAllUsers();
                new ResultForm(index, score, question);
                dispose();
            }
        }else if (e.getSource() == nextBTN){
            saveAnswer();
            if (currentQuestionIndex < question.length - 1) {
                currentQuestionIndex++;
                loadQuestion(currentQuestionIndex);
            }
        }else if (e.getSource() == previousBTN){
            saveAnswer();
            if (currentQuestionIndex > 0) {
                currentQuestionIndex--;
                loadQuestion(currentQuestionIndex);
            }
        }else if (e.getSource() == endBTN){

            saveAnswer();
            int temp = JOptionPane.showConfirmDialog(null, "آیا مطمعن هستید؟", "close confirmation", JOptionPane.YES_NO_OPTION);
            if (temp != 1){
                DataBase.users[this.index].addNewAttempt(score);
                DataBase.saveAllUsers();
                new ResultForm(index, score, question);
                dispose();
            }
        }else if (e.getSource() == r1){
            answers[currentQuestionIndex] = 1;
        }else if (e.getSource() == r2){
            answers[currentQuestionIndex] = 2;
        }else if (e.getSource() == r3){
            answers[currentQuestionIndex] = 3;
        }else if (e.getSource() == r4){
            answers[currentQuestionIndex] = 4;
        }
    }

    public void saveAnswer(){

        if (question[currentQuestionIndex] instanceof Four_choiceQuestion){


            if (map[currentQuestionIndex] == 0) {
                if (r1.isSelected()) {
                    ((Four_choiceQuestion) question[currentQuestionIndex]).setSelectedAnswer(1);
                } else if (r2.isSelected()) {
                    ((Four_choiceQuestion) question[currentQuestionIndex]).setSelectedAnswer(2);
                } else if (r3.isSelected()) {
                    ((Four_choiceQuestion) question[currentQuestionIndex]).setSelectedAnswer(3);
                } else if (r4.isSelected()) {
                    ((Four_choiceQuestion) question[currentQuestionIndex]).setSelectedAnswer(4);
                }
            }

            if (r1.isSelected() && ((Four_choiceQuestion) question[currentQuestionIndex]).isCorrect(1) && map[currentQuestionIndex] == 0){
                score += 20 / question.length;
                ((Four_choiceQuestion) question[currentQuestionIndex]).setSelectedAnswer(1);
                map[currentQuestionIndex] = 1;
            }else if (r2.isSelected() && ((Four_choiceQuestion) question[currentQuestionIndex]).isCorrect(2) && map[currentQuestionIndex] == 0){
                score += 20 / question.length;
                map[currentQuestionIndex] = 1;
                ((Four_choiceQuestion) question[currentQuestionIndex]).setSelectedAnswer(2);
            }else if (r3.isSelected() && ((Four_choiceQuestion) question[currentQuestionIndex]).isCorrect(3) && map[currentQuestionIndex] == 0){
                score += 20 / question.length;
                map[currentQuestionIndex] = 1;
                ((Four_choiceQuestion) question[currentQuestionIndex]).setSelectedAnswer(3);
            }else if (r4.isSelected() && ((Four_choiceQuestion) question[currentQuestionIndex]).isCorrect(4) && map[currentQuestionIndex] == 0){
                score += 20 / question.length;
                map[currentQuestionIndex] = 1;
                ((Four_choiceQuestion) question[currentQuestionIndex]).setSelectedAnswer(4);
            }else if (r1.isSelected() && !((Four_choiceQuestion) question[currentQuestionIndex]).isCorrect(1) && map[currentQuestionIndex] == 1){
                score -= 20 / question.length;
                map[currentQuestionIndex] = 0;
                ((Four_choiceQuestion) question[currentQuestionIndex]).setSelectedAnswer(1);
            }else if (r2.isSelected() && !((Four_choiceQuestion) question[currentQuestionIndex]).isCorrect(2) && map[currentQuestionIndex] == 1){
                score -= 20 / question.length;
                map[currentQuestionIndex] = 0;
                ((Four_choiceQuestion) question[currentQuestionIndex]).setSelectedAnswer(2);
            }else if (r3.isSelected() && !((Four_choiceQuestion) question[currentQuestionIndex]).isCorrect(3) && map[currentQuestionIndex] == 1){
                score -= 20 / question.length;
                map[currentQuestionIndex] = 0;
                ((Four_choiceQuestion) question[currentQuestionIndex]).setSelectedAnswer(3);
            }else if (r4.isSelected() && !((Four_choiceQuestion) question[currentQuestionIndex]).isCorrect(4) && map[currentQuestionIndex] == 1){
                score -= 20 / question.length;
                map[currentQuestionIndex] = 0;
                ((Four_choiceQuestion) question[currentQuestionIndex]).setSelectedAnswer(4);
            }

        }else if (question[currentQuestionIndex] instanceof SelectionQuestion){

            int[] sa = new int[4];

            if (r1.isSelected()){
                sa[0] = 1;
            }
            if (r2.isSelected()){
                sa[1] = 1;
            }
            if (r3.isSelected()){
                sa[2] = 1;
            }
            if (r4.isSelected()){
                sa[3] = 1;
            }

            ((SelectionQuestion) question[currentQuestionIndex]).setSelectedAnswers(sa);

            if (((SelectionQuestion) question[currentQuestionIndex]).isCorrect() && map[currentQuestionIndex] == 0){
                score += 20 / question.length;
                map[currentQuestionIndex] = 1;
            }else if (!((SelectionQuestion) question[currentQuestionIndex]).isCorrect() && map[currentQuestionIndex] == 1){
                score -= 20 / question.length;
                map[currentQuestionIndex] = 0;
            }
        }

    }

    public void loadQuestion(int ind){

        mainPanel.removeAll();


        questionTA = new JTextArea();
        questionTA.setBounds(20, 60, 560, 200);
        questionTA.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        questionTA.setBorder(null);
        questionTA.setEditable(false);
        questionTA.setFont(new Font("Arial", Font.CENTER_BASELINE, 14));
        questionTA.setText(question[ind].getContent());


        JScrollPane pane = new JScrollPane(questionTA);
        pane.setBounds(20, 60, 560, 200);


        mainPanel.add(pane);

        JLabel ti = new JLabel();
        ti.setBounds(50, 10, 150, 40);
        ti.setFont(new Font("Arial", Font.CENTER_BASELINE, 18));
        ti.setForeground(new Color(44,36,60));
        mainPanel.add(ti);

        JLabel ti2 = new JLabel("سوال " + (ind + 1));
        ti2.setBounds(450, 10, 150, 40);
        ti2.setFont(new Font("Arial", Font.CENTER_BASELINE, 18));
        ti2.setForeground(new Color(44,36,60));
        mainPanel.add(ti2);

        r1 = new JRadioButton();
        r1.setBounds(550, 280, 20, 20);

        JLabel l1 = new JLabel(question[ind].getAnswers()[0] ,  SwingConstants.RIGHT);
        l1.setBounds(20, 280, 510, 20);
        mainPanel.add(l1);

        r2 = new JRadioButton();
        r2.setBounds(550, 320, 20, 20);

        JLabel l2 = new JLabel(question[ind].getAnswers()[1],  SwingConstants.RIGHT);
        l2.setBounds(20, 320, 510, 20);
        mainPanel.add(l2);


        r3 = new JRadioButton();
        r3.setBounds(550, 360, 20, 20);

        JLabel l3 = new JLabel(question[ind].getAnswers()[2],  SwingConstants.RIGHT);
        l3.setBounds(20, 360, 510, 20);
        mainPanel.add(l3);

        r4 = new JRadioButton();
        r4.setBounds(550, 400, 20, 20);

        JLabel l4 = new JLabel(question[ind].getAnswers()[3],  SwingConstants.RIGHT);
        l4.setBounds(20, 400, 510, 20);
        mainPanel.add(l4);



        if (question[ind] instanceof Four_choiceQuestion){

            ti.setText("سوال چهار گزینه ایی");

            if (((Four_choiceQuestion) question[ind]).getSelectedAnswer() == 1){
                r1.setSelected(true);
            }else  if (((Four_choiceQuestion) question[ind]).getSelectedAnswer() == 2){
                r2.setSelected(true);
            }else  if (((Four_choiceQuestion) question[ind]).getSelectedAnswer() == 3){
                r3.setSelected(true);
            }else  if (((Four_choiceQuestion) question[ind]).getSelectedAnswer() == 4){
                r4.setSelected(true);
            }


            buttonGroup = new ButtonGroup();

            buttonGroup.add(r1);
            buttonGroup.add(r2);
            buttonGroup.add(r3);
            buttonGroup.add(r4);

        }else if (question[ind] instanceof SelectionQuestion){
            ti.setText("سوال انتخابی");

            if (((SelectionQuestion) question[ind]).getSelectedAnswers() != null) {
                if (((SelectionQuestion) question[ind]).getSelectedAnswers()[0] == 1) {
                    r1.setSelected(true);
                }
                if (((SelectionQuestion) question[ind]).getSelectedAnswers()[1] == 1) {
                    r2.setSelected(true);
                }
                if (((SelectionQuestion) question[ind]).getSelectedAnswers()[2] == 1) {
                    r3.setSelected(true);
                }
                if (((SelectionQuestion) question[ind]).getSelectedAnswers()[3] == 1) {
                    r4.setSelected(true);
                }
            }

        }

        mainPanel.add(r1);
        mainPanel.add(r2);
        mainPanel.add(r3);
        mainPanel.add(r4);

        mainPanel.repaint();
    }

}
