<script setup lang="ts">
import {ref} from "vue";
// const count = ref(0) // 반응형 변수
import axios from "axios";
import {useRouter} from "vue-router";

const title = ref("");
const text = ref("");

const router = useRouter();

const write = function () {
  axios.post("/api/write", {
    title: title.value,
    text: text.value,
  })
      .then(() => {
        router.replace({
          name: "home", // 라우터의 home으로 이동.
        })
      })
};
</script>

<template>
<!--<p>안녕하세요</p> <button @click="count += 1">버튼을 눌러주세요</button>-->
<!--<p>{{count}}</p>-->
  <div>
    <el-input v-model="title" placeholder="제목을 입력해주세요." />
  </div>
  <div class="mt-2">
    <el-input v-model="text" type="textarea"  rows="15" />
  </div>
  <div class="mt-2">
    <div class="d-flex justify-content-end">
      <el-button type="primary" @click="write()">글 작성 완료</el-button>
    </div>
  </div>
</template>

<style>

</style>