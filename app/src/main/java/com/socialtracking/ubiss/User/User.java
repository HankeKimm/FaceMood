package com.socialtracking.ubiss.User;

/**
 * Created by shkurtagashi on 28.06.18.
 */

public class User {

    public String _android_id;
    public String _username;
    public String _emailAdress;
    public String _gender;
    public String _age;

    public User(){}

    public User(String android_id, String username, String email, String gender, String age){
        setAndroidId(android_id);
        setUsername(username);
        setEmail(email);
        setGender(gender);
        setAge(age);
    }

    public User(String username, String email, String gender, String age){
        setUsername(username);
        setEmail(email);
        setGender(gender);
        setAge(age);
    }



    public void setAndroidId(String id) {
        this._android_id = id;
    }

    public String getAndroidId(){
        return this._android_id;
    }

    public void setGender(String gender) {
        this._gender = gender;
    }

    public String getGender(){
        return this._gender;
    }

    public void setAge(String age) {
        this._age = age;
    }

    public String getAge(){
        return this._age;
    }

    public void setUsername(String username) {
        this._username = username;
    }

    public String getUsername(){
        return this._username;
    }

    public void setEmail(String email) {
        this._emailAdress = email;
    }

    public String getEmail(){
        return this._emailAdress;
    }


}
