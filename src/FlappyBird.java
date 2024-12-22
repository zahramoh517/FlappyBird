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
    Random random = new Random();

    Timer gameLoop;
    Timer placePipesTimer;
    double score = 0;


    boolean gameOver = false;
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

    pipes = new ArrayList<Pipe>();
    
    //place pipes timer
    placePipesTimer = new Timer(1500, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e){
            placePipes();
        }
        
        
    });
    placePipesTimer.start();


    //game timer
    gameLoop = new Timer(1000/60, this);
    gameLoop.start();
    }
    public void placePipes(){

        int randomPipeY = (int)(pipeY - pipeHight/4 - Math.random()*(pipeHight/2));
        int openingSpace = boradHight/4;
        Pipe topPipe = new Pipe(topPipImage);
        topPipe.y = randomPipeY;
        pipes.add(topPipe);

        Pipe bottomPipe = new Pipe(bottomPipeImage);
        bottomPipe.y = topPipe.y + pipeHight + openingSpace;
        pipes.add(bottomPipe);

    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);  
    }

    public void draw(Graphics g){
        g.drawImage(backgroundiImage, 0, 0, boradWidth, boradHight, null);

        //bird
        g.drawImage(bird.img, bird.x, bird.y, bird.width, bird.hight, null);

        //pipes
        for (int i = 0; i < pipes.size(); i++){
            Pipe pipe = pipes.get(i);
            g.drawImage(pipe.img, pipe.x, pipe.y, pipe.width, pipe.hight, null);
            

        }

                //score
                g.setColor(Color.white);

                g.setFont(new Font("Arial", Font.PLAIN, 32));
                if (gameOver) {
                    g.drawString("Game Over: " + String.valueOf((int) score), 10, 35);
                }
                else {
                    g.drawString(String.valueOf((int) score), 10, 35);
                } 
    }

    public void move(){
        //bird 
        velocityY += gravity;
        bird.y += velocityY;
        bird.y = Math.max(bird.y, 0);

        //pipes
        for(int i = 0; i < pipes.size(); i++){
            Pipe pipe = pipes.get(i);
            pipe.x += velocityX;

            if (!pipe.passed && bird.x > pipe.x + pipe.width) {
                score += 0.5; 
                pipe.passed = true;
            }

            if (collision(bird, pipe)){
                gameOver = true;
            }
        }

        if (bird.y > boradHight){
            gameOver = true;
        }

    }

    public boolean collision(Bird a, Pipe b){
        return a.x < b.x + b.width &&
               a.x + a.width > b.x &&
               a.y < b.y + b.hight &&
               a.y + a.hight > b.y;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        repaint();
        if (gameOver){
            placePipesTimer.stop();
            gameLoop.stop();
        }
    }


    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode()== KeyEvent.VK_SPACE);
        velocityY = -9;
      if(gameOver){
            bird.y = birdy;
            velocityY = 0;
            pipes.clear();
            gameOver = false;
            score = 0;
            gameLoop.start();
            placePipesTimer.start();
        
        }
        
    }

    @Override
    public void keyTyped(KeyEvent e) {
        
    }
    @Override
    public void keyReleased(KeyEvent e) {
    }


}

