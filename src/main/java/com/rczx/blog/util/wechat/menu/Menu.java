package com.rczx.blog.util.wechat.menu;

import com.alibaba.fastjson.JSONObject;

/**
 * Created by Administrator on 2018/9/24.
 */
public class Menu{


    private static String CREATE_MENU_URL="https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";

    private Button[] button;
    public Button[] getButton() {
        return button;
    }
    public void setButton(Button[] button) {
        this.button = button;
    }


    public static Menu initMenu(){
        Menu menu = new Menu();
        ClickButton clickButton = new ClickButton();
        clickButton.setKey("clickButton");
        clickButton.setType("click");
        clickButton.setName("clickMenu");
        ViewButton viewButton = new ViewButton();
        viewButton.setName("viewButton");
        viewButton.setType("view");
        viewButton.setUrl("http://www.baidu.com");
        ClickButton clickButton1 = new ClickButton();
        clickButton1.setKey("scanButton");
        clickButton1.setType("scancode_push");
        clickButton1.setName("scanButton");
        ClickButton clickButton2 = new ClickButton();
        clickButton2.setKey("locationButton");
        clickButton2.setType("location_select");
        clickButton2.setName("locationButton");
        Button button = new Button();
        button.setName("Menu");
        button.setSub_button(new Button[]{clickButton1,clickButton2});
        menu.setButton(new Button[]{clickButton,viewButton,button});
        return menu;
    }
    //创建菜单的url拼接
    public static int createMenu(String menu, String token){
        String url = CREATE_MENU_URL.replace("ACCESS_TOKEN", token);
        int result = 0;
      /*  JSONObject jsonObject = doPostStr(url, menu);
        if(jsonObject != null){
            result = jsonObject.getInt("errcode");
        }*/
        return result;
    }
    //查询菜单的url的拼接
 /*   public static JSONObject queryMenu(String token){
        String url = QUERY_MENU_URL.replace("ACCESS_TOKEN", token);
        JSONObject jsonObject = doGetUrl(url);
        return jsonObject;
    }*/

}
