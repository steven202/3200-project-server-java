package com.example.shoppingcart.services;


import com.example.shoppingcart.models.Record;

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;
interface Procedure{
    ArrayList<String> execute(String cmd) throws SQLException;
    ArrayList<String> print() throws SQLException;
}
//partial code is from https://www.xyzws.com/javafaq/how-to-use-jdbc-java-to-dynamically-create-a-stored-procedure/171
public class JDBCCreateTable {
    private static Scanner scan = new Scanner(System.in);
    private static boolean DEBUG = false;
    private static String username;
    private static String dbName = "online_shopping";
    private static String password;
    private static Connection con = null;
    private static Statement stmt = null;
    private static final String DBDRIVER = "org.gjt.mm.mysql.Driver";
    private static Procedure procedure=null;
    private static ArrayList<String> messages=new ArrayList<>();
    static Connection getConnection() {

        String DBURL =
                "jdbc:mysql://localhost:3306/" +
                        dbName +
                        "?user=" + username +
                        "&password=" + password + "&useUnicode=true&characterEncoding=UTF-8";
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(DBURL);
            System.out.println("Successfully connected to database " + dbName + ".");
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
        return connection;
    }

    private static Boolean tryConnect() {
        String DBURL = "jdbc:mysql://localhost:3306/" +
                        dbName +
                        "?user=" + username +
                        "&password=" + password + "&useUnicode=true&characterEncoding=UTF-8";
        try {
            Connection c = DriverManager.getConnection(DBURL);
            c.close();
            con = getConnection();
            stmt = con.createStatement();
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    private static void finalStep() {
        if (stmt != null) {
            try {
                stmt.close();
                System.out.println("Statement closed.");
            } catch (SQLException e) {
                System.err.println("SQLException: " + e.getMessage());

            }
        }
        if (con != null) {
            try {
                con.close();
                System.out.println("Connection closed.");
            } catch (SQLException e) {
                System.err.println("SQLException: " + e.getMessage());
            }
        }
    }

    private static ResultSet executeCommand(String cmd) {
        ResultSet result = null;
        try {
            result = stmt.executeQuery(cmd);
        } catch (SQLException ex) {
            System.err.println("SQLException: " + ex.getMessage());
            System.exit(-1);
        }
        return result;
    }

    private static boolean error_message() {
        System.out.println("Error!");
        return true;
    }

    // copy from https://coderwall.com/p/609ppa/printing-the-result-of-resultset
    private static ArrayList<String> print_res(ResultSet rs) throws SQLException {
        ArrayList<String> ans=new ArrayList<>();
        ResultSetMetaData rsmd = rs.getMetaData();
        int columnsNumber = rsmd.getColumnCount();
        while (rs.next()) {
            StringBuilder str = new StringBuilder();
            for (int i = 1; i <= columnsNumber; i++) {
                if (i == 1) str.append("(");
                if (i > 1) str.append(", ");
                String columnValue = rs.getString(i);
                str.append(rsmd.getColumnName(i) + ": " + columnValue);
                if (i == columnsNumber) str.append(")");
            }
            ans.add(str.toString());
        }
        return ans;
    }


    ///////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////
    

    private static class read_customer implements Procedure {


        @Override
        public ArrayList<String> execute(String cmd) throws SQLException {
            ResultSet res=executeCommand("call read_customer();");
            ArrayList ans = print_res(res);
            ans.add(0, "here are all the customers:");
            return ans;
        }



        @Override
        public ArrayList<String> print() throws SQLException {
            messages.clear();
            messages.add("you don't need to enter anything, just click 'submit' button.");
            return messages;
        }
    }
    private static class read_seller implements Procedure {


        @Override
        public ArrayList<String> execute(String cmd) throws SQLException {
            ResultSet res=executeCommand("call read_seller();");
            ArrayList ans = print_res(res);
            ans.add(0, "here are all the sellers:");
            return ans;
        }



        @Override
        public ArrayList<String> print() throws SQLException {
            messages.clear();
            messages.add("you don't need to enter anything, just click 'submit' button.");
            return messages;
        }
    }
    private static class read_order implements Procedure {


        @Override
        public ArrayList<String> execute(String cmd) throws SQLException {
            ResultSet res=executeCommand("call read_order();");
            ArrayList ans = print_res(res);
            ans.add(0, "here are all the orders:");
            return ans;
        }



        @Override
        public ArrayList<String> print() throws SQLException {
            messages.clear();
            messages.add("you don't need to enter anything, just click 'submit' button.");
            return messages;
        }
    }

    private static class read_product implements Procedure {


        @Override
        public ArrayList<String> execute(String cmd) throws SQLException {
            ResultSet res=executeCommand("call read_product();");
            ArrayList ans = print_res(res);
            ans.add(0, "here are all the products:");
            return ans;
        }



        @Override
        public ArrayList<String> print() throws SQLException {
            messages.clear();
            messages.add("you don't need to enter anything, just click 'submit' button.");
            return messages;
        }
    }

    private static class read_warehouse implements Procedure {


        @Override
        public ArrayList<String> execute(String cmd) throws SQLException {
            ResultSet res=executeCommand("call read_warehouse();");
            ArrayList ans = print_res(res);
            ans.add(0, "here are all the warehouses:");
            return ans;
        }



        @Override
        public ArrayList<String> print() throws SQLException {
            messages.clear();
            messages.add("you don't need to enter anything, just click 'submit' button.");
            return messages;
        }
    }


    private static class create_customer implements Procedure {


        @Override
        public ArrayList<String> execute(String cmd) throws SQLException {
            Scanner scan= new Scanner(cmd);
            String name = scan.nextLine();
            String tel = scan.nextLine();
            String addr = scan.nextLine();
            String command="call create_customer(\"" +
                    name +
                    "\", " +
                    tel +
                    ", \"" +
                    addr +
                    "\");";
            ResultSet res=executeCommand(command);
            res=executeCommand("call read_customer();");
            ArrayList<String> ans = print_res(res);
            ans.add(0, "successfully create customer!");
            ans.add(1, "here's the data after the change:");
            return ans;
        }



        @Override
        public ArrayList<String> print() throws SQLException {
            messages.clear();
            messages.add("here are all the customers:");
            messages.addAll(print_res(executeCommand("call read_customer();")));
            messages.add("enter the following info line by line:");
            messages.add("enter the customer name");
            messages.add(
                    "enter the customer phone #");
            messages.add(
                    "enter the customer address");
            return messages;
        }
    }



    private static class update_customer implements Procedure {


        @Override
        public ArrayList<String> execute(String cmd) throws SQLException {
            Scanner scan= new Scanner(cmd);
            String id=scan.nextLine();
            String name = scan.nextLine();
            String tel = scan.nextLine();
            String addr = scan.nextLine();
            executeCommand("call update_customer(" +
                    id +
                    ",\"" +
                    name +
                    "\", " +
                    tel +
                    ", \"" +
                    addr +
                    "\");");
            ResultSet res=executeCommand("call read_customer();");
            ArrayList<String> ans = print_res(res);
            ans.add(0, "successfully update customer!");
            ans.add(1, "here's the data after the change:");
            return ans;
        }



        @Override
        public ArrayList<String> print() throws SQLException {
            messages.clear();
            messages.add("here are all the customers:");
            messages.addAll(print_res(executeCommand("call read_customer();")));
            messages.add("enter the following info line by line:");
            messages.add("enter the customer id");
            messages.add("enter the customer name");
            messages.add(
                    "enter the customer phone #");
            messages.add(
                    "enter the customer address");
            return messages;
        }
    }
    private static class create_product implements Procedure {
        @Override
        public ArrayList<String> execute(String cmd) throws SQLException {
            Scanner scan= new Scanner(cmd);
            String name = scan.nextLine();
            String type = scan.nextLine();
            String price = scan.nextLine();
            String warehouse = scan.nextLine();
            executeCommand("CALL `online_shopping`.`create_product`(\"" +
                    name +
                    "\", \"" +
                    type +
                    "\", " +
                    price + ","+ warehouse +
                    ");");
            ResultSet res=executeCommand("call read_product();");
            ArrayList ans = print_res(res);
            ans.add(0, "successfully create product!");
            ans.add(1, "here's the data after the change:");
            return ans;
        }
        @Override
        public ArrayList<String> print() throws SQLException {
            messages.clear();
            messages.add("here are all the products:");
            messages.addAll(print_res(executeCommand("call read_product();")));
            messages.add("enter the following info line by line:");
            messages.add("enter the product name");
            messages.add("enter the type of the product");
            messages.add("enter the product price");
            messages.add("enter the warehouse to store the product, if you wanna leave it" +
                    "empty, type 'null'");
            return messages;
        }
    }
    private static class update_product implements Procedure {
        @Override
        public ArrayList<String> execute(String cmd) throws SQLException {
            Scanner scan= new Scanner(cmd);
            String id = scan.nextLine();
            String name = scan.nextLine();
            String type = scan.nextLine();
            String price = scan.nextLine();
            String warehouse = scan.nextLine();
            executeCommand("CALL `online_shopping`.`update_product`(" +
                    id +
                    ", \"" +
                    name +
                    "\", \"" +
                    type +
                    "\", \"" +
                    price +
                    "\", \"" +
                    warehouse +
                    "\");");
            ResultSet res=executeCommand("call read_product();");
            ArrayList ans = print_res(res);
            ans.add(0, "successfully update product!");
            ans.add(1, "here's the data after the change:");
            return ans;
        }
        @Override
        public ArrayList<String> print() throws SQLException {
            messages.clear();
            messages.add("here are all the products:");
            messages.addAll(print_res(executeCommand("call read_product();")));
            messages.add("enter the following info line by line:");
            messages.add("enter the product id");
            messages.add("enter the product name");
            messages.add("enter the type of the product");
            messages.add("enter the product price");
            messages.add("enter the warehouse to store the product, it cannot be null");
            return messages;
        }
    }

    private static class create_seller implements Procedure {
        @Override
        public ArrayList<String> execute(String cmd) throws SQLException {
            Scanner scan= new Scanner(cmd);
            String sname = scan.nextLine();
            String tele = scan.nextLine();
            executeCommand("CALL `online_shopping`.`create_seller`(\"" +
                    sname +
                    "\", \"" +
                    tele +
                    "\");");
            ResultSet res=executeCommand("call read_seller();");
            ArrayList<String> ans = print_res(res);
            ans.add(0, "successfully create seller!");
            ans.add(1, "here's the data after the change:");
            return ans;
        }
        @Override
        public ArrayList<String> print() throws SQLException {
            messages.clear();
            messages.add("here are all the sellers:");
            messages.addAll(print_res(executeCommand("call read_seller();")));
            messages.add("enter the following info line by line:");
            messages.add("enter the name of the seller");
            messages.add("enter the seller telephone number");
            return messages;
        }
    }

    private static class update_seller implements Procedure {
        @Override
        public ArrayList<String> execute(String cmd) throws SQLException {
            Scanner scan= new Scanner(cmd);
            String sid=scan.nextLine();
            String sname = scan.nextLine();
            String tele = scan.nextLine();
            executeCommand("CALL `online_shopping`.`update_seller`(" +
                    sid +
                    ", \"" +
                    sname +
                    "\", " +
                    tele +
                    ");\n");
            ResultSet res=executeCommand("call read_seller();");
            ArrayList<String> ans = print_res(res);
            ans.add(0, "successfully update seller!");
            ans.add(1, "here's the data after the change:");
            return ans;
        }
        @Override
        public ArrayList<String> print() throws SQLException {
            messages.clear();
            messages.add("here are all the sellers:");
            messages.addAll(print_res(executeCommand("call read_seller();")));
            messages.add("enter the following info line by line:");
            messages.add("enter the seller id");
            messages.add("enter the name of the seller");
            messages.add("enter the seller telephone number");
            return messages;
        }
    }

    private static class create_warehouse implements Procedure {
        @Override
        public ArrayList<String> execute(String cmd) throws SQLException {
            Scanner scan= new Scanner(cmd);
            String addr = scan.nextLine();
            String cap = scan.nextLine();
            String sid = scan.nextLine();
            executeCommand("CALL `online_shopping`.`create_warehouse`(\"" +
                    addr +
                    "\", \"" +
                    cap +
                    "\", \"" +
                    sid +
                    "\");");
            ResultSet res=executeCommand("call read_warehouse();");
            ArrayList<String> ans = print_res(res);
            ans.add(0, "successfully create warehouse!");
            ans.add(1, "here's the data after the change:");
            return ans;
        }
        @Override
        public ArrayList<String> print() throws SQLException {
            messages.clear();
            messages.add("here are all the warehouses:");
            messages.addAll(print_res(executeCommand("call read_warehouse();")));
            messages.add("enter the following info line by line:");
            messages.add("enter the address of the warehouse");
            messages.add("enter the capacity of the warehouse (in m^2)");
            messages.add("enter the seller id (cannot leave to empty)");
            return messages;
        }
    }
    private static class update_warehouse implements Procedure {
        @Override
        public ArrayList<String> execute(String cmd) throws SQLException {
            Scanner scan= new Scanner(cmd);
            String wid=scan.nextLine();
            String addr = scan.nextLine();
            String cap = scan.nextLine();
            String sid = scan.nextLine();
            executeCommand("CALL `online_shopping`.`update_warehouse`(" +
                    wid +
                    ", \"" +
                    addr +
                    "\", " +
                    cap +
                    ", " +
                    sid +
                    ");\n");
            ResultSet res=executeCommand("call read_warehouse();");
            ArrayList<String> ans = print_res(res);
            ans.add(0, "successfully update warehouse!");
            ans.add(1, "here's the data after the change:");
            return ans;
        }
        @Override
        public ArrayList<String> print() throws SQLException {
            messages.clear();
            messages.add("here are all the warehouses:");
            messages.addAll(print_res(executeCommand("call read_warehouse();")));
            messages.add("enter the following info line by line:");
            messages.add("enter the warehouse id");
            messages.add("enter the address of the warehouse");
            messages.add("enter the capacity of the warehouse (in m^2)");
            messages.add("enter the seller id (cannot leave to empty)");
            return messages;
        }
    }

    private static class create_order implements Procedure {
        @Override
        public ArrayList<String> execute(String cmd) throws SQLException {
            Scanner scan= new Scanner(cmd);
            String cid = scan.nextLine();
            String pid = scan.nextLine();
            String np = scan.nextLine();
            executeCommand("CALL `online_shopping`.`create_order`(\"" +
                    cid +
                    "\", \"" +
                    pid +
                    "\", \"" +
                    np +
                    "\");");
            ResultSet res=executeCommand("call read_order();");
            ArrayList<String> ans = print_res(res);
            ans.add(0, "successfully create order!");
            ans.add(1, "here's the data after the change:");
            return ans;
        }
        @Override
        public ArrayList<String> print() throws SQLException {
            messages.clear();
            messages.add("here are all the orders:");
            messages.addAll(print_res(executeCommand("call read_order();")));
            messages.add("enter the following info line by line:");
            messages.add("enter the customer id");
            messages.add("enter the product id");
            messages.add("enter the number of purchase");
            return messages;
        }
    }

    private static class update_order implements Procedure {
        @Override
        public ArrayList<String> execute(String cmd) throws SQLException {
            Scanner scan= new Scanner(cmd);
            String oid=scan.nextLine();
            String cid = scan.nextLine();
            String pid = scan.nextLine();
            String np = scan.nextLine();
            executeCommand("CALL `online_shopping`.`update_order`(\"" +
                    oid +
                    "\", \"" +
                    cid +
                    "\", \"" +
                    pid +
                    "\", \"" +
                    np +
                    "\");");
            ResultSet res=executeCommand("call read_order();");
            ArrayList<String> ans = print_res(res);
            ans.add(0, "successfully update order!");
            ans.add(1, "here's the data after the change:");
            return ans;
        }
        @Override
        public ArrayList<String> print() throws SQLException {
            messages.clear();
            messages.add("here are all the orders:");
            messages.addAll(print_res(executeCommand("call read_order();")));
            messages.add("enter the following info line by line:");
            messages.add("enter the order id");
            messages.add("enter the customer id");
            messages.add("enter the product id");
            messages.add("enter the number of purchase");
            return messages;
        }
    }
    private static class delete_product implements Procedure {
        @Override
        public ArrayList<String> execute(String cmd) throws SQLException {
            Scanner scan= new Scanner(cmd);
            String id = scan.nextLine();
            executeCommand("CALL `online_shopping`.`delete_product`(" +
                    id+
                    ");");
            ResultSet res=executeCommand("call read_product();");
            ArrayList ans = print_res(res);
            ans.add(0, "successfully delete the product!");
            ans.add(1, "here's the data after the change:");
            return ans;
        }
        @Override
        public ArrayList<String> print() throws SQLException {
            messages.clear();
            messages.add("here are all the products:");
            messages.addAll(print_res(executeCommand("call read_product();")));
            messages.add("enter the product id to delete:");
            return messages;
        }
    }
    private static class delete_order implements Procedure {
        @Override
        public ArrayList<String> execute(String cmd) throws SQLException {
            Scanner scan= new Scanner(cmd);
            String id = scan.nextLine();
            executeCommand("CALL `online_shopping`.`delete_order`(" +
                    id+
                    ");");
            ResultSet res=executeCommand("call read_order();");
            ArrayList ans = print_res(res);
            ans.add(0, "successfully delete the order!");
            ans.add(1, "here's the data after the change:");
            return ans;
        }
        @Override
        public ArrayList<String> print() throws SQLException {
            messages.clear();
            messages.add("here are all the orders:");
            messages.addAll(print_res(executeCommand("call read_order();")));
            messages.add("enter the order id to delete:");
            return messages;
        }
    }
    private static class delete_warehouse implements Procedure {
        @Override
        public ArrayList<String> execute(String cmd) throws SQLException {
            Scanner scan= new Scanner(cmd);
            String id = scan.nextLine();
            executeCommand("CALL `online_shopping`.`delete_warehouse`(" +
                    id+
                    ");");
            ResultSet res=executeCommand("call read_warehouse();");
            ArrayList ans = print_res(res);
            ans.add(0, "successfully delete the warehouse!");
            ans.add(1, "here's the data after the change:");
            return ans;
        }
        @Override
        public ArrayList<String> print() throws SQLException {
            messages.clear();
            messages.add("here are all the warehouses:");
            messages.addAll(print_res(executeCommand("call read_warehouse();")));
            messages.add("enter the warehouse id to delete:");
            return messages;
        }
    }
    private static class delete_customer implements Procedure {
        @Override
        public ArrayList<String> execute(String cmd) throws SQLException {
            Scanner scan= new Scanner(cmd);
            String id = scan.nextLine();
            executeCommand("CALL `online_shopping`.`delete_customer`(" +
                    id+
                    ");");
            ResultSet res=executeCommand("call read_customer();");
            ArrayList ans = print_res(res);
            ans.add(0, "successfully delete the customer!");
            ans.add(1, "here's the data after the change:");
            return ans;
        }
        @Override
        public ArrayList<String> print() throws SQLException {
            messages.clear();
            messages.add("here are all the customers:");
            messages.addAll(print_res(executeCommand("call read_customer();")));
            messages.add("enter the customer id to delete:");
            return messages;
        }
    }
    private static class delete_seller implements Procedure {
        @Override
        public ArrayList<String> execute(String cmd) throws SQLException {
            Scanner scan= new Scanner(cmd);
            String id = scan.nextLine();
            executeCommand("CALL `online_shopping`.`delete_seller`(" +
                    id+
                    ");");
            ResultSet res=executeCommand("call read_seller();");
            ArrayList ans = print_res(res);
            ans.add(0, "successfully delete the seller!");
            ans.add(1, "here's the data after the change:");
            return ans;
        }
        @Override
        public ArrayList<String> print() throws SQLException {
            messages.clear();
            messages.add("here are all the sellers:");
            messages.addAll(print_res(executeCommand("call read_seller();")));
            messages.add("enter the seller id to delete:");
            return messages;
        }
    }


    private static class NullProcedure implements Procedure{
        @Override
        public ArrayList<String> execute(String cmd) throws SQLException {
            ArrayList<String> c=new ArrayList<>();
            c.add("syntax error, try again.");
            return c;
        }

        @Override
        public ArrayList<String> print() throws SQLException {
            ArrayList<String> c=new ArrayList<>();
            c.add("not meaningful argument");
            return c;
        }
    }




































    public static Record processCommand(String entity, String operation) throws SQLException {
        if (entity.equals("customer")) {
            if (operation.equals("create")) {
                procedure = new create_customer();
            } else if (operation.equals("read")) {
                procedure = new read_customer();
            } else if (operation.equals("update")) {
                procedure = new update_customer();
            } else if (operation.equals("delete")) {
                procedure = new delete_customer();
            } else {
                procedure = new NullProcedure();
            }
        } else if (entity.equals("order")) {
            if (operation.equals("create")) {
                procedure = new create_order();
            } else if (operation.equals("read")) {
                procedure = new read_order();
            } else if (operation.equals("update")) {
                procedure = new update_order();
            } else if (operation.equals("delete")) {
                procedure = new delete_order();
            } else {
                procedure = new NullProcedure();
            }
        } else if (entity.equals("product")) {
            if (operation.equals("create")) {
                procedure = new create_product();
            } else if (operation.equals("read")) {
                procedure = new read_product();
            } else if (operation.equals("update")) {
                procedure = new update_product();
            } else if (operation.equals("delete")) {
                procedure = new delete_product();
            } else {
                procedure = new NullProcedure();
            }
        }
        else if (entity.equals("seller")) {
            if (operation.equals("create")) {
                procedure = new create_seller();
            } else if (operation.equals("read")) {
                procedure = new read_seller();
            } else if (operation.equals("update")) {
                procedure = new update_seller();
            } else if (operation.equals("delete")) {
                procedure = new delete_seller();
            } else {
                procedure = new NullProcedure();
            }
        }
        else if (entity.equals("warehouse")) {
            if (operation.equals("create")) {
                procedure = new create_warehouse();
            } else if (operation.equals("read")) {
                procedure = new read_warehouse();
            } else if (operation.equals("update")) {
                procedure = new update_warehouse();
            } else if (operation.equals("delete")) {
                procedure = new delete_warehouse();
            } else {
                procedure = new NullProcedure();
            }
        }else{
            procedure=new NullProcedure();
        }

        Record c =new Record();
        c.setRecords(procedure.print());
        return c;
    }

    public static Record processCommand2(Record record) throws SQLException {
        ArrayList<String> strs= procedure.execute(record.getRecords().get(0));
        Record c =new Record();
        c.setRecords(strs);
        return c;
    }

    public static Record processLogin(String username2, String password2) {
        Record c =new Record();
        username=username2;
        password=password2;
        if(tryConnect()) {
            c.addRecord("yes");
        }else{
            c.addRecord("no");
        }
        return c;
    }

    public static void main(String[] args) throws SQLException {
        if (DEBUG) {
            username = "root";
            password = "abcdefg";
        }

        tryConnect();
        processCommand("create", "customer");
        Record c = new Record();
        c.addRecord("alice 123 asd");
        processCommand2(c);
        // //////////////////////////////////
    }
        public static Record logout ()  {
        finalStep();
            Record c = new Record();
            c.addRecord("safely log out!");
            return c;
    }
}
