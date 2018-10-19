package edu.studentmangment.service;

import edu.studentmangment.StudentTemplet;

import java.util.HashMap;
import java.util.HashSet;

/**
 * 学生服务类，单例实现
 *
 * @author Diaz
 */
public class StudentService {
    private static StudentService instance;

    private HashMap<Integer, StudentTemplet> students;

    private HashSet<String> classes;

    /**
     * 获取本类的唯一实例
     *
     * @return 本类的唯一实例
     */
    public static synchronized StudentService getInstance() {
        if (null == instance) {
            instance = new StudentService();
        }
        return instance;
    }

    /**
     * 构造函数
     */
    private StudentService() {
        students = DataService.getInstance().getStudents();
        classes = DataService.getInstance().getClasses();
    }

    public boolean add(StudentTemplet student) {
        if (!students.containsKey(student.getId())) {
            students.put(student.getId(), student);
            classes.add(student.getClassID());
            return true;
        } else {
            return false;
        }
    }

    /**
     * 删除学生信息
     *
     * @param id 要删除的学生的学号
     * @return 删除是否成功
     */
    public boolean remove(Integer id) {
        if (students.containsKey(id)) {
            students.remove(id);
            return true;
        } else {
            return false;
        }
    }

    /**
     * 更新学生信息
     *
     * @param student 更新的学生信息
     * @return 更新是否成功
     */
    public boolean update(StudentTemplet student) {
        if (students.containsKey(student.getId())) {
            students.put(student.getId(), student);
            return true;
        } else {
            return false;
        }
    }

    /**
     * 获取学生信息
     *
     * @param id 要查找的学生的学号
     * @return 查找到的学生信息，如何没找到返回null
     */
    public StudentTemplet get(Integer id) {
        return students.get(id);
    }
}
