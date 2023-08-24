package org.springboot.app.constants;

import lombok.Generated;

/**
 * @author jmedina
 *
 */
@Generated
public final class Constants {

	public static final String MSG_PRE_ERROR = "**=> error: {}";

	public static final String MSG_CANT_RETURN_COUNTRY = "No fue posible regresar el pais.";
	public static final String MSG_CANT_RETURN_COUNTRIES = "No fue posible regresar el listado de paises.";
	public static final String MSG_CANT_SAVE_COUNTRY = "No fue posible guardar el pais.";
	public static final String MSG_CANT_UPDATE_COUNTRY = "No fue posible modificar el pais.";
	public static final String MSG_CANT_DELETE_COUNTRY = "No fue posible eliminar el pais.";

	public static final String MSG_COUNTRY_NAME_IS_NULL = "Name is null";
	public static final String MSG_COUNTRY_NAME_IS_EMPTY = "Name is empty";
	public static final String MSG_COUNTRY_NAME_IS_BLANK = "Name is blank";

	public static final String MSG_COUNTRY_DESC_IS_NULL = "Description is null";
	public static final String MSG_COUNTRY_DESC_IS_EMPTY = "Description is empty";
	public static final String MSG_COUNTRY_DESC_IS_BLANK = "Description is blank";

	public static final String MSG_HELLO = "OK!";

	public static final String URI_COUNTRY_ENDPOINT_HELLO = "/";
	public static final String URI_COUNTRY_ENDPOINT_FIND = "/find";
	public static final String URI_COUNTRY_ENDPOINT_FIND_BY_ID = "/findById/";
	public static final String URI_COUNTRY_ENDPOINT_CREATE = "/create";
	public static final String URI_COUNTRY_ENDPOINT_MODIFY_BY_ID = "/modify/";
	public static final String URI_COUNTRY_ENDPOINT_DELETE_BY_ID = "/delete/";

	public static final String TABLE_COUNTRY = "COUNTRIES";
	public static final String TABLE_COUNTRY_COLUMN_NAME = "NAME";
	public static final String TABLE_COUNTRY_COLUMN_DESCRIPTION = "DESCRIPTION";

	private Constants() {
	}

}