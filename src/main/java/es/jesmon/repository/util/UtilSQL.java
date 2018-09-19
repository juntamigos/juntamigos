package es.jesmon.repository.util;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;

public class UtilSQL {

	public static String getOrderBy(List<ParBean> criteriosOrdenacion) {
		String orderBy = "";
		if (criteriosOrdenacion != null)
			for (int i = 0; i < criteriosOrdenacion.size(); i++) {
				ParBean par = criteriosOrdenacion.get(i);
				if (i == 0)
					orderBy += " ORDER BY " + par.getNombre() + " "
							+ par.getValor();
				else
					orderBy += "," + par.getNombre() + " " + par.getValor();
			}
		return orderBy;
	}

	public static void setOrderBy(List<ParBean> criteriosOrdenacion,
			Criteria criteria) {
		if (criteriosOrdenacion != null)
			for (ParBean par : criteriosOrdenacion) {
				String[] columnas = StringUtils.split(par.getNombre(), "|");
				for (String columna : columnas)
					if (par.getValor().equalsIgnoreCase(CriteriosBusqueda.ASC))
						criteria.addOrder(Order.asc(columna));
					else
						criteria.addOrder(Order.desc(columna));
			}
	}
}
