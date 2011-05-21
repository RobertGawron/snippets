package genetycznyszukacz;
import java.awt.Point;
import java.util.Random;
import java.util.*;

public class Population {
    public Population(int ileOsobnikow, Terain teren) {
        this.teren = teren;
        osobniki = new ArrayList();
        for(int i=0; i<ileOsobnikow; i++){
            Random gen = new Random();
            Point wsp = new Point( gen.nextInt(teren.getWidth()), 
                                    gen.nextInt(teren.getHeight()) );
            osobniki.add( new Tracker(wsp) );
        }
        gen = new Random();
    }
    
    public void zabijNajgorsze(){
        for(int os=0; os<osobniki.size(); os++){
            if( ((Tracker)osobniki.get(os)).dajDopasowanie(teren) == 0 ){
                osobniki.remove(os);
            }
        } 
    }
    
    public void odbudujPopulacje(){
        for(int os=0; os< osobniki.size()/2; os++){
            osobniki.add( this.dajZdrowego().krzyzuj( this.dajZdrowego() ) );
        }
        // teraz, skoro mamy okreslone osobniki to usmiercmy te najslabsze,
        // mutujmy i krzyzujmy te silne, znow uproszczenie na max'a
        for(int os=0; os<osobniki.size(); os++){
             double prMutacji = 0.1;
             if( gen.nextDouble() <= prMutacji ){
                 ((Tracker)osobniki.get(os)).mutuj();
             }
        }
    }
    
    private Tracker dajZdrowego(){
        int id = gen.nextInt( osobniki.size() );
        return (Tracker)osobniki.get(id);
    }
    
    public Tracker[] dajOsobniki(){
        zabijNajgorsze();
        Object ob[] = osobniki.toArray();// to jest do niczego :/
        Tracker[] t = new Tracker[ob.length];
        
        for(int i=0; i<ob.length; i++)
            t[i] = (Tracker)ob[i];
        
        return t;
    }
    
    private Random gen;
    private Terain teren;
    private ArrayList osobniki;
}
