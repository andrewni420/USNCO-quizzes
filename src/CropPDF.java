import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import javax.imageio.ImageIO;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;

public class CropPDF {
    public static final int DPI = 500;
    public static final int Default = 72;
    public static final double charheight = 6.593520164489746;
    public static final double numwidth = 4.979999542236328;
    public static final double parwidth = 3.316680908203125;
    public static final int awidth = 235;
    public static int year = 2010;
    public static String USNCO = "N";

    public CropPDF() {
    }

    public static void main(String[] args) throws IOException {
        String textfile = "/Users/andrewni/IdeaProjects/USNCO test reader/src/USNCO_Locations/"+year+USNCO+".txt";
        Scanner scanner = new Scanner(new FileReader(textfile));
        int year = scanner.nextInt();
        String isUSNCO = scanner.nextLine().substring(1);
        String fileName;
        if (isUSNCO.equals("N")) fileName = "Nationals/"+year+" usnco.pdf";
        else fileName = "Locals/" + year + " local.pdf";
        PDDocument document = null;

        try {
            document = PDDocument.load(new File(fileName));
            PDFRenderer renderer = new PDFRenderer(document);
            BufferedImage image = null;
            int currentpage = -1;

            int[] formats = new int[60];
            for(int i = 0; i < 60; ++i) {
                double[] xy = new double[12];
                int page = scanner.nextInt();
                if (page != currentpage) {
                    image = renderer.renderImageWithDPI(page - 1, DPI);
                    currentpage = page;
                }

                for(int j = 0; j < 12; ++j) {
                    xy[j] = scanner.nextDouble();
                }


                BufferedImage[] answers = new BufferedImage[4];
                int answerformat = -1;
                if (xy[5] - xy[3] < 0.01) {
                    ++answerformat;
                }

                if (xy[7] - xy[3] < 0.01) {
                    ++answerformat;
                }
                formats[i]=answerformat;

                int answerwidth = 0;
                int[] answerheights = new int[4];
                if (answerformat == -1) {
                    answerwidth = awidth;
                    for(int j = 0; j < 4; ++j) {
                        answerheights[j] = (int)((xy[2 * j + 5] - xy[2 * j + 3]) * DPI / Default);
                    }

                    answerheights[3] += (int)(2.75*charheight*DPI/Default);
                }

                if (answerformat == 0) {
                    answerwidth = awidth/2;
                    int h1 = (int)((xy[7] - xy[3]) * DPI / Default);
                    int h2=(int)((xy[11]-xy[7]+2.75*charheight)*DPI/Default);
                    h2=h1;

                    answerheights = new int[]{h1, h1, h2, h2};
                }

                if (answerformat == 1) {
                    answerwidth = awidth/4;
                    int h = (int)(2.75*charheight*DPI/Default);
                    answerheights = new int[]{h, h, h, h};
                }

                int height=(int)(xy[3] - xy[1]) * DPI / Default;
                int width=265;
                int y0=(int)((xy[1] - 1.75*charheight) * DPI / Default);
                //if (i==58)y0+=20;
                BufferedImage question = image.getSubimage((int)((xy[0] - numwidth) * DPI / Default), y0, width*DPI/Default, height);

                //if (i==5||i==6) answerwidth-=2;
                for(int j = 0; j < 4; ++j) {
                    double overheight=1.75;
                    /*if (i==7&&j==3) answerwidth+=5;
                    if (i==28&&j==2)answerwidth-=5;
                    if (i==28&&j==3)answerwidth+=5;
                    if (i==30&&j==0)answerwidth+=15;
                    if (i==30&&j==1)answerwidth-=15;
                    if (i==30&&j==2)answerwidth+=15;
                    if (i==30&&j==3)answerwidth-=15;
                    if (i==58&&j==0)answerwidth-=2;/**/
                    answers[j] = image.getSubimage((int)((xy[2 * j + 2] - parwidth) * DPI / Default), (int)((xy[2 * j + 3] - overheight*charheight) * DPI / Default), answerwidth * DPI/Default, answerheights[j]);
                    /*if (i==27){
                        overheight=1.45;
                        answerheights[0]+=2;
                        answerheights[1]+=1;
                        answerheights[2]+=1;
                        answerheights[3]-=5;
                        answers[j] = image.getSubimage((int)((xy[2 * j + 2] - 2*parwidth) * DPI / Default), (int)((xy[2 * j + 3] - overheight*charheight) * DPI / Default), answerwidth * DPI/Default, answerheights[j]);
                    }*/
                }
                //Q x y
                //a x y
                //b x y
                //c x y
                //d x y
                //final x y

                String basename = "/Users/andrewni/USNCO quizzes/quizzes/public/" + year + isUSNCO;
                basename="src/"+year+isUSNCO;
                File folder = new File(basename);
                folder.mkdir();
                basename=basename+ "/q" + (i + 1);
                for(int j = 0; j < 5; ++j) {
                    if (j == 0) {
                        ImageIO.write(question, "png", new File(basename + ".png"));
                    } else {
                        ImageIO.write(answers[j - 1], "png", new File(basename + "-" + j + ".png"));
                        /************/
                        AddTransparency.transparent(basename+"-"+j+".png");
                    }
                }
            }



        } finally {
            if (document != null) {
                document.close();
            }

        }

    }
}
