package edgedector;

import java.awt.Point;
import java.util.Random;

public class Specimen {

    public Specimen(Point birthPlace) {
        place = birthPlace;
        oracle = new Random();
    }

    @Override 
    public int hashCode() {
        return place.x << 4 & place.y;
    }
    
    @Override 
    public boolean equals(Object buddy) {
        if (buddy == null) {
            return false;
        }
        if (buddy.getClass() != this.getClass()) {
            return false;
        }
        
        Point hisPlace = ((Specimen)buddy).place;        
        return (hisPlace.x == place.x) && (hisPlace.y == place.y);
    }
       
    public Point place() {
        return place;
    }

    public Specimen bornChild() {
        Point birthPlace = new Point();
        int range = 4;
        birthPlace.x = place.x + oracle.nextInt(2 * range + 1) - range;
        birthPlace.y = place.y + oracle.nextInt(2 * range + 1) - range;
        
        if (oracle.nextDouble() < mutationChance) {
            int mutationRange = 50;
            birthPlace.x = birthPlace.x - oracle.nextInt(2 * mutationRange + 1) - mutationRange;
            birthPlace.y = birthPlace.y - oracle.nextInt(2 * mutationRange + 1) - mutationRange;        
        }
        return new Specimen(birthPlace);
    }
   
    private Point place;
    static double mutationChance = 0.5;    
    Random oracle;
}
