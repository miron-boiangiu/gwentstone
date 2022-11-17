package game;

public class OutputNode {
    private String command;
    private String output;
    private int playerIdx = -1;

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder("{");
        if(command != null){
            s.append("command:");
            s.append(command);
        }
        if(playerIdx != -1){
            s.append(",");
            s.append("playerIdx:");
            s.append(playerIdx);
        }
        if(output != null){
            s.append(",");
            s.append("output:");
            s.append(output);
        }
        s.append("}");
        return s.toString();
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    public int getPlayerIdx() {
        return playerIdx;
    }

    public void setPlayerIdx(int playerIdx) {
        this.playerIdx = playerIdx;
    }
}
