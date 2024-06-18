# API Document
## User
- [X] 회원가입 : `POST` /api/users/signup
- [X] 로그인 : `POST` /api/users/login
- [X] 로그아웃 : `POST` /api/users/logout
- [X] 회원 정보 조회 : `GET` /api/users/me
- [ ] 회원 정보 수정 : `POST` /api/users/me
- [ ] 회원 비밀번호 수정 : `POST` /api/users/password
- [ ] 회원 탈퇴 : `DELETE` /api/users/me

## Board
- [X] 게시글 목록 조회 : `GET` /api/boards
- [X] 게시글 상세 조회 : `GET` /api/boards/{boardId}
- [X] 게시글 등록 : `POST` /api/boards
- [ ] 게시글 수정 : `POST` /api/boards/{boardId}
- [ ] 게시글 삭제 : `DELETE` /api/boards/{boardId}

## Comment
- [X] 댓글 조회 : `GET` /api/boards/{boardId}/comments
- [X] 댓글 등록 : `POST` /api/boards/{boardId}/comments
- [ ] 댓글 수정 : `POST` /api/boards/{boardId}/comments/{commentId}
- [ ] 댓글 삭제 : `DELETE` /api/boards/{boardId}/comments/{commentId}