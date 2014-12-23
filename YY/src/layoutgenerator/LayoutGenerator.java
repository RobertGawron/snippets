package layoutgenerator;

import java.io.*;
import java.awt.image.*;
import javax.imageio.*;
import java.awt.*;


class ParsedLayout {
	public ParsedLayout(String filename){
		this.filename = filename;
		usedPixelsAmount = 0;
	}
	public void parse() throws IOException  {
		BufferedImage inputImage = ImageIO.read(new File(
				filename));

		PrintWriter writer = new PrintWriter(
				"C:\\Users\\rgawron\\uFormicarium\\layouts\\intro.h",
				"UTF-8");
		writer.println("int introData[][2] = {");

		for (int x = 0; x < inputImage.getWidth(); x++) {
			for (int y = 0; y < inputImage.getHeight(); y++) {
				if (inputImage.getRGB(x, y) == Color.BLACK.getRGB()) {
					writer.println("\t{" + x + ", " + y + "},");
					usedPixelsAmount++;
				}

			}
		}
		writer.println("};");
		writer.close();		
	}
	
	private Integer usedPixelsAmount;
	private String filename;
}

public class LayoutGenerator {

	public static void main(String[] args) {
		ParsedLayout layout = new ParsedLayout("C:\\Users\\rgawron\\intro.bmp");
		try {
			layout.parse();
			System.out.println("Parsing done.");
		} catch (Exception e) {
			System.err.println("Unable to open .bmp file");
		}
	}
}
