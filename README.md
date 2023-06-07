# 개발 환경
1. IDE : IntelliJ IDEA Ultimate
2. Spring Boot 3.1.0
3. JDK 17
4. MySQL

# 사용 프레임워크
1. Spring Data JPA
2. Thymeleaf
3. Spring Security
4. JQuery Ajax
5. WebSocket

# 게시판 주요 기능
1. 글쓰기(/board/save)
2. 글목록(/board/)
3. 글조회(/board/{id})
4. 글수정(/board/update)
    - 상세페이지에서 수정 버튼 클릭
    - 서버에서 해당 게시글의 정보를 가지고 수정 화면 출력
    - 제목, 내용 입력을 받아 서버에 요청
    - 수정 처리
5. 글삭제(/board/delete/{id})
6. 페이징처리(/board/paging)
   - /board/paging?page=2
   - 만약 게시글이 14개
     - 한 페이지에 10개 -> 2페이지
7. 댓글 처리
   - 글 상세페이지에서 댓글 입력
   - 상세 조회 시 기존에 작성된 댓글 목록이 보임
   - 댓글을 입력하면 기존 댓글 목록에 새로 작성한 댓글 추가
   - 댓글용 테이블 필요
8. 로그인,로그아웃
   - Spring Security 를 이용하여 구현
   - 게시글 작성 시 유저 이름이 매핑
   - 수정, 삭제는 글을 작성한 유저만 가능
   - 로그아웃 시 쿠키를 삭제하여 로그아웃 구현
9. 회원가입
   - Spring Security 를 이용하여 구현
   - 아이디(username)와 비밀번호(password)를 입력받아 유저 정보 저장
   - DB 에 저장
10. 채팅
   - WebSocket 과 STOMP 를 이용해 구현
   - 메인화면에서 실행 가능
   - Connect 버튼을 누르고 Contents 입력 후 보내기 가능
   - Disconnect 버튼으로 Disconnect 가능
   - Contents 입력 후 Send 시 Chating Area 밑에 출력