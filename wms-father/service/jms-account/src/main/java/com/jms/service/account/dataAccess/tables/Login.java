package com.jms.service.account.dataAccess.tables;

import java.util.Date;

import com.jfcore.orm.Column;
import com.jfcore.orm.IdAuto;
import com.jfcore.orm.Table;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
@IdAuto
@Table (name="u_login",key="id", uniqueKey = "login_key")
public class Login {
	
	/*   id      */
	@Column(name = "id",lable = "")
	private Integer id;

	/*   uid      */
	@Column(name = "uid",lable = "")
	private Integer uid;

	/*   login_key      */
	@Column(name = "login_key",lable = "")
	private String loginKey;

	/*   ctime      */
	@Column(name = "ctime",lable = "")
	private Date ctime;

	/*   browser      */
	@Column(name = "browser",lable = "")
	private String browser;
	
	

}
