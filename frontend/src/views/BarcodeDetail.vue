<template>
  <div class="container mt-5">
    <h2 class="mb-4 text-center">바코드 상세 조회</h2>

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

    <button class="mb-2 btn btn-primary" v-if="!cert" @click="goToCertUpdate">인증정보 추가</button>
    <button class="mb-2 btn btn-primary" v-if="cert" @click="deleteCert" >인증정보 삭제</button>

    <!-- 바코드 정보 -->
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
    <div v-else class="alert alert-warning text-center">
      인증정보를 추가해 주세요.
    </div>

    <button class="mt-2 me-2 btn btn-danger" @click.prevent="deleteBarcode">바코드 삭제</button>
    <a href="/barcode/list" class="mt-2 btn btn-primary">바코드 목록</a>

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
  // console.log("➡️ 넘어온 barcodeId:", barcodeId);

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
    // console.log('✅ 받은 barcode 데이터:', barcodeData);
    barcode.value = barcodeData;

    const { data: certData } = await axios.get('/api/cert/barcode/detail', {
      params: { seqNoA004: barcodeId }, // 여기에서 seqNoA004를 그대로 쓰는 게 맞는지 확인 필요
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

const deleteBarcode = async () => {
  const token = getToken();
  const barcodeNo = barcode.value?.barcode;

  if (!barcodeNo) {
    alert('바코드 정보가 없습니다.');
    return;
  }

  // 인증정보가 존재하면 삭제 불가
  if (cert.value) {
    alert('인증정보가 있는 바코드는 삭제할 수 없습니다.');
    return;
  }

  // 사용자 확인
  if (!confirm('정말 이 바코드를 삭제하시겠습니까?')) {
    return;
  }

  try {
    await axios.delete('/api/barcode/delete', {
      params: { barcode: barcodeNo },
      headers: { Authorization: `Bearer ${token}` }
    });
    alert('✅ 바코드가 성공적으로 삭제되었습니다.');
    router.push('/barcode/list');
  } catch (error) {
    console.error('바코드 삭제 실패:', error);
    alert('❌ 바코드 삭제 중 오류가 발생했습니다.');
  }
};

const deleteCert = async () => {
  const token = getToken();
  const barcodeId = route.query.barcodeId;

  if (!barcodeId) {
    alert('바코드 ID가 없습니다.');
    return;
  }

  if (!confirm('정말 이 바코드의 인증정보를 삭제하시겠습니까?')) {
    return;
  }

  try {
    await axios.post(
        '/api/barcode/delete/cert/of/barcode',
        null,
        {
          params: { barcodeId },
          headers: { Authorization: `Bearer ${token}` }
        }
    );
    alert('✅ 인증정보가 성공적으로 삭제되었습니다.');
    // 새로고침 또는 cert 다시 불러오기
    cert.value = null;
  } catch (err) {
    console.error('인증정보 삭제 실패:', err);
    alert('❌ 인증정보 삭제 중 오류가 발생했습니다.');
  }
};

</script>
