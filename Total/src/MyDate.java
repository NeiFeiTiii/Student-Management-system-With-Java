//日期类
import java.io.Serializable;

public class MyDate implements Serializable {
    private int year,month,day;
    private int leap;
    public MyDate(){}
    public MyDate(int year,int month,int day) throws DataException {
        setYear(year);
        setMonth(month);
        setDay(day);
    }
    public void setYear(int year)  {
        this.year = year;
        if((this.year % 4 == 0 && this.year % 100 != 0)||(this.year%400 == 0))
            this.leap = 1;
        else this.leap = 0;

    }

    public void setMonth(int month) throws DataException{
        if (month > 12 || month <1)
            throw (new DataException("month is illeage"));
            //System.out.println("month setting error,month has been set to 0");
        else this.month = month;
    }

    public void setDay(int day) throws DataException{
        if(this.month ==2) {
            if (day > 28 + leap || day <= 0)
                throw (new DataException("day is illeage"));
            else this.day = day;
        }
        else if(this.month == 1||this.month == 3||this.month == 5||this.month == 7||this.month == 8||this.month == 10||this.month == 12) {
            if (day > 31 || day <= 0)
                throw (new DataException("day is illeage"));
            else this.day = day;
        }
        else {
            if(day>30||day<=0)
                throw (new DataException("day is illeage"));
            else this.day = day;
        }
    }

    @Override
    public String toString() {
        return year + "/" + month + "/" + day;
    }
}
