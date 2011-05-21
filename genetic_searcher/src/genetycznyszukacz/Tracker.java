package genetycznyszukacz;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
import java.awt.*;
import java.awt.image.ColorModel;
import java.awt.image.IndexColorModel;
import java.awt.Color.*;
import java.awt.Point;
import java.util.Random;

public class Tracker {
    // interfejs
    public Tracker(Point pozycja) {
        this.pozycja = pozycja;
        kolor = new Color(0x00, 0x00, 0x00);
    }
    
    public void mutuj(){
        // zalozmy, ze mutacja jest mala, max 20%
        int maxMutacja = 20; // w procentach
        Random gen = new Random();
        
        pozycja.x = pozycja.x + gen.nextInt(2*maxMutacja)-maxMutacja;
        pozycja.y = pozycja.y + gen.nextInt(2*maxMutacja)-maxMutacja;
        
    }
    
    public Tracker krzyzuj(Tracker t){
        Random gen = new Random();
        Point lokacja = new Point();
        if(gen.nextDouble()>=0.5){
            lokacja.x = this.pozycja.x + t.dajPozycje().x%10;
            lokacja.y = this.pozycja.y + t.dajPozycje().y%10;
        }
        else{
            lokacja.x = t.dajPozycje().x + this.pozycja.x%10;
            lokacja.y = t.dajPozycje().y + this.pozycja.y%10;    
        }
        return new Tracker(lokacja);
    }
    
    // interfejs: setery, getery
    public int dajKolor(){
        return this.kolor.getRGB();
    }
    
    public Point dajPozycje(){
        return this.pozycja;
    }
    
    public int dajDopasowanie(Terain teren){        
         // sprawdzimy kwadrat 5x5, w srodku jest nasz osobnik, chcemy 
         // wiedziec czy jego otoczenie zawiera krawedzie, zalozmy ze jezeli
         // sa krawedzie to jest troche bialych i troche czarnych pixeli w okolo
         int dopasowanie = 0;            
         int promien = 2;
         int x = pozycja.x;
         int y = pozycja.y;
             
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
    private Point pozycja;
    private Color kolor;
}
