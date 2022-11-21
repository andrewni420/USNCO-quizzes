
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import org.apache.pdfbox.contentstream.operator.DrawObject;
import org.apache.pdfbox.contentstream.operator.Operator;
import org.apache.pdfbox.contentstream.operator.state.Concatenate;
import org.apache.pdfbox.contentstream.operator.state.Restore;
import org.apache.pdfbox.contentstream.operator.state.Save;
import org.apache.pdfbox.contentstream.operator.state.SetGraphicsStateParameters;
import org.apache.pdfbox.contentstream.operator.state.SetMatrix;
import org.apache.pdfbox.cos.COSBase;
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.graphics.PDXObject;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.TextPosition;
import org.apache.pdfbox.util.Matrix;

import javax.imageio.ImageIO;

public class SolutionScraper extends PDFTextStripper {
    public static int year = 2020;
    public static boolean isUSNCO = false;
    public static ArrayList<Double>[][] coordinates = new ArrayList[2][];
    public static int currentpage = 0;
    public static final int DPI = 500;
    public static final int Default = 72;
    public static final double charheight = 6.593520164489746;
    public static final double numwidth = 4.979999542236328;
    public static final int width = 355*DPI/Default;
    public static final int PDFwidth = 612;
    public static final int PDFheight = 700;
    public static final int PDFstart = 122;
    private static BufferedImage lastImage=null;
    public static String output = "src/"+year+(isUSNCO ? "N" : "L")+" Solutions";

    /********** Constructor **********/
    public SolutionScraper() throws IOException {
        this.addOperator(new Concatenate());
        this.addOperator(new DrawObject());
        this.addOperator(new SetGraphicsStateParameters());
        this.addOperator(new Save());
        this.addOperator(new Restore());
        this.addOperator(new SetMatrix());
    }
    /*********************************/

    /********** Main Method **********/
    public static void main(String[] args) throws IOException {

        //while (year>2015) {
            /********** Get Locations **********/

            PDDocument document = null;

            String fileName = "/Users/andrewni/IdeaProjects/USNCO test reader/Solutions/" + year + (isUSNCO ? "N" : "L") + ".pdf";

            try {
                document = PDDocument.load(new File(fileName));
                PDFTextStripper stripper = new SolutionScraper();
                stripper.setStartPage(0);
                int pages = document.getNumberOfPages();
                stripper.setEndPage(pages);
                coordinates[0] = new ArrayList[pages];
                coordinates[1] = new ArrayList[pages];
                Writer dummy = new OutputStreamWriter(new ByteArrayOutputStream());
                stripper.writeText(document, dummy);

                /********** Get Images **********/
                PDFRenderer renderer = new PDFRenderer(document);
                BufferedImage image;
                File folder = new File(output);
                folder.mkdir();

                int problemnumber = 1;
                for (int i = 0; i < pages; i++) {
                    image = renderer.renderImageWithDPI(i, DPI);
                    ArrayList<Double> xcoords = coordinates[0][i];
                    ArrayList<Double> ycoords = coordinates[1][i];
                    int X = (int) ((xcoords.get(0) - numwidth) * DPI / Default);

                    if (i != 0) {
                        int height = (int) ((ycoords.get(0) - PDFstart - 1.75 * charheight) * DPI / Default);
                        BufferedImage question = image.getSubimage(X, PDFstart * DPI / Default, width, height);
                        int width1 = question.getWidth();
                        int height1 = question.getHeight() + lastImage.getHeight();
                        BufferedImage finalimage = new BufferedImage(width1, height1, BufferedImage.TYPE_INT_ARGB);
                        addImage(finalimage, lastImage, 0, 0);
                        addImage(finalimage, question, 0, lastImage.getHeight());
                        ImageIO.write(finalimage, "png", new File(output + "/q" + (problemnumber++) + ".png"));
                    }

                    for (int j = 1; j < xcoords.size(); j++) {
                        double Y0 = ycoords.get(j - 1);
                        double Y = ycoords.get(j);
                        int height = (int) ((Y - Y0) * DPI / Default);
                        BufferedImage question = image.getSubimage(X, (int) ((Y0 - 1.75 * charheight) * DPI / Default), width, height);
                        ImageIO.write(question, "png", new File(output + "/q" + (problemnumber++) + ".png"));
                    }

                    double Y0 = ycoords.get(ycoords.size() - 1);
                    int height = (int) ((PDFheight - Y0) * DPI / Default);
                    lastImage = image.getSubimage(X, (int) ((Y0 - 1.75 * charheight) * DPI / Default), width, height);
                }
                ImageIO.write(lastImage, "png", new File(output + "/q" + (problemnumber++) + ".png"));
            } finally {
                if (document != null) {
                    document.close();
                }
            }
            //year--;
            /**end of while loop is below**/
        //}
    }
    /*********************************/

    /********** writeString **********/
    protected void writeString(String string, List<TextPosition> textPositions) throws IOException {

        int page = this.getCurrentPageNo();
        if (page != currentpage) {
            currentpage = page;
            coordinates[0][currentpage-1]=new ArrayList<>();
            coordinates[1][currentpage-1]=new ArrayList<>();
        }

        int n = textPositions.size();

        boolean singleDigit = n==2 &&
                isdigit(textPositions.get(0).getUnicode()) &&
                textPositions.get(1).getUnicode().equals(".");
        boolean doubleDigit = n==3 &&
                isdigit(textPositions.get(0).getUnicode()) &&
                isdigit(textPositions.get(1).getUnicode()) &&
                textPositions.get(2).getUnicode().equals(".");

        if ((singleDigit||doubleDigit) && textPositions.get(0).getFont().getName().equals("MTRDDD+CMBX10")    ){
            double[] xyhw = {textPositions.get(0).getXDirAdj(), textPositions.get(0).getYDirAdj()};
            coordinates[0][currentpage-1].add(xyhw[0]);
            coordinates[1][currentpage-1].add(xyhw[1]);

        }
    }
    /*********************************/

    /********** isDigit **********/
    public boolean isdigit(String string) {
        return string.equals("0") || string.equals("1") || string.equals("2") || string.equals("3") || string.equals("4") || string.equals("5") || string.equals("6") || string.equals("7") || string.equals("8") || string.equals("9");
    }
    /*********************************/

    private static void addImage(BufferedImage buff1, BufferedImage buff2,
                                 int x, int y) {
        Graphics2D g2d = buff1.createGraphics();
        g2d.drawImage(buff2, x, y, null);
        g2d.dispose();
    }

}

