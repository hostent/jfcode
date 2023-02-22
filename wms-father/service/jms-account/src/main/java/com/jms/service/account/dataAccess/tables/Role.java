package com.jms.service.account.dataAccess.tables;

import com.jfcore.orm.Column;
import com.jfcore.orm.IdAuto;
import com.jfcore.orm.Table;

import lombok.Getter;
import lombok.Setter;


@Setter@Getter
@IdAuto 
@Table(name = "u_role",key = "id",uniqueKey = "name")
public class Role {
	
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "name")
	private String name;
	
	


}
