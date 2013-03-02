package edgedetection;

import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
import java.awt.*;
import java.awt.image.ColorModel;
import java.awt.image.IndexColorModel;
import java.awt.Color.*;
import java.awt.Point;
import java.util.Random;

public class Specimen {

    public Specimen(Point birthPlace) {
        place = birthPlace;
    }
    public Point place() {
        return place;
    }
/*    
    public void mutuj(){
        // zalozmy, ze mutacja jest mala, max 20%
        int maxMutacja = 20; // w procentach
        Random gen = new Random();
        
        place.x = place.x + gen.nextInt(2*maxMutacja)-maxMutacja;
        place.y = place.y + gen.nextInt(2*maxMutacja)-maxMutacja;
        
    }
    
    public Specimen krzyzuj(Specimen t){
        Random gen = new Random();
        Point lokacja = new Point();
        if(gen.nextDouble()>=0.5){
            lokacja.x = this.place.x + t.dajPozycje().x%10;
            lokacja.y = this.place.y + t.dajPozycje().y%10;
        }
        else{
            lokacja.x = t.dajPozycje().x + this.place.x%10;
            lokacja.y = t.dajPozycje().y + this.place.y%10;    
        }
        return new Specimen(lokacja);
    }
    
    // interfejs: setery, getery
    public int dajKolor(){
        return this.kolor.getRGB();
    }
    
    public Point dajPozycje(){
        return this.place;
    }
    
    public int dajDopasowanie(Terain teren){        
         // sprawdzimy kwadrat 5x5, w srodku jest nasz osobnik, chcemy 
         // wiedziec czy jego otoczenie zawiera krawedzie, zalozmy ze jezeli
         // sa krawedzie to jest troche bialych i troche czarnych pixeli w okolo
         int dopasowanie = 0;            
         int promien = 2;
         int x = place.x;
         int y = place.y;
             
         for(int i=(x-promien); i<(x+promien); i++){
            for(int j=(y-promien); j<(y+promien); j++){                
                if(i<teren.getWidth() && i>=0 && j<teren.getHeight() && j>=0){
                    dopasowanie+=teren.getSytuacja(new Point(i,j));
                }                    
            }
         }

         if(dopasowanie>0 && dopasowanie<Math.pow(promien, 2))
            return 1;
         
         return 0;
    }
    
    // pola prywatne
*/
     private Point place;
//    private Color kolor;

}
