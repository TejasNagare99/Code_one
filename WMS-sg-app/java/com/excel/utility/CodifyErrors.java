package com.excel.utility;

import java.util.HashMap;
import java.util.Map;

public class CodifyErrors {

	private static Map<String, Integer> codeMap = new HashMap<String, Integer>();

	public CodifyErrors() {
		//repo or persistence exceptions
		codeMap.put("java.sql.SQLException", 2101);
		codeMap.put("java.lang.IllegalArgumentException", 2102);
		codeMap.put("java.lang.IllegalStateException", 2103);
		codeMap.put("javax.persistence.QueryTimeoutException", 2104);
		codeMap.put("javax.persistence.TransactionRequiredException", 2105);
		codeMap.put("javax.persistence.TransactionRequiredException", 2106);
		codeMap.put("javax.persistence.TransactionRequiredException", 2107);
		codeMap.put("javax.persistence.TransactionRequiredException", 2108);
		codeMap.put("javax.persistence.NoResultException", 2109);
		codeMap.put("javax.persistence.NonUniqueResultException", 2110);
		codeMap.put("javax.persistence.EntityExistsException", 2111);
		
		//file and io exceptions
		codeMap.put("java.io.FileNotFoundException", 6001);
		codeMap.put("java.io.IOException", 6002);
		codeMap.put("java.io.IOException", 6003);
		//mail send exceptions
		codeMap.put("javax.mail.SendFailedException", 1301);
		codeMap.put("javax.mail.MessagingException", 1302);
		//null pointer
		codeMap.put("java.lang.NullPointerException", 1701);
	}

	public static int getErrorCode(Exception e) {
		try {
			//System.out.println(e.getClass().getCanonicalName());
			return codeMap.get(e.getClass().getCanonicalName());
		}
		catch (Exception e1) {
			// TODO: handle exception
				return 0;
		}
	}

}
