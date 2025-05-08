<!-- BarcodeList.vue -->
<template>
  <div class="container">
    <h2 class="my-4 text-center">바코드 조회</h2>

    <!-- 👤 사용자 정보 -->
    <div class="row mb-4" v-if="userInfo">
      <div class="col-md-4">
        <div class="card p-2 text-center">
          <strong>업체명/생산자명</strong><br />{{ userInfo.companyName }}
        </div>
      </div>
      <div class="col-md-4">
        <div class="card p-2 text-center">
          <strong>사업자등록번호</strong><br />{{ userInfo.bnsNo }}
        </div>
      </div>
      <div class="col-md-4">
        <div class="card p-2 text-center">
          <strong>GLN(업체코드)</strong><br />{{ userInfo.company880Code || '-' }}
        </div>
      </div>
    </div>

    <!-- 🔍 검색 필터 -->
    <form @submit.prevent="fetchBarcodes" class="row g-2 align-items-center mb-4">
      <div class="col-md-2">
        <select v-model="searchField" class="form-select">
          <option value="">전체</option>
          <option value="barcodeNo">바코드번호</option>
          <option value="barcodeName">상품명</option>
          <option value="stdCertNo">인증번호</option>
          <option value="repItemName">대표품목명</option>
        </select>
      </div>

      <div class="col-md-5">
        <input v-model="searchText" class="form-control" placeholder="검색어 입력" />
      </div>

      <div class="col-md-1"></div>

      <div class="col-md-1">상품유형</div>
      <div class="col-md-2">
        <select v-model="search.productType" class="form-select">
          <option value="">전체</option>
          <option value="단일상품">단일상품</option>
          <option value="혼합상품">혼합상품</option>
        </select>
      </div>

      <div class="col-md-1">
        <button type="submit" class="btn btn-primary w-100">조회</button>
      </div>
    </form>

    <!-- 📄 바코드 목록 -->
    <table class="table table-bordered table-hover">
      <thead class="table-light text-center">
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
        <tr
            v-for="barcode in barcodes"
            :key="barcode.barcodeNo"
            @click="goToDetail(barcode.barcodeId)"
            style="cursor: pointer"
        >
          <td>{{ barcode.barcodeNo }}</td>
          <td>{{ barcode.barcodeName }}</td>
          <td>{{ formatDate(barcode.barcodeRegDate) }}</td>
          <td>{{ barcode.productType }}</td>
          <td>{{ barcode.stdCertNo }}</td>
          <td>{{ barcode.repItemName }}</td>
        </tr>
      </template>
      <tr v-else>
        <td colspan="6" class="text-center text-muted">조회된 데이터가 없습니다.</td>
      </tr>
      </tbody>
    </table>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import axios from 'axios';
import { useRouter } from 'vue-router';
import { getToken } from '@/utils/auth';

const router = useRouter();
const barcodes = ref([]);
const userInfo = ref(null);
const search = ref({ productType: '' });
const searchField = ref('');
const searchText = ref('');

const fetchUserInfo = async () => {
  const token = getToken();
  try {
    const { data } = await axios.get('/api/list/member', {
      headers: { Authorization: `Bearer ${token}` }
    });
    userInfo.value = data;
  } catch (error) {
    console.error('사용자 정보 조회 실패:', error);
  }
};

const fetchBarcodes = async () => {
  const token = getToken();
  if (!token) return;

  const params = {};
  if (searchField.value === '') {
    params.all = searchText.value; // ✅ 전체 검색일 경우 all 파라미터로
  } else if (searchText.value) {
    params[searchField.value] = searchText.value;
  }
  if (search.value.productType) {
    params.productType = search.value.productType;
  }

  try {
    const { data } = await axios.get('/api/barcode/list', {
      params,
      headers: { Authorization: `Bearer ${token}` }
    });
    barcodes.value = data;
  } catch (error) {
    console.error('바코드 조회 실패:', error);
    barcodes.value = [];
  }
};

const goToDetail = (barcodeId) => {
  router.push({ path: '/barcode/detail', query: { barcodeId } });
  // console.log(barcodes);
};

const formatDate = (dateStr) => {
  if (!dateStr) return '-';
  return new Date(dateStr).toLocaleDateString();
};

onMounted(() => {
  fetchUserInfo();
  fetchBarcodes();
});
</script>
