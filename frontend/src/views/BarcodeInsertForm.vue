<template>
  <div class="insert-container">
    <h2 class="insert-title"><strong>바코드 등록</strong></h2>

    <!-- 👤 사용자 정보 -->
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

    <!-- 공통 입력 -->
    <div class="form-group">
      <label class="form-label">상품 바코드<span class="required">*</span></label>
      <input type="text" v-model="form.barcodeNo" />
    </div>
    <div class="form-group">
      <label class="form-label">상품명<span class="required">*</span></label>
      <input type="text" v-model="form.barcodeName" />
    </div>

    <!-- 상품유형 -->
    <div class="form-group">
      <label class="form-label">상품유형<span class="required">*</span></label>
      <div class="radio-group">
        <label><input type="radio" value="단일상품" v-model="form.productType" /> 단일상품</label>
        <label><input type="radio" value="혼합상품" v-model="form.productType" /> 혼합상품</label>
      </div>
    </div>

    <!-- 버튼 -->
    <div class="form-actions">
      <button class="btn btn-submit" @click="submit">확인</button>
      <router-link to="/barcode/list" class="btn btn-cancel">취소</router-link>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import axios from 'axios';
import { useRouter } from 'vue-router';
import { getToken } from '@/utils/auth';

const router = useRouter();
const userInfo = ref(null);

const form = ref({
  productType: '단일상품',
  barcodeNo: '',
  barcodeName: '',
  producerCompanyName: '',
  itemName: ''
});

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

const submit = async () => {
  if (!form.value.barcodeNo || !form.value.barcodeName) {
    alert('바코드번호와 상품명은 필수 입력 항목입니다.');
    return;
  }

  const token = getToken();
  const payload = {
    userNo: userInfo.value.no,
    barcodeNo: form.value.barcodeNo,
    barcodeName: form.value.barcodeName,
    productType: form.value.productType
  };

  if (form.value.productType === '혼합상품') {
    payload.producerCompanyName = form.value.producerCompanyName;
    payload.itemName = form.value.itemName;
  }

  try {
    await axios.post('/api/barcode/insert', payload, {
      headers: { Authorization: `Bearer ${token}` }
    });
    alert('바코드가 등록되었습니다.');
    router.push('/barcode/list');
  } catch (error) {
    console.error('등록 실패:', error);
    alert('등록에 실패했습니다.');
  }
};

onMounted(fetchUserInfo);
</script>

<style scoped>
.insert-container {
  max-width: 800px;
  margin: auto;
  padding: 2rem;
  font-family: 'Segoe UI', sans-serif;
  color: #333;
}

.insert-title {
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
  border-radius: 6px;
  padding: 1rem;
  text-align: center;
}

.label {
  font-weight: bold;
  color: #555;
}

.value {
  display: block;
  margin-top: 0.3rem;
  color: #222;
}

.form-group {
  margin-bottom: 2rem;
}

.form-label {
  font-size: 1.05rem;
  font-weight: 700;
  color: #2b4c7e;
  margin-bottom: 0.5rem;
  display: block;
  border-left: 4px solid #2b4c7e;
  padding-left: 0.6rem;
}

.required {
  color: red;
  margin-left: 4px;
}

input[type="text"] {
  width: 100%;
  padding: 0.75rem 1rem;
  border: 1px solid #ccc;
  border-radius: 6px;
  background-color: #f9fbfd;
  box-shadow: inset 0 1px 2px rgba(0, 0, 0, 0.05);
  transition: border-color 0.2s ease;
}

input[type="text"]:focus {
  border-color: #2b4c7e;
  outline: none;
}

.radio-group {
  display: flex;
  gap: 2rem;
  padding-left: 0.2rem;
}

.radio-group label {
  font-weight: 500;
  color: #444;
}

input[type="radio"] {
  margin-right: 6px;
}

.form-actions {
  display: flex;
  justify-content: flex-end;
  gap: 1rem;
  margin-top: 2rem;
}

/* ✅ 버튼 통일 스타일 */
.btn {
  padding: 0.6rem 1.4rem;
  font-weight: 600;
  font-size: 0.95rem;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  text-align: center;
}

.btn-submit {
  background-color: #1f8249;
  color: white;
}

.btn-cancel {
  background-color: #2b4c7e;
  color: white;
  text-decoration: none;
  line-height: 1.8rem;
}
</style>
