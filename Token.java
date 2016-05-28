/**
 * 
 * @author Christine Almodovar Spring '16
 *
 */
public class Token {
    private String tokenType;
    private int lineNum;
    private int lineCol;
    private String tokenName;



    public void Token(){tokenType = null;}



    public void setTOKEN_TYPE(String TK_type){
        boolean isAssign;
        isAssign= false;
        for (int i = 0; i < TKArray.length; i++){
            if(TK_type == TKArray[i])
                tokenType = TK_type;
                isAssign = true;
        }
        if (!(isAssign)) System.out.println("Error while Assigning Token Type");
    }



    public String getTOKEN_TYPE(){  return tokenType;    }

    public void setLine_num(int line){  lineNum = line;    }

    public int getLine_num(){    return lineNum;    }

    public void setLine_col(int col){   lineCol = col;    }

    public int getLine_col() { return lineCol; }

    public void setTokenName( String Name){    tokenName = Name;   }

    public String getTokenName() {  return tokenName;   }

    public String toString() {
        StringBuffer Total = new StringBuffer(153);
        Total.append("|");
        Total.append(("  " + tokenName + "                                     ").substring(0,37));
        Total.append("|");
        Total.append(("  " + tokenType + "                                     ").substring(0,37));
        Total.append("|");
        Total.append(("  " + lineNum + "                                     ").substring(0,37));
        Total.append("|");
        Total.append(("  " + lineCol + "                                     ").substring(0,37));
        Total.append("|");

       return (Total.toString());
    }

    private static String[] TKArray = new String[]
            {
                    //  Identifier
                    "TK_ID",

                    //  Keyword
                    "TK_PROGRAM",   "TK_BEGIN",     "TK_END",       "TK_PROCEDURE",
                    "TK_WHILE",     "TK_FOR",       "TK_DO",        "TK_REPEAT",
                    "TK_TYPE", 	    "TK_IF",        "TK_THEN",      "TK_ELSE",
                    "TK_UNTIL",     "TK_FUNCTION",  "TK_BREAK",     "TK_LABEL",
                    "TK_END_CODE",  "TK_VAR",       "TK_INT",       "TK_REAL",
                    "TK_WRITELN",   "TK_CONST",     "TK_ENDBLOCK",  "TK_OF",
                    "TK_ARRAY",     "TK_TO",

                    //  Operators
                    "TK_PLUS", 	    "TK_MINUS",     "TK_MULTI",     "TK_DIV",
                    "TK_GREATER",   "TK_LESS", 	    "TK_LESS_EQ",   "TK_GREATER_EQ",
                    "TK_MOD",		"TK_EQUALS",	"TK_NOT_EQ",    "TK_EXCLAMATION",
                    "TK_AND",       "TK_XOR",       "TK_OR",        "TK_NOT",
                    "TK_ASSIGNMENT",    "TK_DIV_FLOAT",     "TK_ARRAYOFINT",

                    //  Delimiters
                    "TK_SEMICOLON", 		"TK_OPEN_PAREN",		"TK_CLOSE_PAREN",
                    "TK_CLOSE_BRACKET",	    "TK_BEGIN_COMMENT",	    "TK_END_COMMENT",
                    "TK_COMMA",			    "TK_OPEN_BRACKET",	    "TK_SINGLE_QUOTE",
                    "TK_DOUBLE_QUOTE",	    "TK_COLON",             "TK_PERIOD",

                    //  Literals
                    "TK_INTEGER_LIT", "TK_REAL_LIT", "TK_STR_LIT",

                    //  End of line
                    "TK_EOLN",

                    //  End of file
                    "TK_EOF",

                    //  ERROR
                    "UNDEF", "TK_ERROR"
            };
}