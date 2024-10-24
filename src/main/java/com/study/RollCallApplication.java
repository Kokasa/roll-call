   package com.study;

   import com.study.controller.StudentController;
   import com.study.controller.MyClassController;
   import com.study.controller.GroupController;
   import com.study.service.StudentService;
   import com.study.service.MyClassService;
   import com.study.service.GroupService;
   import com.study.service.imp.StudentServiceImp; // 导入实现类
   import com.study.service.imp.MyClassServiceImp; // 导入实现类
   import com.study.service.imp.GroupServiceImp;   // 导入实现类
   import java.util.Scanner;

   public class RollCallApplication {

       public static void main(String[] args) {
           // 创建 Scanner 对象
           Scanner scanner = new Scanner(System.in);

           // 创建 Service 对象
           StudentService studentService = new StudentServiceImp(); // 使用实现类
           MyClassService classService = new MyClassServiceImp();   // 使用实现类
           GroupService groupService = new GroupServiceImp();       // 使用实现类

           // 创建 Controller 对象
           StudentController studentController = new StudentController(studentService, scanner);
           MyClassController myClassController = new MyClassController(classService, scanner);
           GroupController groupController = new GroupController(groupService, scanner);

           while (true) {
               // 显示菜单
               System.out.println("\n请选择操作:");
               System.out.println("1. 学生管理");
               System.out.println("2. 班级管理");
               System.out.println("3. 小组管理");
               System.out.println("4. 退出");

               int choice = scanner.nextInt();
               switch (choice) {
                   case 1:
                       handleStudentOperations(scanner, studentController);
                       break;
                   case 2:
                       handleClassOperations(scanner, myClassController);
                       break;
                   case 3:
                       handleGroupOperations(scanner, groupController);
                       break;
                   case 4:
                       System.out.println("退出程序");
                       scanner.close();
                       System.exit(0);
                   default:
                       System.out.println("无效的选择，请重试。");
               }
           }
       }

       private static void handleStudentOperations(Scanner scanner, StudentController studentController) {
           System.out.println("\n请选择学生操作:");
           System.out.println("1. 新增学生");
           System.out.println("2. 删除学生");
           System.out.println("3. 修改学生");
           System.out.println("4. 设置学生班级");
           System.out.println("5. 按编号查询学生");
           System.out.println("6. 查询随机学生");
           System.out.println("7. 查询所有学生");

           int choice = scanner.nextInt();
           switch (choice) {
               case 1:
                   studentController.addStudent();
                   break;
               case 2:
                   studentController.deleteStudent();
                   break;
               case 3:
                   studentController.updateStudent();
                   break;
               case 4:
                   studentController.setStudentClass();
                   break;
               case 5:
                   studentController.getStudentById();
                   break;
               case 6:
                   studentController.getRandomStudent();
                   break;
               case 7:
                   studentController.getAllStudent();
                   break;
               default:
                   System.out.println("无效的选择，请重试。");
           }
       }

       private static void handleClassOperations(Scanner scanner, MyClassController myClassController) {
           System.out.println("\n请选择班级操作:");
           System.out.println("1. 新增班级");
           System.out.println("2. 删除班级");
           System.out.println("3. 修改班级");
           System.out.println("4. 展示班级内学生列表");
           System.out.println("5. 按编号查询班级");
           System.out.println("6. 查询随机班级");
           System.out.println("7. 查询所有班级");

           int choice = scanner.nextInt();
           switch (choice) {
               case 1:
                   myClassController.addClass();
                   break;
               case 2:
                   myClassController.deleteClass();
                   break;
               case 3:
                   myClassController.updateClass();
                   break;
               case 4:
                   myClassController.getAllStudentInClass();
                   break;
               case 5:
                   myClassController.getClassById();
                   break;
               case 6:
                   myClassController.getRandomClass();
                   break;
               case 7:
                   myClassController.getAllClass();
                   break;
               default:
                   System.out.println("无效的选择，请重试。");
           }
       }

       private static void handleGroupOperations(Scanner scanner, GroupController groupController) {
           System.out.println("\n请选择小组操作:");
           System.out.println("1. 新增小组");
           System.out.println("2. 删除小组");
           System.out.println("3. 修改小组");
           System.out.println("4. 展示小组内所有成员");
           System.out.println("5. 添加学生到小组");
           System.out.println("6. 按编号查询小组");
           System.out.println("7. 查询随机小组");
           System.out.println("8. 查询所有小组");

           int choice = scanner.nextInt();
           switch (choice) {
               case 1:
                   groupController.addGroup();
                   break;
               case 2:
                   groupController.deleteGroup();
                   break;
               case 3:
                   groupController.updateGroup();
                   break;
               case 4:
                   groupController.getAllStudentInGroup();
                   break;
               case 5:
                   groupController.addStudent();
                   break;
               case 6:
                   groupController.getGroupById();
                   break;
               case 7:
                   groupController.getRandomGroup();
                   break;
               case 8:
                   groupController.getAllGroup();
                   break;
               default:
                   System.out.println("无效的选择，请重试。");
           }
       }
   }
   