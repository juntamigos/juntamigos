package es.jesmon.repository.util;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.CriteriaQuery;
import org.hibernate.criterion.SimpleProjection;
import org.hibernate.type.Type;
import org.hibernate.internal.util.StringHelper;

public class TruncProjection extends SimpleProjection { 
	protected final String propertyName; 
	private final String aggregate; 
	private final String format; 

	public TruncProjection(String aggregate, String propertyName, String format) { 
	this.aggregate = aggregate; 
	this.propertyName = propertyName; 
	this.format = format; 
	} 

	public String toString() { 
	return aggregate + "(" + propertyName + "," + format+ ')'; 
	} 

	public Type[] getTypes(Criteria criteria, CriteriaQuery criteriaQuery) 
	throws HibernateException { 
	return new Type[] { criteriaQuery.getType(criteria, propertyName) }; 
	} 

	public String toSqlString(Criteria criteria, int loc, CriteriaQuery criteriaQuery) 
	throws HibernateException { 
	StringBuffer buffer = new StringBuffer() 
	.append(aggregate) 
	.append("(") 
	.append( criteriaQuery.getColumn(criteria, propertyName)) 
	.append( ", '"+format+"'" ).append(") as y") 
	.append(loc) 
	.append('_'); 
	return buffer.toString(); 
	} 

	public boolean isGrouped() { 
	return true; 
	} 
	/* 
	public String toGroupSqlString(Criteria criteria, CriteriaQuery criteriaQuery) 
	throws HibernateException { 
	throw new UnsupportedOperationException("not a grouping projection"); 
	}*/ 
	public String toGroupSqlString(Criteria criteria, CriteriaQuery criteriaQuery) 
	throws HibernateException { 
	return StringHelper.replace( aggregate+"("+criteriaQuery.getColumn(criteria, propertyName)+", '"+format+"')", "{alias}", criteriaQuery.getSQLAlias(criteria) ); 
	} 
	} 
