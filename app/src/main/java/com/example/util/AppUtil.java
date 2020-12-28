package com.example.util;


import android.util.Log;
import android.widget.LinearLayout;

import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.htmlcleaner.XPatherException;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.seimicrawler.xpath.JXDocument;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;

public class AppUtil {

    private String op[];//运算符
    private int num[];//操作数
    private ArrayList<String> resultArrayList;//运算结果集

    /*
     *构造函数
     */
    public AppUtil(int a,int b,int c,int d) {
        // TODO Auto-generated constructor stub
        resultArrayList=new ArrayList<String>();
        op =new String[4];
        num =new int[4];
        op[0]="+";
        op[1]="-";
        op[2]="*";
        op[3]="/";
        num[0]=a;
        num[1]=b;
        num[2]=c;
        num[3]=d;
    }

    /*
     * cal(float,float,String)输入操作符和操作数返回运算结果
     */
    private float Cal(float a,float b,String op){
        float res = 0;
        switch (op) {
            case "+":
                res=a+b;
                break;
            case "-":
                res=a-b;
                break;
            case "*":
                res=a*b;
                break;
            case "/":
                res=a/b;
                break;
            default:
                break;
        }
        return res;
    }


	/*五种计算模式函数   若能算出24返回算式字符串，若不能则返回字符串"fail"
	((A op B) op C) op D    String mode_one(float a,String op1,float b,String op2,float c,String op3,float d)
	(A op (B op C)) op D    String mode_two(float a,String op1,float b,String op2,float c,String op3,float d)
	A op (B op (C op D))    String mode_three(float a,String op1,float b,String op2,float c,String op3,float d)
	A op ((B op C) op D)    String mode_four(float a,String op1,float b,String op2,float c,String op3,float d)
	(A op B) op (C op D)    String mode_five(float a,String op1,float b,String op2,float c,String op3,float d)
	 */

    /*
     * ((A op B) op C) op D
     */
    private String mode_one(float a,String op1,float b,String op2,float c,String op3,float d) {
        float res= Cal(Cal(Cal(a, b, op1), c, op2), d, op3);
        if(res==24)
        {
            return "(("+Integer.valueOf((int)a)+op1+Integer.valueOf((int)b)+")"+op2+Integer.valueOf((int)c)+")"+op3+Integer.valueOf((int)d);
        }
        else {
            return "fail";
        }
    }

    /*
     * (A op (B op C)) op D
     */
    private String mode_two(float a,String op1,float b,String op2,float c,String op3,float d) {
        float res= Cal(Cal(a,Cal(b, c, op2),op1), d, op3);
        if(res==24)
        {
            return "("+Integer.valueOf((int)a)+op1+"("+Integer.valueOf((int)b)+op2+Integer.valueOf((int)c)+"))"+op3+Integer.valueOf((int)d);
        }
        else {
            return "fail";
        }
    }

    /*
     * 	A op (B op (C op D))
     */
    private String mode_three(float a,String op1,float b,String op2,float c,String op3,float d) {
        float res=Cal(a,Cal(b, Cal(c, d, op3), op2),op1);
        if(res==24)
        {
            return Integer.valueOf((int)a)+op1+"("+Integer.valueOf((int)b)+op2+"("+Integer.valueOf((int)c)+op3+Integer.valueOf((int)d)+"))";
        }
        else {
            return "fail";
        }
    }

    /*
     * 	A op ((B op C) op D)
     */
    private String mode_four(float a,String op1,float b,String op2,float c,String op3,float d) {
        float res=Cal(a,Cal(Cal(b, c, op2), d, op3),op1);
        if(res==24)
        {
            return Integer.valueOf((int)a)+op1+"(("+Integer.valueOf((int)b)+op2+Integer.valueOf((int)c)+")"+op3+Integer.valueOf((int)d)+")";
        }
        else {
            return "fail";
        }
    }

    /*
     * (A op B) op (C op D)
     */
    private String mode_five(float a,String op1,float b,String op2,float c,String op3,float d) {
        float res= Cal(Cal(a, b, op1),Cal(c, d, op3),op2);
        if(res==24)
        {
            return "("+Integer.valueOf((int)a)+op1+Integer.valueOf((int)b)+")"+op2+"("+Integer.valueOf((int)c)+op3+Integer.valueOf((int)d)+")";
        }
        else {
            return "fail";
        }
    }

    public ArrayList<String> find_24() {
        /*
         * 四重for循环列出操作数所有排序可能，
         */
        for(int i=0;i<4;i++)
        {
            for (int j = 0; j < 4; j++) {
                if (j==i) {
                    continue;
                }
                for (int k = 0; k < 4; k++) {
                    if (k==i||k==j) {
                        continue;
                    }
                    for (int l = 0; l < 4; l++) {
                        if (l==i||l==j||l==k) {
                            continue;
                        }
                        /*
                         * 三重循环列出操作符的可能排列顺序
                         */
                        for (int op1 = 0; op1 < 4; op1++) {
                            for (int op2 = 0; op2 < 4; op2++) {
                                for (int op3 = 0; op3 < 4; op3++) {
                                    /*
                                     * 用五种模式计算将满足条件的算式添加到resultArrarlisst
                                     */
                                    if(mode_one(num[i], op[op1], num[j], op[op2], num[k], op[op3], num[l])!="fail")
                                    {
                                        resultArrayList.add(mode_one(num[i], op[op1], num[j], op[op2], num[k], op[op3], num[l]));
                                    }

                                    if(mode_two(num[i], op[op1], num[j], op[op2], num[k], op[op3], num[l])!="fail")
                                    {
                                        resultArrayList.add(mode_two(num[i], op[op1], num[j], op[op2], num[k], op[op3], num[l]));
                                    }

                                    if(mode_three(num[i], op[op1], num[j], op[op2], num[k], op[op3], num[l])!="fail")
                                    {
                                        resultArrayList.add(mode_three(num[i], op[op1], num[j], op[op2], num[k], op[op3], num[l]));
                                    }

                                    if(mode_four(num[i], op[op1], num[j], op[op2], num[k], op[op3], num[l])!="fail")
                                    {
                                        resultArrayList.add(mode_four(num[i], op[op1], num[j], op[op2], num[k], op[op3], num[l]));
                                    }

                                    if(mode_five(num[i], op[op1], num[j], op[op2], num[k], op[op3], num[l])!="fail")
                                    {
                                        resultArrayList.add(mode_five(num[i], op[op1], num[j], op[op2], num[k], op[op3], num[l]));
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return resultArrayList;

    }


    public static void main(String[] args) {
        AppUtil appUtil=new AppUtil(4, 4, 3, 3);
        ArrayList<String> res=appUtil.find_24();
        for (String re: res) {
            System.out.println(re);
        }
    }
/*
//div/ul/li[@id="stroke_count"]/span
 */
    public static void pachongtest() throws IOException, XPatherException {
        Log.e("hello","aloha");
        String name="我";
        //String name=URLEncoder.encode(xw,"utf-8");
        // String url="https://hanyu.baidu.com/s?wd=%E5%90%91&from=zici";
        String url="https://hanyu.baidu.com/zici/s?wd="+name+"&query="+name;
        String contents = Jsoup.connect(url).post().html();

        HtmlCleaner hc = new HtmlCleaner();
        TagNode tn = hc.clean(contents);
        //代表class="article-title"的div标签下面的h2标签里面的内容
        String xpath = "//div/ul/li[@id=\"stroke_count\"]/span/text()";
        Object[] objects = tn.evaluateXPath(xpath);
        for (Object object : objects) {
            Log.e("bihua",object.toString());

        }


    }
}

