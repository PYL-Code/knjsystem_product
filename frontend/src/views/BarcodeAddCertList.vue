<template>
  <div class="cert-container">
    <h2 class="cert-title"><strong>인증정보 추가</strong></h2>

    <!-- 🔍 검색 필터 -->
    <form @submit.prevent="fetchCerts" class="cert-form">
      <select v-model="searchField">
        <option value="">전체</option>
        <option value="stdCertNo">인증번호</option>
        <option value="repItemName">대표품목</option>
        <option value="itemName">품목</option>
      </select>
      <input v-model="searchText" type="text" placeholder="검색어 입력" />
      <button type="submit">조회</button>
    </form>

    <!-- 📄 인증정보 목록 -->
    <table class="cert-table">
      <thead>
      <tr>
        <th>번호</th>
        <th>인증번호</th>
        <th>대표품목</th>
        <th>농가/단체명</th>
        <th>소속농가</th>
        <th>품목</th>
        <th>선택</th>
      </tr>
      </thead>
      <tbody>
      <template v-if="certs.length > 0">
        <tr v-for="(cert, index) in certs" :key="cert.certId">
          <td>{{ certs.length - index }}</td>
          <td>{{ cert.stdCertNo }}</td>
          <td>{{ cert.repItemName }}</td>
          <td>{{ cert.prdrGrpNm }}</td>
          <td>{{ cert.frmrNm }}</td>
          <td>{{ cert.itemName }}</td>
          <td>
            <label class="radio-wrapper">
              <input
                  type="radio"
                  name="selectedCert"
                  :value="cert.certId"
                  v-model="selectedCertId"
              />
              <span class="checkmark"></span>
            </label>
          </td>
        </tr>
      </template>
      <tr v-else>
        <td colspan="7" class="no-data">조회된 인증정보가 없습니다.</td>
      </tr>
      </tbody>
    </table>

    <!-- 등록 & 취소 버튼 -->
    <div class="cert-action">
      <button class="btn-submit" @click="submitSelectedCert" :disabled="!selectedCertId">
        등록
      </button>
      <button class="btn-deny" @click="goBack">
        취소
      </button>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import axios from 'axios';
import { getToken } from '@/utils/auth';
import { useRoute, useRouter } from 'vue-router';

const route = useRoute();
const router = useRouter();

const certs = ref([]);
const selectedCertId = ref(null);
const searchField = ref('');
const searchText = ref('');
const userInfo = ref(null);

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
  if (!userInfo.value?.no) return;
  params.userNo = userInfo.value.no;
  if (searchField.value === '') {
    params.all = searchText.value;
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

const submitSelectedCert = async () => {
  const token = getToken();
  const barcodeId = route.query.barcodeId;
  const certId = selectedCertId.value;
  const userNo = userInfo.value?.no;

  try {
    await axios.post(
        '/api/barcode/update/cert/into/barcode',
        null,
        {
          params: { certId, barcodeId, userNo },
          headers: { Authorization: `Bearer ${token}` }
        }
    );
    alert('인증정보가 바코드에 등록되었습니다!');
    router.push({ path: '/barcode/detail', query: { barcodeId } });
  } catch (error) {
    console.error('인증정보 등록 실패:', error);
    alert('인증정보 등록에 실패했습니다.');
  }
};

const goBack = () => {
  router.go(-1);
};

onMounted(async () => {
  await fetchUserInfo();
  await fetchCerts();
});
</script>

<style scoped>
.cert-container {
  max-width: 960px;
  margin: 0 auto;
  padding: 2rem;
  font-family: 'Segoe UI', sans-serif;
  color: #333;
}

.cert-title {
  text-align: center;
  color: #2b4c7e;
  font-size: 2rem;
  margin-bottom: 1.5rem;
}

.cert-form {
  display: flex;
  gap: 1rem;
  margin-bottom: 1.5rem;
  justify-content: center;
}

.cert-form select,
.cert-form input {
  padding: 0.6rem;
  flex: 1;
  border: 1px solid #ccc;
  border-radius: 4px;
}

.cert-form button {
  padding: 0.6rem 1.5rem;
  background-color: #2b4c7e;
  color: #fff;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  font-size: 0.95rem;
  font-weight: 600;
}

.cert-table {
  width: 100%;
  border-collapse: collapse;
  margin-bottom: 1.5rem;
}

.cert-table thead {
  background-color: #e9f0f6; /* 연한 회색-블루 */
}

.cert-table th {
  color: #2b4c7e; /* 진한 블루 */
  font-weight: 600;
  font-size: 0.95rem;
  border-top: 2px solid #c9d6e2;
  padding: 0.75rem;
  text-align: center;
  background-color: #e9f0f6;
}

.cert-table td {
  padding: 0.75rem;
  border: 1px solid #e0e0e0;
  text-align: center;
}

.no-data {
  color: #aaa;
  text-align: center;
}

.radio-wrapper {
  display: inline-block;
  position: relative;
}

.radio-wrapper input[type="radio"] {
  opacity: 0;
  position: absolute;
}

.radio-wrapper .checkmark {
  width: 16px;
  height: 16px;
  border: 2px solid #2b4c7e;
  border-radius: 50%;
  display: inline-block;
  position: relative;
}

.radio-wrapper input:checked + .checkmark::after {
  content: "";
  width: 8px;
  height: 8px;
  background: #2b4c7e;
  border-radius: 50%;
  position: absolute;
  top: 4px;
  left: 4px;
}

.cert-action {
  display: flex;
  justify-content: flex-end;
  gap: 0.8rem;
  margin-top: 1rem;
}

.btn-submit {
  padding: 0.7rem 1.5rem;
  background-color: #1f8249;
  color: white;
  border: none;
  border-radius: 5px;
  font-weight: 600;
  font-size: 0.95rem;
  cursor: pointer;
}

.btn-submit:disabled {
  background-color: #aaa;
  cursor: not-allowed;
}

.btn-deny {
  padding: 0.7rem 1.5rem;
  background-color: #2b4c7e;
  color: white;
  border: none;
  border-radius: 5px;
  font-weight: 600;
  font-size: 0.95rem;
  cursor: pointer;
}
</style>

