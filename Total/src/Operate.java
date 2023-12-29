import java.io.*;
import java.net.Socket;
import java.util.Scanner;

import static java.lang.System.exit;

public class Operate {

    //修改学生信息函数
    public static void ChangeStuMessage(Students[] stu, int index) {
        Scanner scan = new Scanner(System.in);
        System.out.println("以下设置模块，均为当输入-1时保存退出。");
        while(true) {
            System.out.println("请输入新的姓名：");
            String NewName = scan.nextLine();
            if(NewName.equals("-1"))
                break;
            stu[index - 1].setName(NewName);
        }
        while(true) {
            try {
                System.out.println("请输入新的生日：（年 月 日）");
                int year,month,day;
                year = scan.nextInt();
                if(year == -1)
                    break;
                month = scan.nextInt();
                day = scan.nextInt();
                MyDate mydate = new MyDate(year,month,day);
                stu[index - 1].setBirth(mydate);
                scan.nextLine();
            }
            catch (Exception e) {
                System.out.println(e+"内容不合法，请重新输入");
            }
        }
        while(true) {
            try {
                int score;
                System.out.println("请输入新的Java课成绩：");
                score = scan.nextInt();
                if(score == -1)
                    break;
                stu[index - 1].setJavaScore(score);
                scan.nextLine();
            }
            catch (Exception e){
                System.out.println(e+"内容不合法，请重新输入");
            }
        }
    }

    //查找学生函数
    public static int SearchStu(Students[] stu, int N) {
        Scanner scan = new Scanner(System.in);
        System.out.println("请输入你要查找的学号（因为可能会有重名情况）");
        String id = scan.nextLine();
        for (int i = 0; i < N; i++) {
            if (id.equals(stu[i].getStuID())) {
                return i + 1;
            }
        }
        return -1;
    }

    //删除学生函数
    public static void deleteStudent(Students[] stu, int index) {
        for (int i = index; i < stu.length - 1; i++) {
            stu[i] = stu[i + 1];
        }
        stu[stu.length - 1] = null;
    }

    //增加学生函数
    public static void AddStudent(Students[] stu, int adders, int N){
        Scanner scan = new Scanner(System.in);
        for (int i = N; i < adders+N; i++) {
            Creat(stu, scan, i);
        }
    }
    //创建学生
    public static void Creat(Students[] stu, Scanner scan, int i) {
        stu[i] = new Students();
        System.out.println("请输入姓名：");
        stu[i].setName(scan.nextLine());
        stu[i].setStuID();
        while(true) {
            try {
                System.out.println("请输入生日：（年 月 日）");
                MyDate mydate = new MyDate(scan.nextInt(),scan.nextInt(),scan.nextInt());
                stu[i].setBirth(mydate);
                scan.nextLine();
                break;
            } catch (Exception e) {
                System.out.println(e+",请重新输入");
                scan.nextLine();
            }
        }
        while(true){
            try {
                System.out.println("请输入其java课成绩：");
                stu[i].setJavaScore(scan.nextInt());
                scan.nextLine();
                break;
            } catch (Exception e) {
                System.out.println(e+",请重新输入");
                scan.nextLine();
            }
        }
    }

    //输出到文件函数
    public static void UpToFile(Students[] stu, int N,double ave_score,int max_score,int min_score){
        try{
            FileOutputStream fout = new FileOutputStream("StuInfo.txt");
            FileOutputStream fcopy = new FileOutputStream("Backup.txt");
            FileInputStream fcopyread = new FileInputStream("Backup.txt");
            FileInputStream fin = new FileInputStream("StuInfo.txt");
            ObjectOutputStream ocopy = new ObjectOutputStream(fcopy);
            ObjectOutputStream oout = new ObjectOutputStream(fout);
            ObjectInputStream oin = new ObjectInputStream(fin);
            ObjectInputStream ocopyread = new ObjectInputStream(fcopyread);
            for (int i = 0; i < N; i++) {
                oout.writeObject(stu[i]);
            }
            oout.writeChars("Java课成绩的平均值是"+ave_score+",最大值是"+max_score+",最小值是"+min_score+"\n");
            oout.close();
            fout.close();
            System.out.println("写入的文件内容是：");
            while (true){
                try{
                    Object n = oin.readObject();
                    ocopy.writeObject(n);
                    System.out.println(n);
                }
                catch (Exception e){
                    break;
                }
            }
            while (true){
                try{
                    char n = oin.readChar();
                    ocopy.writeChar(n);
                    System.out.print(n);
                }
                catch (Exception e){
                    break;
                }
            }
            oin.close();
            ocopy.close();
            System.out.println();
            System.out.println("复制好的文件中的内容是：");
            while (true){
                try{
                    Object n = ocopyread.readObject();
                    System.out.println(n);
                }
                catch (Exception e){
                    break;
                }
            }
            while (true){
                try{
                    char n = ocopyread.readChar();
                    System.out.print(n);
                }
                catch (Exception e){
                    break;
                }
            }
            fcopyread.close();
            ocopyread.close();
        } catch(Exception e){
            System.out.println(e);
        }
        System.out.println();
    }
    //通过Socket传输给网络上的服务器
    public static void SocketSend(){
        try {
            Scanner scan = new Scanner(System.in);
            Socket socket = new Socket("localhost", 8888);      // 连接到服务器
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            FileInputStream fcopyread = new FileInputStream("Backup.txt");
            ObjectInputStream ocopyread = new ObjectInputStream(fcopyread);
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            FileReader freader = new FileReader("Backup.txt");
            BufferedReader fileReader = new BufferedReader(freader);

            String res = in.readLine();
            System.out.println(res);
            if(!res.equals("Hi，欢迎连接到服务器上!请输入密码：")){
                System.out.println("连接到了错误的服务器上");
                socket.close();
                return;
            }
            while(true){
                String s = scan.nextLine();
                out.println(s);
                String outl = in.readLine();
                if(outl.equals("密码验证通过,下面可开始发送消息：")) {
                    System.out.println(outl);
                    break;
                }
                if(outl.equals("输入的密码不正确，请重新输入！")){
                    System.out.println("用户密码输入错误，请重新输入！");
                }
                if(outl.equals("密码错误次数多于 3 次，退出登录！")){
                    System.out.println("连续三次输入错误密码，已被强制断开！");
                    return;
                }
            }
            while(true) {
                try {
                    Object n = ocopyread.readObject();
                    oos.writeObject(n);
                }
                catch (Exception e){
                    Object n = null;
                    oos.writeObject(n);
                    break;
                }
            }
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }
}
