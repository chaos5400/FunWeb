# FunWeb
부산 IT WILL 개인 프로젝트

개발자: 정진원

!참고: 개발시에는 Eclipse 툴을 사용하였지만 프로젝트를 GitHub 에서 Pull하여 사용할 경우 편의를 위해 STS(Spring Tool Suite)에 내장된 git plugin을 사용한다.

프로젝트 개발환경
- 개발도구 : Eclipse Mars 2 Release, MySQL community 5.6.35.0, Apache Tomcat v8.0, Java Compiler 1.8
- SDK : JDK 1.8
- API : CKEditor, 다음주소 API
- 개발언어 : JAVA, JSP, Javascript, Jquery, Ajax, CSS, MySQL
- Pull 시 Eclipse Mars 2 Release 대신 STS 3.8.4 를 이용한다.

핵심 구현 클래스***

- JdbcContext

JdbcTemplate 과 유사한 기능을 하며 데이터베이스 SQL문을 간편하게 실행 할 수 있도록 한다.
