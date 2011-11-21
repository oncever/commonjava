package org.commonjava.web.servlet;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class GradientGenerator extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			int w = Integer.parseInt(req.getParameter("w"));
			int h = Integer.parseInt(req.getParameter("h"));
			Color c1 = cor(req.getParameter("c1"));
			Color c2 = cor(req.getParameter("c2"));
		
			BufferedImage bufferedImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
			Graphics2D g2d = (Graphics2D)(Graphics2D) bufferedImage.getGraphics();
			GradientPaint gradient = new GradientPaint(0,0,c1,w,h,c2,true);
			g2d.setPaint(gradient);
			g2d.fillRect(0,0,w,h);
			Color s2 = Color.yellow;
			Color e1 = Color.pink;
			GradientPaint gradient1 = new GradientPaint(10,10,s2,30,30,e1,true);
			g2d.setPaint(gradient1);
			g2d.fillRect(99,99,199,119);
			
			ImageIO.write(bufferedImage, "png", resp.getOutputStream());
			resp.getOutputStream().flush();
			resp.getOutputStream().close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private static Color cor(String hex) {
		int h1 = Integer.parseInt(hex.substring(0,2), 16);
		int h2 = Integer.parseInt(hex.substring(2,4), 16);
		int h3 = Integer.parseInt(hex.substring(4,6), 16);
		int h4 = Integer.parseInt(hex.substring(6,8), 16);
		Color color = new Color(h1, h2, h3, h4);
		return color;
	}
}
