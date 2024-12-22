import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class FlappyBird extends JPanel implements ActionListener, KeyListener {
    int boradWidth = 360;
    int boradHight = 640;

    //images
    Image backgroundiImage;
    Image birdImage;
    Image topPipImage;
    Image bottomPipeImage;

    // bird 
    int birdx = boradWidth/8;
    int birdy = boradHight/2;
    int birdWidth = 34;
    int birdHight = 24;

    class Bird{
        int x = birdx;
        int y = birdy;
        int hight = birdHight;
        int width = birdWidth;
        Image img;

        Bird(Image img){
            this.img = img;
        }
    }
    
    //pipes
    int pipeX = boradWidth;
    int pipeY = 0;
    
    int pipeWidth = 64;
    int pipeHight = 512;

    class Pipe{
        int x = pipeX;
        int y = pipeY;
        int width = pipeWidth;
        int hight = pipeHight;
        Image img;
        boolean passed = false;

        Pipe(Image img){
            this.img = img;
        }
    }
    //game logic
    Bird bird;
    int velocityX = -4;
    int velocityY = 0; 
    int gravity = 1;
    
    ArrayList<Pipe> pipes;

    Timer gameLoop;
    Timer placePipTimer;


    
    FlappyBird(){
        setPreferredSize(new Dimension(boradWidth, boradHight)); 
        // setBackground(Color.blue);
        setFocusable(true);
        addKeyListener(this);


    //load images
    backgroundiImage = new ImageIcon(getClass().getResource("./flappybirdbg.png")).getImage();
    birdImage = new ImageIcon(getClass().getResource("./flappybird.png")).getImage();
    topPipImage = new ImageIcon(getClass().getResource("./toppipe.png")).getImage();
    bottomPipeImage = new ImageIcon(getClass().getResource("./bottompipe.png")).getImage();

    bird = new Bird(birdImage);

    //game timer
    gameLoop = new Timer(1000/60, this);
    gameLoop.start();
    }
    public void placePipes(){
        Pipe toPipe = new Pipe(topPipImage);
        pipes.add(topPipe);

    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g){
        g.drawImage(backgroundiImage, 0, 0, boradWidth, boradHight, null);

        //bird
        g.drawImage(bird.img, bird.x, bird.y, bird.width, bird.hight, null);
    }

    public void move(){
        //bird 
        velocityY += gravity;
        bird.y += velocityY;
        bird.y = Math.max(bird.y, 0);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        repaint();
    }


    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode()== KeyEvent.VK_SPACE);
        velocityY = -9;
        
    }

    @Override
    public void keyTyped(KeyEvent e) {
        
    }
    @Override
    public void keyReleased(KeyEvent e) {
    }


}

