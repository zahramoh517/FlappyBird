import javax.swing.*;
public class App {
    public static void main(String[] args) throws Exception {
        int boradWidth = 360;
        int boradHight = 640;

        JFrame frame = new JFrame("Flappy Bird");
        //frame.setVisible(true);
        frame.setSize(boradWidth, boradHight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        FlappyBird flappyBird = new FlappyBird();
        frame.add(flappyBird);
        frame.pack();
        frame.setVisible(true);

    } 
}
