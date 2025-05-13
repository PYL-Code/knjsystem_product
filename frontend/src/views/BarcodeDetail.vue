<template>
  <div class="detail-container">
    <h2 class="detail-title"><strong>바코드 상세 조회</strong></h2>

    <!-- 사용자 정보 -->
    <div class="user-info" v-if="userInfo">
      <div class="user-card">
        <span class="label">업체명</span>
        <span class="value">{{ userInfo.companyName }}</span>
      </div>
      <div class="user-card">
        <span class="label">사업자등록번호</span>
        <span class="value">{{ userInfo.bnsNo }}</span>
      </div>
      <div class="user-card">
        <span class="label">GLN(업체코드)</span>
        <span class="value">{{ userInfo.company880Code || '-' }}</span>
      </div>
    </div>

    <!-- 인증정보 버튼 -->
    <div class="action-buttons">
      <button v-if="!cert" @click="goToCertUpdate" class="btn btn-add">인증정보 추가</button>
      <button v-if="cert" @click="deleteCert" class="btn btn-add">인증정보 삭제</button>
    </div>

    <!-- 바코드 정보 -->
    <div class="detail-section" v-if="barcode">
      <div class="section-title">상품 바코드 정보</div>
      <div class="info-table">
        <div class="info-row">
          <div class="info-label">바코드번호</div>
          <div class="info-value">{{ barcode.barcode }}</div>
        </div>
        <div class="info-row alt">
          <div class="info-label">상품명</div>
          <div class="info-value">{{ barcode.barcodeName }}</div>
        </div>
        <div class="info-row">
          <div class="info-label">상품유형</div>
          <div class="info-value">{{ barcode.productType }}</div>
        </div>
      </div>
    </div>

    <!-- 인증 정보 -->
    <div class="detail-section" v-if="cert">
      <div class="section-title">인증 정보</div>
      <div class="info-table">
        <div class="info-row"><div class="info-label">인증번호</div><div class="info-value">{{ cert.stdCertNo }}</div></div>
        <div class="info-row alt"><div class="info-label">인증구분</div><div class="info-value">{{ cert.certGbNm }}</div></div>
        <div class="info-row"><div class="info-label">인증기간</div><div class="info-value">{{ cert.certStartDate }} ~ {{ cert.certEndDate }}</div></div>
        <div class="info-row alt"><div class="info-label">인증상태</div><div class="info-value">{{ cert.certStateNm }}</div></div>
        <div class="info-row"><div class="info-label">생산자단체명</div><div class="info-value">{{ cert.prdrGrpNm }}</div></div>
        <div class="info-row alt"><div class="info-label">개인/단체</div><div class="info-value">{{ cert.psnGroupNm }}</div></div>
        <div class="info-row"><div class="info-label">대표품목명</div><div class="info-value">{{ cert.repItemNm }}</div></div>
        <div class="info-row alt"><div class="info-label">품목명</div><div class="info-value">{{ cert.itemNm }}</div></div>
        <div class="info-row"><div class="info-label">소속농가명</div><div class="info-value">{{ cert.frmrNm }}</div></div>
      </div>
    </div>
    <div v-else class="no-cert-alert">
      인증정보를 추가해 주세요.
    </div>

    <!-- 하단 버튼 -->
    <div class="footer-buttons">
      <button class="btn btn-delete" @click.prevent="deleteBarcode">바코드 삭제</button>
      <router-link to="/barcode/list" class="btn btn-back">바코드 목록</router-link>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import axios from 'axios';
import { useRouter, useRoute } from 'vue-router';
import { getToken } from '@/utils/auth';

const router = useRouter();
const route = useRoute();

const barcode = ref(null);
const cert = ref(null);
const userInfo = ref(null);

onMounted(async () => {
  const token = getToken();
  const barcodeId = route.query.barcodeId;

  const config = {
    headers: { Authorization: `Bearer ${token}` }
  };

  try {
    const userRes = await axios.get('/api/list/member', config);
    userInfo.value = userRes.data;

    const { data: barcodeData } = await axios.get('/api/barcode/detail', {
      params: { barcodeId },
      ...config
    });
    barcode.value = barcodeData;

    const { data: certData } = await axios.get('/api/cert/barcode/detail', {
      params: { seqNoA004: barcodeId },
      ...config
    });
    cert.value = certData;
  } catch (err) {
    console.error('조회 실패:', err);
  }
});

const goToCertUpdate = () => {
  const barcodeId = route.query.barcodeId;
  router.push({ path: '/barcode/update/cert', query: { barcodeId } });
};

const deleteCert = async () => {
  const token = getToken();
  const barcodeId = route.query.barcodeId;
  const barcodeNo = barcode.value?.barcode;
  const productType = barcode.value?.productType;

  if (!confirm('정말 이 바코드의 인증정보를 삭제하시겠습니까?')) return;

  try {
    await axios.post('/api/barcode/delete/cert/of/barcode', null, {
      params: { barcodeId, barcodeNo, productType },
      headers: { Authorization: `Bearer ${token}` }
    });
    alert('인증정보가 성공적으로 삭제되었습니다.');
    cert.value = null;
  } catch (err) {
    console.error('인증정보 삭제 실패:', err);
    alert('인증정보 삭제 중 오류가 발생했습니다.');
  }
};

const deleteBarcode = async () => {
  const token = getToken();
  const barcodeNo = barcode.value?.barcode;

  if (!barcodeNo) {
    alert('바코드 정보가 없습니다.');
    return;
  }

  if (cert.value) {
    alert('인증정보가 있는 바코드는 삭제할 수 없습니다.');
    return;
  }

  if (!confirm('정말 이 바코드를 삭제하시겠습니까?')) return;

  try {
    await axios.delete('/api/barcode/delete', {
      params: { barcode: barcodeNo },
      headers: { Authorization: `Bearer ${token}` }
    });
    alert('바코드가 성공적으로 삭제되었습니다.');
    router.push('/barcode/list');
  } catch (error) {
    console.error('바코드 삭제 실패:', error);
    alert('바코드 삭제 중 오류가 발생했습니다.');
  }
};
</script>

<style scoped>
.detail-container {
  max-width: 960px;
  margin: auto;
  padding: 2rem;
  font-family: 'Segoe UI', sans-serif;
  color: #333;
}
.detail-title {
  text-align: center;
  font-size: 2rem;
  color: #2b4c7e;
  margin-bottom: 2rem;
}
.user-info {
  display: flex;
  justify-content: space-between;
  gap: 1rem;
  margin-bottom: 2rem;
}
.user-card {
  flex: 1;
  background: #f0f4f8;
  border: 1px solid #d0d7e2;
  padding: 1rem;
  border-radius: 6px;
  text-align: center;
}
.label {
  font-weight: 600;
  color: #555;
}
.value {
  color: #222;
  display: block;
  margin-top: 0.3rem;
}
.action-buttons {
  display: flex;
  justify-content: flex-end;
  gap: 1rem;
  margin-bottom: 1rem;
}
.detail-section {
  background-color: #fefefe;
  border: 1px solid #e1e4ea;
  padding: 1.5rem;
  margin-bottom: 2rem;
  border-radius: 6px;
  box-shadow: 0 2px 4px rgba(0,0,0,0.04);
}
.section-title {
  font-size: 1.2rem;
  font-weight: bold;
  margin-bottom: 1rem;
  color: #2b4c7e;
  border-left: 4px solid #2b4c7e;
  padding-left: 0.6rem;
}
.info-grid {
  display: grid;
  grid-template-columns: 150px 1fr;
  row-gap: 0.6rem;
  column-gap: 1.5rem;
}
.no-cert-alert {
  text-align: center;
  background: #fff3cd;
  color: #856404;
  padding: 1rem;
  border-radius: 4px;
  margin-bottom: 2rem;
  font-weight: 500;
}
.footer-buttons {
  display: flex;
  justify-content: center;
  gap: 1rem;
  margin-top: 1.5rem;
}

/* 버튼 공통 스타일 */
.btn {
  padding: 0.6rem 1.2rem;
  font-weight: 600;
  font-size: 0.95rem;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  text-align: center;
}

/* 개별 색상 정의 */
.btn-add {
  background-color: #1f8249;
  color: white;
}
.btn-delete {
  background-color: #d9534f;
  color: white;
  padding: 0.6rem 1.2rem;
}
.btn-back {
  background-color: #2b4c7e;
  color: white;
  text-decoration: none;
  padding: 0.6rem 1.2rem;
  border-radius: 5px;
  font-weight: 600;
  font-size: 0.95rem;
  line-height: 2.2rem;
}

.info-table {
  border-top: 1px solid #ccc;
  border-left: 1px solid #ccc;
  border-radius: 4px;
  overflow: hidden;
}
.info-row {
  display: flex;
  border-right: 1px solid #ccc;
  border-bottom: 1px solid #ccc;
}
.info-row.alt {
  background-color: #f8f9fa;
}
.info-label {
  width: 160px;
  padding: 0.75rem;
  background-color: #f0f4f8;
  font-weight: bold;
  color: #2b4c7e;
  border-right: 1px solid #ccc;
}
.info-value {
  flex: 1;
  padding: 0.75rem;
  color: #333;
}
</style>
