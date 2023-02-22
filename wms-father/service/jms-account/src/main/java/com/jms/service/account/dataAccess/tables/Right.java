package com.jms.service.account.dataAccess.tables;

import com.jfcore.orm.Column;
import com.jfcore.orm.IdAuto;
import com.jfcore.orm.Table;

import lombok.Getter;
import lombok.Setter;


@Setter@Getter
@IdAuto
@Table (name="u_right",key="id", uniqueKey = "xxx")
public class Right {

	/*   id      */
	@Column(name = "id",lable = "")
	private Integer id;

	/*   name      */
	@Column(name = "name",lable = "")
	private String name;

	/*   type   类型（menu,page,button）   */
	@Column(name = "type",lable = "类型（menu,page,button）")
	private String type;

	/*   module   模块   */
	@Column(name = "module",lable = "模块")
	private String module;

	/*   key   权限key   */
	@Column(name = "key",lable = "权限key")
	private String key;
	
	
	
	
	
}
