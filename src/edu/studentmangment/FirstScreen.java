package edu.studentmangment;

import edu.studentmangment.service.StudentService;

import java.text.ParseException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class FirstScreen {
    private StudentService service;

    Scanner scanner;

    /**
     * 构造函数
     */
    public FirstScreen() {
        service = StudentService.getInstance();
        scanner = new Scanner(System.in);
    }

    /**
     * 启动方法
     */
    public void start() {
        while (true) {
            System.out.println("---------------------");
            System.out.println("-----学生管理系统-----");
            System.out.println("------1.添加学生------");
            System.out.println("------2.修改学生------");
            System.out.println("------3.查找学生------");
            System.out.println("------4.删除学生------");
            System.out.println("------5.退出系统------");
            System.out.println("----------------------");
            System.out.print("请选择：");
            int userInput;
            try {
                userInput = scanner.nextInt();
            } catch (Exception e) {
                System.out.println("输入错误，请重试");
                continue;
            }
            scanner.nextLine();
            try {
                switch (userInput) {
                    case 1:
                        addStudent();
                        break;
                    case 2:
                        updateStudent();
                    case 3:
                        getStudent();
                        break;
                    case 4:
                        removeStudent();
                    case 5: {
                        System.out.println("程序退出");
                        System.exit(0);
                    }
                }
            } catch (InputMismatchException e) {
                System.out.println("输入有误，请重试！");
                continue;
            }
        }
    }

    /**
     * 添加学生信息
     */
    private void addStudent() {
        while (true) {
            System.out.print("请输入学生信息（学号/姓名/性别/年龄/班级）：");
            String userInput = scanner.nextLine();
            try {
                String[] datas = userInput.split("/");
                boolean gander;
                if ("男".equals(datas[2]) || "女".equals(datas[2])) {
                    gander = datas[2].equals("男");
                    StudentTemplet student = new StudentTemplet(datas[1], Integer.parseInt(datas[3]), Integer.parseInt(datas[0]), datas[4], gander);
                    if (service.add(student)) {
                        System.out.println("添加成功");
                        System.out.println(student);
                        break;
                    } else {
                        System.out.println("该学号已注册,请重试");
                        continue;
                    }
                } else {
                    System.out.println("性别错误，请重新输入");
                    continue;
                }
            } catch (Exception e) {
                System.out.println("信息输入错误，请重新输入");
                continue;
            }
        }
    }

    /**
     * 更新学生信息
     */
    private void updateStudent() {
        while (true) {
            System.out.print("请输入更新的学生信息（学号/姓名/性别/年龄/班级）：");
            String userInput = scanner.nextLine();
            try {
                String[] datas = userInput.split("/");
                boolean gander;
                if ("男".equals(datas[2]) || "女".equals(datas[2])) {
                    gander = datas[2].equals("男");
                    StudentTemplet student = new StudentTemplet(datas[1], Integer.parseInt(datas[2]), Integer.parseInt(datas[0]), datas[4], gander);
                    if (service.update(student)) {
                        System.out.println("修改成功");
                        System.out.println(student);
                        break;
                    } else {
                        System.out.println("该学号不存在");
                        continue;
                    }
                } else {
                    System.out.println("性别错误，请重新输入");
                    continue;
                }
            } catch (Exception e) {
                System.out.println("信息输入错误，请重新输入");
                continue;
            }
        }
    }

    /**
     * 获取学生信息
     */
    private void getStudent() {
        while (true) {
            System.out.print("请输入学号以查询学生信息：");
            try {
                int userInput = scanner.nextInt();
                scanner.nextLine();
                if (null != service.get(userInput)) {
                    System.out.println(service.get(userInput));
                    break;
                } else {
                    System.out.print("学生信息不存在");
                }

            } catch (Exception e) {
                System.out.print("输入错误，请重试");
                continue;
            }
        }
    }

    /**
     * 删除学生信息
     */
    private void removeStudent() {
        loop:
        while (true) {
            try {
                System.out.print("请输入学号以查找要删除的学生信息(#end退出)");
                String sUserInput = scanner.nextLine();
                if ("#end".equals(sUserInput))
                    System.exit(0);
                int userInput = Integer.parseInt(sUserInput);
                if (!(null == service.get(userInput))) {
                    System.out.println(service.get(userInput));
                    System.out.print("是否删除(y/n)?");
                    switch (scanner.nextLine()) {
                        case "y": {

                            if (service.remove(userInput)) {
                                System.out.println("删除成功");
                            } else {
                                System.out.println("删除失败，请重试");
                            }
                            break;
                        }
                        case "n": {

                            System.out.println("操作取消");
                            continue loop;
                        }
                        default: {

                            System.out.println("输入错误，操作取消");
                            continue loop;
                        }
                    }
                } else {
                    System.out.println("学生信息不存在，请重试");
                }
            } catch (Exception e) {
                System.out.println("输入错误，请重试");
                continue;
            }
        }
    }
}
