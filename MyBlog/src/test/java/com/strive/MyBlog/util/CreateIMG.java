package com.strive.MyBlog.util;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

/**
 * @author <a href="mailto:18335553083@163.com">Mr_He</a>
 * @Copyright (c)</ b> HeC<br/>
 * @createTime 2018/3/21 23:50
 * @Description:图形验证码
 */
@Controller
public class CreateIMG {
    private int width = 900;//定义图片的width
    private int height = 200;//定义图片的height
    private int codeCount = 4;//定义图片上显示验证码的个数
    private int xx = 150;
    private int fontHeight = 180;
    private int codeY = 160;
    char[] codeSequence = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
            'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W',
            'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };

    @RequestMapping("/code")
    public void getCode(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        // 定义图像buffer
        BufferedImage buffImg = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_RGB);
//      Graphics2D gd = buffImg.createGraphics();
        //Graphics2D gd = (Graphics2D) buffImg.getGraphics();
        Graphics gd = buffImg.getGraphics();
        // 创建一个随机数生成器类
        Random random = new Random();
        // 将图像填充为白色
        gd.setColor(Color.WHITE);
        gd.fillRect(0, 0, width, height);

        // 创建字体，字体的大小应该根据图片的高度来定。
        Font font = new Font("宋体", Font.BOLD, fontHeight);
        // 设置字体。
        gd.setFont(font);

        // 画边框。
        gd.setColor(Color.GRAY);
        gd.drawRect(0, 0, width-1, height-1);

        // 随机产生10条干扰线，使图象中的认证码不易被其它程序探测到。
        gd.setColor(Color.BLACK);
        for (int i = 0; i < 100; i++) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            int xl = random.nextInt(120);
            int yl = random.nextInt(120);
            gd.drawLine(x, y, x + xl, y + yl);
        }

        // randomCode用于保存随机产生的验证码，以便用户登录后进行验证。
        StringBuffer randomCode = new StringBuffer();
        int red = 0, green = 0, blue = 0;

        // 随机产生codeCount数字的验证码。
        for (int i = 0; i < codeCount; i++) {
            // 得到随机产生的验证码数字。
            String code = String.valueOf(codeSequence[random.nextInt(36)]);
            // 产生随机的颜色分量来构造颜色值，这样输出的每位数字的颜色值都将不同。
            red = random.nextInt(255);
            green = random.nextInt(255);
            blue = random.nextInt(255);

            // 用随机产生的颜色将验证码绘制到图像中。
            gd.setColor(new Color(red, green, blue));

            gd.drawString(code, (i + 1) * xx, codeY);

            // 将产生的四个随机数组合在一起。
            randomCode.append(code);
        }
        // 将四位数字的验证码保存到Session中。
        HttpSession session = req.getSession();
        System.out.print(randomCode);
        session.setAttribute("code", randomCode.toString());

        // 禁止图像缓存。
        resp.setHeader("Pragma", "no-cache");
        resp.setHeader("Cache-Control", "no-cache");
        resp.setDateHeader("Expires", 0);

        resp.setContentType("image/jpeg");

        // 将图像输出到Servlet输出流中。
        ServletOutputStream sos = resp.getOutputStream();
        ImageIO.write(buffImg, "jpeg", sos);
        sos.close();
    }
}

@Controller
class CreateIMG2{
    private int weight=100;             //验证码图片的长和宽
    private int height=50;
    private String text;                //用来保存验证码的文本内容
    private Random r=new Random();      //获取随机数对象
    private String[] fontNames={"宋体","华文楷体", "黑体", "微软雅黑", "楷体_GB2312"};   //字体数组
    private String codes="23456789abcdefghjkmnopqrstuvwxyzABCDEFGHJKMNPQRSTUVWXYZ";    //验证码数组

    private Color randomColor()          //获取随机的颜色
    {
        int r=this.r.nextInt(150);        //这里为什么是150，因为当r，g，b都为255时，即为白色，为了好辨认，需要颜色深一点。
        int g=this.r.nextInt(150);
        int b=this.r.nextInt(150);
        return new Color(r,g,b);          //返回一个随机颜色
    }

    private Font randomFont()              //获取随机字体
    {
        int index=r.nextInt(fontNames.length);      //获取随机的字体
        String fontName=fontNames[index];
        int style=r.nextInt(4);            //随机获取字体的样式，0是无样式，1是加粗，2是斜体，3是加粗加斜体
        int size=r.nextInt(5)+24;              //随机获取字体的大小
        return new Font(fontName,style,size);   //返回一个随机的字体
    }

    private char randomChar()           //获取随机字符
    {
        int index=r.nextInt(codes.length());
        return codes.charAt(index);
    }

    private void drawLine(BufferedImage image)             //画干扰线，验证码干扰线用来防止计算机解析图片
    {
        int num=3;                                         //定义干扰线的数量
        Graphics2D g=(Graphics2D) image.getGraphics();
        for(int i=0;i<num;i++)
        {
            int x1=r.nextInt(weight);
            int y1=r.nextInt(height);
            int x2=r.nextInt(weight);
            int y2=r.nextInt(height);
            g.setColor(randomColor());
            g.drawLine(x1, y1, x2, y2);
        }
    }

    private BufferedImage  createImage()           //创建图片的方法
    {
        BufferedImage image=new BufferedImage(weight,height,BufferedImage.TYPE_INT_RGB); //创建图片缓冲区
        Graphics2D g=(Graphics2D) image.getGraphics();     //获取画笔
        g.setColor(Color.GRAY);                 //设置背景色
        g.fillRect(0, 0, weight, height);
        return image;                           //返回一个图片
    }
    public BufferedImage getImage()             //获取验证码图片的方法
    {
        BufferedImage image=createImage();
        Graphics2D g=(Graphics2D) image.getGraphics();     //获取画笔
        StringBuilder sb=new StringBuilder();
        for(int i=0;i<4;i++)                    //画四个字符即可
        {
            String s=randomChar()+"";                           //随机生成字符，因为只有画字符串的方法，没有画字符的方法，所以需要将字符变成字符串再画
            sb.append(s);                                  //添加到StringBuilder里面
            float x=i*1.0F*weight/4;                     //定义字符的x坐标
            g.setFont(randomFont());                      //设置字体，随机
            g.setColor(randomColor());                    //设置颜色，随机
            g.drawString(s, x, height-5);
        }
        this.text=sb.toString();
        drawLine(image);
        return image;
    }

    public String getText()                             //获取验证码文本的方法
    {
        return text;
    }

    public static void output(BufferedImage image,OutputStream out) throws IOException                  //将验证码图片写出的方法
    {
        ImageIO.write(image, "JPEG", out);
    }
    
    @RequestMapping("code1")
    public void getcode(HttpServletResponse resp) throws IOException {
        BufferedImage buffImg = getImage();

        // 禁止图像缓存。
        resp.setHeader("Pragma", "no-cache");
        resp.setHeader("Cache-Control", "no-cache");
        resp.setDateHeader("Expires", 0);

        resp.setContentType("image/jpeg");

        // 将图像输出到Servlet输出流中。
        ServletOutputStream sos = resp.getOutputStream();
        ImageIO.write(buffImg, "jpeg", sos);
        sos.close();
    }
}