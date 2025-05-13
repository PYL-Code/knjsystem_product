<template>
  <div class="detail-container">
    <h2 class="detail-title"><strong>바코드 상세 조회</strong></h2>

    <!-- 사용자 정보 -->
    <div class="user-info" v-if="userInfo">
      <div class="user-card" v-for="(value, label) in userLabels" :key="label">
        <span class="label">{{ label }}</span>
        <span class="value">{{ value || '-' }}</span>
      </div>
    </div>

    <!-- 인증정보 버튼 (단일상품용) -->
    <div v-if="isSingle" class="action-buttons">
      <button v-if="!cert" @click="goToCertUpdate" class="btn btn-add">인증정보 추가</button>
      <button v-if="cert" @click="deleteCert" class="btn btn-add">인증정보 삭제</button>
    </div>

    <!-- 바코드 정보 -->
    <div class="detail-section" v-if="barcode">
      <div class="section-title">상품 바코드 정보</div>
      <div class="info-table">
        <div class="info-row" v-for="(value, label) in barcodeLabels" :key="label">
          <div class="info-label">{{ label }}</div>
          <div class="info-value">{{ value }}</div>
        </div>
      </div>
    </div>

    <!-- 인증 정보 (단일상품) -->
    <div class="detail-section" v-if="isSingle && cert">
      <div class="section-title">인증 정보</div>
      <div class="info-table">
        <div class="info-row" v-for="(value, label) in certLabels" :key="label">
          <div class="info-label">{{ label }}</div>
          <div class="info-value">{{ value }}</div>
        </div>
      </div>
    </div>
    <div v-else-if="isSingle" class="no-cert-alert">인증정보를 추가해 주세요.</div>

    <!-- 구성상품 관리 (혼합상품) -->
    <div v-if="isMixed" class="detail-section">
      <div class="section-title">구성상품 관리</div>

      <div class="mix-action">
        <button class="btn btn-add" @click="showForm = !showForm">구성상품 추가</button>
      </div>

      <div class="mix-add-form" v-if="showForm">
        <input v-model="newItemName" type="text" placeholder="구성상품명 입력" />
        <button class="btn btn-add" @click="addProductItem">등록</button>
      </div>

      <div v-if="productItems.length > 0">
        <div class="mix-list" v-for="item in productItems" :key="item.g002Id">
          <div class="mix-header">
            <strong>{{ item.itemName }}</strong>
            <button class="btn btn-delete" @click="deleteProductItem(item.g002Id)" :disabled="item.hasCert">삭제</button>
          </div>

          <div class="mix-cert-add">
            <input v-model="item.newCertId" type="number" placeholder="인증 ID 입력" />
            <button class="btn btn-add" @click="addCertToProduct(item.g002Id, item.newCertId)">인증 추가</button>
          </div>

          <table class="cert-table">
            <thead>
            <tr>
              <th>인증번호</th>
              <th>대표품목</th>
              <th>소속농가</th>
              <th>품목</th>
              <th>삭제</th>
            </tr>
            </thead>
            <tbody>
            <tr v-for="cert in item.certs" :key="cert.certId">
              <td>{{ cert.stdCertNo }}</td>
              <td>{{ cert.repItemName }}</td>
              <td>{{ cert.frmrNm }}</td>
              <td>{{ cert.itemName }}</td>
              <td>
                <button class="btn btn-delete" @click="deleteCertFromProduct(item.g002Id, cert.certId)">삭제</button>
              </td>
            </tr>
            </tbody>
          </table>
        </div>
      </div>
      <div v-else class="no-product-alert">
        구성상품을 추가해 주세요.
      </div>

    </div>

    <!-- 하단 버튼 -->
    <div class="footer-buttons">
      <button class="btn btn-delete" @click.prevent="deleteBarcode">바코드 삭제</button>
      <router-link to="/barcode/list" class="btn btn-back">바코드 목록</router-link>
    </div>
  </div>
</template>
<script setup>
import { ref, onMounted, computed } from 'vue';
import axios from 'axios';
import { useRoute, useRouter } from 'vue-router';
import { getToken } from '@/utils/auth';

const route = useRoute();
const router = useRouter();
const token = getToken();
const config = { headers: { Authorization: `Bearer ${token}` } };

const barcode = ref(null);
const cert = ref(null);
const userInfo = ref(null);
const productItems = ref([]);
const newItemName = ref('');
const showForm = ref(false);

const isSingle = computed(() => barcode.value?.productType === '단일상품');
const isMixed = computed(() => barcode.value?.productType === '혼합상품');

const userLabels = computed(() => ({
  '업체명': userInfo.value?.companyName,
  '사업자등록번호': userInfo.value?.bnsNo,
  'GLN(업체코드)': userInfo.value?.company880Code
}));

const barcodeLabels = computed(() => ({
  '바코드번호': barcode.value?.barcode,
  '상품명': barcode.value?.barcodeName,
  '상품유형': barcode.value?.productType
}));

const certLabels = computed(() => ({
  '인증번호': cert.value?.stdCertNo,
  '인증구분': cert.value?.certGbNm,
  '인증기간': `${cert.value?.certStartDate} ~ ${cert.value?.certEndDate}`,
  '인증상태': cert.value?.certStateNm,
  '생산자단체명': cert.value?.prdrGrpNm,
  '개인/단체': cert.value?.psnGroupNm,
  '대표품목명': cert.value?.repItemNm,
  '품목명': cert.value?.itemNm,
  '소속농가명': cert.value?.frmrNm
}));

onMounted(async () => {
  const barcodeId = route.query.barcodeId;
  try {
    const userRes = await axios.get('/api/list/member', config);
    userInfo.value = userRes.data;

    const { data: barcodeData } = await axios.get('/api/barcode/detail', { params: { barcodeId }, ...config });
    barcode.value = barcodeData;

    if (isSingle.value) {
      const { data: certData } = await axios.get('/api/cert/barcode/detail', {
        params: { seqNoA004: barcodeId }, ...config
      });
      cert.value = certData;
    }

    if (isMixed.value) {
      const { data: g002List } = await axios.get('/api/barcode/mix/product/list', {
        params: { barcodeId }, ...config
      });

      productItems.value = g002List.map(item => ({
        ...item,
        certs: item.certs || [],
        newCertId: '',
        hasCert: Array.isArray(item.certs) && item.certs.length > 0
      }));
    }
  } catch (err) {
    console.error('데이터 조회 실패:', err);
  }
});

const goToCertUpdate = () => {
  const barcodeId = route.query.barcodeId;
  router.push({ path: '/barcode/update/cert', query: { barcodeId } });
};

const deleteCert = async () => {
  const barcodeId = route.query.barcodeId;
  const barcodeNo = barcode.value?.barcode;
  const productType = barcode.value?.productType;
  if (!confirm('정말 인증정보를 삭제하시겠습니까?')) return;
  await axios.post('/api/barcode/delete/cert/of/barcode', null, {
    params: { barcodeId, barcodeNo, productType }, ...config
  });
  cert.value = null;
};

const deleteBarcode = async () => {
  const barcodeNo = barcode.value?.barcode;
  if (!barcodeNo || cert.value) return alert('삭제 불가');
  if (!confirm('바코드를 삭제하시겠습니까?')) return;
  await axios.delete('/api/barcode/delete', { params: { barcode: barcodeNo }, ...config });
  router.push('/barcode/list');
};

const addProductItem = async () => {
  if (!newItemName.value) return alert('상품명을 입력해주세요');
  const payload = { g001Id: barcode.value.g001Id, itemName: newItemName.value };
  await axios.post('/api/barcode/mix/product/add/item', payload, config);
  showForm.value = false;
  location.reload();
};

const deleteProductItem = async (g002Id) => {
  const item = productItems.value.find(i => i.g002Id === g002Id);
  if (item.hasCert) return alert('인증정보가 있는 구성상품은 삭제할 수 없습니다.');
  if (!confirm('정말 삭제하시겠습니까?')) return;
  await axios.delete('/api/barcode/mix/product/delete', { params: { g002Id }, ...config });
  location.reload();
};

const addCertToProduct = async (g002Id, certId) => {
  if (!certId) return;
  await axios.post('/api/barcode/mix/product/insert/cert', null, {
    params: { g002Id, certId }, ...config
  });
  location.reload();
};

const deleteCertFromProduct = async (g002Id, certId) => {
  await axios.delete('/api/barcode/mix/product/delete/cert', {
    params: { g002Id, certId }, ...config
  });
  location.reload();
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
.btn {
  padding: 0.6rem 1.2rem;
  font-weight: 600;
  font-size: 0.95rem;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  text-align: center;
}
.btn-add {
  background-color: #1f8249;
  color: white;
}
.btn-delete {
  background-color: #d9534f;
  color: white;
}
.btn-back {
  background-color: #2b4c7e;
  color: white;
  text-decoration: none;
}
.mix-action {
  display: flex;
  justify-content: flex-end;
  margin-bottom: 1rem;
}
.mix-add-form {
  display: flex;
  gap: 1rem;
  margin-bottom: 1.5rem;
}
.mix-add-form input {
  flex: 1;
  padding: 0.6rem;
  border: 1px solid #ccc;
  border-radius: 4px;
}
.mix-list {
  margin-bottom: 2rem;
  border: 1px solid #ccc;
  padding: 1rem;
  border-radius: 6px;
  background-color: #f9f9f9;
}
.mix-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 1rem;
  font-weight: bold;
  font-size: 1.1rem;
}
.mix-cert-add {
  display: flex;
  gap: 0.5rem;
  margin-bottom: 1rem;
}
.mix-cert-add input {
  flex: 1;
  padding: 0.5rem;
  border: 1px solid #ccc;
  border-radius: 4px;
}
.cert-table {
  width: 100%;
  border-collapse: collapse;
}
.cert-table th,
.cert-table td {
  padding: 0.6rem;
  border: 1px solid #ddd;
  text-align: center;
}
.cert-table thead {
  background-color: #f0f4f8;
}
</style>
