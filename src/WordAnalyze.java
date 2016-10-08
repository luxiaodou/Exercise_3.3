import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by luxia on 2016/10/8.
 */

/*
说明：
控制台输入需要进行处理的字符
结束输入时需要用#END
个人测试样例：
BEGIN
ENDIF END IF THEN ELSE
a a1
1+ - * / (),:
;
:=
=
#END
 */
public class WordAnalyze {
    //宏定义
    private static final int BEGINSY = 1;
    private static final int ENDSY = 2;
    private static final int IFSY = 3;
    private static final int THENSY = 4;
    private static final int ELSE = 5;
    private static final int IDSY = 20;
    private static final int INTSY = 21;
    private static final int PLUSSY = 22;
    private static final int MINUSSY = 23;
    private static final int STARSY = 24;
    private static final int DIVISY = 25;
    private static final int LPARSY = 26;
    private static final int RPARSY = 27;
    private static final int COMMASY = 28;
    private static final int SEMISY = 29;
    private static final int COLONSY = 30;
    private static final int ASSIGNSY = 31;
    private static final int EQUSY = 32;

    private char c; //当前读进字符
    private String token;   //存放当前单词的字符串
    private int num;    //存放整形数值
    private int symbol; //识别类型
    private char[] input;   //控制台输入
    private int index = -1; //当前指针位置

    //从控制台获取输入
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

    //执行完整的输入-处理-输出步骤的函数
    public void fullprocedure() {
        try {
            getInput();
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (index < input.length) {
            getsym();
            if (symbol != -1) {
                System.out.println(symbol);
            }
        }
    }

    //词法分析的主要步骤
    private int getsym() {
        clearToken();
        getchar();
        while (isSpace() || isNewline() || isTab())
            getchar();
        if (isLetter()) {
            while (isLetter() || isDigit()) {
                catToken();
                getchar();
            }
            retract();

            int resultValue = reserver();
            if (resultValue == 0)
                symbol = IDSY;
            else symbol = resultValue;
        } else if (isDigit()) {
            while (isDigit()) {
                catToken();
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
            if (isStar()) {
                do {
                    do {
                        getchar();
                    } while (!isStar());
                    do {
                        getchar();
                        if (isDivi()) return 0;
                    } while (isStar());
                } while (!isStar());
            }
            retract();
            symbol = DIVISY;
        } else error();
        return 0;
    }

    //判别各个符号的函数
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

    private boolean isDigit() {
        return c >= '0' && c <= '9';
    }

    private boolean isTab() {
        return c == '\t';
    }

    private boolean isNewline() {
        return c == '\n';
    }

    public boolean isLetter() {
        return (c >= 'a' && c <= 'z' || c >= 'A' && c <= 'Z');
    }

    private boolean isSpace() {
        return c == ' ';
    }

    //将数字字符转化为数字的函数
    private int transNum(String token) {
        return Integer.parseInt(token);
    }

    //识别是否保留字的函数
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

    //将指针向前移动的函数
    private void retract() {
        if (index > 0)
            index--;
    }

    //拼接token
    private void catToken() {
        token = token + c;
    }

    //清空token
    private void clearToken() {
        token = "";
    }

    //从缓冲区中读取字符，如果字符已全部完成处理，则退出程序
    private void getchar() {
        if (index < input.length - 1)
            c = input[++index];
        else {
            System.out.println("Word Analyze complete !");
            System.exit(0);
        }
    }

    //报错，返回symbol = -1
    private void error() {
        symbol = -1;
        System.out.println("Something is error!");
    }

}


