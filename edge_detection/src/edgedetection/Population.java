package edgedetection;
import java.awt.Point;
import java.util.Random;
import java.util.*;

public class Population {
    public Population(Environment enviroment, int specimensAmount) {
        oracle = new Random();

        specimens = new ArrayList<Specimen>();
        for(int i = 0; i < specimensAmount; i++){
            Point birthPlace = new Point( oracle.nextInt(enviroment.width()), 
                                            oracle.nextInt(enviroment.height()) );
            specimens.add( new Specimen(birthPlace) );
        }
    }
    
    public ArrayList<Specimen> evolve(int generations) {
        System.out.print(specimens.size());
        return specimens;
    
    }
/*    
    public void zabijNajgorsze(){
        for(int os=0; os<osobniki.size(); os++){
            if( ((Tracker)specimens.get(os)).dajDopasowanie(teren) == 0 ){
                specimens.remove(os);
            }
        } 
    }
    
    public void odbudujPopulacje(){
        for(int os=0; os< specimens.size()/2; os++){
            specimens.add( this.dajZdrowego().krzyzuj( this.dajZdrowego() ) );
        }
        // teraz, skoro mamy okreslone specimens to usmiercmy te najslabsze,
        // mutujmy i krzyzujmy te silne, znow uproszczenie na max'a
        for(int os=0; os<osobniki.size(); os++){
             double prMutacji = 0.1;
             if( oracle.nextDouble() <= prMutacji ){
                 ((Tracker)specimens.get(os)).mutuj();
             }
        }
    }
    
    private Tracker dajZdrowego(){
        int id = oracle.nextInt( specimens.size() );
        return (Tracker)specimens.get(id);
    }
    
    public Tracker[] dajOsobniki(){
        zabijNajgorsze();
        Object ob[] = specimens.toArray();// to jest do niczego :/
        Tracker[] t = new Tracker[ob.length];
        
        for(int i=0; i<ob.length; i++)
            t[i] = (Tracker)ob[i];
        
        return t;
    }
*/    
    private Random oracle;
    private ArrayList<Specimen> specimens;
}
