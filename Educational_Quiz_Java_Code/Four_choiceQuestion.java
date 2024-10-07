public class Four_choiceQuestion extends Question{

    private int correctIndex;
    private int selectedAnswer;

    public Four_choiceQuestion(String content, String[] answers, int correctIndex) {
        super(content, answers);
        this.correctIndex = correctIndex;
    }

    public int getCorrectIndex() {
        return correctIndex;
    }

    public void setCorrectIndex(int correctIndex) {
        this.correctIndex = correctIndex;
    }

    public int getSelectedAnswer() {
        return selectedAnswer;
    }

    public void setSelectedAnswer(int selectedAnswer) {
        this.selectedAnswer = selectedAnswer;
    }

    public boolean isCorrect(int i){
        if (i == correctIndex){
            return true;
        }
        return false;
    }

}
