# FunWeb
부산 IT WILL 개인 프로젝트

개발자: 정진원

제작기간 : 2018.04.01 ~ 2018.05.10

!참고: 개발시에는 Eclipse 툴을 사용하였지만 프로젝트를 GitHub 에서 Pull하여 사용할 경우 편의를 위해 STS(Spring Tool Suite)에 내장된 git plugin을 사용한다.

프로젝트 개발환경
- 개발도구 : Eclipse Mars 2 Release, MySQL community 5.6.35.0, Apache Tomcat v8.0, Java Compiler 1.8
- SDK : JDK 1.8
- API : CKEditor, 다음주소 API
- 개발언어 : JAVA, JSP, Javascript, Jquery, Ajax, CSS, MySQL
- Pull 시 Eclipse Mars 2 Release 대신 STS 3.8.4 를 이용한다.

핵심 구현 클래스***

※ JdbcContext

- JdbcTemplate 과 유사한 기능을 하며 데이터베이스 SQL문을 간편하게 실행 할 수 있도록 한다.
- JNDI 기술을 사용하여 Name값을 생성자에 입력하면 간단하게 객체를 생성할 수 있다.
사용예) – new JdbcContext("java:comp/env/jdbc/funweb");
- 람다식을 사용하기 때문에 자바 컴파일러 버전이 1.8이상인 환경에서만 사용 가능하다.

사용예) - executeUpdate(“INSERT INTO TableName (Name, Age) VALUES (‘Hong’, 24)“);

사용예) - queryForObject("SELECT FirstName, LastName, Address, PhoneNumber, MobilePhoneNumber "

		+ "FROM Account WHERE Idx = " + idx,
	
			rs -> {
			
				Account dto = new Account();
				
				dto.setFirstName(rs.getString(1));
				
				dto.setLastName(rs.getString(2)); 
				
				dto.setAddress(rs.getString(3));
				
				dto.setPhoneNumber(rs.getString(4));
				
				dto.setMobilePhoneNumber(rs.getString(5));
				
				return dto;
			
			});
