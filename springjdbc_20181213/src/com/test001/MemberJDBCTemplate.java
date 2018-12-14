package com.test001;

import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

public class MemberJDBCTemplate {

	private DataSource dataSource;
	private JdbcTemplate jdbcTemplateObject;

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		this.jdbcTemplateObject = new JdbcTemplate(dataSource);
	}

	public void connectionTest() {
		try {
			System.out.println(this.dataSource.getConnection().toString());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public List<Member> memberList() {
		String sql = "SELECT mid_, name_, phone FROM members";

		// JDBCTemplate Ŭ������
		// query() �޼ҵ�� ���� ���� �� �÷��� ��ȯ
		List<Member> result = this.jdbcTemplateObject.query(sql, new MemberRowMapper());

		return result;
	}

	public List<Member> memberSearch(String key, String value) {
		String sql = "SELECT mid_, name_, phone FROM members ";

		// JDBCTemplate Ŭ������
		// query() �޼ҵ�� ���� ���� �� �÷��� ��ȯ

		if (key.equals("all")) {
		} else if (key.equals("mid_")) {
			sql += "WHERE mid_=?";
		} else if (key.equals("name_")) {
			sql += "WHERE INSTR(name_,?) > 0 ";
		} else if (key.equals("phone")) {
			sql += "WHERE INSTR(phone,?) > 0 ";
		}
		sql += "ORDER BY mid_";

		List<Member> result = null;
		
		if (key.equals("all")) {
			result = this.jdbcTemplateObject.query(sql, new MemberRowMapper());
		} else {
			//�ܺο��� �����ϴ� �ڷῡ ���� ���ε�
			// -> new Object[] {�ڷ�1, �ڷ�2, �ڷ�3, ...}
			result = this.jdbcTemplateObject.query(sql, new Object[] {value}, new MemberRowMapper());
		}

		return result;
	}

}