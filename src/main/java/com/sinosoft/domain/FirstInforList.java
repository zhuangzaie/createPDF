package com.sinosoft.domain;

public class FirstInforList {
	/** 险种名称**/
	private String insuranceName;
	/** 保障金额**/
	private String guaranteeAmount;
	/** 保险责任起止时间**/
	private String insuranceLiabilityTime;
	
	public String getInsuranceName() {
		return insuranceName;
	}
	public void setInsuranceName(String insuranceName) {
		this.insuranceName = insuranceName;
	}
	public String getGuaranteeAmount() {
		return guaranteeAmount;
	}
	public void setGuaranteeAmount(String guaranteeAmount) {
		this.guaranteeAmount = guaranteeAmount;
	}
	public String getInsuranceLiabilityTime() {
		return insuranceLiabilityTime;
	}
	public void setInsuranceLiabilityTime(String insuranceLiabilityTime) {
		this.insuranceLiabilityTime = insuranceLiabilityTime;
	}
	
}
