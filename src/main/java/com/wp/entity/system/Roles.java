package com.wp.entity.system;
/**
 * 
* 类名称：Roles.java
* 类描述： 
* @author FH
* 作者单位： 
* 联系方式：
* 创建时间：2014年3月10日
* @version 1.0
 */
public class Roles {
	private String USER_ID;
	private String ROLE_ID;
	private String NAME;
	private String post;
	private String PHONE;
	private String RIGHTS;
	private String PARENT_ID;
	private String ADD_QX;
	private String DEL_QX;
	private String EDIT_QX;
	private String CHA_QX;
	private String QX_ID;
	private String factory_id;

	public String getFactory_id() {
		return factory_id;
	}

	public void setFactory_id(String factory_id) {
		this.factory_id = factory_id;
	}

	public String getROLE_ID() {
		return ROLE_ID;
	}

	public void setROLE_ID(String ROLE_ID) {
		this.ROLE_ID = ROLE_ID;
	}

	public String getUSER_ID() {
		return USER_ID;
	}

	public void setUSER_ID(String USER_ID) {
		this.USER_ID = USER_ID;
	}

	public String getNAME() {
		return NAME;
	}

	public void setNAME(String NAME) {
		this.NAME = NAME;
	}

	public String getPost() {
		return post;
	}

	public void setPost(String post) {
		this.post = post;
	}

	public String getPHONE() {
		return PHONE;
	}

	public void setPHONE(String PHONE) {
		this.PHONE = PHONE;
	}

	public String getRIGHTS() {
		return RIGHTS;
	}

	public void setRIGHTS(String RIGHTS) {
		this.RIGHTS = RIGHTS;
	}

	public String getPARENT_ID() {
		return PARENT_ID;
	}

	public void setPARENT_ID(String PARENT_ID) {
		this.PARENT_ID = PARENT_ID;
	}

	public String getADD_QX() {
		return ADD_QX;
	}

	public void setADD_QX(String ADD_QX) {
		this.ADD_QX = ADD_QX;
	}

	public String getDEL_QX() {
		return DEL_QX;
	}

	public void setDEL_QX(String DEL_QX) {
		this.DEL_QX = DEL_QX;
	}

	public String getEDIT_QX() {
		return EDIT_QX;
	}

	public void setEDIT_QX(String EDIT_QX) {
		this.EDIT_QX = EDIT_QX;
	}

	public String getCHA_QX() {
		return CHA_QX;
	}

	public void setCHA_QX(String CHA_QX) {
		this.CHA_QX = CHA_QX;
	}

	public String getQX_ID() {
		return QX_ID;
	}

	public void setQX_ID(String QX_ID) {
		this.QX_ID = QX_ID;
	}

	@Override
	public String toString() {
		return "Roles{" +
				"USER_ID='" + USER_ID + '\'' +
				", ROLE_ID='" + ROLE_ID + '\'' +
				", NAME='" + NAME + '\'' +
				", post='" + post + '\'' +
				", PHONE='" + PHONE + '\'' +
				", RIGHTS='" + RIGHTS + '\'' +
				", PARENT_ID='" + PARENT_ID + '\'' +
				", ADD_QX='" + ADD_QX + '\'' +
				", DEL_QX='" + DEL_QX + '\'' +
				", EDIT_QX='" + EDIT_QX + '\'' +
				", CHA_QX='" + CHA_QX + '\'' +
				", QX_ID='" + QX_ID + '\'' +
				", factory_id='" + factory_id + '\'' +
				'}';
	}
}
