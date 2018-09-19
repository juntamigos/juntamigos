package es.jesmon.repository.util;

import org.hibernate.Criteria;
import org.hibernate.EntityMode;
import org.hibernate.HibernateException;
import org.hibernate.criterion.CriteriaQuery;
import org.hibernate.criterion.Criterion;
import org.hibernate.engine.spi.TypedValue;
import org.hibernate.type.StringType;;

public class NormaLikeExpression implements Criterion {

	private static final long serialVersionUID = 8168696298099310278L;
	private final String propertyName;
    private final String valor;

    public NormaLikeExpression(String propertyName, String valor) {
        this.propertyName = propertyName;
        this.valor = valor;
    }

    public String toSqlString(Criteria criteria, CriteriaQuery criteriaQuery) throws HibernateException {
        String[] columns = criteriaQuery.getColumnsUsingProjection(criteria, propertyName);
        if (columns.length!=1) {
            throw new HibernateException("norma may only be used with single-column properties");
        }
        return "norma (" + columns[0] + ") like UPPER(?)";
    }

    public TypedValue[] getTypedValues(Criteria criteria, CriteriaQuery criteriaQuery) throws HibernateException {
        return new TypedValue[] {new TypedValue(new StringType(), new String (valor), EntityMode.POJO)};
    }

    public String toString() {
        //return "month(" + propertyName + ") = " + month;
    	return "norma (" + propertyName + ") like UPPER(" + valor + ")";
    }
}
