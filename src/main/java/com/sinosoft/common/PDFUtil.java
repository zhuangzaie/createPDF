package com.sinosoft.common;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import java.io.IOException;
import java.io.OutputStream;
import java.io.StringWriter;
import java.util.Hashtable;
import java.util.Properties;

import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;
import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.BaseFont;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.util.StringUtils;

public class PDFUtil {
	
	
	@SuppressWarnings("rawtypes")
	private static Hashtable tempMap=new Hashtable();


	/**
	 * 创建PDF
	 * 
	 * @param htmlCode
	 * @param path
	 * @throws FileNotFoundException 
	 * @throws IOException
	 * @throws DocumentException
	 */
	public static void createPDF(String htmlCode, String path) throws FileNotFoundException{
		OutputStream os = null ;
		try {
			ITextRenderer renderer = new ITextRenderer();
			// step 3 解决中文支持
			ITextFontResolver fontResolver = renderer.getFontResolver();
			String fontPath = PDFUtil.class.getClassLoader()
					.getResource("font").getPath();// 字体文件在项目中的路径
			File fontDir = new File(fontPath);// 字体文件目录
			File[] files = fontDir.listFiles();// 字体文件目录下的所有字体文件
			for (int i = 0; i < files.length; i++) {// 循环遍历设置字体
				String key = files[i].getName();
				fontResolver.addFont(fontPath + "/" + key, BaseFont.IDENTITY_H,
						BaseFont.NOT_EMBEDDED);
			}
			// 设置PDF文档
			//htmlCode=htmlCode.replaceAll(\"&\", \"&amp;\");
			renderer.setDocumentFromString(htmlCode);
			renderer.layout();
			os = new FileOutputStream(path);
			// 生成PDF
			renderer.createPDF(os);
			os.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
            if (os != null){
            	try {
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
            }
        }
	}
	
	
	
	/**
	 * 检测路径是否存在，不存在生成 生成成功：true 生成失败：false
	 * 
	 * @param filepath
	 */
	final public static boolean createDirectory(String filepath) {
		File file = new File(filepath);
		boolean result = true;
		if (!file.exists() && !file.isDirectory()) {
			try {
				result = file.mkdirs();
			} catch (Exception e) {
				result = false;
				e.printStackTrace();
				System.out.println("Create the file directory failed：--" + filepath);
			}
		}
		return result;
	}
	
	/**
	 * 初始化模板引擎
	 * 
	 * @param rootPath
	 *            模板路径
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static void initTemplate(String rootPath) throws Exception {
		// 设置属性
		if(null==tempMap.get(rootPath)){
			Properties properties = new Properties();
			properties.setProperty(Velocity.FILE_RESOURCE_LOADER_PATH, rootPath);
			properties.setProperty(Velocity.ENCODING_DEFAULT, "UTF-8");
			properties.setProperty(Velocity.INPUT_ENCODING, "UTF-8");
			properties.setProperty(Velocity.OUTPUT_ENCODING, "UTF-8");
			VelocityEngine velocity = new  VelocityEngine();
			velocity.init(properties);
			tempMap.put(rootPath, velocity);
		}
	}
	
	/**
	 * 获取单模板HTML
	 * 
	 * @param tempName
	 *            模板文件名
	 * @param context
	 *            要组装的数据
	 * @return
	 * @throws ResourceNotFoundException
	 * @throws ParseErrorException
	 * @throws Exception
	 */
	public static String getHtmlByTemplate(String tempName,
			VelocityContext context) throws ResourceNotFoundException,
			ParseErrorException, Exception {
		if(!context.containsKey("stringUtils")){
			context.put("stringUtils", new StringUtils());  
		}
		if(!context.containsKey("PDFUtil")){
			context.put("PDFUtil", new PDFUtil());  
		}
		String result = "";
		// 赋值
		VelocityEngine velocity=(VelocityEngine) tempMap.get(context.get("rootPath").toString());
		Template template = velocity.getTemplate(tempName);
		StringWriter sw = new StringWriter();
		template.merge(context, sw);
		// 获取最后的HTML代码
		result = sw.toString();
		return result;
	}
	
}
