package com.zdx.zookeeper.zookeeperconfig.controller;

import com.zdx.zookeeper.zookeeperconfig.entity.DbConf;
import org.apache.zookeeper.ZooKeeper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;


/**
 * 配置属性修改Controller
 * Created by BF100177 on 2017/6/8.
 */
@Controller
public class ZkController {

    @Autowired
    private ZooKeeper zk;

    @RequestMapping("/")
    public String index(Model model, HttpServletRequest request){
       try {
           String url =  new String(zk.getData("/dbConf/urlNode",true,null));
           String username =  new String(zk.getData("/dbConf/userNameNode",true,null));
           String pwd =  new String(zk.getData("/dbConf/passwordNode",true,null));
           model.addAttribute("url",url);
           model.addAttribute("username",username);
           model.addAttribute("pwd",pwd);
           DbConf dbConf = new DbConf();
           dbConf.setUrl(url);
           dbConf.setUsername(username);
           dbConf.setPwd(pwd);
           request.getSession().setAttribute("dbConf",dbConf);
       }catch (Exception e){
           e.printStackTrace();
       }
        return "/index";
    }

    @RequestMapping("/update")
    public String update(DbConf dbConf,HttpServletRequest request){
        try {
                DbConf conf = (DbConf) request.getSession().getAttribute("dbConf");
                request.getSession().removeAttribute("dbConf");
                if(conf != null){
                    if(! conf.getUsername().equals(dbConf.getUsername())){
                        zk.setData("/dbConf/userNameNode",dbConf.getUsername().getBytes(),-1);
                    }
                    if(! conf.getUrl().equals(dbConf.getUrl())){
                        zk.setData("/dbConf/urlNode",dbConf.getUrl().getBytes(),-1);
                    }
                    if(! conf.getPwd().equals(dbConf.getPwd())){
                        zk.setData("/dbConf/passwordNode",dbConf.getPwd().getBytes(),-1);
                    }
                }
                return "redirect:/";
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

}
