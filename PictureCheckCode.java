package com.suhailong;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class PictureCheckCode
 */
public class PictureCheckCode extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public PictureCheckCode() {
        super();
    }
    public void destroy(){
    	super.destroy();
    }
    public void init() throws ServletException {
    	super.init();
    }
    //该方法是随机生成颜色
    public Color getRandColor(int s,int e){
    	Random ron=new Random();
    	if(s>255) s=255;
    	if(e>255) e=255;
    	int r,g,b;
    	r=s+ron.nextInt(e-s);//随机生成RGB中的R值
    	g=s+ron.nextInt(e-s);//随机生成RGB中的G值
    	b=s+ron.nextInt(e-s);//随机生成RGB中的B值
    	return new Color(r, g, b);
    }
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//设置不缓存照片
		response.setHeader("pragma", "No-cache");
		response.setHeader("Cache-Control","No-cache");
		response.setDateHeader("Expires", 0);
		//指定生成的响应图片 一定要有这句话 否则会出错
		response.setContentType("image/jpeg");
		int width=86;int height=30;//指定显示验证码的高度和宽度
		//创建BufferedImage的对象  其作用相当于一张图片
		BufferedImage image=new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics graphics=image.getGraphics();//创建Graphics对象 作用相当于画笔
		Graphics2D g2d=(Graphics2D)graphics;
		Random random=new Random();
		Font mFont=new Font("楷体", Font.BOLD, 16);//定义字体样式
		graphics.setColor(getRandColor(200, 250));
		graphics.fillRect(0, 0, width, height);//绘制背景
		graphics.setFont(mFont);
		graphics.setColor(getRandColor(180, 200));
		//绘制随机产生的线条
		for(int i=0;i<100;i++){
			int x=random.nextInt(width-1);
			int y=random.nextInt(height-1);
			int x1=random.nextInt(6)+1;
			int y1=random.nextInt(12)+1;
			BasicStroke bs=new BasicStroke(2f,BasicStroke.CAP_BUTT,BasicStroke.JOIN_BEVEL);//定制线条样式
			Line2D line=new Line2D.Double(x1, y1, x+x1, y+y1);
			g2d.setStroke(bs);
			g2d.draw(line);//绘制直线
		}
		//输出由数字 英文字母 和中文随机生成的验证文字  
		String sRand="";
		String ctmp="";
		int item=0;
		//指定输出的验证码为4位
		for (int i = 0; i < 4; i++) {
			switch(random.nextInt(3)){
			case 1:  //生成A-Z的字母
				item=random.nextInt(26)+65;
				ctmp=String.valueOf((char)item);
				break;
			case 2:  //生成汉字
				String rBase[]={"0","1","2","3","4","5","6","7","8","9","a","b","c","d","e","f"};
				//生成第一位区码
				int r1=random.nextInt(3)+11;
				//System.out.println("第一位区码  "+r1);
				String str_r1=rBase[r1];
			//	System.out.println(str_r1);
				//生成第二位区码
				int r2;
				if(r1==13){
					r2=random.nextInt(7);
				}else {
					r2=random.nextInt(16);
				}
				String str_r2=rBase[r2];
			//	System.out.println("=======>"+str_r2);
				//生成第一位位码  
                int r3=random.nextInt(6)+10;  
                String str_r3=rBase[r3];  
                //生成第二位位码  
                int r4;  
                if(r3==10){  
                    r4=random.nextInt(15)+1;  
                }else if(r3==15){  
                    r4=random.nextInt(15);  
                }else{  
                    r4=random.nextInt(16);  
                }  
                String str_r4=rBase[r4];  
                //将生成的机内码转换为汉字  
                byte[] bytes=new byte[2];  
                //将生成的区码保存到字节数组的第一个元素中  
                String str_12=str_r1+str_r2;  
                int tempLow=Integer.parseInt(str_12, 16);  
                bytes[0]=(byte) tempLow;  
                //将生成的位码保存到字节数组的第二个元素中  
                String str_34=str_r3+str_r4;  
                int tempHigh=Integer.parseInt(str_34, 16);  
                bytes[1]=(byte)tempHigh;  
                ctmp=new String(bytes);  
                break;  
           default:  
                item=random.nextInt(10)+48;  
                ctmp=String.valueOf((char)item);  
                break;  
       }  
       sRand+=ctmp;  
       Color color=new Color(20+random.nextInt(110),20+random.nextInt(110),random.nextInt(110));  
       graphics.setColor(color);  
       //将生成的随机数进行随机缩放并旋转制定角度 PS.建议不要对文字进行缩放与旋转,因为这样图片可能不正常显示  
       /*将文字旋转制定角度*/  
       Graphics2D g2d_word=(Graphics2D)graphics;  
       AffineTransform trans=new AffineTransform();  
       trans.rotate((45)*3.14/180,15*i+8,7);  
       /*缩放文字*/  
       float scaleSize=random.nextFloat()+0.8f;  
       if(scaleSize>1f) scaleSize=1f;  
       trans.scale(scaleSize, scaleSize);  
       g2d_word.setTransform(trans);  
       graphics.drawString(ctmp, 15*i+18, 14);  
   }  
   HttpSession session=request.getSession(true);  
   session.setAttribute("randCheckCode", sRand);  
   graphics.dispose();    //释放graphics所占用的系统资源  
   ImageIO.write(image,"JPEG",response.getOutputStream()); //输出图片  
}  
	} 
