package xxxx;

import java.io.*;
import java.awt.image.*;
import javax.imageio.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
import java.awt.*;
import java.util.*;

public class xxxx {
	public static void main(String[] args) {
		try {
			BufferedImage inputImage = ImageIO.read(new File(
					"C:\\Users\\rgawron\\workspace\\YY\\bin\\xxxx\\intro.bmp"));

			int f = 0;
			for (int i = 0; i < inputImage.getWidth(); i++) {
				for (int j = 0; j < inputImage.getHeight(); j++) {
					if (inputImage.getRGB(i, j) == Color.BLACK.getRGB()) {
						System.out.println("\t{" + i + ", " + j + "},");
						f++;
					}

				}
			}
			System.out.println(f);

			System.out.println("Hello world!");
		} catch (Exception e) {

			System.out.println("error");
		}
	}
}
