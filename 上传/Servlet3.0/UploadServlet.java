package com.itheima.upload;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.websocket.Session;

import com.itheima.utils.UUIDUtils;
import com.itheima.utils.UploadUtils;

/**
 * Servlet implementation class UploadServlet
 */
@WebServlet("/UploadServlet")
@MultipartConfig
public class UploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
    public UploadServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String filedesc = request.getParameter("filedesc");
		System.out.println(filedesc);
		Part part = request.getPart("upload");
		long size = part.getSize();
		String name = part.getName();
		System.out.println(size+" "+name);
		String header = part.getHeader("Content-Disposition");
		System.out.println(header);
		int idx = header.lastIndexOf("filename=\"");
		String filename = header.substring(idx+10, header.length()-1);
		System.out.println(filename);
		InputStream is = part.getInputStream();
		String path = this.getServletContext().getRealPath("/555");
		System.out.println(path);
		String uuidFileName = UUIDUtils.getUUIDFileName(filename);
		String realPath = path + UploadUtils.getPath(uuidFileName);
		File file = new File(realPath);
		if(!file.exists()){
			file.mkdirs();
		}
		OutputStream os = new FileOutputStream(realPath+"/"+uuidFileName);
		byte[] b = new byte[1024];
		int len;
		while((len=is.read(b))!=-1){
			os.write(b, 0, len);
		}
		is.close();
		os.close();
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
