package genetycznyszukacz;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
import java.awt.*;
import java.awt.image.ColorModel;
import java.awt.image.IndexColorModel;
import java.awt.Color.*;
import java.awt.Point;

public class Terain {
    // interfejs
    public Terain(String img_in, int poziomSzarosci){
        try {
            teren = ImageIO.read(new File(img_in));
        } catch (IOException e) {/* Java ssie z wyjatkami */}
        
        this.poziomSzarosci = poziomSzarosci;
        this.makeItGrey();
        this.makeItBlackOrWhite();
    }
    
    public BufferedImage pokazTeren(Color figury, Color tlo){
        // zmienmy kolory na obrazku
        for(int i=0; i<teren.getWidth(); i++){
            for(int j=0; j<teren.getHeight(); j++){
                Color c = new Color( teren.getRGB(i,j) );
                if(getGrey(c)==0)
                    teren.setRGB(i,j, figury.getRGB());
                else
                    teren.setRGB(i,j, tlo.getRGB());
            }
        }

        return teren;
    }
    
    // 1-bialy, 0 czarny
    public int getSytuacja(Point p){
        Color c = new Color( this.teren.getRGB(p.x, p.y) );
        return 1 - this.getGrey(c)/255;
    }
    
    // interfejs: getery i setery
    public int getWidth(){
        return this.teren.getWidth();
    }
    
    public int getHeight(){
        return this.teren.getHeight();
    }
    
    // pola i metody prywatne
    private int getGrey(Color c){
        return ( c.getGreen()+c.getRed()+c.getBlue() )/3;
    }
    
    private void makeItBlackOrWhite(){
        for(int i=0; i<teren.getWidth(); i++){
            for(int j=0; j<teren.getHeight(); j++){
               int val = teren.getRGB(i,j);
               Color c = new Color(val);
               int grey = getGrey(c);
               if(grey < poziomSzarosci)
                    teren.setRGB(i,j, 0x000000);
                else
                    teren.setRGB(i,j, 0xffffff);
            }
        }
    }
    
    private void makeItGrey(){
        for(int i=0; i<teren.getWidth(); i++){
            for(int j=0; j<teren.getHeight(); j++){
                int val = teren.getRGB(i,j);
                Color c = new Color(val);
                int grey = (c.getGreen()+c.getRed()+c.getBlue())/3;
                //int grey = i+j;
                // bialy to 0xffffff, czarny to 0x0
                // grey ma zakres 0..255, skalujemy:
                double tmp = (double)grey%(double)255;
                grey = (int)((double)0xffffff * tmp);
                teren.setRGB(i,j, grey);
                
            }
        }
    }
        
    private int poziomSzarosci;
    private BufferedImage teren;
}
