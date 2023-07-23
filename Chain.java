

import enigma.core.Enigma;
import enigma.event.TextMouseEvent;
import enigma.event.TextMouseListener;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;
import java.util.*;
import java.io.IOException;


public class Chain {
    public enigma.console.Console cn = Enigma.getConsole("Chain", 100, 20, 20, 0);
    public TextMouseListener tmlis;
    public KeyListener klis;

    // ------ Standard variables for mouse and keyboard ------
    public int mousepr;          // mouse pressed?
    public int mousex, mousey;   // mouse text coords.
    public int keypr;   // key pressed?
    public int rkey;    // key   (for press/release)
    // ----------------------------------------------------

    // map burada tanımlanır
    char map[][] = new char[20][100];

    char tableThingy[] = new char[200];

    int round = 1;
    int ROW = 31;
    int COLUMN = 19;
    int score = 0;

    public static int jk = 0;

    //MultilinkedList<String> mlList = new MultilinkedList<>();

    MultiLinkedList multiLinkedList = new MultiLinkedList();

    List<Character> list1 = new ArrayList<>();
    Scanner scanner = new Scanner(System.in); // for taking value from user





    Chain(int seed) throws Exception {   // --- Contructor
        Random random = new Random(1254);
        int seedNo = 1254;

        System.out.println("Please enter your name "); // telling user to enter a number
        String name = scanner.next();







        // ------ Standard code for mouse and keyboard ------ Do not change
        tmlis = new TextMouseListener() {
            public void mouseClicked(TextMouseEvent arg0) {
            }

            public void mousePressed(TextMouseEvent arg0) {
                if (mousepr == 0) {
                    mousepr = 1;
                    mousex = arg0.getX();
                    mousey = arg0.getY();
                }
            }

            public void mouseReleased(TextMouseEvent arg0) {
            }
        };
        cn.getTextWindow().addTextMouseListener(tmlis);

        klis = new KeyListener() {
            public void keyTyped(KeyEvent e) {
            }

            public void keyPressed(KeyEvent e) {
                if (keypr == 0) {
                    keypr = 1;
                    rkey = e.getKeyCode();
                }
            }

            public void keyReleased(KeyEvent e) {
            }
        };
        cn.getTextWindow().addKeyListener(klis);
        // ----------------------------------------------------

        fillMap();

        printConsole();


        // +'nın yerini mapte belirle
        int px, py;


        // cn.getTextWindow().output(px, py, '+');
        int k = 10;//for setCursorPosition
        while (true) {
            if (mousepr == 1) {  // if mouse button pressed

                px = mousex;
                py = mousey;


                if (py > 19 || px > 31) ;// it can't add + sign out of map
                else if (map[mousey][mousex] == ' ' && checkHowManyPlus(mousey, mousex))
                    ;// can't add more than 2 + around a character
                else if (!plusError(mousey, mousex)) ;//can't add + near .
                else if (map[py][px] == ' ' && !isTheGap(py, px)) {// check isTheGap method
                    cn.getTextWindow().output(mousex, mousey, '+');
                    map[py][px] = '+';

                } else if (map[py][px] == '+') {
                    map[py][px] = ' '; // deleting +  sign
                }

                printConsole();
                mousepr = 0;     // last action
            }

            if (keypr == 1) {    // if keyboard button pressed

                if (rkey == KeyEvent.VK_ENTER) {
                    print("Board Seed:   " + seedNo, 40, 0);
                    print("Round     :   " + round, 40, 1);
                    print("Score     :   ", 40, 2);
                    print("-----------------------------------------------", 40, 3);
                    print("Table:", 40, 4);



//                    if(!isMoreThanOneChain(mousey,mousex)){
//                        print("ERROR: INVALID CHAIN",40,k);
//                        invalidMap();
//                        deletePlusSigns();
//                        k++;
//                    }

                    int a = plusSigns();
                    /*if(isMoreThanOneChain(mousex,mousey))
                    {
                        print("Error in chain - Game Over - ", 40, k);
                        saveScoreToFile();

                        Thread.sleep(2000);

                        String fileName = "highscore.txt";
                        printFileContents("highscore.txt");

                        invalidMap();
                        deletePlusSigns();
                        k++;
                        break;
                    }

                   else */ if (checkChain(fillSLL())) {//zincir doğru, skor bastırılır

                        score = score(a);

                        print("Score     :   " + score, 40, 2);
                        //String result = convertMultiLinkedListToString((List<List<Character>>) mlList);
                        // print(result, 40 , round + 4);
                        //multiLinkedList.add(list1);
                        // String result = multiLinkedList.convertToString();
                        //print(result, 40 , round + 5);
                        validMap();


                        multiLinkedList.add(list1);

                        round += 1;

                        print("+", 37, round + 5);
                        print("", 37, round + 4);

                        // if(list1.size() < 7){
                        //      print("+", 40 , round + 5);
                        //  }
                        //  else{
                        //      print("+", 40 , round + 3);
                        // }
                        // printMultiLinkedList(multiLinkedList, 40, 10);
                        for(int i =1; i<=list1.size(); i++){
                            if(i !=list1.size()){
                                if(i == 1){
                                    System.out.print("   ");
                                    System.out.print(list1.get(i - 1));
                                    System.out.print("+");


                                }
                                else if (i != 1 && i != list1.size()){
                                    System.out.print(list1.get(i - 1));
                                    System.out.print("+");


                                }
                            }
                            else if (i == list1.size()){
                                System.out.print(list1.get(i - 1));

                            }
                        }

                        for(int i = list1.size(); list1.size() > 0; i--){
                            list1.remove(i - 1);
                        }

                    } else {
                        print("Error in chain - Game Over - ", 40, k);
                        boolean isEmpty = isFileEmpty("highscore.txt");
                        if (isEmpty) {
                            writeEmptyLines("highscore.txt", 100);

                        }
                        else
                        {int z = 0;}

                        saveNameToFile(name);
                        saveScoreToFile();

                        Thread.sleep(2000);

                        String fileName = "highscore.txt";
                        printFileContents("highscore.txt");

                        invalidMap();
                        deletePlusSigns();
                        k++;
                        break;
                    }
                    printConsole();
                }

                if (rkey == KeyEvent.VK_R) {
                    round++;

                    // round bitimi
                }

                if (rkey == KeyEvent.VK_E) {

                    print("You pressed E , exiting", 40, 12);
                    boolean isEmpty = isFileEmpty("highscore.txt");
                    if (isEmpty) {
                        writeEmptyLines("highscore.txt", 100);

                    }
                    else
                    {int z = 0;}


                    saveNameToFile(name);
                    saveScoreToFile();

                    Thread.sleep(2000);


                    String fileName = "highscore.txt";
                    printFileContents("highscore.txt");


                    break;

                }

                keypr = 0;    // last action
            }
            Thread.sleep(20);
        }

    }

    public void deletePlusSigns() {
        for (int i = 0; i < COLUMN; i++) {
            for (int j = 0; j < ROW; j++) {
                if (map[i][j] == '+') {
                    map[i][j] = ' ';
                }
            }
        }
    }

    public void fillMap() {
        // map burada doldurulur
        Random random = new Random(1254);
        for (int i = 0; i < COLUMN; i++) {
            for (int j = 0; j < ROW; j++) {
                if (i % 2 == 0 && j % 2 == 0) {
                    int element = random.nextInt(4) + 1;
                    map[i][j] = Integer.toString(element).charAt(0);
                } else
                    map[i][j] = ' ';
            }
        }
    }

    public void print(String str, int i, int i1) {
        cn.getTextWindow().setCursorPosition(i, i1);
        cn.getTextWindow().output(str);
    }

    public void printC(Character cha, int i, int i1){
        cn.getTextWindow().setCursorPosition(i,i1);
        cn.getTextWindow().output(cha);
    }

    public void printM(MultiLinkedList list, int i ,int i1){
        cn.getTextWindow().setCursorPosition(i,i1);
        cn.getTextWindow().output(list.toString());
    }


    public boolean isTheGap(int column, int row) { // it doesn't add + signs
        boolean flag = false;


        if (column % 2 == 1 && row % 2 == 1)

            flag = true;

        return flag;
    }

    public void printConsole() {
        //print map
        for (int i = 0; i < COLUMN; i++) {
            for (int j = 0; j < ROW; j++) {
                cn.getTextWindow().output(j, i, map[i][j]);
            }
        }
    }

    public boolean plusError(int b, int a) {
        boolean flag = true;
        if (a + 1 < 31 && map[b][a + 1] == '.') flag = false;
        if (a - 1 > 0 && map[b][a - 1] == '.') flag = false;
        if (b + 1 < 19 && map[b + 1][a] == '.') flag = false;
        if (b - 1 > 0 && map[b - 1][a] == '.') flag = false;
        return flag;
    }

    public int checkFourDirections(int a, int b) {
        int count = 0;
        if (a + 1 < 31 && map[b][a + 1] == '+') count += 1;//sağında + var
        if (a - 1 > 0 && map[b][a - 1] == '+') count += 1;//solunda + var
        if (b + 1 < 19 && map[b + 1][a] == '+') count += 1;//altında + var
        if (b - 1 > 0 && map[b - 1][a] == '+') count += 1;//üstünde + var

        return count;
    }

    public boolean checkHowManyPlus(int y, int x) {
        boolean flag = false;
        int a, b;
        if (y % 2 == 1) {//karakter üstünde
            a = x;
            b = y - 1;
            if (checkFourDirections(a, b) > 1) flag = true;
        }
        if (y % 2 == 1) {//karakter altında
            a = x;
            b = y + 1;
            if (checkFourDirections(a, b) > 1) flag = true;
        }
        if (y % 2 == 0) {//karakter sağında
            a = x + 1;
            b = y;
            if (checkFourDirections(a, b) > 1) flag = true;
        }
        if (y % 2 == 0) {//karakter solunda
            a = x - 1;
            b = y;
            if (checkFourDirections(a, b) > 1) flag = true;
        }
        return flag;
    }

    public int[] coordinatesOfHead() {
        int[] koordinatlar = new int[2];
        koordinatlar[0] = -1;
        koordinatlar[1] = -1;
        boolean flag = false;
        for (int i = 0; i < COLUMN; i++) {
            for (int j = 0; j < ROW; j++) {
                if (map[i][j] != '+' && map[i][j] != ' ') {
                    if (checkFourDirections(j, i) == 1) {
                        koordinatlar[0] = i;
                        koordinatlar[1] = j;
                        flag = true;
                        break;
                    }
                }

            }
            if (flag) break;
        }
        return koordinatlar;
    }

    public int plusSigns() {
        int count = 0;
        for (int i = 0; i < COLUMN; i++) {
            for (int j = 0; j < ROW; j++) {
                if (map[i][j] == '+') count++;
            }
        }
        return count;
    }

    public int score(int a) {

        int numberOfCharacters = (a + 1);
        score += numberOfCharacters * numberOfCharacters;


        return score;
    }

    public char dene(char character) {
        char dene = 0;
        if (character == '1') dene = 'a';
        if (character == '2') dene = 'b';
        if (character == '3') dene = 'c';
        if (character == '4') dene = 'd';

        return dene;
    }

    public void validMap() {
        for (int i = 0; i < COLUMN; i++) {
            for (int j = 0; j < ROW; j++) {
                if (map[i][j] == 'a'){
                    list1.add('1');
                    // mlList.addNode(map[i][j]);

                    map[i][j] = '.';
                }
                if (map[i][j] == 'b') {
                    list1.add('2');
                    //  mlList.addNode(map[i][j]);
                    map[i][j] = '.';
                }
                if (map[i][j] == 'c') {
                    list1.add('3');
                    // mlList.addNode(map[i][j]);
                    map[i][j] = '.';
                }
                if (map[i][j] == 'd') {
                    list1.add('4');
                    // mlList.addNode(map[i][j]);
                    map[i][j] = '.';
                }
                // multiLinkedList.add(list1);
            }
        }
    }

    public void invalidMap() {
        for (int i = 0; i < COLUMN; i++) {
            for (int j = 0; j < ROW; j++) {
                if (map[i][j] == 'a') map[i][j] = '1';
                if (map[i][j] == 'b') map[i][j] = '2';
                if (map[i][j] == 'c') map[i][j] = '3';
                if (map[i][j] == 'd') map[i][j] = '4';

            }
        }
    }

    public SingleLinkedList fillSLL() {
        SingleLinkedList sll = new SingleLinkedList();
        if (coordinatesOfHead()[0] != -1) {
            int i = coordinatesOfHead()[0];
            int j = coordinatesOfHead()[1];
            int numberOfCharacters = plusSigns();
            sll.add(map[i][j]);
            map[i][j] = dene(map[i][j]);
            for (int k = 0; k < numberOfCharacters; k++) {
                if (j + 1 < ROW && map[i][j + 1] == '+') {//sağa gidiyor
                    map[i][j + 1] = ' ';
                    j += 2;
                    sll.add(map[i][j]);
                    map[i][j] = dene(map[i][j]);
                } else if (i + 1 < COLUMN && map[i + 1][j] == '+') {//aşağı gidiyor
                    map[i + 1][j] = ' ';
                    i += 2;
                    sll.add(map[i][j]);
                    map[i][j] = dene(map[i][j]);
                } else if (j - 1 > 0 && map[i][j - 1] == '+') {//sola gidiyor
                    map[i][j - 1] = ' ';
                    j -= 2;
                    sll.add(map[i][j]);
                    map[i][j] = dene(map[i][j]);
                } else if (i - 1 > 0 && map[i - 1][j] == '+') {//yukarı gidiyor
                    map[i - 1][j] = ' ';
                    i -= 2;
                    sll.add(map[i][j]);
                    map[i][j] = dene(map[i][j]);
                }
            }
        }
        return sll;

    }



    public boolean checkChain(SingleLinkedList sll) {
        boolean flag = true;
        if (sll.size() < 4) {
            print("ERROR", 40, 3);
            flag = false;
        } else {
            Node node = sll.head;
            for (int i = 1; i < sll.size(); i++) {
                int a = (char) node.getData();
                int b = (char) node.getLink().getData();
                if (Math.abs(a - b) == 1) {
                    node = node.link;
                } else {
                    flag = false;
                    break;
                }
            }
        }
        return flag;
    }

    public boolean isMoreThanOneChain(int y, int x) {
        boolean flag = false;
        int a, b;
        if (y % 2 == 1 && y-1>0) {//karakter üstünde
            a = x;
            b = y - 1;
            map[b][a]=dene(map[b][a]);
        }
        if (y % 2 == 1 && y+1<19) {//karakter altında
            a = x;
            b = y + 1;
            map[b][a]=dene(map[b][a]);

        }
        if (y % 2 == 0 && x+1<31) {//karakter sağında
            a = x + 1;
            b = y;
            map[b][a]=dene(map[b][a]);
        }
        if (y % 2 == 0 && x-1>0) {//karakter solunda
            a = x - 1;
            b = y;
            map[b][a]=dene(map[b][a]);
        } else
            flag = true;
        return flag;
    }


    public static List<String> getSortedScores(String fileName) {
        List<String> scores = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            String currentName = null;
            int currentScore = 0;

            while ((line = reader.readLine()) != null) {
                if (line.startsWith("----")) {
                    if (currentName != null) {
                        scores.add(currentName + " - " + currentScore);
                    }
                    currentName = null;
                    currentScore = 0;
                } else if (line.startsWith("Your Score is: ")) {
                    currentScore = Integer.parseInt(line.substring(15));
                } else {
                    currentName = line;
                }
            }

            if (currentName != null) {
                scores.add(currentName + " - " + currentScore);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        // Sort scores in descending order
        Collections.sort(scores, Comparator.reverseOrder());

        return scores;
    }


    private void saveScoreToFile() {
        String filePath = "highscore.txt";

        try {
            FileWriter fileWriter = new FileWriter(filePath, true);
            PrintWriter printWriter = new PrintWriter(fileWriter);


            printWriter.println("Your Score is: " + score);
            printWriter.print("-------------------");
            printWriter.print("");

            printWriter.close();

            //System.out.println("   Score saved to file.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveNameToFile(String name){

        String filePath = "highscore.txt";


        try{
            FileWriter fileWriter = new FileWriter(filePath, true);
            PrintWriter printWriter = new PrintWriter(fileWriter);


            printWriter.println("-------------------");
            printWriter.println(name);

            printWriter.close();


        } catch (IOException e){
            e.printStackTrace();
        }
    }


    public static ArrayList<Integer> readHighScoresFromFile(String filename) {
        ArrayList<Integer> highScores = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Assuming each line contains a single high score as an integer
                int score = Integer.parseInt(line);
                highScores.add(score);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Sort the high scores in descending order
        Collections.sort(highScores, Collections.reverseOrder());

        return highScores;
    }
    public static void printFileContents(String fileName) {
        try {
            String filePath = System.getProperty("user.dir") + "/" + fileName;
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
                jk += 1;
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public class MultiLinkedList {
        private List<List<Character>> multiLinkedList;

        public MultiLinkedList() {
            multiLinkedList = new ArrayList<>();
        }

        public void add(List<Character> list) {
            multiLinkedList.add(list);
        }

        public List<List<Character>> getMultiLinkedList() {
            return multiLinkedList;
        }
    }

    public static void printMultiLinkedList(MultiLinkedList multiLinkedList, int startRow, int startColumn) {
        List<List<Character>> elements = multiLinkedList.getMultiLinkedList();
        int currentRow = startRow;

        for (List<Character> list : elements) {
            int currentColumn = startColumn;

            for (Character element : list) {
                moveCursorToPosition(currentRow, currentColumn);
                System.out.print(element);
                System.out.print('+');

                // Move to the next column
                currentColumn++;
            }

            // Move to the next row
            currentRow++;
            System.out.println();
        }
    }



    public static void moveCursorToPosition(int row, int column) {
        System.out.print("\033[" + row + ";" + column + "H");
    }
    public static boolean isFileEmpty(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            return reader.readLine() == null;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true; // Return true if an exception occurs or file cannot be opened
    }

    public static void writeEmptyLines(String filePath, int numLines) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            for (int i = 0; i < numLines; i++) {
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




}