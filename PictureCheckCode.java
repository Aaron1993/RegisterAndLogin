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
    //�÷��������������ɫ
    public Color getRandColor(int s,int e){
    	Random ron=new Random();
    	if(s>255) s=255;
    	if(e>255) e=255;
    	int r,g,b;
    	r=s+ron.nextInt(e-s);//�������RGB�е�Rֵ
    	g=s+ron.nextInt(e-s);//�������RGB�е�Gֵ
    	b=s+ron.nextInt(e-s);//�������RGB�е�Bֵ
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
		//���ò�������Ƭ
		response.setHeader("pragma", "No-cache");
		response.setHeader("Cache-Control","No-cache");
		response.setDateHeader("Expires", 0);
		//ָ�����ɵ���ӦͼƬ һ��Ҫ����仰 ��������
		response.setContentType("image/jpeg");
		int width=86;int height=30;//ָ����ʾ��֤��ĸ߶ȺͿ��
		//����BufferedImage�Ķ���  �������൱��һ��ͼƬ
		BufferedImage image=new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics graphics=image.getGraphics();//����Graphics���� �����൱�ڻ���
		Graphics2D g2d=(Graphics2D)graphics;
		Random random=new Random();
		Font mFont=new Font("����", Font.BOLD, 16);//����������ʽ
		graphics.setColor(getRandColor(200, 250));
		graphics.fillRect(0, 0, width, height);//���Ʊ���
		graphics.setFont(mFont);
		graphics.setColor(getRandColor(180, 200));
		//�����������������
		for(int i=0;i<100;i++){
			int x=random.nextInt(width-1);
			int y=random.nextInt(height-1);
			int x1=random.nextInt(6)+1;
			int y1=random.nextInt(12)+1;
			BasicStroke bs=new BasicStroke(2f,BasicStroke.CAP_BUTT,BasicStroke.JOIN_BEVEL);//����������ʽ
			Line2D line=new Line2D.Double(x1, y1, x+x1, y+y1);
			g2d.setStroke(bs);
			g2d.draw(line);//����ֱ��
		}
		//��������� Ӣ����ĸ ������������ɵ���֤����  
		String sRand="";
		String ctmp="";
		int item=0;
		//ָ���������֤��Ϊ4λ
		for (int i = 0; i < 4; i++) {
			switch(random.nextInt(3)){
			case 1:  //����A-Z����ĸ
				item=random.nextInt(26)+65;
				ctmp=String.valueOf((char)item);
				break;
			case 2:  //���ɺ���
				String rBase[]={"0","1","2","3","4","5","6","7","8","9","a","b","c","d","e","f"};
				//���ɵ�һλ����
				int r1=random.nextInt(3)+11;
				//System.out.println("��һλ����  "+r1);
				String str_r1=rBase[r1];
			//	System.out.println(str_r1);
				//���ɵڶ�λ����
				int r2;
				if(r1==13){
					r2=random.nextInt(7);
				}else {
					r2=random.nextInt(16);
				}
				String str_r2=rBase[r2];
			//	System.out.println("=======>"+str_r2);
				//���ɵ�һλλ��  
                int r3=random.nextInt(6)+10;  
                String str_r3=rBase[r3];  
                //���ɵڶ�λλ��  
                int r4;  
                if(r3==10){  
                    r4=random.nextInt(15)+1;  
                }else if(r3==15){  
                    r4=random.nextInt(15);  
                }else{  
                    r4=random.nextInt(16);  
                }  
                String str_r4=rBase[r4];  
                //�����ɵĻ�����ת��Ϊ����  
                byte[] bytes=new byte[2];  
                //�����ɵ����뱣�浽�ֽ�����ĵ�һ��Ԫ����  
                String str_12=str_r1+str_r2;  
                int tempLow=Integer.parseInt(str_12, 16);  
                bytes[0]=(byte) tempLow;  
                //�����ɵ�λ�뱣�浽�ֽ�����ĵڶ���Ԫ����  
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
       //�����ɵ����������������Ų���ת�ƶ��Ƕ� PS.���鲻Ҫ�����ֽ�����������ת,��Ϊ����ͼƬ���ܲ�������ʾ  
       /*��������ת�ƶ��Ƕ�*/  
       Graphics2D g2d_word=(Graphics2D)graphics;  
       AffineTransform trans=new AffineTransform();  
       trans.rotate((45)*3.14/180,15*i+8,7);  
       /*��������*/  
       float scaleSize=random.nextFloat()+0.8f;  
       if(scaleSize>1f) scaleSize=1f;  
       trans.scale(scaleSize, scaleSize);  
       g2d_word.setTransform(trans);  
       graphics.drawString(ctmp, 15*i+18, 14);  
   }  
   HttpSession session=request.getSession(true);  
   session.setAttribute("randCheckCode", sRand);  
   graphics.dispose();    //�ͷ�graphics��ռ�õ�ϵͳ��Դ  
   ImageIO.write(image,"JPEG",response.getOutputStream()); //���ͼƬ  
}  
	} 
