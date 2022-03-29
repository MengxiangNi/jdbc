package com.atguigu.connection;

import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionTest {
    //方式一
   @Test
    public void testConnection1() throws SQLException {
        Driver driver=new com.mysql.cj.jdbc.Driver();
        String url="jdbc:mysql://localhost:3306/test?autoReconnect=true&failOverReadOnly=false";
       Properties info=new Properties();
       info.setProperty("user","root");
       info.setProperty("password","root1234");
       Connection conn=driver.connect(url,info);

        System.out.println(conn);
    }
    //方式二
    @Test
    public void testConnection2() throws Exception {
       //1.获取Driver实现类对象，使用反射
        Class clazz=Class.forName("com.mysql.cj.jdbc.Driver");
        Driver dirver=(Driver)clazz.newInstance();
        String url="jdbc:mysql://localhost:3306/test";
        Properties info=new Properties();
        info.setProperty("user","root");
        info.setProperty("password","root1234");
        Connection conn=dirver.connect(url,info);
        System.out.println(conn);
   }
   //方式三
   @Test
    public void testConnection3() throws Exception, IllegalAccessException {
       //获取Driver实现类的对象
       Class clazz=Class.forName("com.mysql.cj.jdbc.Driver");
       Driver driver=(Driver)clazz.newInstance();
       //注册驱动
       DriverManager.registerDriver(driver);
       String url="jdbc:mysql://localhost:3306/test";
       String user="root";
       String password="root1234";
       //获取连接
      Connection conn= DriverManager.getConnection(url,user,password);
       System.out.println(conn);
   }
   //方式四
    @Test
    public void testConnection4() throws Exception {
        //获取Driver实现类的对象
       Class.forName("com.mysql.cj.jdbc.Driver");
       //相较于方式三，可以省略如下操作,因为在mysql的Driver实现类中已经声明
//        Driver driver=(Driver)clazz.newInstance();
//        //注册驱动
//        DriverManager.registerDriver(driver);
        String url="jdbc:mysql://localhost:3306/test";
        String user="root";
        String password="root1234";
        //获取连接
        Connection conn= DriverManager.getConnection(url,user,password);
        System.out.println(conn);
    }
    //方式五:将数据库需要的四个基本信息声明在配置文件中，通过读取配置文件的方式，获取连接
    @Test
    public void testConnection5() throws Exception {
       InputStream is= ConnectionTest.class.getClassLoader().getResourceAsStream("jdbc.properties");
        Properties pros=new Properties();
        pros.load(is);
        String user= pros.getProperty("user");
        String password= pros.getProperty("password");
        String url= pros.getProperty("url");
        String driverClass= pros.getProperty("driverClass");
        Class.forName(driverClass);
        Connection conn = DriverManager.getConnection(url, user, password);
//        Connection conn=DriverManager.getConnection(url,password,user);
            System.out.println(conn);

    }
}
