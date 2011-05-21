package genetycznyszukacz;

import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
import java.awt.*;
import java.awt.image.ColorModel;
import java.awt.image.IndexColorModel;
import java.awt.Color.*;
import java.awt.Point;

public class Main {
    public static void main(String[] args) {
        String img_in = "/tmp/wat.jpg";
        String img_out = "/tmp/watA.png";
        int ileOsobnikow = 500;
        int ileEwolucji = 15;
        
        Terain teren = new Terain(img_in, 222);
        World swiat = new World(ileOsobnikow, teren);
        swiat.wyewoluuj(ileEwolucji);
               
        // zapis obrazka do pliku
        BufferedImage img = swiat.pokazWyniki();
        File file = new File(img_out);
        file.delete();
        try{
            ImageIO.write(img, "png", file);
        }catch(Exception e){}        
    }
}