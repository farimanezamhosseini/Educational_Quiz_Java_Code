public class SelectionQuestion extends Question{

    private int totalAnswers;
    private int[] answerIndexes;
    private int[] selectedAnswers;

    public SelectionQuestion(String content, String[] answers, int totalAnswers, int[] answerIndexes) {
        super(content, answers);
        this.totalAnswers = totalAnswers;
        this.answerIndexes = answerIndexes;
    }

    public int getTotalAnswers() {
        return totalAnswers;
    }

    public void setTotalAnswers(int totalAnswers) {
        this.totalAnswers = totalAnswers;
    }

    public int[] getAnswerIndexes() {
        return answerIndexes;
    }

    public void setAnswerIndexes(int[] answerIndexes) {
        this.answerIndexes = answerIndexes;
    }

    public int[] getSelectedAnswers() {
        return selectedAnswers;
    }

    public void setSelectedAnswers(int[] selectedAnswers) {
        this.selectedAnswers = selectedAnswers;
    }

    public boolean isCorrect(){



        for (int i = 0; i < totalAnswers; i++){
            if (answerIndexes[i] == 1 && selectedAnswers[0] != 1){
                return false;
            }else if (answerIndexes[i] == 2 && selectedAnswers[1] != 1){
                return false;
            }else if (answerIndexes[i] == 3 && selectedAnswers[2] != 1){
                return false;
            }else if (answerIndexes[i] == 4 && selectedAnswers[3] != 1){
                return false;
            }
        }

        return true;
    }

    public boolean isAnswered(){

        if (selectedAnswers == null){
            return false;
        }

        for (int i = 0 ; i < 4; i++){
            if (selectedAnswers[i] == 1){
                return true;
            }
        }
        return false;
    }

}
