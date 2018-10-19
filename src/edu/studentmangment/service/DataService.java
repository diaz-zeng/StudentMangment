package edu.studentmangment.service;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import edu.studentmangment.StudentTemplet;

import java.io.*;
import java.util.HashMap;
import java.util.HashSet;

/**
 * 本类提供了对数据的维护与备份功能，本类为单例实现，继承自java.lang.Thread
 *
 * @author Diaz
 */
public class DataService extends Thread {

    private HashMap<Integer, StudentTemplet> students;
    private HashSet<String> classes;
    private static DataService instance;

    private static final File classFile = new File("./ClassProfile.json");
    private static final File studentFile = new File("./StudentProfile.json");

    /**
     * 获取本类的唯一实例
     *
     * @return 本类的唯一实例
     */
    protected static synchronized DataService getInstance() {
        if (null == instance) {
            instance = new DataService();
        }
        return instance;
    }

    /**
     * 构造函数
     */
    private DataService() {
        if (loadProfile())
            System.out.println("加载数据成功");
        else

            System.out.println("加载数据失败");
        this.start();
    }

    /**
     * 将数据备份至JSON文件
     *
     * @return 备份是否成功
     */
    private synchronized boolean backup() {

        try (FileOutputStream studentStream = new FileOutputStream(studentFile, false);
             FileOutputStream classStream = new FileOutputStream(classFile, false)) {
            if (!studentFile.exists())
                studentFile.createNewFile();
            String sData = JSONObject.toJSONString(students);
            studentStream.write(sData.getBytes("UTF-8"));
            if (!classFile.exists())
                classFile.createNewFile();
            String cData = JSONArray.toJSONString(classes);
            classStream.write(cData.getBytes("UTF-8"));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 线程运行时方法
     */
    @Override
    public void run() {
        while (true) {
            try {
                sleep(500);
                if (!dataMaintenance()) {
                    throw new Exception("数据维护失败");
                }
                sleep(500);
                if (!backup()) {
                    throw new Exception("数据备份失败");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 加载JSON文件中的数据到内存
     *
     * @return
     */
    private boolean loadProfile() {
        try (FileInputStream classStream = new FileInputStream(classFile);
             FileInputStream studentStream = new FileInputStream(studentFile)) {
            byte[] sBuffer = new byte[studentStream.available()];
            byte[] cBuffer = new byte[classStream.available()];
            studentStream.read(sBuffer);
            classStream.read(cBuffer);
            String studentString = new String(sBuffer, "UTF-8");
            String classString = new String(cBuffer, "UTF-8");
            students = (HashMap<Integer, StudentTemplet>) JSONObject.parseObject(studentString, new TypeReference<HashMap<Integer, StudentTemplet>>() {
            });
            classes = (HashSet<String>) JSONObject.parseObject(classString, new TypeReference<HashSet<String>>() {
            });
            return true;
        } catch (Exception e) {
            students = new HashMap<>();
            classes = new HashSet<>();
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 请求所有的学生数据
     *
     * @return 所有学生的数据
     */
    public synchronized HashMap<Integer, StudentTemplet> getStudents() {
        return students;
    }

    /**
     * 获取所有的班级信息
     *
     * @return 所有的班级信息
     */
    public synchronized HashSet<String> getClasses() {
        return classes;
    }

    /**
     * 维护数据结构，删除没有学生的班级
     *
     * @return 是否维护成功
     */
    private synchronized boolean dataMaintenance() {
        try {
            HashSet<String> temp = new HashSet<>();
            students.values().forEach(x -> temp.add(x.getClassID()));
            classes = temp;
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
