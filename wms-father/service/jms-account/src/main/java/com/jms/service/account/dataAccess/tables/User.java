package com.jms.service.account.dataAccess.tables;

 
import com.jfcore.orm.Column;
import com.jfcore.orm.IdAuto;
import com.jfcore.orm.Table;
 

import lombok.Getter;
import lombok.Setter;


@Setter@Getter
@IdAuto 
@Table(name = "u_user",key = "id",uniqueKey = "name")
public class User {
	
	

	@Column(name = "id")
	private Integer id;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "phone")
	private String phone;
	
	@Column(name = "status")
	private Integer status;
	
	@Column(name = "department")
	private String department;
	
	@Column(name = "post")
	private String post;
	
	@Column(name = "admin_flag")
	private Integer adminFlag;
	
	@Column(name = "password")
	private String password;
	
	



}
