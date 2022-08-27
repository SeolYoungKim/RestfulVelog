<script setup lang="ts">
import {defineProps, ref} from "vue";
// const count = ref(0) // 반응형 변수
import axios from "axios";
import {useRouter} from "vue-router";

const router = useRouter();

const post = ref({
  id: 0,
  title: "",
  text: "",
});

const props = defineProps({
  postId: {
    type: [Number, String],
    require: true,
  }
});

axios.get(`/api/article/${props.postId}`).then((response) => {
  console.log(response);
  post.value = response.data;
})

const edit = () => {
  axios.patch(`/api/article/${props.postId}`,
      post.value) // 넘겨주는 data
      .then(() => {
        router.replace({ name: "home" })
      })
}

</script>

<template>
  <div>
    <el-input v-model="post.title" />
  </div>
  <div class="mt-2">
    <el-input v-model="post.text" type="textarea"  rows="15" />
  </div>
  <div class="mt-2 d-flex justify-content-end">
    <el-button type="warning" @click="edit()">수정완료</el-button>
  </div>
</template>

<style>

</style>