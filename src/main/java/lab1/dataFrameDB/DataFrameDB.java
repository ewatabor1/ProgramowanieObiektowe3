package main.java.lab1.dataFrameDB;

import main.java.lab1.dataFrame.*;
import main.java.lab1.myExceptions.WrongTypeInColumn;

import javax.xml.crypto.Data;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataFrameDB extends DataFrame {
    private Connection conn = null;
    private Statement stmt = null;
    private ResultSet rs = null;
    private String address,user,password;


    public DataFrameDB (String address, String user, String password){
        this.address=address;
        this.user=user;
        this.password=password;
        connect();
    }
    public DataFrameDB (DataFrame dataFrame, String name, String address, String user, String password,boolean drop) throws SQLException{
        this.address=address;
        this.user=user;
        this.password=password;
        connect();
        String[] types = new String[dataFrame.width()];
        String type;
        for (int i = 0; i < dataFrame.width(); i++) {
            type = dataFrame.get(i).getType().toString();
            if (type.contains("Integer")) types[i] = "INT";
            else if (type.contains("Float")) types[i] = "FLOAT";
            else if (type.contains("Double")) types[i] = "DOUBLE";
            else if (type.contains("Date")) types[i] = "DATE";
            else types[i] = "TEXT";
        }
        createTable(name, dataFrame.getColumnsNames(), types,drop);
        String[] values= new String[dataFrame.width()];
        String query = "SELECT * FROM "+name+";";
        rs = stmt.executeQuery(query);
        ResultSetMetaData rsmd = rs.getMetaData();
        int j=0,temp=1000;
        if (temp>=dataFrame.size()) temp=dataFrame.size();
        for (int m=0;m<1000;m++){
            query="INSERT INTO "+name+" VALUES ";
            for (; j<temp;j++){
                for (int k=0;k<dataFrame.width();k++){
                    values[k]=dataFrame.get(k).elementAtIndex(j).toString();
                }
                query+=valuesToAdd(rsmd,values);
                //System.out.println(j);
                if (j!=temp-1) query+=", \n";
            }
            temp+=1000;
            if (temp>=dataFrame.size()) temp=dataFrame.size();
            query+=";";
            //System.out.println(j+" "+query);
            if(query.equals("INSERT INTO "+name+" VALUES ;")) break;
            stmt.executeUpdate(query);
        }

    }
    public boolean checkIfTableExists(String name) throws SQLException{
        DatabaseMetaData dbm = conn.getMetaData();
        ResultSet rs = dbm.getTables(null, null, name, null);
        if (!rs.next()) return false;
        return true;
    }
    public void createTable (String name, String[] colNames, String[] types,boolean drop) throws SQLException{
        connect();
        stmt = conn.createStatement();
        String query;
        if (drop && checkIfTableExists(name)) {
            query="DROP TABLE "+name+";";
            stmt.executeUpdate(query);
        }
        query = "CREATE TABLE "+name+ "(";
        for (int i=0;i<colNames.length;i++){
            query = query + colNames[i] + " " + types[i];
            if (i!=colNames.length-1) query = query + ", ";
        }
        query = query + ");";
        System.out.println(query);
        stmt.executeUpdate(query);
    }
    public void addRowsToTable (String name, String[] values) throws SQLException{
        //connect();
        String query = "SELECT * FROM "+name+";";
        rs = stmt.executeQuery(query);
        ResultSetMetaData rsmd = rs.getMetaData();
        int width = rsmd.getColumnCount();
        if (values.length==width){
            query = "INSERT INTO "+name+" VALUES ";
            query+=valuesToAdd(rsmd,values);
            query+=";";
            System.out.println(query);
            stmt.executeUpdate(query);
        }
    }
    public String valuesToAdd(ResultSetMetaData rsmd,String...values) throws SQLException{
        String result="(";
        for (int i=0;i<values.length;i++){
            if (rsmd.getColumnTypeName(i+1).contains("CHAR") || rsmd.getColumnTypeName(i+1).contains("DATE")){
                result=result+"'"+values[i]+"'";
            }
            else result=result+values[i];
            if (i!=values.length-1) result=result+", ";
        }
        result+=")";
        return result;
    }

    public void connect() {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            conn =
                    DriverManager.getConnection("jdbc:mysql://"+address,
                            user,password);
                            //mysql.agh.edu.pl/agatatab",
                            //"agatatab", "DMQZxAQGWaQJvHWk");
            //System.out.println(conn);
            stmt = conn.createStatement();

        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public DataFrame getDataFrame (String name) throws SQLException, IllegalAccessException, InvocationTargetException,
            NoSuchMethodException, WrongTypeInColumn, InstantiationException{
        String query = "SELECT * FROM "+name;
        return getDataFrameFromQuery(query);
    }
    public String[] getNameListFromDB (String name) throws SQLException{
        stmt = conn.createStatement();
        String query = "SELECT * FROM "+name+" LIMIT 1";
        rs = stmt.executeQuery(query);
        ResultSetMetaData rsmd = rs.getMetaData();
        int width = rsmd.getColumnCount();
        String [] names = new String[width];
        for (int i=0;i<width;i++){
            names[i] =rsmd.getColumnName(i+1);
        }
        return names;
    }
    public Class<? extends Value>[] getTypesFromDB (String name) throws SQLException{
        stmt = conn.createStatement();
        String query = "SELECT * FROM "+name+" LIMIT 1";
        rs = stmt.executeQuery(query);
        ResultSetMetaData rsmd = rs.getMetaData();
        Class<? extends Value>[] types = new Class[rsmd.getColumnCount()];
        for (int j=0;j<rsmd.getColumnCount();j++){
            String type = rsmd.getColumnTypeName(j+1);
            if (type.contains("INT")) types[j] = IntegerValue.class;
            else if(type.contains("FLOAT")) types[j]= FloatValue.class;
            else if(type.contains("DOUBLE") || type.contains("DECIMAL")) types[j]= DoubleValue.class;
            else if (type.contains("DATE") || type.contains("TIME") ||
                    type.contains("YEAR")) types[j]=DateTimeValue.class;
            else types[j]=StringValue.class;
        }
        return types;
    }
    public DataFrame getDataFrameFromQuery (String query) throws SQLException, IllegalAccessException, InvocationTargetException,
            NoSuchMethodException, WrongTypeInColumn, InstantiationException{

        stmt = conn.createStatement();
        rs = stmt.executeQuery(query);
        ResultSetMetaData rsmd = rs.getMetaData();
        int width = rsmd.getColumnCount();
        String [] names = new String[width];
        for (int i=0;i<width;i++){
            names[i] =rsmd.getColumnName(i+1);
        }
        Class<? extends Value>[] types = new Class[width];
        for (int j=0;j<width;j++){
            String type = rsmd.getColumnTypeName(j+1);
            if (type.contains("INT")) types[j] = IntegerValue.class;
            else if(type.contains("FLOAT")) types[j]= FloatValue.class;
            else if(type.contains("DOUBLE") || type.contains("DECIMAL")) types[j]= DoubleValue.class;
            else if (type.contains("DATE") || type.contains("TIME") ||
                    type.contains("YEAR")) types[j]=DateTimeValue.class;
            else types[j]=StringValue.class;
        }
        DataFrame result=new DataFrame(names,types);
        Value[] values = new Value[width];
        List<Constructor<? extends Value>> constructors = new ArrayList<>(types.length);
        for (int i = 0; i < types.length; i++) {
            constructors.add(types[i].getConstructor(String.class));
        }
        int ind=0;
        while (rs.next()){
            for (int j = 0; j < width; j++) {
                values[j] = constructors.get(j).newInstance(rs.getString(j+1));
                if (!values[j].getSet())
                    throw new WrongTypeInColumn(names[j],ind);
                ind++;
            }
            addRowToDF(width,result,values);
        }
        return result;
    }
    static public DataFrame getDataFrameFromQueryStatic (String address, String user, String password,String query) throws SQLException, IllegalAccessException, InvocationTargetException,
            NoSuchMethodException, WrongTypeInColumn, InstantiationException{
        Connection conn=null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            conn =
                    DriverManager.getConnection("jdbc:mysql://"+address,
                            user,password);
            //mysql.agh.edu.pl/agatatab",
            //"agatatab", "DMQZxAQGWaQJvHWk");
            //System.out.println(conn);
            stmt = conn.createStatement();

        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        } catch (Exception e) {
            e.printStackTrace();
        }
        stmt = conn.createStatement();
        //tring query = "SELECT * FROM "+name;
        rs = stmt.executeQuery(query);
        ResultSetMetaData rsmd = rs.getMetaData();
        int width = rsmd.getColumnCount();
        String [] names = new String[width];
        for (int i=0;i<width;i++){
            names[i] =rsmd.getColumnName(i+1);
        }
        Class<? extends Value>[] types = new Class[width];
        for (int j=0;j<width;j++){
            String type = rsmd.getColumnTypeName(j+1);
            if (type.contains("INT")) types[j] = IntegerValue.class;
            else if(type.contains("FLOAT")) types[j]= FloatValue.class;
            else if(type.contains("DOUBLE") || type.contains("DECIMAL")) types[j]= DoubleValue.class;
            else if (type.contains("DATE") || type.contains("TIME") ||
                    type.contains("YEAR")) types[j]=DateTimeValue.class;
            else types[j]=StringValue.class;
        }
        DataFrame result=new DataFrame(names,types);
        Value[] values = new Value[width];
        List<Constructor<? extends Value>> constructors = new ArrayList<>(types.length);
        for (int i = 0; i < types.length; i++) {
            constructors.add(types[i].getConstructor(String.class));
        }
        int ind=0;
        while (rs.next()){
            for (int j = 0; j < width; j++) {
                values[j] = constructors.get(j).newInstance(rs.getString(j+1));
                if (!values[j].getSet())
                    throw new WrongTypeInColumn(names[j],ind);
                ind++;
            }
            for (int i=0;i<values.length;i++){
                if(!result.get(i).checkElement(values[i])) {
                    int a=result.get(0).size();
                    if(i==0){
                        a=-1;
                        if(a<0) a=0;
                    }
                    throw new WrongTypeInColumn(names[i],a);
                }
            }
            for (int j=0;j<values.length;j++){
                result.get(j).addElement(values[j]);
            }
        }
        return result;
    }

    public DataFrame addRowToDF(int width,DataFrame result,Value...values) throws WrongTypeInColumn {
        if(width!=values.length){
            System.out.println("Nie podano wszystkich argumentów!");
            return result;
        }
        for (int i=0;i<values.length;i++){
            if(!result.get(i).checkElement(values[i])) {
                int a=result.get(0).size();
                if(i==0){
                    a=-1;
                    if(a<0) a=0;
                }
                throw new WrongTypeInColumn(get(i).getName(),a);
            }
        }
        for (int j=0;j<values.length;j++){
            result.get(j).addElement(values[j]);
        }
        return result;
    }
    public void dropTable (String name) throws SQLException{
        stmt = conn.createStatement();
        String query = "DROP TABLE "+name+";";
        stmt.executeUpdate(query);
    }
    public void getMin (String name) throws SQLException{
        String result="";
        String[] colNames = getNameListFromDB(name);
        String query="SELECT ";
        stmt = conn.createStatement();
        
        for (int i=0;i<colNames.length;i++){
            query+="MIN("+colNames[i]+")";
            if (i!=colNames.length-1) query+=", ";
        }
        query+=" FROM "+name+";";
        System.out.println(query);
        rs = stmt.executeQuery(query);
    }
    public String[] groupby (String name, String...strings){
        String[] result = null;



        return result;
    }
}
