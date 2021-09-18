package com.teqgin.image_laboratory.config;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author TeqGin
 * 拦截前缀为/user和/file的请求
 * @quations:
 * 1.Q: The webFilter is out of work,how can I fix it?
 *   A: If you want to make the webFilter work,you should add @component at the head of LoginFilter class,
 *      but if you add the @component ,the property 'urlPatterns' will out of work.
 * 2.Q: After I add @component,the property 'urlPatterns' out of work.Why?
 *   A: After you add @component,the property 'urlPatterns' will out of work,if you want to make it work,
 *      you should add @ServletComponentScan at the xxxxApplication.java.
 *
 * 3.Q: I already write the filter,but why chrome can access the data,while edge cannot?
 *   A: Maybe you should clear the browse data at chrome.
 *
 * 3.Remind:You must add at least one annotation(@component or @ServletComponentScan)to make the filter work!
 *
 *
 * */
@WebFilter(filterName = "sessionFilter",urlPatterns = {"/user/*", "/file/*"})
@Slf4j
public class LoginFilter implements Filter {
    //不进行拦截的请求
    String [] excludeUris =new String []{"/user/login","/user/sign_up","/info/about",
            "/user/verify", "/user/forget", "/user/veri_sign_up","/user/verify_forget",
            "/user/send_verify_code"};

    @Override
    public void init(FilterConfig filterConfig) {

    }

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession();
        //获取请求路径
        String uri  = request.getRequestURI();

        if (isNotNeedFilter(uri)){
            log.info("filter uri:" + uri +"  allow");
            filterChain.doFilter(servletRequest,servletResponse);
        }else {
            //判断是否登陆，若已登陆则放行
            if (session != null && session.getAttribute("user") != null){
                log.info("filter uri:" + uri +"  allow");
                filterChain.doFilter(servletRequest,servletResponse);
            }else {
                //未登录，返回登陆页面
                log.info("filter uri:" + uri +"  reject");
                response.sendRedirect(request.getContextPath() + "/user/login");

            }
        }
    }

    /**
     *
     * @param uri 验证的路径
     * @return 如果该路径不必被拦截则返回true，反之返回false
     */
    public boolean isNotNeedFilter(String uri){
        for(String i: excludeUris){
            if (i.equals(uri)){
                return true;
            }
        }
        return false;
    }

    @Override
    public void destroy() {
    }
}
