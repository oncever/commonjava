package org.commonjava.web.servlet;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
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
public class ResizeImagem extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String folder = getInitParameter("folder");
		
		String pathInfo = req.getPathInfo();
		String[] split = pathInfo.split("/");
		int w = Integer.parseInt(split[1]);
		int h = Integer.parseInt(split[2]);
		String image = split[3];
		for (int i = 4; i < split.length; i++) image+="/"+split[i];
		
		GenericResponseWrapper respW = new GenericResponseWrapper(resp);
		String dispatcherString = "/"+folder+"/"+image;
		req.getRequestDispatcher(dispatcherString).forward(req, respW);
		byte[] data = respW.getData();
		
		if(data.length==0) throw new RuntimeException("Array tem tamanho ZERO!");
		
		ByteArrayInputStream input = new ByteArrayInputStream(data);
		BufferedImage read = ImageIO.read(input);
		input.close();
		
		BufferedImage bufferedImage = resizeImageWithHint(read, w, h);
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(bufferedImage, image.split("\\.")[1].toLowerCase(), baos);
		baos.close();
		byte[] byteArray = baos.toByteArray();		
		
		resp.setContentLength(byteArray .length);
		resp.getOutputStream().write(byteArray);
		resp.getOutputStream().flush();
		resp.getOutputStream().close();
	}
	
	private static BufferedImage resizeImageWithHint(BufferedImage image, int width, int height) {

		BufferedImage resizedImage = new BufferedImage(width, height, image.getType());
		Graphics2D g = resizedImage.createGraphics();
		g.drawImage(image, 0, 0, width, height, null);
		g.dispose();
		g.setComposite(AlphaComposite.Src);

		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		return resizedImage;
	}
}
