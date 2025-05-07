<template>
  <div class="container">
    <h2 class="my-4">📦 바코드 조회</h2>

    <!-- 🔍 검색 필터 -->
    <form @submit.prevent="fetchBarcodes" class="search-form mb-4">
      바코드번호 <input v-model="search.barcodeNo" placeholder="바코드번호" />
      상품명 <input v-model="search.barcodeName" placeholder="상품명" />
      인증번호 <input v-model="search.stdCertNo" placeholder="인증번호" />
      대표품목명 <input v-model="search.repItemName" placeholder="대표품목명" />
      상품유형
      <select v-model="search.productType">
        <option value="">전체</option>
        <option value="단일상품">단일상품</option>
        <option value="혼합상품">혼합상품</option>
      </select>
      <button type="submit">조회</button>
    </form>

    <!-- 📄 바코드 목록 -->
    <table class="barcode-table">
      <thead>
      <tr>
        <th>바코드번호</th>
        <th>상품명</th>
        <th>등록일</th>
        <th>상품유형</th>
        <th>인증번호</th>
        <th>대표품목명</th>
      </tr>
      </thead>
      <tbody>
      <template v-if="barcodes.length > 0">
        <tr v-for="barcode in barcodes" :key="barcode.barcodeNo">
          <td>{{ barcode.barcodeNo }}</td>
          <td>{{ barcode.barcodeName }}</td>
          <td>{{ formatDate(barcode.barcodeRegDate) }}</td>
          <td>{{ barcode.productType }}</td>
          <td>{{ barcode.stdCertNo }}</td>
          <td>{{ barcode.repItemName }}</td>
        </tr>
      </template>
      <tr v-else>
        <td colspan="6" class="text-center">조회된 데이터가 없습니다.</td>
      </tr>
      </tbody>
    </table>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue';
import axios from 'axios';
import { getToken } from '@/utils/auth'; // 🔑 토큰 불러오기

const barcodes = ref([]);
const search = reactive({
  barcodeNo: '',
  barcodeName: '',
  stdCertNo: '',
  repItemName: '',
  productType: ''
});

const fetchBarcodes = async () => {
  const token = getToken();
  if (!token) {
    console.warn('로그인 후 이용해주세요.');
    barcodes.value = [];
    return;
  }

  try {
    const { data } = await axios.get('/api/barcode/getlist', {
      params: search,
      headers: {
        Authorization: `Bearer ${token}` // 👉 JWT 포함
      }
    });

    barcodes.value = data;
  } catch (error) {
    console.error('바코드 조회 실패:', error);
    barcodes.value = [];
  }
};

const formatDate = (dateStr) => {
  if (!dateStr) return '-';
  return new Date(dateStr).toLocaleDateString();
};

onMounted(() => {
  fetchBarcodes();
});
</script>


<style scoped>
.container {
  max-width: 900px;
  margin: auto;
}
.search-form input,
.search-form select {
  margin-right: 8px;
  padding: 5px;
}
.barcode-table {
  width: 100%;
  border-collapse: collapse;
}
.barcode-table th,
.barcode-table td {
  border: 1px solid #ccc;
  padding: 8px;
}
.barcode-table th {
  background-color: #f0f0f0;
}
.text-center {
  text-align: center;
}
</style>
