package layoutgenerator;

import java.io.*;
import java.awt.image.*;
import javax.imageio.*;
import java.awt.*;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;




class ParsedLayout {
	public ParsedLayout(String inputFileNaname, String outputFileNaname) {
		this.inputFileNaname = inputFileNaname;
		this.outputFileNaname = outputFileNaname;
		usedPixelsAmount = 0;
	}

	public void parse() throws IOException {
		System.out.println(inputFileNaname);
		BufferedImage inputImage = ImageIO.read(new File(inputFileNaname));

		PrintWriter writer = new PrintWriter(outputFileNaname, "UTF-8");
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
	private String inputFileNaname;
	private String outputFileNaname;
}

public class LayoutGenerator {

	public static void main(String[] args) {
		try {
			File configlFile = new File(args[0]);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(configlFile);
			NodeList nList = doc.getElementsByTagName("config");

			for (int temp = 0; temp < nList.getLength(); temp++) {
				Node nNode = nList.item(temp);
				Element eElement = (Element) nNode;
				System.out.println("Input dir  : "
						+ eElement.getElementsByTagName("inputdirectory")
								.item(0).getTextContent());
				System.out.println("Output dir : "
						+ eElement.getElementsByTagName("outputdirectory")
								.item(0).getTextContent());

				String inputDirectory = eElement
						.getElementsByTagName("inputdirectory").item(0)
						.getTextContent();
				File file = new File(inputDirectory);
				// if (file.exists()) {
				File[] inputFiles = file.listFiles(new FilenameFilter() {

					@Override
					public boolean accept(File dir, String name) {
						return name.toLowerCase().endsWith(".png");
					}
				});

				for (File alp : inputFiles) {
					System.out.println(alp.getName());
					String[] inputFileNameSplited = alp.getName().split("\\.");
					String headerFileName = inputFileNameSplited[0];
					System.out.println(headerFileName);
					
					ParsedLayout layout = new ParsedLayout(
							inputDirectory +alp.getName(), inputDirectory + headerFileName + ".h");
					try {
						layout.parse();
						System.out.println("Parsing done.");
					} catch (Exception e) {
						System.err.println("Unable to open .png file");
						e.printStackTrace();
					}
				}

			}
		} catch (Exception e) {
			System.err.println("Unable to open configuration file");
			e.printStackTrace();
		}
	}
}
