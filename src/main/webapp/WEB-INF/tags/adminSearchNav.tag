<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ attribute name="pageName" %>

<script src="/resources/admin/assets/vendor/libs/jquery/jquery.js"></script>

<!-- 페이지별 변수 설정 -->
<c:if test='${pageName eq "productList"}'>
  <c:set var="sortDisplayList" value='${fn:split("등록일순,판매 가격순,판매 상태순", ",")}' />
  <c:set var="sortCodeList" value='${fn:split("registerDate,salePrice,saleState", ",")}' />
  
  <c:set var="placeholder" value="상품명 검색" />
</c:if>
<!-- / 페이지별 변수 설정 -->

<nav class="navbar navbar-expand-lg navbar-light mb-3">
  <div class="container-fluid">
    <form class="collapse navbar-collapse" action="/${pageName}/searchAndSort" method="GET" id="search-sort-form">
      <ul class="navbar-nav me-auto mb-2 mb-lg-0">
        <li class="nav-item">
          <div class="d-flex">
            <input class="form-control me-2" type="search" name="searchKeyword" id="search-keyword" value="" placeholder="${placeholder}" aria-label="Search">
            <button class="btn btn-outline-primary" type="submit">Search</button>
          </div>
        </li>
      </ul>
      <div class="btn-group">
        <button
          type="button"
          class="btn btn-outline-primary dropdown-toggle"
          id="dropdown-sort-btn"
          data-bs-toggle="dropdown"
          aria-expanded="false"
        >
          000순
        </button>
        <input type="hidden" name="sortColumnName" value="" id="sort-column-name" />
        <ul class="dropdown-menu">
          <c:forEach var="sortDisplay" items="${sortDisplayList}" varStatus="status">
            <li>
              <button type="button" class="dropdown-item" onclick="handleSort('${sortDisplay}', '${sortCodeList[status.index]}')" id="dropdown-sort-item-${status.index}">${sortDisplay}</button>
            </li>
          </c:forEach>
        </ul>
      </div>
    </form>
  </div>
</nav>

<script>
function handleSort(sortDisplay, sortCode) {
	let searchKeyword = $("#search-keyword");
	let sortColumnName = $("#sort-column-name");
 
	// Dropdown btn에 보이는 문자 수정
	let btn = $('#dropdown-sort-btn');
	btn.empty();
	btn.text(sortDisplay);
	
	// Controller에 submit할 value 추가
	sortColumnName.val(sortCode);
	console.log("searchKeyword.val() : " + searchKeyword.val());
	console.log("sortColumnName.val() : " + sortColumnName.val());
	
	$.ajax({
		type : "GET",
		dataType : "json",
		url : "/productList/searchAndSort",
		data : {
			"searchKeyword" : searchKeyword.val(),
			"sortColumnName" : sortColumnName.val()
		},
		success : function(datas) {
			console.log("curFile after ajax: " + curFile);
			composePage(datas, curFile, "");
		}
	});
}

// Dropdown 초기화
(function () {
	let btn = $('#dropdown-sort-btn');
	let item0 = $('#dropdown-sort-item-0').html();
	
	btn.empty();
	btn.text(item0);
})();
</script>