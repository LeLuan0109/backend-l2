package com.globits.da.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Child extends Parent{
    private int age;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Child(String name, int age) {
        super(name);
        this.age = age;
    }

    public static void main(String[] args) {
        new Child(
    }
}

