
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

public class Locations extends PDFTextStripper {
    public static int year = 2011;
    public static boolean isUSNCO = false;
    public static ArrayList<Float>[] imagearray = new ArrayList[4];
    public static BufferedImage pageimage = null;
    //public static PDFRenderer renderer = null;
    public static boolean wasd = false;
    public static double lastdx = 0;
    public static double lastdy = 0;
    public static StringBuffer print = new StringBuffer(year + " ");
    public static int answercounter = 0;
    public static int currentpage = 0;
    //public static int answerpage=-1;
    public static int prevn=-1;

    /********** Constructor **********/
    public Locations() throws IOException {
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
        while(year>2009) {
            print=new StringBuffer(year+" ");
            imagearray = new ArrayList[4];
            pageimage = null;
            wasd = false;
            lastdx = 0;
            lastdy = 0;
            print = new StringBuffer(year + " ");
            answercounter = 0;
            currentpage = 0;
            //answerpage=-1;


            for (int i = 0; i < 4; ++i) {
                imagearray[i] = new ArrayList<Float>();
            }

            PDDocument document = null;
            String fileName = "/Users/andrewni/IdeaProjects/USNCO test reader";
            String output="src/USNCO_Locations/";
            if (isUSNCO) {
                print.append("N\n");
                fileName += "/Nationals/" + year + " usnco.pdf";
                output+=year+"N.txt";
            } else {
                print.append("L\n");
                fileName += "/Locals/" + year + " local.pdf";
                output+=year+"L.txt";
            }

            PrintStream tester = new PrintStream(new FileOutputStream(output));

            try {
                document = PDDocument.load(new File(fileName));
                //renderer = new PDFRenderer(document);
                PDFTextStripper stripper = new Locations();
                stripper.setStartPage(0);
                stripper.setEndPage(document.getNumberOfPages());
                Writer dummy = new OutputStreamWriter(new ByteArrayOutputStream());
                stripper.writeText(document, dummy);
            } finally {
                if (document != null) {
                    document.close();
                }

            }

            tester.print(print);
            tester.close();
            if (isUSNCO) {
                year--;
                isUSNCO=false;
            } else isUSNCO=true;
            //System.out.println("---------------------------------------------------------");
        }
    }
    /*********************************/

    /********** writeString **********/
    protected void writeString(String string, List<TextPosition> textPositions) throws IOException {

        int page = this.getCurrentPageNo();
        if (page != currentpage) {
            if (wasd) {
                wasd = false;
                print.append(" ").append(lastdx).append(" ").append(lastdy).append("\n");
            }
            currentpage = page;
            for (int j = 0; j < 4; ++j) {
                imagearray[j].clear();
            }
        }

        StringBuffer positions = new StringBuffer();
        int n = textPositions.size();

        for (int i = 0; i < n; i++) {
            String[] texts = new String[4];

            for (int j = 0; j < 4; ++j) {
                if (i >= j) {
                    texts[j] = textPositions.get(i - j).getUnicode();
                }
            }

            boolean abcd = i > 1 && (texts[1].equals("A") || texts[1].equals("B") || texts[1].equals("C") || texts[1].equals("D"));
            boolean parentheses = i > 1 && texts[2].equals("(") && texts[0].equals(")");
            boolean ddigit = i > 1 && this.isdigit(texts[2]);
            boolean bolded = i > 0 && textPositions.get(i - 1).getFont().getName().contains("Bold");
            boolean numnumperspace = i > 0 && bolded && this.isdigit(texts[1]) && texts[0].equals(".");
            /*boolean ddigit = i > 2 && this.isdigit(texts[3]);
            boolean bolded = i > 0 && textPositions.get(i - 1).getFont().getName().contains("Bold");
            boolean numnumperspace = i > 1 && bolded && this.isdigit(texts[2]) && texts[1].equals(".") && !this.isdigit(texts[0]);
            if (n<4 && i>0 && bolded && texts[0].equals(".") && this.isdigit(texts[1])) numnumperspace = true;*/

            if (bolded && (abcd && parentheses || numnumperspace)) {
                /*if (numnumperspace) {
                    answercounter++;
                    System.out.println(answercounter);
                    System.out.println(texts[3] + texts[2] + texts[1] + texts[0]);
                }*/

                if (texts[1].equals("D")) {
                    wasd = true;
                }
                if (wasd && numnumperspace) {
                    wasd = false;
                    print.append(" ").append(lastdx).append(" ").append(lastdy).append("\n");
                }

                TextPosition text;
                if (ddigit) {
                    text = textPositions.get(i - 2);
                } else {
                    text = textPositions.get(i - 1);
                }
                /*if (ddigit) {
                    text = textPositions.get(i - 3);
                } else {
                    text = textPositions.get(i - 2);
                }*/

                double[] xyhw = {text.getXDirAdj(), text.getYDirAdj()};
                if (numnumperspace) {
                    positions.append(currentpage);
                }

                positions.append(" ");

                for (int j = 0; j < xyhw.length; j++) {
                    double d = xyhw[j];
                    positions.append(d).append(" ");
                }

                positions.append("\n");
                if (texts[1].equals("D")) {
                    double x = textPositions.get(i).getXDirAdj();
                    double y = textPositions.get(i).getYDirAdj() - CropPDF.charheight;

                    for (int j = 0; j < imagearray[0].size(); ++j) {
                        if (Math.abs(x - imagearray[0].get(j)) < 25.0 && Math.abs(y - imagearray[1].get(j)) < 5.0) {
                            wasd = false;
                            positions.append(" ").append(imagearray[0].get(j) + imagearray[2].get(j));
                            positions.append(" ").append(imagearray[1].get(j) + imagearray[3].get(j)).append("\n");
                            System.out.println(isUSNCO + " " + currentpage + texts[1]);
                            break;
                        }
                    }
                }
                if (numnumperspace) break;//exit for loop for this line
            }
        }
        if (wasd) {
            if (n == 1) {
                wasd = false;
                print.append(" ").append(lastdx).append(" ").append(lastdy).append("\n");
            } else {
                //if (textPositions.size() == 4 && textPositions.get(1).getUnicode().equals("D")) { }
                lastdx = textPositions.get(n - 1).getXDirAdj();
                lastdy = textPositions.get(n - 1).getYDirAdj();
            }
        }

        print.append(positions);
        prevn=n;
    }
    /*********************************/

    /********** isDigit **********/
    public boolean isdigit(String string) {
        return string.equals("0") || string.equals("1") || string.equals("2") || string.equals("3") || string.equals("4") || string.equals("5") || string.equals("6") || string.equals("7") || string.equals("8") || string.equals("9");
    }
    /*********************************/

    /********** processOperator **********/
    protected void processOperator(Operator operator, List<COSBase> operands) throws IOException {
        //processing page currentpage+1

        int page = this.getCurrentPageNo();
        if (page != currentpage)
        { //System.out.println("Page "+currentpage+" "+isUSNCO+" "+imagearray[0].size());
            if (wasd) {
                wasd = false;
                print.append(" ").append(lastdx).append(" ").append(lastdy).append("\n");
            }
            currentpage = page;

            for(int j = 0; j < 4; ++j) {
                imagearray[j].clear();
            }
        }

        String operation = operator.getName();

        if ("Do".equals(operation)) {
            wasd = false;
            COSName objectName = (COSName)operands.get(0);
            PDXObject xobject = this.getResources().getXObject(objectName);
            if (xobject instanceof PDImageXObject) {
                Matrix ctmNew = this.getGraphicsState().getCurrentTransformationMatrix();
                imagearray[0].add(ctmNew.getTranslateX());
                imagearray[1].add(792.0F - ctmNew.getTranslateY() - ctmNew.getScalingFactorY());
                imagearray[2].add(1.09F);
                imagearray[3].add(ctmNew.getScalingFactorY());
            }
        } else {
            super.processOperator(operator, operands);
        }

    }
    /*********************************/

}

