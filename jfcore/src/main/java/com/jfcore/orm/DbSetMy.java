package com.jfcore.orm;

import java.sql.Connection;
import java.util.*;

 

public class DbSetMy<T> implements ISet<T>,IDbQuery<T> {

	public DbSetMy(String connKey,Class<T> cls) {
		_connKey = connKey;
		query = new DbQueryMy<T>(connKey, cls);
		_tEntity = new Entity<T>(cls);
	}

	public DbSetMy(String connKey, String prefix ,Class<T> cls) {
		_connKey = connKey;
		query = new DbQueryMy<T>(connKey, cls, prefix);
		_tEntity = new Entity<T>(cls,prefix);

	}
	

	private String _connKey;

	private DbQueryMy<T> query;
	private Entity<T> _tEntity;
	
 

	@Override
	public Object Add(T t) {

		String sql = "insert into `{table}` ( {columns} ) values ( {values} );";  
																				 
		String[] columns;

		Object returnId = null;

		boolean isNeedId = false;

		if (_tEntity.isIdAuto) {
			isNeedId = false;
		} else {
			returnId = _tEntity.getIdValue(t);
			isNeedId = true;
		}

		columns = _tEntity.getColumns(isNeedId);

		sql = sql.replace("{table}",  _tEntity.tableName);

		sql = sql.replace("{columns}", "`" + String.join("`,`", columns) + "`");

		sql = sql.replace("{values}", String.join(",", _tEntity.getColumnSymbol(columns)));
		
		Connection conn = MybatisUtils.getConn(_connKey);

		try {
			if (!isNeedId) {
				returnId = BaseSqlHelp.ExecAndScalar(conn, sql, _tEntity.getColumnValues(isNeedId, t));
			} else {
				BaseSqlHelp.ExecSql(conn, sql, _tEntity.getColumnValues(isNeedId, t));
			}
			
		}
		catch (Exception e) {
			 e.printStackTrace();
			 return 0;
		}
		finally {
			MybatisUtils.close();
		}
		
		query.resetCache();
 

		return returnId;
	}

	@Override
	public int Delete(Object id) {

		String sql = "delete from `{table}` where `{key}`=?";

		sql = sql.replace("{table}",  _tEntity.tableName);
		sql = sql.replace("{key}", _tEntity.key);

		Object[] par = new Object[1];
		par[0] = id;

		Connection conn = MybatisUtils.getConn(_connKey);
		
		int result =0;
		try {
			result = BaseSqlHelp.ExecSql(conn, sql, par);
		}
		catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		finally {
			MybatisUtils.close();
		}
		
		query.resetCache();

		return result;
	}

	@Override
	public int Update(T t) {

		String sql = "update `{table}` set {updateStr} where {key}='{id}'";
		sql = sql.replace("{table}", _tEntity.tableName);

		String[] cols = _tEntity.getColumns(false);
		String colStr = "";
		for (int i = 0; i < cols.length; i++) {
			colStr = colStr + "," + String.format("`%s`=?", cols[i]);
		}
		colStr = colStr.substring(1, colStr.length());
		sql = sql.replace("{updateStr}", colStr);
		sql = sql.replace("{key}", _tEntity.key);

		sql = sql.replace("{id}", _tEntity.getIdValue(t).toString());

		Connection conn = MybatisUtils.getConn(_connKey);

		int result =0;
		try {
			 result = BaseSqlHelp.ExecSql(conn, sql, _tEntity.getColumnValues(false, t));
		}catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		finally {
			MybatisUtils.close();
		}
		
		query.resetCache();

		return result;
	}


	@Override
	public IDbQuery<T> Where(String exp, Object... par) {

		return query.Where(exp, par);
	}

	@Override
	public IDbQuery<T> OrderBy(String exp) {

		return query.OrderBy(exp);
	}

	@Override
	public IDbQuery<T> OrderByDesc(String exp) {

		return query.OrderByDesc(exp);
	}

	@Override
	public IDbQuery<T> Limit(int form, int length) {

		return query.Limit(form, length);
	}

	@Override
	public IDbQuery<T> Distinct() {

		return query.Distinct();
	}

	@Override
	public T First() {

		return query.First();
	}

	@Override
	public List<T> ToList() {

		return query.ToList();
	}

	@Override
	public long Count() {

		return query.Count();
	}

	@Override
	public boolean Exist() {

		return query.Exist();
	}

	@Override
	public T Get(Object id) {

		return query.Get(id);
	}

	@Override
	public T GetUnique(Object unique) {

		return query.GetUnique(unique);
	}
	
	@Override
	public Map<String, Double> Sum(String sumColum, String groupColum) {
		
		return query.Sum(sumColum, groupColum);
	}
	
	@Override
	public IDbQuery<T> Select(String... cols) {
		 
		return query.Select(cols);
	}
	

	@Override
	public List<T> getList(List<Integer> ids) {
		return query.getList(ids);
	}
	
	
	@Override
	public int exec(String sql, Object... pars) {

		Connection conn = MybatisUtils.getConn(_connKey);

		int result = 0;
		try {
			result = BaseSqlHelp.ExecSql(conn, sql, pars);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		} finally {
			MybatisUtils.close();
		}

		query.resetCache();

		return result;

	}
	
	@Override
	public String[] getCols(boolean isIncludeId) {
		
		return _tEntity.getColumns(isIncludeId);
		
	}
	
	@Override
	public String getKey() {
		return _tEntity.key;
	}
	
	@Override
	public String getTableName() {
		 
		return _tEntity.tableName;
	}
	

	


}
