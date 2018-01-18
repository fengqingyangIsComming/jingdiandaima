package com.itheima.c_download;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sun.misc.BASE64Encoder;

/**
 * 文件下载的Servlet
 */
public class DownloadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 1.接收参数
		String filename = new String(request.getParameter("filename").getBytes("ISO-8859-1"),"UTF-8");
		System.out.println(filename);
		// 2.完成文件下载:
		// 2.1设置Content-Type头
		String type = this.getServletContext().getMimeType(filename);
		response.setHeader("Content-Type", type);
		// 2.3设置文件的InputStream.
		String realPath = this.getServletContext().getRealPath("/download/"+filename);
		
		// 根据浏览器的类型处理中文文件的乱码问题:
		String agent = request.getHeader("User-Agent");
		System.out.println(agent);
		if(agent.contains("Firefox")){
			filename = base64EncodeFileName(filename);
		}else{
			filename = URLEncoder.encode(filename,"UTF-8");
		}
		
		// 2.2设置Content-Disposition头
		response.setHeader("Content-Disposition", "attachment;filename="+filename);
		
		InputStream is = new FileInputStream(realPath);
		// 获得response的输出流:
		OutputStream os = response.getOutputStream();
		int len = 0;
		byte[] b = new byte[1024];
		while((len = is.read(b))!= -1){
			os.write(b, 0, len);
		}
		is.close();
	}
	
	public static String base64EncodeFileName(String fileName) {
		BASE64Encoder base64Encoder = new BASE64Encoder();
		try {
			return "=?UTF-8?B?"
					+ new String(base64Encoder.encode(fileName
							.getBytes("UTF-8"))) + "?=";
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
