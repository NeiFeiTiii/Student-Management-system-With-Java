//学生类
import java.io.Serializable;

import static java.lang.Integer.parseInt;

public class Students implements Serializable {
    private static String OriID = "221000";
    private String StuID;
    private String name;
    private MyDate Birth;
    private int JavaScore;

    public void setName(String name) {
        this.name = name;
    }

    public void setJavaScore(int javaScore) throws Exception {
        if(javaScore > 100 || javaScore <0)
            throw new Exception("分数不合法");
        else this.JavaScore = javaScore;
    }

    public void setStuID() {
        StuID = OriID;
        int ori = parseInt(OriID,10); //字符串转int，10进制，javaNB！！
        ori++;
        OriID = String.valueOf(ori);        //int转字符串，耶耶耶
    }

    public void setBirth(MyDate birth) {
        Birth = birth;
    }

    public String getName() {
        return name;
    }

    public String getStuID() {
        return StuID;
    }

    public MyDate getBirth() {
        return Birth;
    }

    public int getJavaScore() {
        return JavaScore;
    }

    @Override
    public String toString() {
        return "学号："+this.StuID+",名字："+this.name+"，出生日期是"+this.Birth+"，Java分数是："+this.JavaScore;
    }
    public void errorClear(){
        int ori = parseInt(OriID,10); //字符串转int，10进制，javaNB！！
        ori--;
        OriID = String.valueOf(ori);        //int转字符串，耶耶耶
    }
}

