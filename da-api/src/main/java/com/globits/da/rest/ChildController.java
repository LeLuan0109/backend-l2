package com.globits.da.rest;

import com.globits.da.domain.Child;
import com.globits.da.domain.Parent;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChildController {
    @RequestMapping("child")
    public Child get(){
        Child child = new Child();
        child.setAge(3);
        return child;
    }

    @RequestMapping("pa")
    public Parent get2(){
        Child child = new Child();
        child.setAge(3);
        return child;
    }
}
