// 配套的测试用服务器代码

import java.io.*;
import java.net.*;
import java.util.Scanner;
import static java.lang.System.exit;

public class TestServer {
    public static void main(String[] args) {
        try {
            Scanner scan = new Scanner(System.in);
            ServerSocket serverSocket = new ServerSocket(8888);

            System.out.println("请设置一个密码：");
            String Password = scan.nextLine();

            System.out.println("服务器已启动，等待客户端连接...");
            Socket socket = serverSocket.accept();

            System.out.println("客户端已连接，IP地址为：" + socket.getInetAddress().getHostAddress());
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            out.println("Hi，欢迎连接到服务器上!请输入密码：");
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            int i = 0;
            while(true){
                String message = in.readLine();
                if(i >= 2){
                    out.println("密码错误次数多于 3 次，退出登录！");
                    System.out.println("连续三次输入错误密码，已强制断开与用户的连接！");
                    socket.close();
                    serverSocket.close();
                    exit(1);
                }
                if(message.equals(Password)) {
                    out.println("密码验证通过,下面可开始发送消息：");
                    System.out.println("密码验证通过,下面可开始发送消息：");
                    break;
                }
                else {
                    out.println("输入的密码不正确，请重新输入！");
                    i++;
                }
            }
            try {
                while(true){
                    Object n = ois.readObject();
                    if(n == null)
                        break;
                    System.out.println(n);
                }
                in.close();
            }
            catch (Exception e){
                System.out.println(e);
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}