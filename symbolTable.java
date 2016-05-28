/**
 * 
 * @author Christine Almodovar Spring '16
 *
 */
public class symbolTable {
    private String type;
    private String name;
    private String value;
    private int address;

    public symbolTable() {
        type = null; name = null; value = null; address = -1;
    }

    public String getType(){
        return type;
    }

    public String getName(){
        return name;
    }

    public String getValue(){
        return value;
    }

    public int getAddress() {
        return address;
    }

    public void setType(String tkType){
        type = tkType;
    }

    public void setName(String tkName){
        name = tkName;
    }

    public void setValue(String tkValue){
        value = tkValue;
    }

    public void setAddress(int addr) {
        address = addr;
    }

    public String toString(){
        StringBuffer Total = new StringBuffer(153);
        Total.append("|");
        Total.append(("  " + name + "                                     ").substring(0,37));
        Total.append("|");
        Total.append(("  " + type + "                                     ").substring(0,37));
        Total.append("|");
        Total.append(("  " + value + "                                     ").substring(0,37));
        Total.append("|");
        Total.append(("  " + address + "                                     ").substring(0,37));
        Total.append("|");

        return (Total.toString());
    }
}
