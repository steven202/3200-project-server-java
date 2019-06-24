package com.example.shoppingcart.controllers;

import com.example.shoppingcart.models.Record;
import com.example.shoppingcart.services.JDBCCreateTable;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.sql.SQLException;

@CrossOrigin(origins =  "*" )
@RestController
public class CartController {
    JDBCCreateTable db = new JDBCCreateTable();


    @GetMapping("/request")
    Record getCommand(@PathParam("entity") String entity, @PathParam("operation")String operation) throws SQLException {
        try {
            return db.processCommand(entity, operation);
        }catch(Exception e){
            Record c=new Record();
            c.addRecord(e.getMessage());
            return c;
        }

    }
    @GetMapping("/logout")
    Record logout() throws SQLException {
        try {
             return db.logout();
        }catch(Exception e){
            Record c=new Record();
            c.addRecord(e.getMessage());
            return c;
        }

    }
    @PostMapping("/query")
    Record getResultSet(@RequestBody Record record) throws SQLException {
        try{
        return db.processCommand2(record);}
        catch(Exception e){
                Record c=new Record();
                c.addRecord(e.getMessage());
                return c;
            }
    }
    @GetMapping("/login")
    Record login(@PathParam("hostname")String hostname,@PathParam("username") String username, @PathParam("password")String password){
        try{
        return db.processLogin(hostname,username, password);}
        catch(Exception e){
            Record c=new Record();
            c.addRecord(e.getMessage());
            return c;
        }
    }
}
