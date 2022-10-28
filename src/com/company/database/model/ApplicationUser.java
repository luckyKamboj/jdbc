package com.company.database.model;

import com.company.database.annotation.Table;

@Table(name = "USER")
public class ApplicationUser {

    private Integer id;
    private String name;
    private String username;

    @Override
    public String toString() {
        return "ApplicationUser{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", username='" + username + '\'' +
                ", lastName='" + lastName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", gender=" + gender +
                ", email='" + email + '\'' +
                ", age=" + age +
                ", active=" + active +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", role=" + role +
                '}';
    }

    private String lastName;
    private String middleName;
    private GENDER gender;
    private String email;
    private Integer age;
    private Boolean active;
    private String phoneNumber;
    private Role role;


    public enum GENDER {
        MALE('M'), FEMALE('F'), OTHER('O');
        
        private final char type;

        GENDER(char type) {
            this.type  = type ;
        }
        public  char getType(){
            return type;
        }
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public GENDER getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = GENDER.valueOf(gender);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Boolean isActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = Role.valueOf(role);
    }
}
