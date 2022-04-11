package com.example.roomdbtutorial.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.roomdbtutorial.User;

import java.util.List;

@Dao
public interface UserDAO {
    @Insert
    void insertUser(User user);

    @Query("SELECT * FROM tbl_user")
    List<User> getListUser();

    @Query("SELECT * FROM tbl_user WHERE username = :username")
    List<User> checkUser(String username);

    @Update
    void updateUser(User user);
}
