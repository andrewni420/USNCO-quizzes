import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Scanner;

public class AnswerScraper {
    public static final int DPI = 500;
    public static final int Default = 72;
    public static final double charheight = 6.593520164489746;
    public static final double numwidth = 4.979999542236328;
    public static final double parwidth = 3.316680908203125;
    public static final int awidth = 235;
    public static int docyear = 2010;
    public static String USNCO = "N";
    public static String outfile="src/Answers&Formats";

    public static void main(String[] args) throws IOException {
            String textfile = "/Users/andrewni/IdeaProjects/USNCO test reader/src/USNCO_Locations/" + docyear + USNCO + ".txt";
            String outfile="src/Answers&Formats"+docyear+USNCO+".txt";
        //PrintStream Outfile = new PrintStream(new FileOutputStream(outfile));
            Scanner scanner = new Scanner(new FileReader(textfile));
            int year = scanner.nextInt();
            String isUSNCO = scanner.nextLine().substring(1);

            /*String answers = "1 D 86% 31 D 87%\n" +
                    "2 B 93% 32 B 86%\n" +
                    "3 A 86% 33 C 49%\n" +
                    "4 A 69% 34 C 37%\n" +
                    "5 D 84% 35 B 30%\n" +
                    "6 B 61% 36 B 33%\n" +
                    "7 B 49% 37 A 73%\n" +
                    "8 B 64% 38 C 55%\n" +
                    "9 B 31% 39 A 44%\n" +
                    "10 D 18% 40 B 10%\n" +
                    "11 C 45% 41 B 64%\n" +
                    "12 C 70% 42 A 36%\n" +
                    "13 A 56% 43 A 27%\n" +
                    "14 D 63% 44 D 58%\n" +
                    "15 B 57% 45 C 55%\n" +
                    "16 A 40% 46 D 30%\n" +
                    "17 B 57% 47 A 84%\n" +
                    "18 A 90% 48 B 49%\n" +
                    "19 D 89% 49 D 57%\n" +
                    "20 D 93% 50 A 40%\n" +
                    "21 A 70% 51 C 31%\n" +
                    "22 D 33% 52 D 76%\n" +
                    "23 D 36% 53 D 24%\n" +
                    "24 C 62% 54 B 18%\n" +
                    "25 B 33% 55 A 37%\n" +
                    "26 C 91% 56 B 24%\n" +
                    "27 B 43% 57 C 42%\n" +
                    "28 C 67% 58 A 51%\n" +
                    "29 C 60% 59 C 44%\n" +
                    "30 A 49% 60 B 21%";
            String[] firstarray=answers.split(" |\n");
            String[] answerarray=new String[60];
            for (int i=0;i<30;i++){
                answerarray[i]=firstarray[i*6+1];
                answerarray[30+i]=firstarray[i*6+4];
            }/****/

        String answers = "1. A 85%\n" +
                "2. D 78%\n" +
                "3. B 60%\n" +
                "4. B 65%\n" +
                "5. A 19%\n" +
                "6. B 75%\n" +
                "7. D 62%\n" +
                "8. C 46%\n" +
                "9. C 47%\n" +
                "10. A 41%\n" +
                "11. A 48%\n" +
                "12. D 70%\n" +
                "13. B 13%\n" +
                "14. B 26%\n" +
                "15. A 47%\n" +
                "16. B 32%\n" +
                "17. C 59%\n" +
                "18. B 59%\n" +
                "19. C 87%\n" +
                "20. A 91%\n" +
                "21. D 61%\n" +
                "22. B 51%\n" +
                "23. B 65%\n" +
                "24. D 28%\n" +
                "25. A 93%\n" +
                "26. A 57%\n" +
                "27. B 73%\n" +
                "28. D 28%\n" +
                "29. B 26%\n" +
                "30. B 48%\n" +
                "31. C 17%\n" +
                "32. B 31%\n" +
                "33. A 33%\n" +
                "34. D 59%\n" +
                "35. A 62%\n" +
                "36. B 8%\n" +
                "37. D 41%\n" +
                "38. D 30%\n" +
                "39. C 48%\n" +
                "40. D 25%\n" +
                "41. A 48%\n" +
                "42. A 40%\n" +
                "43. D 61%\n" +
                "44. D 36%\n" +
                "45. D 35%\n" +
                "46. B 32%\n" +
                "47. C 76%\n" +
                "48. A 51%\n" +
                "49. A 88%\n" +
                "50. D 66%\n" +
                "51. A 61%\n" +
                "52. A 39%\n" +
                "53. C 53%\n" +
                "54. C 53%\n" +
                "55. B 54%\n" +
                "56. D 74%\n" +
                "57. C 39%\n" +
                "58. B 28%\n" +
                "59. A 47%\n" +
                "60. C 40%";
        String[] firstarray=answers.split(" |\n");
        String[] answerarray = new String[60];
        for (int i=0;i<60;i++){
            answerarray[i]=firstarray[3*i+1];
            //System.out.println(answerarray[i]);
        }/***/


                int currentpage = -1;

                int[] formats = new int[60];
                for (int i = 0; i < 60; ++i) {
                    //System.out.println(i);
                    double[] xy = new double[12];
                    int page = scanner.nextInt();
                    if (page != currentpage) {
                        currentpage = page;
                    }

                    for (int j = 0; j < 12; ++j) {
                        xy[j] = scanner.nextDouble();
                    }

                    int height = (int) (xy[3] - xy[1]) * DPI / Default;
                    int answerformat = -1;
                    if (xy[5] - xy[3] < 0.01) {
                        ++answerformat;
                    }

                    if (xy[7] - xy[3] < 0.01) {
                        ++answerformat;
                    }
                    formats[i] = answerformat;

                    int answerwidth = 0;
                    int[] answerheights = new int[4];
                    if (answerformat == -1) {
                        answerwidth = awidth;
                        //if (i==58) answerwidth+=30;/******/
                        for (int j = 0; j < 4; ++j) {
                            answerheights[j] = (int) ((xy[2 * j + 5] - xy[2 * j + 3]) * DPI / Default);
                        }

                        answerheights[3] += (int) (2.75 * charheight * DPI / Default);
                        //if (i==31) answerheights[3]=answerheights[0];
                    }

                    if (answerformat == 0) {
                        answerwidth = awidth / 2;
                        int h1 = (int) ((xy[7] - xy[3]) * DPI / Default);

                        //if (i==59) h1-=4*DPI/Default;
                        int h2 = (int) ((xy[11] - xy[7] + 2.75 * charheight) * DPI / Default);
                        h2 = h1;

                        answerheights = new int[]{h1, h1, h2, h2};
                    }

                    if (answerformat == 1) {
                        answerwidth = awidth / 4;
                        int h = (int) (2.75 * charheight * DPI / Default);
                        answerheights = new int[]{h, h, h, h};
                    }



                }

                int[] actualarray = new int[60];
                for (int i=0;i<60;i++){
                    if (answerarray[i].equals("A")) actualarray[i]=1;
                    if (answerarray[i].equals("B")) actualarray[i]=2;
                    if (answerarray[i].equals("C")) actualarray[i]=3;
                    if (answerarray[i].equals("D")) actualarray[i]=4;
                }


        System.out.print("[");
        for (int i=0;i<60;i++){
            System.out.print(actualarray[i]+",");
        }System.out.println("],");
        System.out.print("[");
        for (int i=0;i<60;i++){
            System.out.print(formats[i]+",");
        } System.out.println("],");

        //Outfile.close();


            if (USNCO.equals("L")) USNCO="N";
            if (USNCO.equals("N")) {
                USNCO="L";
                docyear--;
            }

    }
}
