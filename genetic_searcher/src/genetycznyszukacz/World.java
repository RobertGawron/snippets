package genetycznyszukacz;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
import java.awt.*;
import java.awt.image.ColorModel;
import java.awt.image.IndexColorModel;
import java.awt.Color.*;
import java.awt.Point;

public class World {
    public World(int ileOsobnikow, Terain teren){
        this.teren = teren;
        this.populacja = new Population(ileOsobnikow, teren);
    }

    public void wyewoluuj(int ileIteracji){
        for(int i=0; i<ileIteracji; i++){
            populacja.zabijNajgorsze();
            populacja.odbudujPopulacje();
            populacja.zabijNajgorsze();
        }
    }
    
    public BufferedImage pokazWyniki(){
        Color figury = new Color(0x55, 0xdf, 0xff);
        Color tlo = new Color(0xff, 0xff, 0xff);

        BufferedImage wynik = teren.pokazTeren(figury, tlo);
        // pokazanie osobnikow
        Tracker[] osobniki = populacja.dajOsobniki();
        // wyswietlimy kazdego zyjacego osobnika
        for(int i=0; i<osobniki.length; i++){
            if(osobniki[i]!=null){
                int ia = osobniki[i].dajPozycje().x;
                int ib = osobniki[i].dajPozycje().y;
                if(ia<teren.getWidth() && ia>=0 && ib<teren.getHeight() && ib>=0){
                    wynik.setRGB( osobniki[i].dajPozycje().x,
                        osobniki[i].dajPozycje().y, osobniki[i].dajKolor() );
                }
            }
        }
        return wynik;
    }
    
    // prywatne skladowe
    private Population populacja;
    private Terain teren;
}
