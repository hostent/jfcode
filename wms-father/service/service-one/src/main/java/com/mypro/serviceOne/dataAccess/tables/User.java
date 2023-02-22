package com.mypro.serviceOne.dataAccess.tables;

import java.util.Date;

import com.jfcore.frame.Result;
import com.jfcore.orm.Column;
import com.jfcore.orm.IdAuto;
import com.jfcore.orm.Table;
import com.mypro.serviceOne.dataAccess._userDb;

 

@IdAuto //id 自增
//@Cache
@Table(name = "t_user",key = "id")
public class User {
	
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "reg_date")
	private Date regDate;
	
	@Column(name = "point")
	private Double point;
	
	
	
	public Result checkSave()
	{
		
		// TODO
		
		return Result.succeed();
		
	}
	
	
	
	public Result save()
	{
		
		Result res = checkSave();
		if(!res.isSucceed())
		{
			return res;
		}
		
		if(id==null)
		{
			id = Integer.parseInt(_userDb.getUserSet().Add(this).toString());
		}
		else
		{
			_userDb.getUserSet().Update(this);
		}		
		
		
		
		return Result.succeed(id);
	}
	
	
	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getRegDate() {
		return regDate;
	}

	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}

	public Double getPoint() {
		return point;
	}

	public void setPoint(Double point) {
		this.point = point;
	}


	

}
