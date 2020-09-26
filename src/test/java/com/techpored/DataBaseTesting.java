package com.techpored;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.*;

public class DataBaseTesting {

    //Database url
    String url = "jdbc:mysql://107.182.225.121:3306/LibraryMgmt";

    // kullanıcı adi
    String username = "techpro";

    //şifre
    String password = "tchpr2020";

    //Connection, Statement, ResultSet objelerini oluşturalım

    Connection connection;
    Statement statement;
    ResultSet resultSet;

    @Before
    public void setUp() throws SQLException {
        //getConnetion methodu ile databasee bağlanıyoruz
        connection= DriverManager.getConnection(url,username,password);
        //createStatement methoduyla statement objesi oluşturuyoruz. Bu objeyi kullanarak resultTest oluşturacağız
        statement=connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

    }

    @Test
    public void Test1() throws SQLException {

        resultSet=statement.executeQuery("SELECT*FROM Book");
        //ilk satırı atlıyoruz
        resultSet.next();
        String deger1=resultSet.getString("Bookname");
        System.out.println("Değer1: "+deger1);

        //TC_02_BookName deki tum degerleri yazdir
        int rowSayisi=1;//şuan ilk satırda olduğumuzdan 1 den başlıyoruz
        while (resultSet.next()){
            Object deger2=resultSet.getObject("BookName");
            System.out.println(deger2.toString());//toString() kullanmak zorunda değiliz.
            rowSayisi++;
        }
        System.out.println(rowSayisi);

        //TC_03_toplam 14 satirin olup olmadigini test et
        Assert.assertEquals(14, rowSayisi);

        //TC_04  5. degerin JAVA olup olmadigini test et
        resultSet.absolute(5);//5.satıra git
        //5.satırdaki değeri al
        String deger5=resultSet.getString("BookName");
        Assert.assertEquals("JAVA", deger5);//FAILED.BUG BULDUK**Datatyı kesinlikle değiştirmmiyoruz!

    }
    @Test
    public void Test2() throws SQLException {
        resultSet=statement.executeQuery("SELECT*FROM Book");

        //TC_05_son degerin UIPath olup olmadigini test et
        resultSet.last();//dinamik kod ile son satıra gittik
        String degerSon=resultSet.getString("BookName");
        Assert.assertEquals("UIPath",degerSon);

        //TC_06_ilk satirdaki degerin SQL olup olmadigii test et
        resultSet.first();//ilk satıra git
        String degerIlk=resultSet.getString("BookName");
        Assert.assertEquals("SQL",degerIlk);
        /*
        String actualResult=resultSet.getString("BookName");
        String expectedResult="SQL";
        Assert.assertEquals(expectedResult,actualResult);
         */

    }

    @Test
    public void Test3() throws SQLException {
        //MetaData: Datayla ilgili bilgiler(data ile ilgili datalar)
        DatabaseMetaData dbMetaData=connection.getMetaData();
        System.out.println(" USERNAME: "+dbMetaData.getUserName());
        System.out.println("DataBase Name: "+dbMetaData.getDatabaseProductName());
        System.out.println("DataBase Version :"+dbMetaData.getDatabaseProductVersion());


    }


}
