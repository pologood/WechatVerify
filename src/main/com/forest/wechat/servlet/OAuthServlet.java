package com.forest.wechat.servlet;

import com.forest.wechat.pojo.SNSUserInfo;
import com.forest.wechat.pojo.WeixinOauth2Token;
import com.forest.wechat.util.AdvancedUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class OAuthServlet extends HttpServlet {
    private static final long serialVersionUID = -1847238807216447030L;

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("gb2312");
        response.setCharacterEncoding("gb2312");

        // 用户同意授权后，能获取到code
        String code = request.getParameter("code");

        // 用户同意授权
        if (!"authdeny".equals(code)) {
            // 获取网页授权access_token
            WeixinOauth2Token weixinOauth2Token = AdvancedUtil.getOauth2AccessToken("APPID", "APPSECRET", code);
            // 网页授权接口访问凭证
            String accessToken = weixinOauth2Token.getAccessToken();
            // 用户标识
            String openId = weixinOauth2Token.getOpenId();
            // 获取用户信息
            SNSUserInfo snsUserInfo = AdvancedUtil.getSNSUserInfo(accessToken, openId);

            // 设置要传递的参数
            request.setAttribute("snsUserInfo", snsUserInfo);
        }
        // 跳转到index.jsp
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }
}

//https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx9b67a1845cda4a36&http%3A%2F%2F1.sirui.applinzi.com%2FoauthServlet&response_type=code&scope=snsapi_userinfo&state=STATE&connect_redirect=1#wechat_redirect