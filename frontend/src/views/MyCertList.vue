<template>
  <div class="container mt-5">
    <h1 class="mb-4 fw-bold">인증번호 관리</h1>

    <!-- 사용자 정보 -->
    <div class="bg-light p-3 rounded mb-4">
      <div><p><strong>업체명/생산자명:</strong> 홍길동농장</p></div>
      <div><p><strong>사업자등록번호:</strong> 123-45-67890</p></div>
      <div><p><strong>GLN(업체코드):</strong> 8801234567890</p></div>
    </div>

    <!-- 상세 검색 -->
    <div class="card mb-4">
      <div class="card-body">
        <h5 class="card-title mb-3">상세검색</h5>
        <div class="row g-3">
          <div class="col-md-3">품목코드<input type="text" class="form-control" placeholder="품목코드" /></div>
          <div class="col-md-3">단체명<input type="text" class="form-control" placeholder="단체명" /></div>
          <div class="col-md-3">소속농가<input type="text" class="form-control" placeholder="소속농가" /></div>
          <div class="col-md-3">
            인증상태
            <select class="form-select">
              <option>전체</option>
              <option>인증</option>
              <option>인증취소</option>
              <option>기간종료</option>
              <option>인증포기</option>
              <option>인증부적합</option>
            </select>
          </div>
          <div class="col-md-3">인증번호<input type="text" class="form-control" placeholder="인증번호" /></div>
          <div class="col-md-3">품목명<input type="text" class="form-control" placeholder="품목명" /></div>
          <div class="col-md-3">
            바코드존재여부
            <select class="form-select">
              <option>전체</option>
              <option>존재</option>
              <option>미존재</option>
            </select>
          </div>
        </div>
        <div class="mt-3">
          <button class="btn btn-primary">검색</button>
        </div>
      </div>
    </div>

    <!-- 리스트 -->
    <div class="card mb-4">
      <div class="card-body">
        <table class="table table-bordered text-center">
          <thead class="table-light">
          <tr>
            <th>번호</th>
            <th>인증번호</th>
            <th>대표품목</th>
            <th>단체명</th>
            <th>인증상태</th>
            <th>인증종류</th>
            <th>상세정보</th>
          </tr>
          </thead>
          <tbody>
          <tr>
            <td colspan="7">데이터가 없습니다!</td>
          </tr>
          </tbody>
        </table>

        <!-- 페이징 -->
        <nav class="d-flex justify-content-center">
          <ul class="pagination">
            <li class="page-item"><a class="page-link" href="#">«</a></li>
            <li class="page-item active"><a class="page-link" href="#">1</a></li>
            <li class="page-item"><a class="page-link" href="#">2</a></li>
            <li class="page-item"><a class="page-link" href="#">3</a></li>
            <li class="page-item"><a class="page-link" href="#">»</a></li>
          </ul>
        </nav>
      </div>
    </div>

    <!-- 인증정보 등록 -->
    <div class="d-flex justify-content-end">
      <button class="btn btn-success">인증정보 등록</button>
    </div>
  </div>
</template>

<script setup>
// bootstrap 필요 시 main.js에 import 'bootstrap/dist/css/bootstrap.min.css'
</script>
