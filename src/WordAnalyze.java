import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by luxia on 2016/10/8.
 */
public class WordAnalyze {
    private static final int BEGINSY = 1;
    public static final int ENDSY = 2;
    public static final int IFSY = 3;
    public static final int THENSY = 4;
    public static final int ELSE = 5;
    public static final int IDSY = 20;
    public static final int INTSY = 21;
    public static final int PLUSSY = 22;
    public static final int MINUSSY = 23;
    public static final int STARSY = 24;
    public static final int DIVISY = 25;
    public static final int LPARSY = 26;
    public static final int RPARSY = 27;
    public static final int COMMASY = 28;
    public static final int SEMISY = 29;
    public static final int COLONSY = 30;
    public static final int ASSIGNSY = 31;
    public static final int EQUSY = 32;
    private char c;
    private String token;
    private int num;
    private int symbol;
    private char[] input;
    private int index = -1;


    public void getInput() throws IOException {
        String temp = "";
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        String str = null;
        while ((str = bf.readLine()) != null) {
            if (str.equals("#END"))
                break;
            temp = temp + str + "\n";
        }
        input = temp.toCharArray();
    }

    public void fullprocedure() {
        try {
            getInput();
        } catch (IOException e) {
            e.printStackTrace();
        }
        while(index < input.length)
            System.out.println(getsym());
    }

    private int getsym() {
        clearToken();
        getchar();
        while (isSpace() || isNewline() || isTab())
            getchar();
        if (isLetter()) {
            while (isLetter() || isDigit()) {
                carToken();
                getchar();
            }
            retract();

            int resultValue = reserver();
            if (resultValue == 0)
                symbol = IDSY;
            else symbol = resultValue;
        } else if (isDigit()) {
            while (isDigit()) {
                carToken();
                getchar();
            }
            retract();
            num = transNum(token);
            symbol = INTSY;
        } else if (isColon()) {
            getchar();
            if (isEqu())
                symbol = ASSIGNSY;
            else {
                retract();
                symbol = COLONSY;
            }
        } else if (isPlus()) symbol = PLUSSY;
        else if (isMinus()) symbol = MINUSSY;
        else if (isStar()) symbol = STARSY;
        else if (isLpar()) symbol = LPARSY;
        else if (isRpar()) symbol = RPARSY;
        else if (isComma()) symbol = COMMASY;
        else if (isSemi()) symbol = SEMISY;
        else if (isDivi()) {
            getchar();
            if(isStar()){
                do {
                    do {
                        getchar();
                    }while (!isStar());
                    do {
                        getchar();
                        if(isDivi()) return 0;
                    }while(isStar());
                }while (!isStar());
            }
            retract();
            symbol = DIVISY;
        }
        else error();
        return 0;
    }

    private boolean isPlus() {
        return c == '+';
    }

    private boolean isMinus() {
        return c == '-';
    }

    private boolean isStar() {
        return c == '*';
    }

    private boolean isLpar() {
        return c == '(';
    }

    private boolean isRpar() {
        return c == ')';
    }

    private boolean isComma() {
        return c == ',';
    }

    private boolean isSemi() {
        return c == ';';
    }

    private boolean isDivi() {
        return c == '/';
    }

    private boolean isEqu() {
        return c == '=';
    }

    private boolean isColon() {
        return c == ':';
    }

    private int transNum(String token) {
        return Integer.parseInt(token);
    }

    private int reserver() {
        switch (token) {
            case "BEGIN":
                return BEGINSY;
            case "END":
                return ENDSY;
            case "IF":
                return IFSY;
            case "THEN":
                return THENSY;
            case "ELSE":
                return ELSE;
            default:
                return 0;
        }
    }

    private void retract() {
        if (index > 0)
            index--;
    }

    private void carToken() {
        token = token + c;
    }

    private boolean isDigit() {
        return c >= '0' && c <= '9';
    }

    private boolean isTab() {
        return c == '\t';
    }

    private boolean isNewline() {
        return c == '\n';
    }

    private void clearToken() {
        token = "";
    }

    private void getchar() {
        if (index < input.length)
            c = input[++index];
        else
            System.out.println("Word Analyze complete !");
    }

    private boolean isSpace() {
        return c == ' ';
    }

    private void error() {
        System.out.println("Something is error!");
    }

    public boolean isLetter() {
        return (c >= 'a' && c <= 'z' || c >= 'A' && c <= 'Z');
    }

}


