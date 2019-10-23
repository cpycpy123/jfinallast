package com.demo.common;

import com.demo.common.model._MappingKit;
import com.demo.index.IndexController;
import com.jfinal.config.*;
import com.jfinal.core.JFinal;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jfinal.template.Engine;
import com.user.UserController;

public class DemoConfig extends JFinalConfig {
    public static void main(String[] args) {
        JFinal.start("src/main/webapp",8081,"/");
    }

    @Override
    public void configConstant(Constants me) {
        // 加载少量必要配置，随后可用PropKit.get(...)获取值
        PropKit.use("config.properties");
        me.setDevMode(PropKit.getBoolean("devMode",true));
    }

    @Override
    public void configRoute(Routes me) {
        me.add("/", IndexController.class, "/index");	// 第三个参数为该Controller的视图存放路径
        me.add("/user", UserController.class);
    }

    @Override
    public void configEngine(Engine me) {
        /**
         * 如果模板引擎在WEB-INF路径下的访问方法
         * 在configEngine(Engine me)方法中使用me.addSharedFunction添加函数模板文件：
         */
        me.addSharedObject("ctx", JFinal.me().getContextPath());
        me.addSharedFunction("/common/_layout.html");
        me.addSharedFunction("/common/_paginate.html");
    }
    public static DruidPlugin createDruidPlugin() {
        return new DruidPlugin(PropKit.get("jdbcUrl"), PropKit.get("user"), PropKit.get("password").trim());
    }
    @Override
    public void configPlugin(Plugins me) {
        // 配置C3p0数据库连接池插件
        DruidPlugin druidPlugin = createDruidPlugin();
        me.add(druidPlugin);

        // 配置ActiveRecord插件
        ActiveRecordPlugin arp = new ActiveRecordPlugin(druidPlugin);
        // 所有映射在 MappingKit 中自动化搞定
        _MappingKit.mapping(arp);
        me.add(arp);
    }

    @Override
    public void configInterceptor(Interceptors me) {

    }

    @Override
    public void configHandler(Handlers me) {

    }
}
