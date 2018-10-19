package edu.studentmangment;

/**
 * 学生信息模板对象
 *
 * @author Diaz
 */
public class StudentTemplet {
    private String name;
    private Integer age, id;
    private String classID;
    private boolean gander;

    /**
     * 构造函数
     *
     * @param name    姓名
     * @param age     年龄
     * @param id      学号
     * @param classID 班级
     * @param gander  性别（男生为true，女生为false）
     */
    public StudentTemplet(String name, Integer age, Integer id, String classID, boolean gander) {
        this.name = name;
        this.age = age;
        this.id = id;
        this.classID = classID;
        this.gander = gander;
    }

    public String getName() {
        return name;
    }

    public Integer getAge() {
        return age;
    }

    public Integer getId() {
        return id;
    }

    public String getClassID() {
        return classID;
    }

    public boolean isGander() {
        return gander;
    }

    @Override
    public String toString() {
        return "学号：" + id + " 姓名：" + name + " 年龄：" + age + " 性别：" + (gander ? "男" : "女") + " 班级：" + classID;
    }
}
