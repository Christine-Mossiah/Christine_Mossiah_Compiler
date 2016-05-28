/**
 * 
 * @author Christine Mossiah (Almodovar) Spring '16
 *
 */
public class instructionTable {
    private int instr_ptr;
    private String type;
    private String instr;
    private String value;

    public instructionTable(){
        instr_ptr = -1; type = null; instr = null; value = null;
    }

    public int getInstPtr() {
        return instr_ptr;
    }

    public String getType() {
        return type;
    }

    public String getinstr() {
        return instr;
    }

    public String getValue() {
        return value;
    }

    public void setinstr(String instr) {
        this.instr = instr;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setInstPtr(int instr_ptr) {
        this.instr_ptr = instr_ptr;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String toString(){
        StringBuffer Total = new StringBuffer(153);
        Total.append("|");
        Total.append(("  " + instr_ptr + "                                     ").substring(0,37));
        Total.append("|");
        Total.append(("  " + type + "                                     ").substring(0,37));
        Total.append("|");
        Total.append(("  " + instr + "                                     ").substring(0,37));
        Total.append("|");
        Total.append(("  " + value + "                                     ").substring(0,37));
        Total.append("|");

        return (Total.toString());
    }


}
