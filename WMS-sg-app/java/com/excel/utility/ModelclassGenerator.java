package com.excel.utility;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.SQLType;
import java.sql.Statement;
import java.util.List;
import java.util.Scanner;

import javax.persistence.Id;

import com.excel.configuration.JpaDatabaseConfiguration;

public class ModelclassGenerator {

	public static Connection getConnection(JpaDatabaseConfiguration dataSource) throws Exception {
		String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
		String url = dataSource.getDataSource().getUrl();
		String username = dataSource.getDataSource().getUsername();
		String password = dataSource.getDataSource().getPassword();

		Class.forName(driver);
		Connection conn = DriverManager.getConnection(url, username, password);
		return conn;
	}

	public static void getColumnNames(ResultSet rs) throws SQLException {
		if (rs == null) {
			return;
		}
		// get result set meta data
		ResultSetMetaData rsMetaData = rs.getMetaData();
		int numberOfColumns = rsMetaData.getColumnCount();
		int columncount = rsMetaData.getColumnCount();
		System.out.println("Column-Count " + columncount);

		System.out.println("");
		// get the column names; column indexes start from 1
		for (int i = 1; i < numberOfColumns + 1; i++) {

			String columnName = rsMetaData.getColumnName(i);
			String columntypeName = rsMetaData.getColumnTypeName(i);
			int columnscale = rsMetaData.getScale(i);
			
			if (i == 1) {
				System.out.println("@Id");
			}

//			System.out.println("columntypeName " + columntypeName);
			System.out.println("@Column(name=\"" + columnName.trim().toUpperCase() + "\")");
			if (columntypeName.trim().contains("int")) {
				System.out.println("private Long " + columnName.trim().toLowerCase() + ";");
			} else if (columntypeName.trim().contains("varchar")) {
				System.out.println("private String " + columnName.trim().toLowerCase() + ";");
			} else if (columntypeName.trim().contains("datetime")) {
				System.out.println("private Date " + columnName.trim().toLowerCase() + ";");
			} else if (columntypeName.trim().contains("numeric") && columnscale == 2) {
				System.out.println("private BigDecimal " + columnName.trim().toLowerCase() + ";");
			} else if (columntypeName.trim().contains("numeric") && columnscale != 2) {
				System.out.println("private Long " + columnName.trim().toLowerCase() + ";");
			} else {
				System.out.println("private String " + columnName.trim().toLowerCase() + ";");
			}
			System.out.println("");
		}

		for (int i = 1; i < numberOfColumns + 1; i++) {
			String columnName = rsMetaData.getColumnName(i);
			String columntypeName = rsMetaData.getColumnTypeName(i);
			int columnscale = rsMetaData.getScale(i);
			
			String getter = "get"
					+ columnName.substring(0, 1).toUpperCase().concat(columnName.substring(1).toLowerCase());
			String setter = "set"
					+ columnName.substring(0, 1).toUpperCase().concat(columnName.substring(1).toLowerCase());

			if (columntypeName.trim().contains("int")) {
				System.out.println("public Long " + getter + "(){\n return " + columnName.toLowerCase() + ";\n} \n");
				System.out.println("public void " + setter + "(Long " + columnName.toLowerCase() + "){\n this."
						+ columnName.toLowerCase() + " = " + columnName.toLowerCase() + ";\n}\n");
			} else if (columntypeName.trim().contains("varchar")) {
				System.out.println("public String " + getter + "(){\n return " + columnName.toLowerCase() + ";\n} \n");
				System.out.println("public void " + setter + "(String " + columnName.toLowerCase() + "){\n this."
						+ columnName.toLowerCase() + " = " + columnName.toLowerCase() + ";\n}\n");
			} else if (columntypeName.trim().contains("datetime")) {
				System.out.println("public Date " + getter + "(){\n return " + columnName.toLowerCase() + ";\n} \n");
				System.out.println("public void " + setter + "(Date " + columnName.toLowerCase() + "){\n this."
						+ columnName.toLowerCase() + " = " + columnName.toLowerCase() + ";\n}\n");
			} else if (columntypeName.trim().contains("numeric") && columnscale == 2) {
				System.out.println(
						"public BigDecimal " + getter + "(){\n return " + columnName.toLowerCase() + ";\n} \n");
				System.out.println("public void " + setter + "(BigDecimal " + columnName.toLowerCase() + "){\n this."
						+ columnName.toLowerCase() + " = " + columnName.toLowerCase() + ";\n}\n");
			} else if (columntypeName.trim().contains("numeric") && columnscale != 2) {
				System.out.println("public Long " + getter + "(){\n return " + columnName.toLowerCase() + ";\n} \n");
				System.out.println("public void " + setter + "(Long " + columnName.toLowerCase() + "){\n this."
						+ columnName.toLowerCase() + " = " + columnName.toLowerCase() + ";\n}\n");
			} else {
				System.out.println("public String " + getter + "(){\n return " + columnName.toLowerCase() + ";\n} \n");
				System.out.println("public void " + setter + "(String " + columnName.toLowerCase() + "){\n this."
						+ columnName.toLowerCase() + " = " + columnName.toLowerCase() + ";\n}\n");
			}
		}
	}

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
    	System.out.print("Enter valid Table name ::::> ");
    	String tablename = sc.next();
		
    	System.out.println("return ::::>  "+ tablename);
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			JpaDatabaseConfiguration con = new JpaDatabaseConfiguration();
			conn = getConnection(con);
			String query = "select * from "+tablename;
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);
			
			getColumnNames(rs);
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
