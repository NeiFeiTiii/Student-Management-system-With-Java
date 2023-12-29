// 12.24完成
// Created BY DDD
// 操作内容的函数见Operate.java

import java.util.Scanner;
import static java.lang.System.exit;

public class Main {
    public static void main(String[] args) {
        System.out.println("#欢迎来到DDD的学生管理系统\n#开头为‘#’的输出语句均为主系统输出");
        Scanner scan = new Scanner(System.in);
        System.out.println("#首先要初始化系统，请输入初始要输入的人数：");
        int N = scan.nextInt();
        scan.nextLine();
        Students[] stu = new Students[10000];
        for (int i = 0; i < N; i++) {
            Operate.Creat(stu, scan, i);
        }
        int max_score, min_score;
        double ave_score;
        ave_score = 0;
        min_score = max_score = stu[0].getJavaScore();

        while (true) {
            try {
                System.out.println("#下面是学生信息，每行是一个学生（序号1到N）：");
                for (int i = 0; i < N; i++) {
                    System.out.println(stu[i]);
                }

                System.out.println("##################################\n");
                System.out.println("#\t1：修改学生信息\n#\t2：删除学生信息\n#\t3：查找学生信息\n#\t4：增加学生信息\n#\t5：计算平均成绩\n#\t6：输出学生信息到文件\n#\t7：输出到外部网络服务器\n#\t8：退出程序\n");
                System.out.println("##################################");
                System.out.println("#请输入要操作数");
                int index;
                int opera = scan.nextInt();
                scan.nextLine();
                switch (opera) {
                    case 1:
                        System.out.println("#请输入要操作的学生序号（1-" + N + "）：（若为修改、删除外操作可以随便写）");
                        index = scan.nextInt();
                        scan.nextLine();
                        //修改学生信息
                        if(index<=0||index>N){
                            System.out.println("#所输入学生id不合法，请重新输入！");
                            break;
                        }
                        Operate.ChangeStuMessage(stu, index);
                        break;
                    case 2:
                        System.out.println("#请输入要操作的学生序号（1-" + N + "）：（若为修改、删除外操作可以随便写）");
                        index = scan.nextInt();
                        scan.nextLine();
                        // 删除学生信息
                        if(index<=0||index>N){
                            System.out.println("#所输入学生id不合法，请重新输入！");
                            break;
                        }
                        Operate.deleteStudent(stu, index - 1);
                        N--;
                        break;
                    case 3:
                        //查找
                        int SearchId = Operate.SearchStu(stu, N);
                        if (SearchId == -1)
                            System.out.println("#没有找到该学生");
                        else {
                            System.out.println("#找到了该学生，学号为：" + SearchId);
                            System.out.println("#该学生的信息为" + stu[SearchId - 1]);
                        }
                        break;
                    case 4:
                        //增加学生
                        System.out.println("#请输入要增加的学生人数：");
                        int addN = scan.nextInt();
                        Operate.AddStudent(stu, addN, N);
                        N += addN;
                        break;
                    case 5:
                        //计算最大分数，最小分数，平均值
                        ave_score = 0;
                        for (int i = 0; i < N; i++) {
                            ave_score = ave_score + stu[i].getJavaScore();
                            if (stu[i].getJavaScore() < min_score) {
                                min_score = stu[i].getJavaScore();
                            }
                            if (stu[i].getJavaScore() > max_score) {
                                max_score = stu[i].getJavaScore();
                            }
                        }
                        ave_score = ave_score / N;
                        System.out.println("#平均值是" + ave_score + ",最大值是" + max_score + ",最小值是" + min_score + "\n");
                        break;
                    case 6:
                        //保存到文件
                        ave_score = 0;
                        for (int i = 0; i < N; i++) {
                            ave_score = ave_score + stu[i].getJavaScore();
                            if (stu[i].getJavaScore() < min_score) {
                                min_score = stu[i].getJavaScore();
                            }
                            if (stu[i].getJavaScore() > max_score) {
                                max_score = stu[i].getJavaScore();
                            }
                        }
                        ave_score = ave_score / N;
                        Operate.UpToFile(stu, N, ave_score, max_score, min_score);
                        break;
                    case 7:
                        //将Backup内容发送至服务器
                        System.out.println("#先执行储存到文件：");
                        Operate.UpToFile(stu, N, ave_score, max_score, min_score);
                        System.out.println("#文件储存更新完毕，现在开始向服务器端传输内容：");
                        Operate.SocketSend();
                        System.out.println("#向服务器传输完毕！！！");
                        break;
                    case 8:
                        //退出
                        System.out.println("#是否进行保存到文件，再退出？（1：yes，2：no）");
                        int flag = 1;
                        flag = scan.nextInt();
                        if(flag == 1) {
                            Operate.UpToFile(stu, N, ave_score, max_score, min_score);
                            System.out.println("#保存成功");
                        }
                        else System.out.println("#没有进行自动保存，即将退出程序！！！");
                        System.out.println("#退出程序");
                        exit(1);
                    default:
                        System.out.println("#无效的序号，请重新输入。\n");
                        break;
                }
            } catch (Exception e) {
                System.out.println("#"+e+",请重新输入");
                scan.nextLine();
            }
        }
    }
}

//就此java实验，敬一学期的java学习