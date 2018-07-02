public class NetzModel {

    private int[] inputColumns = new int[] {0,1};
    private int maxEpoch = 350;
    private double learnrate = 0.9;

    public int[] getInputColumns() {
        return this.inputColumns;
    }

    public int getMaxEpoch() {
        return this.maxEpoch;
    }

    public double getLearnrate() {
        return this.learnrate;
    }

    public void setInputColumns(int[] inputColumns) {
        this.inputColumns = inputColumns;
    }

    public void setMaxEpoch(int maxEpoch){
        this.maxEpoch = maxEpoch;
    }

    public void setLearnrate(double learnrate) {
        this.learnrate = learnrate;
    }
}
