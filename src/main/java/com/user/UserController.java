package com.user;

import com.demo.common.model.User;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.json.Json;
import com.jfinal.plugin.activerecord.Page;

import java.util.Date;

public class UserController extends Controller {
    static UserService service = new UserService();

    public void index(){
        setAttr("userPage", service.paginate(getParaToInt(0,1), 10));
        //render("user.html");
        render("userList.html");
    }


    public void addUser(){}

    public void save(){
        Date day=new Date();
        User user = new User();
        user.set("createTime", day).set("name", getPara("user.name")).set("age", getPara("user.age"))
                .set("address", getPara("user.address")).set("sex", getPara("user.sex")).set("password", getPara("user.password"))
                .set("city", getPara("user.city")).save();
//        User user = getModel(User.class,"u");
        redirect("/user");
    }

    /**
     * 作为
     */



    public void updateUser(){
        getModel(User.class).update();
        redirect("/user");
    }


    public void deleteUser(){
        service.deleteById(getParaToInt());
        System.out.println("删除"+getParaToInt()+"成功");
        redirect("/user");
    }
    /**
     * 页面list
     */
    public void page(){
        int page = getParaToInt("page");
        int limit = getParaToInt("limit");
        Page<User> user = service.paginate(getParaToInt(0,page), limit);
        String jsonList = Json.getJson().toJson(user.getList());
        String jsons= "{\"code\":\"0\",\"msg\":\"\",\"count\":\""+user.getTotalRow()+"\",\"data\":"+jsonList+"}";
        renderJson(jsons);
    }


    /**
     * 修改 或者查看
     */
    public void editUser(){
        int id = getParaToInt("id");
        String islook = "0";
        if(getPara("look").equals("true")){
            islook="1";
        }
        setAttr("islook", islook);
        setAttr("user", service.findById(id));

    }




}
