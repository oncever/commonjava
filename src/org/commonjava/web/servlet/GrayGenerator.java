package org.commonjava.web.servlet;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class GrayGenerator extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String folder = getInitParameter("folder");
		
		String pathInfo = req.getPathInfo();
		String[] split = pathInfo.split("/");
		String image = split[1];
		for (int i = 2; i < split.length; i++) image+="/"+split[i];
		
		GenericResponseWrapper respW = new GenericResponseWrapper(resp);
		req.getRequestDispatcher("/"+folder+"/"+image).forward(req, respW);
		byte[] data = respW.getData();
		ByteArrayInputStream input = new ByteArrayInputStream(data);
		BufferedImage read = ImageIO.read(input);
		input.close();
		
		BufferedImage bufferedImage = new BufferedImage(read.getWidth(), read.getHeight(), read.getType());
		for (int i = 0; i < read.getWidth(); i++) {
			for (int j = 0; j < read.getHeight(); j++) {
				int rgb = read.getRGB(i, j);
				Color color = new Color(rgb, true);
				int alpha 	= color.getAlpha();
				int red 	= color.getRed();
				int blue 	= color.getBlue();
				int green 	= color.getGreen();
				int media = red+blue+green/3;
				bufferedImage.setRGB(i, j, new Color(media, media, media, alpha).getRGB());
			}
		}
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(bufferedImage, image.split("\\.")[1].toLowerCase(), baos);
		baos.close();
		byte[] byteArray = baos.toByteArray();	
		
		resp.setContentLength(byteArray .length);
		resp.getOutputStream().write(byteArray);
		resp.getOutputStream().flush();
		resp.getOutputStream().close();
	}
}
