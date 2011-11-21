package org.commonjava.web.servlet;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class WatermarkGenerator extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			String parameter = req.getParameter("img");
			Color c = cor(req.getParameter("c"));
			String file = getServletContext().getRealPath(parameter);
			BufferedImage read = ImageIO.read(new FileInputStream(file));
		
			BufferedImage bufferedImage = new BufferedImage(read.getWidth(), read.getHeight(), read.getType());
			for (int i = 0; i < read.getWidth(); i++) {
				for (int j = 0; j < read.getHeight(); j++) {
					int rgb = read.getRGB(i, j);
					Color color = new Color(rgb, true);
					int alpha 	= color.getAlpha();
					int red 	= color.getRed()+c.getRed();
					int blue 	= color.getBlue()+c.getBlue();
					int green 	= color.getGreen()+c.getGreen();
					if(red 	<0) red  =0;
					if(blue <0) blue =0;
					if(green<0) green=0;
					if(red 	>255) red  =255;
					if(blue >255) blue =255;
					if(green>255) green=255;
					bufferedImage.setRGB(i, j, new Color(red, green, blue, alpha).getRGB());
				}
			}
			
			
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
		Color color = new Color(h1, h2, h3, 255);
		return color;
	}
}
