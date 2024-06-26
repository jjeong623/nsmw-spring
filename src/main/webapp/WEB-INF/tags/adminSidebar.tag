<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ attribute name="pageName" %>

<script src="/resources/admin/assets/vendor/libs/jquery/jquery.js"></script>

<aside id="layout-menu" class="layout-menu menu-vertical menu bg-menu-theme">
  <div class="app-brand demo">
    <img src="/resources/commonImages/favicon.png" height="35" />
    <a href="/dashboard" class="app-brand-link">
      <span class="app-brand-text demo menu-text fw-bolder ms-2">NAEDDOCO</span>
    </a>

    <a href="javascript:void(0);" class="layout-menu-toggle menu-link text-large ms-auto d-block d-xl-none">
      <i class="bx bx-chevron-left bx-sm align-middle"></i>
    </a>
  </div>

  <div class="menu-inner-shadow"></div>

  <ul class="menu-inner py-1">
    <li class="menu-item" id="dashboard">
      <a href="/dashboard" class="menu-link">
        <i class="menu-icon tf-icons bx bxs-dashboard"></i>
        <div>대시보드</div>
      </a>
    </li>
    <li class="menu-item" id="productList">
      <a href="/productList" class="menu-link">
        <i class="menu-icon tf-icons bx bx-package"></i>
        <div>상품 목록</div>
      </a>
    </li>
    <li class="menu-item" id="memberList">
      <a href="/memberList" class="menu-link">
        <i class="menu-icon tf-icons bx bx-user"></i>
        <div>회원 목록</div>
      </a>
    </li>

    <li class="menu-header small">
      <span class="menu-header-text">판매 통계</span>
    </li>
    <li class="menu-item" id="statDate">
      <a href="/statDate" class="menu-link">
        <i class="menu-icon tf-icons bx bx-calendar"></i>
        <div>기간별</div>
      </a>
    </li>
    <li class="menu-item" id="statProduct">
      <a href="/statProduct" class="menu-link">
        <i class="menu-icon tf-icons bx bx-purchase-tag-alt"></i>
        <div>상품별</div>
      </a>
    </li>
    
    <li class="menu-header small">
      <span class="menu-header-text">쿠폰 지급</span>
    </li>
    <li class="menu-item" id="couponGrade">
      <a href="/couponGrade" class="menu-link">
        <i class="menu-icon tf-icons bx bx-trophy"></i>
        <div>등급별 자동 발송</div>
      </a>
    </li>
    <li class="menu-item" id="couponBatch">
      <a href="/couponBatch" class="menu-link">
        <i class="menu-icon tf-icons bx bx-group"></i>
        <div>전체 발송</div>
      </a>
    </li>
    <li class="menu-item" id="couponDownload">
      <a href="/couponDownload" class="menu-link">
        <i class="menu-icon tf-icons bx bx-download"></i>
        <div>사용자 다운로드</div>
      </a>
    </li>
    
    <li class="menu-header small">
      <span class="menu-header-text">기타</span>
    </li>
    <li class="menu-item">
      <a href="/" class="menu-link">
        <i class="menu-icon tf-icons bx bx-store"></i>
        <div>내 쇼핑몰</div>
      </a>
    </li>
    
  </ul>
</aside>

<script>
  // JavaScript 코드
  var element = $('#${pageName}');
  element.addClass("active");
</script>