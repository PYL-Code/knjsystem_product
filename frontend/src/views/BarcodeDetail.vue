<template>
  <div class="container mt-5">
    <h2>📄 바코드 상세 조회</h2>

    <!-- 사용자 정보 -->
    <div class="row mb-4" v-if="userInfo">
      <div class="col-md-4 text-center">
        <div class="card p-2">
          <strong>업체명</strong><br />{{ userInfo.companyName }}
        </div>
      </div>
      <div class="col-md-4 text-center">
        <div class="card p-2">
          <strong>사업자등록번호</strong><br />{{ userInfo.bnsNo }}
        </div>
      </div>
      <div class="col-md-4 text-center">
        <div class="card p-2">
          <strong>GLN(업체코드)</strong><br />{{ userInfo.company880Code || '-' }}
        </div>
      </div>
    </div>

    <!-- 상품 바코드 정보 -->
    <div class="card mb-3 p-3" v-if="barcode">
      <h5>상품 바코드 정보</h5>
      <p><strong>바코드번호:</strong> {{ barcode.barcode }}</p>
      <p><strong>상품명:</strong> {{ barcode.barcodeName }}</p>
      <p><strong>상품유형:</strong> {{ barcode.productType }}</p>
    </div>

    <!-- 인증 정보 -->
    <div class="card p-3" v-if="cert">
      <h5>인증 정보</h5>
      <p><strong>인증번호:</strong> {{ cert.stdCertNo }}</p>
      <p><strong>인증구분:</strong> {{ cert.certGbNm }}</p>
      <p><strong>인증기간:</strong> {{ cert.certStartDate }} ~ {{ cert.certEndDate }}</p>
      <p><strong>인증상태:</strong> {{ cert.certStateNm }}</p>
      <p><strong>생산자단체명:</strong> {{ cert.prdrGrpNm }}</p>
      <p><strong>개인/단체:</strong> {{ cert.psnGroupNm }}</p>
      <p><strong>대표품목명:</strong> {{ cert.repItemNm }}</p>
      <p><strong>품목명:</strong> {{ cert.itemNm }}</p>
      <p><strong>소속농가명:</strong> {{ cert.frmrNm }}</p>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import axios from 'axios';
import { useRoute } from 'vue-router';
import { getToken } from '@/utils/auth';

const route = useRoute();
const barcode = ref(null);
const cert = ref(null);
const userInfo = ref(null);

onMounted(async () => {
  const token = getToken();
  const seqNoA004 = route.query.seqNoA004;

  const config = {
    headers: { Authorization: `Bearer ${token}` }
  };

  try {
    // 사용자 정보
    const userRes = await axios.get('/api/list/member', config);
    userInfo.value = userRes.data;

    // 바코드 정보
    const { data: barcodeData } = await axios.get('/api/barcode/detail', {
      params: { seqNoA004 },
      ...config
    });
    barcode.value = barcodeData;

    // 인증 정보 (PK는 barcode에서 가져온 seqNoA003 사용)
    const { data: certData } = await axios.get('/api/cert/barcode/detail', {
      params: { seqNoA003: barcodeData.barcodeId }, // ❗ 실제 필드는 DTO 구조에 따라 조정
      ...config
    });
    cert.value = certData;
  } catch (err) {
    console.error('조회 실패:', err);
  }
});
</script>
