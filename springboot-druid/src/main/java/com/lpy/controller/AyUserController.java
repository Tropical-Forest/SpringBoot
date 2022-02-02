package com.lpy.controller;

import com.lpy.error.BusinessException;
import com.lpy.model.AyUser;

import com.lpy.service.AyUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/ayUser")
public class AyUserController {

   @Autowired
   private AyUserService ayUserService;

   @RequestMapping("/test")
    public String test(Model model) {
       //查询数据库所有用户
       List<AyUser> ayUserList = ayUserService.findAll();
       model.addAttribute("users",ayUserList);
       return "ayUser";
   }

   @RequestMapping("/findAll")
   public String findAll(Model model) {
      List<AyUser> ayUser = ayUserService.findAll();
      model.addAttribute("users",ayUser);
      throw new BusinessException("业务异常");
   }

   @RequestMapping("/findByNameAndPasswordRetry")
   public String findByNamePasswordRetry(Model model) {
      AyUser ayUser = ayUserService.findByNameAndPasswordRetry("阿毅", "123456");
      model.addAttribute("users",ayUser);
      return "success";
   }

}
