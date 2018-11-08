package com.sinosoft.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.velocity.VelocityContext;

import com.sinosoft.common.PDFUtil;
import com.sinosoft.domain.FirstInforList;
import com.sinosoft.domain.FirstInformation;

/**
 * @author ZZE
 * @date 2018年4月18日
 * @Description
 */
public class FirstTest {
	
	static PDFUtil pdfUtil ;
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	static FirstInformation firstInformation = new FirstInformation();
	static List<FirstInforList> firstinforList = new ArrayList<FirstInforList>();
	static {
		firstInformation.setBirthday("1993-09-09");
		firstInformation.setBusinessName("");
		firstInformation.setBusinessNumber("");
		firstInformation.setCertificateNumber("2303100001002");
		firstInformation.setCustomerId("0002484350");
		firstInformation.setIdNumber("352203111111111110");
		firstInformation.setIdType("身份证");
		firstInformation.setInsuranceCompanySeal("");
		firstInformation.setInsuredNumber("0002484350/中英1");
		firstInformation.setName(" 中英1");
		firstInformation.setOccupation("一级");
		firstInformation.setPrintTime("");
		firstInformation.setSex("男");
		firstInformation.setTeamId("2405100000026");
		firstInformation.setUnitName("中英测试");
		firstInformation.setUnitNumber("0000038888");
		firstInformation.setSpecialAgreement("特别约定内容!备注：其他未明事项，请查询中英一年期团体定期寿险，中英团体意外伤害保险，中医附加住院团体医疗保险，中英附加团体综合医疗"
+"保险，中英附加疾病住院津贴团体医疗保险条款。");
		firstInformation.setClause("中英一年期团体定期寿险，中英团体意外伤害保险，中医附加住院团体医疗保险，中英附加团体综合医疗保险，中英附加疾病住院津贴团体医疗保险");
		
		FirstInforList inforList1 = new FirstInforList();
		inforList1.setGuaranteeAmount("200000.00");
		inforList1.setInsuranceLiabilityTime("2017-09-09至2019-09-09");
		inforList1.setInsuranceName("中英一年期团体定期寿险");
		firstinforList.add(inforList1);
		FirstInforList inforList2 = new FirstInforList();
		inforList2.setGuaranteeAmount("500000.00");
		inforList2.setInsuranceLiabilityTime("2017-09-09至2019-09-09");
		inforList2.setInsuranceName("中英一年期团体定期寿险");
		firstinforList.add(inforList2);
		FirstInforList inforList3 = new FirstInforList();
		inforList3.setGuaranteeAmount("1000000.00");
		inforList3.setInsuranceLiabilityTime("2017-09-09至2019-09-09");
		inforList3.setInsuranceName("中英一年期团体意外伤害险");
		firstinforList.add(inforList3);
	}
	
	public static void main(String[] args) {
		multiMakePDF("5.html");
		System.out.println("success");
	}
	
	public static boolean multiMakePDF(String mainName){
		boolean result=false;//判断是否生成成功
		//获取所生成pdf文档所需要的数据
		List<VelocityContext> contextList = new ArrayList<VelocityContext>();//模板文件中需要组装的数据列表
		try {
			VelocityContext context = new VelocityContext();// 模板文件中需要组装的数据
			context.put("firstInformation", firstInformation);
			context.put("firstinforList", firstinforList);
			contextList.add(context);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String modelPath = PDFUtil.class.getClassLoader().getResource("main").getPath();//获取模板存放的根路径
		String picPath = PDFUtil.class.getClassLoader().getResource("img").getPath();//获取模板文件中所用到的图片的路径
		String pdfPath = "D:/PDF" +"/"+ sdf.format(new Date());//所生成pdf文档存放的路径
		PDFUtil.createDirectory(pdfPath);//生成pdf文档存放的路径，如果该目录不存在则新建该目录
		try{
			for(int i = 0; i < contextList.size(); i++){
				VelocityContext context = contextList.get(i);
				PDFUtil.initTemplate(modelPath);//初始化模板根路径
				context.put("rootPath", modelPath);
				context.put("picPath", picPath);//模板中所用图片的路径
				String path = pdfPath+"/"+sdf.format(new Date())+"-5-1.pdf";//生成的pdf文档完整路径（路径+文件名）
				String htmlCode = PDFUtil.getHtmlByTemplate(mainName, context);//组装数据完成后模板的内容
				PDFUtil.createPDF(htmlCode, path);//生成pdf
			}
			result = true;
		}catch(Exception e){
			e.printStackTrace();
			result = false;
		}
		return result;
	}

}
