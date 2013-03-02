package edgedetection;
import javax.imageio.*;
import java.awt.image.*;
import java.io.*;
import java.awt.Color.*;
import java.util.ArrayList;

import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
import java.awt.*;
import java.awt.Color.*;

public class Main {
    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            System.err.println("usage: java program_name input_image");
            System.exit(0);
        }

        String inputFileName = args[0];

        Environment environment = new Environment(inputFileName);
        Population population = new Population(environment, 500);
        ArrayList<Specimen> specimens = population.evolve(4);

        // save to output image
        BufferedImage output =new BufferedImage(
                                    environment.width(), 
                                    environment.height(),
                                    BufferedImage.TYPE_INT_RGB);                
        for (Specimen s : specimens) {
            Point place = s.place();
            output.setRGB(place.x, place.y, Color.WHITE.getRGB());
        }
        ImageIO.write(output, "PNG", new File("output.png"));
    }
}