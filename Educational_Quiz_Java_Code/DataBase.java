import java.io.*;
import java.util.Random;

public class DataBase {

    static String path = "C:\\Users\\myPC\\IdeaProjects\\question_System\\src\\Files";

    static User users[] = new User[100000];
    static int cUsers = 0;

    static Four_choiceQuestion four_choiceQuestions[] = new Four_choiceQuestion[100000];
    static int cFour_choiceQuestions = 0;

    static SelectionQuestion selectionQuestions[] = new SelectionQuestion[100000];
    static int cSelectionQuestions = 0;


    public static void readQuestions(){

        try {
            File file = new File(path + "\\Four_choiceQuestion.txt");

            BufferedReader br = new BufferedReader(new FileReader(file));

            String st;
            while ((st = br.readLine()) != null) {

                String[] temp = st.split("#");

                String[] t = {temp[1], temp[2], temp[3], temp[4]};

                four_choiceQuestions[cFour_choiceQuestions] = new Four_choiceQuestion(temp[0],t, Integer.parseInt(temp[5]));
                cFour_choiceQuestions++;
            }
        }catch (Exception ex) {
            System.out.println("Four_choiceQuestion file not found");
        }

        try {

            File file = new File(path + "\\SelectionQuestion.txt");

            BufferedReader br = new BufferedReader(new FileReader(file));

            String st;
            while ((st = br.readLine()) != null) {

                String[] temp = st.split("#");

                String[] t = {temp[1], temp[2], temp[3], temp[4]};

                int cAnswers = Integer.parseInt(temp[5]);
                int answers[] = new int[cAnswers];

                for (int i = 6; i < cAnswers + 6; i++){
                    answers[i -6] = Integer.parseInt(temp[i]);
                }
                selectionQuestions[cSelectionQuestions] = new SelectionQuestion(temp[0], t, cAnswers, answers);
                cSelectionQuestions++;
            }
        }catch (Exception ex) {
            System.out.println("SelectionQuestion file not found");
        }



    }




    public static void getUsersInfo()  {

        try {
            File file = new File(path + "\\users.txt");

            BufferedReader br = new BufferedReader(new FileReader(file));

            String st;
            while ((st = br.readLine()) != null) {

                String[] temp = st.split(" ");

                int cc = Integer.parseInt(temp[3]);
                int[] a = new int[10000];

                for (int i = 4; i < cc + 4; i ++){
                    a[i-4] = Integer.parseInt(temp[i]);
                }
                users[cUsers] = new User(Integer.parseInt(temp[0]), temp[1], temp[2],a, cc );
                cUsers++;
            }
        }catch (Exception ex) {
            System.out.println("users file not found");
        }
    }

    public static void saveAllUsers(){

        try {
            FileWriter fw = new FileWriter(path + "\\users.txt");
            PrintWriter pw = new PrintWriter(fw);

            for (int i = 0; i < cUsers; i++) {
                pw.write(users[i].fileOutput()+"\n");
            }
            pw.close();
        }catch (Exception ex){
            System.out.println("users file not found");
        }
    }

    public static boolean hasIt(Question q[], Question q2){
        for (int i = 0; i < q.length; i++){
            if (q[i] == q2){
                return true;
            }
        }

        return false;
    }

    public static Question[] randomQuestion(int numbers){

        if (numbers > cSelectionQuestions + cFour_choiceQuestions){
            numbers = cFour_choiceQuestions + cSelectionQuestions;
        }

        Question q[] = new Question[numbers];
        Random r = new Random();
        int i = 0;
        while (i < numbers/2){
            int index = r.nextInt(cFour_choiceQuestions);
            if (!hasIt(q, four_choiceQuestions[index])) {
                q[i] = four_choiceQuestions[index];
                i++;
            }
        }
        while (i < numbers){
            int index = r.nextInt(cSelectionQuestions);
            if (!hasIt(q, selectionQuestions[index])) {
                q[i] = selectionQuestions[index];
                i++;
            }
        }
        return q;

    }




}



