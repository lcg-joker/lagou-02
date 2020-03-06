package com.lcg.edu.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lcg.edu.annotations.Autowired;
import com.lcg.edu.annotations.Component;
import com.lcg.edu.entity.Result;
import com.lcg.edu.factory.BeanFactory;
import com.lcg.edu.factory.ProxyFactory;
import com.lcg.edu.service.TransferService;
import com.lcg.edu.service.impl.TransferServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author lichenggang
 * @date 2020/3/1 2:04 上午
 * @description
 */

@WebServlet(name = "transferServlet", urlPatterns = "/transferServlet")
@Component("transferServlet")
public class TransferServlet extends HttpServlet {


    @Autowired
    private TransferService transferService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // 设置请求体的字符编码
        req.setCharacterEncoding("UTF-8");

        String fromCardNo = req.getParameter("fromCardNo");
        String toCardNo = req.getParameter("toCardNo");
        String moneyStr = req.getParameter("money");
        int money = Integer.parseInt(moneyStr);

        Result result = new Result();

        try {

            // 2. 调用service层方法
            TransferService transferService = (TransferService) BeanFactory.getBean("transferService");
            transferService.transfer(fromCardNo, toCardNo, money);
            result.setStatus("200");
        } catch (Exception e) {
            e.printStackTrace();
            result.setStatus("201");
            result.setMessage(e.toString());
        }
        ObjectMapper objectMapper = new ObjectMapper();

        // 响应
        resp.setContentType("application/json;charset=utf-8");
        String s = objectMapper.writeValueAsString(result);
        System.out.println(s);
        resp.getWriter().print(s);
    }
}
