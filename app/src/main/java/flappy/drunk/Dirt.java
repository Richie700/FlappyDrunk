package flappy.drunk;

import java.util.Random;

public class Dirt {
    private int x;
    private int y;
    private int speed = 1;

    private int minY;
    private int maxY;
    private int minX;
    private int maxX;

    //Constructor
    public Dirt (int screenX, int screenY) {
        maxX = screenX;
        maxY = screenY;
        minX = 0;
        minY = 0;
        Random randomGenerator = new Random();
        speed = randomGenerator.nextInt(10);

        //Generating a random coordinate but keeping the coordinate inside the screen size
        x = randomGenerator.nextInt(maxX);
        y = randomGenerator.nextInt(maxY);
    }

    public void update() {
        //Animating the dirt vertically up to down
        y += speed;

        //if the dirt reached the down edge of the screen. Again starting the dirt from up edge. This will give a infinite scrolling background effect
        if (y > maxY) {
            y = minY;
            Random randomGenerator = new Random();
            x = randomGenerator.nextInt(maxX);
            speed = randomGenerator.nextInt(15);
        }
    }

    public float getDirthWidth() {
        //Making dirt random
        float minY = 1.0f;
        float maxY = 4.0f;
        Random random = new Random();
        float finalY = random.nextFloat() * (maxY - minY) + minY;
        return finalY;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
