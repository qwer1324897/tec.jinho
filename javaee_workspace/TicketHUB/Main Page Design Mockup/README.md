
## 공연 예매 플랫폼 UI 템플릿 (스프링 레거시/JSP용)

이 레포는 **메인(홈) 1페이지 UI 템플릿**만 정리해둔 폴더입니다.  
React/TypeScript(Vite)로 생성됐던 소스는 제거했고, **JSP include + 순수 CSS + 바닐라 JS(의존성 0)** 형태로 재구성했습니다.

### 폴더 구조
- `jsp/home.jsp`: 메인 페이지
- `jsp/include/header.jspf`: 헤더 include
- `jsp/include/footer.jspf`: 푸터 include
- `assets/css/reset.css`: 최소 reset
- `assets/css/common.css`: 공통(토큰/버튼/태그/컨테이너 등)
- `assets/css/home.css`: 메인 페이지 섹션별 스타일
- `assets/js/home.js`: 바닐라 JS — 배너 슬라이더 + 탭 active 토글
- `assets/img/*.svg`: 배너/포스터 placeholder 이미지

### 스프링 레거시에 옮길 때(예시)
프로젝트 구조에 맞게 경로만 맞추면 됩니다.

- JSP
  - `/WEB-INF/views/home.jsp`
  - `/WEB-INF/views/include/header.jspf`
  - `/WEB-INF/views/include/footer.jspf`
- 정적 리소스
  - `/resources/css/*` ← `assets/css/*`
  - `/resources/js/*`  ← `assets/js/*`
  - `/resources/img/*` ← `assets/img/*`

`jsp/home.jsp` 안에는 백엔드 연동할 자리(`TODO(백엔드 연동)`)가 표시돼 있습니다.
  