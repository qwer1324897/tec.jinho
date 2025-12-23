<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<header class="site-header" data-header>
  <div class="site-header__top">
    <div class="container site-header__top-inner">
      <a class="site-header__logo" href="${pageContext.request.contextPath}/" aria-label="홈으로">
        <span class="site-header__logo-gradient">TICKET HUB</span>
      </a>

      <form class="search" action="#" method="get" role="search">
        <label class="sr-only" for="q">검색</label>
        <input id="q" name="q" class="search__input" type="text" placeholder="공연, 전시를 검색해보세요" />
        <button class="search__btn" type="submit" aria-label="검색">
          <svg width="20" height="20" viewBox="0 0 24 24" aria-hidden="true" focusable="false">
            <path d="M10.5 3a7.5 7.5 0 1 0 4.67 13.37l4.23 4.23a1 1 0 0 0 1.42-1.42l-4.23-4.23A7.5 7.5 0 0 0 10.5 3Zm0 2a5.5 5.5 0 1 1 0 11 5.5 5.5 0 0 1 0-11Z" fill="currentColor"/>
          </svg>
        </button>
      </form>

      <div class="user-actions">
        <a class="user-actions__link" href="#">
          <span class="user-actions__icon" aria-hidden="true">
            <svg width="20" height="20" viewBox="0 0 24 24" aria-hidden="true" focusable="false">
              <path d="M12 12a4.5 4.5 0 1 0-4.5-4.5A4.5 4.5 0 0 0 12 12Zm0 2c-4.42 0-8 2.24-8 5v1h16v-1c0-2.76-3.58-5-8-5Z" fill="currentColor"/>
            </svg>
          </span>
          로그인
        </a>
        <a class="btn btn--primary" href="#">마이페이지</a>
      </div>
    </div>
  </div>

  <nav class="nav" aria-label="카테고리">
    <div class="container">
      <ul class="nav__list" data-nav-tabs>
        <li class="nav__item"><button class="nav__link" type="button" data-nav-tab>콘서트</button></li>
        <li class="nav__item"><button class="nav__link" type="button" data-nav-tab>뮤지컬</button></li>
        <li class="nav__item"><button class="nav__link" type="button" data-nav-tab>연극</button></li>
        <li class="nav__item"><button class="nav__link" type="button" data-nav-tab>클래식/무용</button></li>
        <li class="nav__item"><button class="nav__link" type="button" data-nav-tab>어린이/가족</button></li>
      </ul>
    </div>
  </nav>
</header>

