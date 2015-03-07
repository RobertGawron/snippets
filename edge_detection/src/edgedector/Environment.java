package edgedector;

import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
import java.awt.*;
import java.util.*;

// it's imposible to inherit from ImageIO
public class Environment {

    public Environment(String inputFileName) throws IOException {
        terrain = ImageIO.read(new File(inputFileName));
        // blur the image
        float[] blurKernel = {
              0f, 1/9f,   0f,
            1/9f, 5/9f, 1/9f,
              0f, 1/9f,   0f
        };

        BufferedImageOp blur = new ConvolveOp(new Kernel(3, 3, blurKernel));
        terrain = blur.filter(terrain, new BufferedImage(terrain.getWidth(),
            terrain.getHeight(),terrain.getType()));
        
        
        // convert image to greyscale
        BufferedImage tmp = new BufferedImage(
                                terrain.getWidth(), 
                                terrain.getHeight(),
                                BufferedImage.TYPE_BYTE_BINARY);
    
        Graphics2D graphics = tmp.createGraphics();
        graphics.drawImage(terrain, 0, 0, null);
        terrain = tmp;

        // init random generator
        oracle = new Random();
        
        // a dummy initial amount of specimens
        maxPopulationSize = width() * height();
        specimens = new HashSet<Specimen>();
        
        for (int i = 0; i < maxPopulationSize; i++) {
            Point birthPlace = new Point(oracle.nextInt(width()), oracle.nextInt(height())); 
            Specimen child = new Specimen(birthPlace);
            if (getFitness(child)){
                specimens.add(child);        
            }
        }
    }

    public HashSet<Specimen> evolve(int generations) {
        for (int i = 0; i < generations; i++) {
            rebuild();
            //System.out.println("iteration: " + i + ", " + specimens.size() + " speciments");
            System.out.println(specimens.size());
        }        
        return specimens;    
    }
        
    public BufferedImage asImage() {
        return terrain;
    }

    public int width(){
        return terrain.getWidth();
    }
    
    public int height(){
        return terrain.getHeight();
    }

    private void rebuild() {
        HashSet<Specimen> newGeneration = new HashSet<Specimen>();
        for(Specimen mother : specimens) {
            Specimen child = mother.bornChild();
            
            if (mother.place() !=  child.place() && getFitness(child))
            {
                newGeneration.add(child);
            }
        }
        
        specimens.addAll(newGeneration);
    }

    private boolean getFitness(Specimen examined) {
        int x = examined.place().x;
        int y = examined.place().y;               
 
        // he can't live out of the terrain anyway 
        if ( !(x >= 0 && x < width() && y >= 0 && y < height()) ) {
            return false;
        }
        
        int neighborhood = 5;
        int goodNeighbours = 0;
        
        for (int i = 0;  i < neighborhood; i++) {
            for (int j = 0;  j < neighborhood; j++) {
                Point p = new Point(x + i, y + j);
                if ((p.x > -1 && p.x < width()) 
                        && (p.y > -1 && p.y < height())
                        && !(i == 0 && j == 0)
                        && terrain.getRGB(x, y) == Color.WHITE.getRGB()
                        && terrain.getRGB(p.x, p.y) == Color.WHITE.getRGB()) {
                    goodNeighbours ++;
                }                
            }        
        }
        
        return (goodNeighbours > 0 && goodNeighbours < 13);
    }    
       
    private BufferedImage terrain;
    private Random oracle;
    private HashSet<Specimen> specimens; 
    private int maxPopulationSize;
}
