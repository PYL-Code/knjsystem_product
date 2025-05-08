<template>
  <div class="container mt-5">
    <h2 class="mb-4 text-center">인증정보 추가</h2>

    <!-- 🔍 검색 필터 -->
    <form @submit.prevent="fetchCerts" class="row g-2 align-items-center mb-4">
      <div class="col-md-3">
        <select v-model="searchField" class="form-select">
          <option value="">전체</option>
          <option value="stdCertNo">인증번호</option>
          <option value="repItemName">대표품목</option>
          <option value="itemName">품목</option>
        </select>
      </div>
      <div class="col-md-6">
        <input v-model="searchText" class="form-control" placeholder="검색어 입력" />
      </div>
      <div class="col-md-3">
        <button type="submit" class="btn btn-primary w-100">조회</button>
      </div>
    </form>

    <!-- 📄 인증정보 목록 -->
    <table class="table table-bordered table-hover">
      <thead class="table-light text-center">
      <tr>
        <th>인증번호</th>
        <th>농가/단체명</th>
        <th>소속농가</th>
        <th>대표품목</th>
        <th>품목</th>
      </tr>
      </thead>
      <tbody>
      <template v-if="certs.length > 0">
        <tr v-for="cert in certs" :key="cert.certId">
          <td>{{ cert.stdCertNo }}</td>
          <td>{{ cert.prdrGrpNm }}</td>
          <td>{{ cert.frmrNm }}</td>
          <td>{{ cert.repItemName }}</td>
          <td>{{ cert.itemName }}</td>
        </tr>
      </template>
      <tr v-else>
        <td colspan="5" class="text-center text-muted">조회된 인증정보가 없습니다.</td>
      </tr>
      </tbody>
    </table>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import axios from 'axios';
import { getToken } from '@/utils/auth';

const certs = ref([]);
const userInfo = ref(null);
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

const fetchCerts = async () => {
  const token = getToken();
  const params = {};

  if (!userInfo.value?.no) {
    console.warn('❗ 사용자 정보 없음');
    return;
  }

  params.userNo = userInfo.value.no;

  if (searchField.value === '') {
    params.all = searchText.value; // 전체 검색
  } else if (searchText.value) {
    params[searchField.value] = searchText.value;
  }

  try {
    const { data } = await axios.get('/api/cert/barcode/update', {
      params,
      headers: { Authorization: `Bearer ${token}` }
    });
    certs.value = data;
  } catch (err) {
    console.error('인증정보 조회 실패:', err);
    certs.value = [];
  }
};

onMounted(async () => {
  await fetchUserInfo();
  await fetchCerts();
});
</script>

<style scoped>
.container {
  max-width: 1000px;
  margin: auto;
}
.table th,
.table td {
  vertical-align: middle;
}
</style>
