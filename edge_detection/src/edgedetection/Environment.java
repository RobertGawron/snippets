package edgedetection;

import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
import java.awt.*;
import java.awt.Color.*;
import java.util.ArrayList;
import java.awt.Point;
//import edgedetection.poupulation;

// it's imposible to inherit from ImageIO
public class Environment {
    public Environment(String inputFileName) throws IOException {
        terrain = ImageIO.read(new File(inputFileName));
        // convert image to greyscale
        BufferedImage tmp = new BufferedImage(
                                terrain.getWidth(), 
                                terrain.getHeight(),
                                BufferedImage.TYPE_BYTE_BINARY);
    
        Graphics2D graphics = tmp.createGraphics();
        graphics.drawImage(terrain, 0, 0, null);
        terrain = tmp;  
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
    
    
/*    public void addPopulation(Population population) {
        this.populations.add(population);
    }*/
    /*    
    public BufferedImage pokazTeren(Color figury, Color tlo){
        // zmienmy kolory na obrazku
        for(int i=0; i<terrain.getWidth(); i++){
            for(int j=0; j<terrain.getHeight(); j++){
                Color c = new Color( terrain.getRGB(i,j) );
                if(getGrey(c)==0)
                    terrain.setRGB(i,j, figury.getRGB());
                else
                    terrain.setRGB(i,j, tlo.getRGB());
            }
        }

        return terrain;
    }
    
    // 1-bialy, 0 czarny
    public int getSytuacja(Point p){
        Color c = new Color( this.terrain.getRGB(p.x, p.y) );
        return 1 - this.getGrey(c)/255;
    }
    
    
    // pola i metody prywatne
    private int getGrey(Color c){
        return ( c.getGreen()+c.getRed()+c.getBlue() )/3;
    }
    
    private void makeItBlackOrWhite(){
        for(int i=0; i<terrain.getWidth(); i++){
            for(int j=0; j<terrain.getHeight(); j++){
               int val = terrain.getRGB(i,j);
               Color c = new Color(val);
               int grey = getGrey(c);
               if(grey < blackWhiteEdge)
                    terrain.setRGB(i,j, 0x000000);
                else
                    terrain.setRGB(i,j, 0xffffff);
            }
        }
    }
    
    private void makeItGrey(){
        for(int i=0; i<terrain.getWidth(); i++){
            for(int j=0; j<terrain.getHeight(); j++){
                int val = terrain.getRGB(i,j);
                Color c = new Color(val);
                int grey = (c.getGreen()+c.getRed()+c.getBlue())/3;
                //int grey = i+j;
                // bialy to 0xffffff, czarny to 0x0
                // grey ma zakres 0..255, skalujemy:
                double tmp = (double)grey%(double)255;
                grey = (int)((double)0xffffff * tmp);
                terrain.setRGB(i,j, grey);
                
            }
        }
    }
*/        
    private BufferedImage terrain;
//    private ArrayList<Population> populations;
}
